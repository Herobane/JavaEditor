package com.herobane.javaeditor;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FontDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static String[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	public static String[] availableFontStyle = {
			"Gras",
			"Italique",
			"Basique"
	};
	public static String[] availableSize;
	
	private String fontName = "Arial";
	private int fontSize = 12;
	private int fontStyle = Font.PLAIN;

	JComboBox<String> fonts = new JComboBox<String>(fontList);
	JComboBox<String> style = new JComboBox<String>(availableFontStyle);
	
	JSpinner sizes = new JSpinner();
	SpinnerNumberModel model = new SpinnerNumberModel(5, 5, 90, 1);
	
	GridLayout gl = new GridLayout(6, 3);
	
	public FontDialog() {
		this.setVisible(true);
		this.setTitle("Choisissez la Police");
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(gl);
		this.add(fonts);
		this.add(style);
		this.add(sizes);
		fonts.setSelectedIndex(1);
		fonts.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fontName = fonts.getSelectedItem().toString();
				Panel.textbox.setFont(new Font(fontName, fontStyle ,fontSize));
			}
		});
		style.setSelectedIndex(2);
		style.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				switch(style.getSelectedItem().toString()) {
					case "Gras":
						fontStyle = Font.BOLD;
						break;
					case "Italique":
						fontStyle = Font.ITALIC;
						break;
					case "Basique":
						fontStyle = Font.PLAIN;
				}
				
				Panel.textbox.setFont(new Font(fontName, fontStyle ,fontSize));
			}
		});
		
		sizes.setModel(model);
		
		sizes.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				try {
					fontSize = (int) sizes.getValue();
					Panel.textbox.setFont(new Font(fontName, fontStyle, fontSize));
				} catch(Exception exception) {
					System.out.println("La taile doit etre un nombre entier");
				}
			}
		});
		
	}
}
