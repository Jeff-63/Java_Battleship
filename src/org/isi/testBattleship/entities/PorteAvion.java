package org.isi.testBattleship.entities;

import java.util.ArrayList;

public class PorteAvion extends Bateau {
	public ArrayList<Integer> positionsPA = new ArrayList<>();
	private Boolean estEnvie;
	
	public Boolean getEstEnvie() {
		return estEnvie;
	}

	public void setEstEnvie(Boolean estEnvie) {
		this.estEnvie = estEnvie;
	}

	public ArrayList<Integer> getPositionsPA() {
		return positionsPA;
	}

	public void genererPA() {
		int posInit;
		int longueur = 4;
		double direction = Math.floor(Math.random() * 2);
		Bateau porteAvion = new Bateau();
		
		if (direction == 1) { // horizontal
			posInit = porteAvion.genererNumero(longueur);
			for (int i = 0; i < longueur; i++) {
				positionsPA.add(posInit + i);
			}
		} else { // Vertical
			posInit = (int) Math.floor(Math.random() * 80);
			for (int i = 0; i < longueur; i++) {
				positionsPA.add(posInit);
				posInit = posInit + porteAvion.getSize();
			}
		}
		
	}
	
	public void ajouterTableau(ArrayList<ArrayList<Integer>> tabBateaux) {
		tabBateaux.add(positionsPA);
	}
}
