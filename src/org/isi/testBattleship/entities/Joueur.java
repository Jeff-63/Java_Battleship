package org.isi.testBattleship.entities;

public class Joueur {

		public enum TypeJoueur
		{
			PRINCIPALE,
			SECONDAIRE,
			AI
		}
	
	
		private String nom;
		private Score score;
		private TypeJoueur typeJoueur;
		
		public Joueur() {
			this.typeJoueur=null;
			this.nom = "";
			this.score = new Score(0);
		}
		
		public Joueur(String nom, Score score) {
			this.nom = nom;
			this.score = score;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public Score getScore() {
			return score;
		}

		public void setScore(Score score) {
			this.score = score;
		}

		public TypeJoueur getTypeJoueur() {
			return typeJoueur;
		}

		public void setTypeJoueur(TypeJoueur typeJoueur) {
			this.typeJoueur = typeJoueur;
		}
		
		public void resetJoueur()
		{
			this.nom = "";
			this.score = new Score(0);
		}
}
