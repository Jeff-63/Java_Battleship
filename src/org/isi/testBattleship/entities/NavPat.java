package org.isi.testBattleship.entities;

import java.util.ArrayList;

public class NavPat extends Bateau {
	public ArrayList<Integer> positionsNavPat = new ArrayList<>();
	private Boolean estEnvie;
	
	public Boolean getEstEnvie() {
		return estEnvie;
	}

	public void setEstEnvie(Boolean estEnvie) {
		this.estEnvie = estEnvie;
	}
	
	public ArrayList<Integer> getPositionsNavPat() {
		return positionsNavPat;
	}

	public void genererNavPat() {
		int posInit;
		int longueur = 2;
		double direction = Math.floor(Math.random() * 2);
		Bateau navPat = new Bateau();
		
		if (direction == 1) { // horizontal
			posInit = navPat.genererNumero(longueur);
			for (int i = 0; i < longueur; i++) {
				positionsNavPat.add(posInit + i);
			}
		} else { // Vertical
			posInit = (int) Math.floor(Math.random() * 80);
			for (int i = 0; i < longueur; i++) {
				positionsNavPat.add(posInit);
				posInit = posInit + navPat.getSize();
			}
		}
		
	}
	
	public void ajouterTableau(ArrayList<ArrayList<Integer>> tabBateaux) {
		tabBateaux.add(positionsNavPat);
	}
}
