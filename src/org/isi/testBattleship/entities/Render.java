package org.isi.testBattleship.entities;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class Render {

	public static void renderTouche(JButton caseGrille)
	{
		ImageIcon img=null;
		try {
			img = new ImageIcon(ImageIO.read(new File("images/touch.gif")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image image = img.getImage();
		Image newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		caseGrille.setIcon(new ImageIcon(newImg));
	}

	public static void renderRate(JButton caseGrille)
	{
		ImageIcon img=null;
		try {
			img = new ImageIcon(ImageIO.read(new File("images/miss3.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image image = img.getImage();
		Image newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		caseGrille.setIcon(new ImageIcon(newImg));
	}

	public static void reRender(JFrame frame)
	{
		frame.validate();
		frame.repaint();
	}
	
}
