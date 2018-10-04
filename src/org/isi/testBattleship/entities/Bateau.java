package org.isi.testBattleship.entities;

import java.util.ArrayList;

public class Bateau {
	private int size = 10;

	public int getSize() {
		return size;
	}

	public int genererNumero(int typeBateau) {
		ArrayList<Integer> tabNum = new ArrayList<>();

		if (typeBateau == 2) {
			int posInvalid[] = {99, 89, 79, 69, 59, 49, 39, 29, 19, 9};
			for (int i = 0; i < (size*size); i++) {
				tabNum.add(i);
			}
			for (int j = 0; j < posInvalid.length; j++) {
				tabNum.remove(posInvalid[j]);
			}   
			return tabNum.get((int) Math.floor(Math.random() * tabNum.size()));
		}
		else if (typeBateau == 3) {
			int posInvalid[] = {99, 98, 89, 88, 79, 78, 69, 68, 59, 58, 49, 48, 39, 38, 29, 28, 19, 18, 9, 8};
			for (int i = 0; i < (size*size); i++) {
				tabNum.add(i);
			}
			for (int j = 0; j < posInvalid.length; j++) {
				tabNum.remove(posInvalid[j]);
			}

			return tabNum.get((int) Math.floor(Math.random() * tabNum.size()));
		}
		else if (typeBateau == 4) {
			int posInvalid[] = {99, 98, 97, 89, 88, 87, 79, 78, 77, 69, 68, 67, 59, 58, 57, 49, 48, 47, 39, 38, 37, 29, 28, 27, 19, 18, 17, 9, 8, 7};
			for (int i = 0; i < (size*size); i++) {
				tabNum.add(i);
			}
			for (int j = 0; j < posInvalid.length; j++) {
				tabNum.remove(posInvalid[j]);
			}

			return tabNum.get((int) Math.floor(Math.random() * tabNum.size()));
		}
		return 0;
	}
}

