package org.isi.testBattleship.entities;

import java.util.ArrayList;

public class TireIntello {
	private int dernierTire;
	private int guess;
	NumberBattleship nb = new NumberBattleship();
	
	public int getDernierTire() {
		return dernierTire;
	}

	public void setDernierTire(int dernierTire) {
		this.dernierTire = dernierTire;
	}

	public int getGuess() {
		return guess;
	}

	public void setGuess(int guess) {
		this.guess = guess;
	}
	
	public int tireAleatoire() {
		int tire = nb.random(0, 98);
		setDernierTire(tire);
		setGuess(tire);
		return tire;
	}
	
	public Boolean verifierTire(ArrayList<Integer> coord, int tire) {
		if (nb.nombreDansArray(coord, tire) == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int continuerTire(int tire, int direction) {
		switch (direction) {
		case 1: 
			if (tire > 9) {
				return tire - 10;
			}
			else {
				break;
			}
		case 2:
			if (tire < 99) {
				return tire + 1;
			}
			else {
				break;
			}
		case 3:
			if (tire < 90) {
				return tire + 10;
			}
			else {
				break;
			}
		case 4:
			if(tire > 0) {
				return tire - 1;
			}
			else {
				break;
			}
		}
		return -1;
		
	}
	
	public int nouvelleDirection(int direction) {
		switch (direction) {
			case 1: 
				return 2;
			case 2:
				return 3;
			case 3:
				return 4;
			case 4:
				return -1;
		}
		return -1;
		
		
	}
}
