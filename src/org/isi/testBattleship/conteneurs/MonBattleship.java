package org.isi.testBattleship.conteneurs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.isi.testBattleship.entities.AI;
import org.isi.testBattleship.entities.GenBateau;
import org.isi.testBattleship.entities.Joueur;
import org.isi.testBattleship.entities.Joueur.TypeJoueur;
import org.isi.testBattleship.entities.Render;
import org.isi.testBattleship.entities.Score;
import org.isi.testBattleship.entities.Sound;
import org.isi.testBattleship.entities.NumberBattleship;

@SuppressWarnings("serial")
public class MonBattleship extends JFrame{

	public enum TypeJeu {
		VS_IA,
		VS_PLAYER
	}

	protected JPanel zoneJoueur1;
	protected JPanel zoneJoueur2;
	protected ArrayList<BattleshipJButton> tabCasesJoueur1 = new ArrayList<>();
	protected ArrayList<BattleshipJButton> tabCasesJoueur2 = new ArrayList<>();
	protected ArrayList<ArrayList<Integer>> tabBateauxJoueur1 = new ArrayList<>();
	protected ArrayList<ArrayList<Integer>> tabBateauxJoueur2 = new ArrayList<>();
	protected JPanel content;
	protected AI ai;
	protected TypeJeu typeJeu;
	protected Joueur joueur = new Joueur();
	protected GenBateau generateurJ1 = new GenBateau();
	protected GenBateau generateurJ2 = new GenBateau();
	protected int nbBateauxRestantsJ1;
	protected int nbBateauxRestantsJ2;
	protected ArrayList<Joueur> listeJoueurs = Score.lireFichierScores();
	protected NumberBattleship nb = new NumberBattleship();

