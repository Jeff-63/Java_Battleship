package org.isi.testBattleship.entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.isi.testBattleship.conteneurs.BattleshipJButton;
import org.isi.testBattleship.conteneurs.MonBattleship;
import org.isi.testBattleship.entities.Joueur.TypeJoueur;

@SuppressWarnings("serial")
public class Server extends MonBattleship implements Runnable{

	private ServerSocket serverSocket;
	private Socket socket;
	private PrintWriter printWriter;
	private int coorTire;

	public Server( String titre) {
		super(TypeJoueur.PRINCIPALE, titre);
	}

	@Override
	public void run() {

		try (ServerSocket serverSocket = new ServerSocket(6969);
				Socket socket = serverSocket.accept();
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));){

			System.out.println("Connexion Server");
			this.serverSocket = serverSocket;
			this.socket = socket;
			this.printWriter = printWriter;

			String line;

			try 
			{
				while((line = bufferedReader.readLine()) != null)
				{
					try {

						if(tabCasesJoueur2.get(Integer.parseInt(line)).isVide())
						{
							boolean touche = testCollision(Integer.parseInt(line),1);
							setCoorTire(Integer.parseInt(line));
							if(touche)
							{
								tabCasesJoueur2.get(coorTire).setVide(false);
								envoyerNotificationCollision(touche);
								joueur.getScore().diminuerScore();
								majBateauxRestants(1);
								actionTouche(tabCasesJoueur2.get(coorTire), TypeJoueur.PRINCIPALE);
							}
							else
							{
								tabCasesJoueur2.get(coorTire).setVide(false);
								envoyerNotificationCollision(touche);
								joueur.getScore().augmenterScore();
								actionRate(tabCasesJoueur2.get(coorTire), TypeJoueur.PRINCIPALE);

							}
							if(nbBateauxRestantsJ2 == 0)
							{
								envoyerNotificationVictoire();
								messageFinDePartie("perdu");
							}
						}

					} catch (Exception e) {


						if(line.contains("touche"))
						{
							tabCasesJoueur1.get(coorTire).setVide(false);
							joueur.getScore().augmenterScore();
							majBateauxRestants(1);
							actionTouche(tabCasesJoueur1.get(coorTire), TypeJoueur.PRINCIPALE);
						}
						else if(line.contains("rate"))
						{
							tabCasesJoueur1.get(coorTire).setVide(false);
							joueur.getScore().diminuerScore();
							actionRate(tabCasesJoueur1.get(coorTire), TypeJoueur.PRINCIPALE);
						}
						else if (line.contains("victoire"))
						{
							messageFinDePartie("victoire");
						}
						else
						{
							System.out.println(line);
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void genererJeu(TypeJeu type, int intelligenceIA) {
		super.genererJeu(type, intelligenceIA);
		if(typeJeu == TypeJeu.VS_PLAYER)
		{			
			zoneJoueur1.setVisible(true);
			zoneJoueur2.setVisible(true);

			generateurJ2.genererBateaux();
			tabBateauxJoueur2 = generateurJ2.getTabBateaux();
			try
			{
				this.printWriter.println(tabBateauxJoueur2);

			}catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void setCoorTire(int coorTire) {
		this.coorTire = coorTire;
	}

	public void envoyerInfo(String coord)
	{
		this.printWriter.println(coord);
		setCoorTire(Integer.parseInt(coord));
	}

	@Override
	public void creerGrille(JPanel container, int numjoueur) {

		for(int i = 0; i<100; i++) {
			BattleshipJButton bouton = new BattleshipJButton(i);
			if(numjoueur == 1)
			{
				if(TypeJeu.VS_IA == typeJeu)
				{
					creerActionListenerCase(bouton);
				}
				else
				{
					bouton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							envoyerInfo(String.valueOf(bouton.getId()));
						}
					});
				}
				tabCasesJoueur1.add(bouton);
			}
			else
			{
				tabCasesJoueur2.add(bouton);
			}

			container.add(bouton);
		}
	}

	public void envoyerNotificationCollision(boolean collision)
	{
		if(collision)
		{
			this.printWriter.println("touche");
		}
		else
		{
			this.printWriter.println("rate");
		}
	}

	public void envoyerNotificationVictoire()
	{
		this.printWriter.println("victoire");
	}

	@Override
	public void messageTouche(TypeJoueur type) {
		if(type == TypeJoueur.PRINCIPALE || type == TypeJoueur.SECONDAIRE)
		{
			JOptionPane.showMessageDialog(this, "Touché !\n \n NbBateaux vous restants : "
					+nbBateauxRestantsJ2
					+ "\n Score actuel : "+joueur.getScore().getValue(),"Touché",JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "L'IA a touché sa cible !\n Au tour du joueur","Touché",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@Override
	public void messageRate(TypeJoueur type) {
		if(type == TypeJoueur.PRINCIPALE || type == TypeJoueur.SECONDAIRE)
		{
			JOptionPane.showMessageDialog(this, "Manqué !\n \n NbBateaux vous restants : " 
					+nbBateauxRestantsJ2
					+  "\n Score actuel : "+joueur.getScore().getValue(), "Raté",JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "L'IA a manqué sa cible !\n Au tour du joueur","Touché",JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
