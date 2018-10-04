package org.isi.testBattleship.entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.isi.testBattleship.conteneurs.BattleshipJButton;
import org.isi.testBattleship.conteneurs.MonBattleship;
import org.isi.testBattleship.entities.Joueur.TypeJoueur;

@SuppressWarnings("serial")
public class Client extends MonBattleship implements Runnable{

	private Socket socket;
	private PrintWriter printWriter;
	private int coorTire;

	public Client(String titre) {
		super(TypeJoueur.SECONDAIRE, titre);
	}

	@Override
	public void run() {
		String addresseIp = JOptionPane.showInputDialog(this, "Saisir l'adresse IP du server : (ex: 192.168.57.7)");
		try (Socket socket = new Socket(addresseIp, 6969);
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));){

			System.out.println("Connexion Client");
			this.socket = socket;
			this.printWriter = printWriter;

			String line;
			try 
			{
				while((line = bufferedReader.readLine()) != null)
				{					
					try {

						if(tabCasesJoueur1.get(Integer.parseInt(line)).isVide())
						{
							boolean touche = testCollision(Integer.parseInt(line),1);
							setCoorTire(Integer.parseInt(line));
							if(touche)
							{
								tabCasesJoueur1.get(coorTire).setVide(false);
								envoyerNotificationCollision(touche);
								joueur.getScore().diminuerScore();
								majBateauxRestants(1);
								actionTouche(tabCasesJoueur1.get(coorTire), TypeJoueur.PRINCIPALE);
							}
							else
							{
								tabCasesJoueur1.get(coorTire).setVide(false);
								envoyerNotificationCollision(touche);
								joueur.getScore().augmenterScore();
								actionRate(tabCasesJoueur1.get(coorTire), TypeJoueur.PRINCIPALE);

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
							tabCasesJoueur2.get(coorTire).setVide(false);
							joueur.getScore().augmenterScore();
							majBateauxRestants(1);
							actionTouche(tabCasesJoueur2.get(coorTire), TypeJoueur.PRINCIPALE);
						}
						else  if(line.contains("rate"))
						{
							tabCasesJoueur2.get(coorTire).setVide(false);
							joueur.getScore().diminuerScore();
							actionRate(tabCasesJoueur2.get(coorTire), TypeJoueur.PRINCIPALE);
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

	public void setCoorTire(int coorTire) {
		this.coorTire = coorTire;
	}

	@Override
	public void genererJeu(TypeJeu type, int intelligenceIA) {
		super.genererJeu(type,intelligenceIA);

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

	public void envoyerInfo(String coord)
	{
		this.printWriter.println(coord);
		setCoorTire(Integer.parseInt(coord));
	}

	@Override
	public void creerGrille(JPanel container, int numjoueur) {

		for(int i = 0; i<100; i++) {
			BattleshipJButton bouton = new BattleshipJButton(i);
			if(numjoueur == 2)
			{
				if(TypeJeu.VS_IA == typeJeu)
				{
					creerActionListenerCase(bouton);
				}

				tabCasesJoueur1.add(bouton);
			}
			else
			{
				bouton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						envoyerInfo(String.valueOf(bouton.getId()));
					}
				});
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
			JOptionPane.showMessageDialog(this, "Touch� !\n \n NbBateaux vous restants : "
					+nbBateauxRestantsJ2
					+ "\n Score actuel : "+joueur.getScore().getValue(),"Touch�",JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "L'IA a touch� sa cible !\n Au tour du joueur","Touch�",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	
	@Override
	public void messageRate(TypeJoueur type) {
		if(type == TypeJoueur.PRINCIPALE || type == TypeJoueur.SECONDAIRE)
		{
			JOptionPane.showMessageDialog(this, "Manqu� !\n \n NbBateaux vous restants : " 
					+nbBateauxRestantsJ2
					+  "\n Score actuel : "+joueur.getScore().getValue(), "Rat�",JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "L'IA a manqu� sa cible !\n Au tour du joueur","Touch�",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void messageSaisieIP()
	{
		
	}
}
