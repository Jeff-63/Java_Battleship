package org.isi.testBattleship.entities;

import java.util.ArrayList;

public class AI {

	public enum Intelligence
	{
		IDIOT,
		INTELLIGENT
	}


	private ArrayList<Integer> coord;
	private Intelligence intelligence;
	private NumberBattleship nb = new NumberBattleship();

	public AI(Intelligence intelligence) {
		this.coord = new ArrayList<>();
		this.intelligence = intelligence;
	}

	public int tirer()
	{
		switch(intelligence)
		{
		case IDIOT :  
		{
			int tireAleatoire = nb.random(0, 98);
			while(nb.nombreDansArray(coord, tireAleatoire))
			{
				tireAleatoire = nb.random(0, 98);
			}
			coord.add(tireAleatoire);
			return tireAleatoire;
		}
		case INTELLIGENT:
		{
			TireIntello ti = new TireIntello();

			int direction = 1;
			int tire = ti.tireAleatoire();

			do { 
				if (ti.getDernierTire() == ti.getGuess()) {
					if (ti.verifierTire(coord, tire) == true) {
						tire = ti.continuerTire(tire, direction);
						
					}
					else {
						direction = ti.nouvelleDirection(direction);
					}
				}
				else {
					if (ti.verifierTire(coord, tire) == true) {
						ti.continuerTire(tire, direction);
						ti.setDernierTire(tire);
					}
				}
			}while (direction != -1);
		}

		default:
			return -1;
		}
	}

}
