package org.isi.testBattleship.entities;

import java.util.ArrayList;

public class Destroyer extends Bateau {
	public ArrayList<Integer> positionsDestroyer = new ArrayList<>();
	private Boolean estEnvie;
	
	public Boolean getEstEnvie() {
		return estEnvie;
	}

	public void setEstEnvie(Boolean estEnvie) {
		this.estEnvie = estEnvie;
	}

	public ArrayList<Integer> getPositionsDestroyer() {
		return positionsDestroyer;
	}

	public void genererDestroyer() {
		int posInit;
		int longueur = 3;
		double direction = Math.floor(Math.random() * 2);
		Bateau destroyer = new Bateau();
		
		if (direction == 1) { // horizontal
			posInit = destroyer.genererNumero(longueur);
			for (int i = 0; i < longueur; i++) {
				positionsDestroyer.add(posInit + i);
			}
		} else { // Vertical
			posInit = (int) Math.floor(Math.random() * 80);
			for (int i = 0; i < longueur; i++) {
				positionsDestroyer.add(posInit);
				posInit = posInit + destroyer.getSize();
			}
		}
		
	}
	
	public void ajouterTableau(ArrayList<ArrayList<Integer>> tabBateaux) {
		tabBateaux.add(positionsDestroyer);
	}
}
