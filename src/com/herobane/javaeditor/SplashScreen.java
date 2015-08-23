package com.herobane.javaeditor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JWindow;

public class SplashScreen  {
	
	public static BufferedImage image = null;

	public SplashScreen() {
		try {
			image = ImageIO.read(new File("SplashScreen.png"));
		} catch (IOException ioe) {
			System.err.println("Error : IOException");
		}
		
		JWindow splashscreen = new JWindow(){

			private static final long serialVersionUID = 1L;
			
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, null);
			}
		};
		
		splashscreen.setVisible(true);
		splashscreen.setSize(image.getWidth(), image.getHeight());
		splashscreen.setLocationRelativeTo(null);
		
		try {
			new Frame();
			Thread.sleep(3000);
			splashscreen.dispose();
		} catch(InterruptedException ie) {
			System.err.println("Error : InterruptedException");
		}
	}
	
}
