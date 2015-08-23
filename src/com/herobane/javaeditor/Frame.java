package com.herobane.javaeditor;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	Panel panel = new Panel();
	KeyListeners kl = new KeyListeners();
	
	public static int width;
	public static int height;
	
	public Frame() {
		
		Data datas = new Data();
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("JavaEditor");
		this.setSize(datas.width, datas.height);
		this.setLocationRelativeTo(null);
		this.setContentPane(panel);
		this.addKeyListener(kl);
		
		panel.updateUI();
			
		searchEvent();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	private void searchEvent() {
		while(this.isShowing()) {
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				System.err.println("Error : InterruptedException");
			}
			
			this.setTitle(Panel.fileName + " - JavaEditor");
			width = this.getSize().width;
			height = this.getSize().height;
			
			if(Panel.quitSignal) {
				this.dispose();
			}
		}
	}
	
}