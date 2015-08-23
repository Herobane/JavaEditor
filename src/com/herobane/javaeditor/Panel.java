package com.herobane.javaeditor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

public class Panel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static boolean quitSignal = false;
	public static String selectedText;
	public static String content;
	public static String fileName = "Untitled";
	
	private File selectedFile;
	private String ligne = "";
	
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	//MenuBar
	JMenuBar menuBar = new JMenuBar();
	
	//Menus
	JMenu file = new JMenu("Fichier");
	JMenu edit = new JMenu("Edition");
	JMenu preferences = new JMenu("Préférences");
	
	//Menu items for file menu
	JMenuItem newf = new JMenuItem("Nouveau");
	JMenuItem openf = new JMenuItem("Ouvrir");
	JMenuItem save = new JMenuItem("Enregistrer");
	JMenuItem saveas = new JMenuItem("Enregistrer Sous");
	JMenuItem quit = new JMenuItem("Quitter");
	
	//Menu items for edit menu
	JMenuItem selectall = new JMenuItem("Tout Sélectionner");
	JMenuItem copy = new JMenuItem("Copier");
	JMenuItem cut = new JMenuItem("Couper");
	JMenuItem paste = new JMenuItem("Coller");
	
	//Menu items for preferences menu
	JMenuItem fontselection = new JMenuItem("Police");
	
	public static JTextArea textbox = new JTextArea();
	public static JScrollPane jsp = new JScrollPane(textbox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JFileChooser fc = new JFileChooser();
	
	FileFilter ff = new FileFilter() {

		public boolean accept(File f) {
			if(f.getName().endsWith(".txt")) {
				return true;
			} else {
				return false;
			}
		}

		public String getDescription() {
			return "Text Files (*.txt)";
		}
		
	};

	public Panel() {
		
		Data datas = new Data();
		
		this.setLayout(new BorderLayout());
		this.add(menuBar, BorderLayout.NORTH);
		this.add(jsp, BorderLayout.CENTER);
		
		textbox.setTabSize(3);
		textbox.setFont(new Font(datas.fontName, datas.fontStyle, datas.fontSize));
		
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(preferences);
		
		file.add(newf);
		file.add(openf);
		file.add(save);
		file.add(saveas);
		file.add(quit);
		
		edit.add(selectall);
		edit.add(copy);
		edit.add(cut);
		edit.add(paste);
		
		preferences.add(fontselection);
		
		fc.setFileFilter(ff);
		
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				quitSignal = true;
			}
		});
		
		openf.addActionListener(new ActionListener(){
			@SuppressWarnings({ "resource" })
			public void actionPerformed(ActionEvent e) {
				fc.showOpenDialog(fc);
				
				selectedFile = fc.getSelectedFile();
				
				if(selectedFile != null) {
					textbox.setText("");
					fileName = selectedFile.getAbsolutePath();
					try {
						InputStream is = new FileInputStream(selectedFile);
						InputStreamReader ipsr = new InputStreamReader(is);
						BufferedReader bf = new BufferedReader(ipsr);
						
						while((ligne = bf.readLine()) != null) {
							textbox.setText(textbox.getText() + ligne + "\n");
						}
					} catch(FileNotFoundException fnfe) {
						System.err.println("Error : FileNotFoundException");
					} catch(IOException ioe) {
						System.err.println("Error : IOException");
					}
				}
			}
		});
		
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					File file1 = new File(fileName);
					FileWriter fw = new FileWriter(file1);
					BufferedWriter bw = new BufferedWriter(fw);
					BufferedReader br2 = new BufferedReader(new StringReader(textbox.getText()));
					
					while((ligne = br2.readLine()) != null) {
						bw.write(ligne);
						bw.newLine();
					}
					
					bw.close();
				} catch(IOException ioe) {
					System.err.println("Error : IOException");
				}
			}
		});
		
		saveas.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				fc.showSaveDialog(fc);
				
				try {
					File savedFile = new File(fc.getSelectedFile().getAbsolutePath());
					fileName = fc.getSelectedFile().getAbsolutePath();
					FileWriter fw = new FileWriter(savedFile.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					BufferedReader br2 = new BufferedReader(new StringReader(textbox.getText()));
					
					while((ligne = br2.readLine()) != null) {
						bw.write(ligne);
						bw.newLine();
					}
					
					bw.close();
					
				} catch(IOException ioe) {
					System.err.println("Error : IOException");
				} catch(NullPointerException npe) {
					
				}
			}
		});
		
		selectall.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				textbox.selectAll();
			}
		});
		
		copy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				selectedText = textbox.getSelectedText();
				StringSelection selection = new StringSelection(selectedText);
				clipboard.setContents(selection, selection);
			}
		});
		
		cut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				selectedText = textbox.getSelectedText();
				textbox.replaceSelection("");
				StringSelection selection = new StringSelection(selectedText);
				clipboard.setContents(selection, selection);
			}
		});
		
		paste.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				content = getClipBoardContent();
				textbox.insert(content, textbox.getCaretPosition());
			}
		});
		
		newf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				textbox.setText("");
				fileName = "Untitled";
			}
		});
		
		fontselection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new FontDialog();
			}
		});
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
			SwingUtilities.updateComponentTreeUI(fc);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getClipBoardContent() {
		String result = "";
		Clipboard clipbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipbrd.getContents(null);
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		
		if(hasTransferableText) {
			try {
				result = (String)contents.getTransferData(DataFlavor.stringFlavor);
			} catch(UnsupportedFlavorException | IOException ufe) {
				System.err.println("Error : An arror has occured");
			}
		}
		
		return result;
	}
	
}