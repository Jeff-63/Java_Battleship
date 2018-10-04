package org.isi.testBattleship.conteneurs;

import java.awt.Color;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class BattleshipJButton extends JButton {

	private int id;
	private boolean vide;
	
	public BattleshipJButton(int id) {
		this.id = id;
		this.vide = true;
		this.setBackground(Color.lightGray);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isVide() {
		return vide;
	}
	public void setVide(boolean vide) {
		this.vide = vide;
	}
}
