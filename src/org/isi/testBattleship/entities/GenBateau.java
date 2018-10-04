package org.isi.testBattleship.entities;

import java.util.ArrayList;

public class GenBateau {
	private ArrayList<ArrayList<Integer>> tabBateaux = new ArrayList<>();
	private Destroyer dest = new Destroyer();
	private PorteAvion pa = new PorteAvion();
	private NavPat navpat = new NavPat();
	private SousMarin sm = new SousMarin();

	public void  genererBateaux() {
		Boolean retry = true;
		Boolean inters;
		do {
			pa.genererPA();
			pa.ajouterTableau(tabBateaux);
			dest.genererDestroyer();
			dest.ajouterTableau(tabBateaux);
			sm.genererSM();
			sm.ajouterTableau(tabBateaux);
			navpat.genererNavPat();
			navpat.ajouterTableau(tabBateaux);

			inters = intersection(tabBateaux);
			if (inters != false) {
				pa.getPositionsPA().clear();
				dest.getPositionsDestroyer().clear();
				sm.getPositionsSM().clear();
				navpat.getPositionsNavPat().clear();
				tabBateaux.clear();
			}
			else {
				retry = false;
			}
		} while (retry != false);

	}

	public Boolean intersection(ArrayList<ArrayList<Integer>> tableauDeTableaux) {

		for (int i = 0; i < tableauDeTableaux.size(); i++) {
			ArrayList<Integer> tableauElementRecherche = tableauDeTableaux.get(i);

			for (int j = 0; j < tableauElementRecherche.size(); j++) {
				int elementRecherche = tableauElementRecherche.get(j);

				for (int k = 0; k < tableauDeTableaux.size(); k++) {
					ArrayList<Integer> tableauCourant = tableauDeTableaux.get(k);

					for (int l = 0; l < tableauCourant.size(); l++) {
						int elementCourant = tableauCourant.get(l);
						if (i == k && j == l) {
							l++;
						}
						else {
							if (elementRecherche == elementCourant) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public ArrayList<ArrayList<Integer>> getTabBateaux() {
		return tabBateaux;
	}

}
