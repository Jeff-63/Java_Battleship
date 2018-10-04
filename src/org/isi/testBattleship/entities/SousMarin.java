package org.isi.testBattleship.entities;

import java.util.ArrayList;

public class SousMarin extends Bateau {
	public ArrayList<Integer> positionsSM = new ArrayList<>();
	private Boolean estEnvie;
	
	public Boolean getEstEnvie() {
		return estEnvie;
	}

	public void setEstEnvie(Boolean estEnvie) {
		this.estEnvie = estEnvie;
	}

	public ArrayList<Integer> getPositionsSM() {
		return positionsSM;
	}

	public void genererSM() {
		int posInit;
		int longueur = 3;
		double direction = Math.floor(Math.random() * 2);
		Bateau sousMarin = new Bateau();
		
		if (direction == 1) { // horizontal
			posInit = sousMarin.genererNumero(longueur);
			for (int i = 0; i < longueur; i++) {
				positionsSM.add(posInit + i);
			}
		} else { // Vertical
			posInit = (int) Math.floor(Math.random() * 80);
			for (int i = 0; i < longueur; i++) {
				positionsSM.add(posInit);
				posInit = posInit + sousMarin.getSize();
			}
		}
		
	}
	
	public void ajouterTableau(ArrayList<ArrayList<Integer>> tabBateaux) {
		tabBateaux.add(positionsSM);
	}
}