	/**
	 * Constructeur du jeu
	 * 
	 * @param typeJoueur
	 * @param titre
	 */
	public MonBattleship(Joueur.TypeJoueur typeJoueur, String titre)
	{
		joueur = new Joueur();
		this.setSize(1280, 768);
		this.setTitle(titre);
		content = new JPanel();
		content.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JMenuBar menu = new JMenuBar();
		JMenu jeu = new JMenu("Jeu");
		JMenu newPartieVsIA = new JMenu("Jouer contre l'ordinateur");
		JMenuItem facile = new JMenuItem("Facile");
		facile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				joueur.setTypeJoueur(Joueur.TypeJoueur.PRINCIPALE);
				genererJeu(TypeJeu.VS_IA, 1);
			}
		});

		JMenuItem normal = new JMenuItem("Normal");
		normal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				joueur.setTypeJoueur(Joueur.TypeJoueur.PRINCIPALE);
				genererJeu(TypeJeu.VS_IA, 2);
			}
		});
		newPartieVsIA.add(facile);
		newPartieVsIA.add(normal);
		JMenuItem newPartieVsPlayer = new JMenuItem("Jouer contre un autre joueur");
		newPartieVsPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				genererJeu(TypeJeu.VS_PLAYER, 0);
			}
		});
		jeu.add(newPartieVsIA);
		jeu.add(newPartieVsPlayer);
		JMenu scores = new JMenu("Scores");
		JMenuItem afficheScores = new JMenuItem("Afficher les scores");
		afficheScores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				afficherFenetreScores();

			}
		});
		scores.add(afficheScores);
		menu.add(jeu);
		menu.add(scores);

		this.setJMenuBar(menu);
		this.setContentPane(content);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Fonction de test de collision
	 * 
	 * @param id
	 * @param turn
	 * @return
	 */
	public boolean testCollision(int id, int turn)
	{
		if(turn == 1)
		{
			return nb.nombreDansArrayDeArray(tabBateauxJoueur2, id);
		}
		else
		{
			return nb.nombreDansArrayDeArray(tabBateauxJoueur1, id);
		}
	}

	/**
	 * Fonction de switch pour la partie contre l'IA
	 */
	public void switchJoueurs()
	{
		if(zoneJoueur1.isVisible())
		{
			zoneJoueur1.setVisible(false);
			zoneJoueur2.setVisible(true);
		}
		else
		{
			zoneJoueur1.setVisible(true);
			zoneJoueur2.setVisible(false);
		}

	}

	/**
	 * Fonction pour l'affichage du message de touché
	 * @param type
	 */
	public void messageTouche(TypeJoueur type)
	{
		if(type == TypeJoueur.PRINCIPALE || type == TypeJoueur.SECONDAIRE)
		{
			JOptionPane.showMessageDialog(this, "Touché !\n \n NbBateaux restants : "
					+nbBateauxRestantsJ2
					+ "\n Score actuel : "+joueur.getScore().getValue(),"Touché",JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "L'IA a touché sa cible !\n Au tour du joueur","Touché",JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Fouction pour l'affichage du message de raté
	 * @param type
	 */
	public void messageRate(TypeJoueur type)
	{
		if(type == TypeJoueur.PRINCIPALE || type == TypeJoueur.SECONDAIRE)
		{
			JOptionPane.showMessageDialog(this, "Manqué !\n \n NbBateaux restants : " 
					+nbBateauxRestantsJ2
					+  "\n Score actuel : "+joueur.getScore().getValue(), "Raté",JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "L'IA a manqué sa cible !\n Au tour du joueur","Touché",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Fonction pour l'affichage du message de fin de partie
	 * @param conclusionFinPartie //prend en paramètre la conclusion de fin de partie (Victoire, Défaite)
	 */
	public void messageFinDePartie(String conclusionFinPartie)
	{

		int reponse = JOptionPane.showConfirmDialog(this, "Vous avez "+conclusionFinPartie+"\n Voulez-vous refaire une partie ?", "Recommencer", JOptionPane.YES_NO_OPTION);
		messageSaveScore();
		if(reponse == JOptionPane.OK_OPTION)
		{
			reset();
		}
		else if(reponse == JOptionPane.NO_OPTION || reponse == JOptionPane.CLOSED_OPTION)
		{
			setVisible(false);
			dispose();
			System.exit(0);
		}
	}

	/**
	 * Fonction pour l'affichage du message d'enregistrement du score
	 */
	public void messageSaveScore()
	{
		int reponse = JOptionPane.showConfirmDialog(this, "Voulez-vous enregistrer votre score ?", "Save Score", JOptionPane.YES_NO_OPTION);
		if(reponse == JOptionPane.OK_OPTION)
		{
			enregistrerScore();
		}
	}

	/**
	 * Fonction de génération du jeu
	 * 
	 * @param type
	 * @param intelligenceIA
	 */
	public void genererJeu(TypeJeu type, int intelligenceIA)
	{
		this.typeJeu = type;
		reset();

		if(typeJeu == TypeJeu.VS_IA)
		{
			if(intelligenceIA == 1)
			{
				ai = new AI(AI.Intelligence.IDIOT);
			}
			else if(intelligenceIA == 2)
			{
				ai = new AI(AI.Intelligence.INTELLIGENT);
			}
			generateurJ1.genererBateaux();
			tabBateauxJoueur1 = generateurJ1.getTabBateaux();
			generateurJ2.genererBateaux();
			tabBateauxJoueur2 = generateurJ2.getTabBateaux();
			System.out.println(tabBateauxJoueur2);
		}
		nbBateauxRestantsJ1 = 4;
		nbBateauxRestantsJ2 = 4;
		JPanel body = new JPanel();
		body.setLayout(new GridLayout(1,1));

		zoneJoueur1 = new JPanel();
		zoneJoueur1.setLayout(new GridLayout(1,1));
		JPanel grilleJoueur1 = new JPanel();
		grilleJoueur1.setLayout(new GridLayout(10, 10));
		creerGrille(grilleJoueur1, 1);
		zoneJoueur1.add(grilleJoueur1);

		zoneJoueur2 = new JPanel();
		zoneJoueur2.setLayout(new GridLayout(1,1));
		JPanel grilleJoueur2 = new JPanel();
		grilleJoueur2.setLayout(new GridLayout(10, 10));
		creerGrille(grilleJoueur2, 2);
		zoneJoueur2.add(grilleJoueur2);

		zoneJoueur1.setVisible(true);
		zoneJoueur2.setVisible(false);

		body.add(zoneJoueur1);
		body.add(zoneJoueur2);

		JPanel footer = new JPanel();
		footer.setBackground(Color.gray);

		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				reset();
			}
		});
		footer.add(reset);

		content.add(body);
		content.add(footer,BorderLayout.SOUTH);
		Render.reRender(this);

	}

	/**
	 * Fonction de création d'une grille de jeu
	 * @param container
	 * @param numjoueur
	 */
	public void creerGrille(JPanel container, int numjoueur)
	{
		for(int i = 0; i<100; i++) {
			BattleshipJButton bouton = new BattleshipJButton(i);
			if(numjoueur == 1)
			{
				if(TypeJeu.VS_IA == typeJeu)
				{
					creerActionListenerCase(bouton);
					tabCasesJoueur1.add(bouton);
				}
			}
			else
			{
				tabCasesJoueur2.add(bouton);
			}

			container.add(bouton);
		}
	}

	/**
	 * Fonction de création des ActionsListeners du jeu
	 * @param bouton
	 */
	public void creerActionListenerCase(BattleshipJButton bouton)
	{
		bouton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(bouton.isVide())
				{
					boolean touche = testCollision(bouton.getId(),1);
					if(touche)
					{
						joueur.getScore().augmenterScore();
						majBateauxRestants(1);
						actionTouche(bouton, TypeJoueur.PRINCIPALE);
					}
					else
					{
						joueur.getScore().diminuerScore();
						actionRate(bouton, TypeJoueur.PRINCIPALE);

					}
					if(nbBateauxRestantsJ2 == 0)
					{
						messageFinDePartie("gagné");
					}
					else 
					{
						bouton.setVide(false);
						switchJoueurs();
						if(typeJeu == TypeJeu.VS_IA)
						{
							actionAi();
						}
					}
				}
			}
		});
	}

	/**
	 * Fonction pour le reset du jeu
	 */
	public void reset()
	{
		content.removeAll();
		Render.reRender(this);
		tabCasesJoueur1 = new ArrayList<>();
		tabCasesJoueur2 = new ArrayList<>();
		tabBateauxJoueur1 = new ArrayList<>();
		tabBateauxJoueur2 = new ArrayList<>();
		joueur.resetJoueur();
		listeJoueurs = Score.lireFichierScores();
	}

	/**
	 * Fonction d'action de l'IA
	 */
	public void actionAi()
	{

		int positionTir = this.ai.tirer();

		if(testCollision(positionTir, 2))
		{
			majBateauxRestants(2);
			actionTouche(tabCasesJoueur2.get(positionTir), TypeJoueur.AI);

		}
		else
		{
			actionRate(tabCasesJoueur2.get(positionTir), TypeJoueur.AI);
		}

		if(nbBateauxRestantsJ1 == 0)
		{
			messageFinDePartie("perdu");
		}
		else
		{
			switchJoueurs();
		}
	}

	/**
	 * Fonction d'action en cas de touché
	 * @param bouton
	 * @param type
	 */
	public void actionTouche(BattleshipJButton bouton, TypeJoueur type)
	{
		Sound.playSoundTouche();
		Render.renderTouche(bouton);
		messageTouche(type);
	}

	/**
	 * Fonction d'action en cas de raté
	 * @param bouton
	 * @param type
	 */
	public void actionRate(BattleshipJButton bouton, TypeJoueur type)
	{
		Sound.playSoundMiss();
		Render.renderRate(bouton);
		messageRate(type);
	}

	/**
	 * Fonction pour l'affichage de la fenêtre des scores
	 */
	public void afficherFenetreScores()
	{

		JFrame fenetreScores = new JFrame();
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());

		JTextPane areaScores = new JTextPane();
		areaScores.setText("Scores"+"\n");
		StyledDocument doc = areaScores.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		int style = Font.BOLD | Font.ITALIC;
		Font font = new Font("Century", style , 35);
		areaScores.setFont(font);
		areaScores.setEditable(false);

		for (Joueur joueur : listeJoueurs) {
			String txtScore = joueur.getNom() + " : " + joueur.getScore().getValue();
			areaScores.setText(areaScores.getText()+"\n"+txtScore);
		}

		JScrollPane scrollPane = new JScrollPane(areaScores);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		content.add(scrollPane);
		fenetreScores.add(content);

		fenetreScores.setLocationRelativeTo(this);
		fenetreScores.setLocation(this.getX() + (this.getWidth()/3), this.getY());
		fenetreScores.setSize(600,800);
		fenetreScores.setVisible(true);

	}

	/**
	 * Fonction pour l'enregistrement du score du joueur à la fin de la partie
	 */
	public void enregistrerScore()
	{
		joueur.setNom(JOptionPane.showInputDialog(this, "Saisir votre nom : "));
		listeJoueurs.add(joueur);
		try(PrintWriter printWriter = new PrintWriter(Score.getFileScore())) {	
			for (Joueur joueur : listeJoueurs) {
				String entree = joueur.getNom()+';'+joueur.getScore().getValue();
				printWriter.println(entree);
			}
			JOptionPane.showMessageDialog(this, "Votre score a été enregistré avec succès");

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Fonction de mise à jour du nombre de bateaux restants
	 * @param joueur
	 */
	public void majBateauxRestants(int joueur)
	{
		int cpt = 4;
		if(joueur == 1)
		{

			for (ArrayList<Integer> Bateaux : tabBateauxJoueur2) {
				if(Bateaux.size() == 0)
				{
					cpt--;
				}
			}
			nbBateauxRestantsJ2 = cpt;
		}
		else
		{
			for (ArrayList<Integer> Bateaux : tabBateauxJoueur1) {
				if(Bateaux.size() == 0)
				{
					cpt--;
				}
			}
			nbBateauxRestantsJ1 = cpt;
		}
	}

}
