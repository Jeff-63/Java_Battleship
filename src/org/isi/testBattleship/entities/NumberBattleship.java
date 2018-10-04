package org.isi.testBattleship.entities;

import java.util.ArrayList;

public class NumberBattleship {

	public int random(int min, int max)
	{

		return (int)Math.round((Math.random() *((max - min) + 1)))+min;
	}

	public boolean nombreDansArray(ArrayList<Integer> listInteger, int valeur)
	{
		for (Integer integer : listInteger) {
			if(valeur == integer)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean nombreDansArrayDeArray(ArrayList<ArrayList<Integer>> arrayDeArray, int valeur)
	{
		for (ArrayList<Integer> Bateau : arrayDeArray) {
			for (Integer coord : Bateau) {
				if(coord == valeur)
				{
					Bateau.remove(coord);
					return true;
				}
			}
		}
		return false;
	}

	public void genererArrayRandom(int min, int max, ArrayList<Integer> listInteger, int nbValueRandom)
	{
		for(int i = 0; i<nbValueRandom; i++)
		{
			int valueRandom = random(min, max);
			while(nombreDansArray(listInteger, valueRandom))
			{
				valueRandom = random(min, max);
			}
			listInteger.add(valueRandom);
		}
	}

}
