package org.isi.testBattleship.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Score {

	private int score;
	private static File fileScore = new File("files/scores.txt");

	public static File getFileScore() {
		return fileScore;
	}

	public Score(int scoreJoueur) {
		this.setValue(scoreJoueur);
	}

	public int getValue() {
		return score;
	}

	public void setValue(int scoreJoueur) {
		this.score = scoreJoueur;
	}

	public void augmenterScore()
	{
		this.score += 2000;
	}

	public void diminuerScore()
	{
		this.score -= 1000;
		if(this.score < 0)
		{
			this.score = 0;
		}
	}

	public static ArrayList<Joueur> lireFichierScores()
	{
		ArrayList<Joueur> joueurs = new ArrayList<>();

		try(FileReader reader = new FileReader(fileScore);
				BufferedReader bufferReader = new BufferedReader(reader);) {
			String ligne;
			while((ligne=bufferReader.readLine())!= null)
			{
				String[] datas = ligne.split(";");
				Score score = new Score(Integer.parseInt(datas[1]));
				Joueur joueur = new Joueur(datas[0], score);

				joueurs.add(joueur);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return joueurs;
	}
}
