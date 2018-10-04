package org.isi.testBattleship.conteneurs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.isi.testBattleship.entities.Render;
import org.isi.testBattleship.entities.Sound;


@SuppressWarnings("serial")
public class MaFenetre extends JFrame {

	//private JWindow fenetreActive;
	private JPanel zoneJoueur1;
	private JPanel zoneJoueur2;
	
	public MaFenetre()
	{
		//fenetreActive = this;
		this.setSize(1024, 768);

		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		JPanel body = new JPanel();
		body.setLayout(new GridLayout(1,1));

		zoneJoueur1 = new JPanel();
		zoneJoueur1.setLayout(new GridLayout(1,1));
		JPanel grilleJoueur1 = new JPanel();
		grilleJoueur1.setLayout(new GridLayout(10, 10));

		for(int i = 0; i<100; i++) {
			JButton bouton = new JButton();
			bouton.setBackground(Color.lightGray);
			bouton.setName(""+i);
			bouton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					boolean touche = testCollision(bouton.getName());
					if(touche)
					{
						Render.renderTouche(bouton);
						renderMessageTouche();
						Sound.playSoundTouche();
					}
					else
					{
						Render.renderRate(bouton);
						renderMessageRate();
						Sound.playSoundMiss();
					}
					bouton.removeActionListener(this);
					switchJoueurs();
				}
			});
			grilleJoueur1.add(bouton);
		}
		zoneJoueur1.add(grilleJoueur1);

		zoneJoueur2 = new JPanel();
		zoneJoueur2.setLayout(new GridLayout(1,1));
		JPanel grilleJoueur2 = new JPanel();
		grilleJoueur2.setLayout(new GridLayout(10, 10));

		for(int i = 0; i<100; i++) {
			JButton bouton = new JButton();
			bouton.setBackground(Color.lightGray);
			bouton.setName(""+i);
			bouton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					boolean touche = testCollision(bouton.getName());
					if(touche)
					{
						Render.renderTouche(bouton);
						renderMessageTouche();
						Sound.playSoundTouche();
					}
					else
					{
						Render.renderRate(bouton);
						renderMessageRate();
						Sound.playSoundMiss();
					}
					bouton.removeActionListener(this);
					switchJoueurs();
				}
			});
			grilleJoueur2.add(bouton);
		}
		zoneJoueur2.add(grilleJoueur2);

		zoneJoueur1.setVisible(true);
		zoneJoueur2.setVisible(false);

		body.add(zoneJoueur1);
		body.add(zoneJoueur2);

		content.add(body);

		JPanel footer = new JPanel();
		footer.setBackground(Color.gray);
		content.add(footer,BorderLayout.SOUTH);

		JButton quitter = new JButton("Quitter");
		quitter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		footer.add(quitter);

		this.setContentPane(content);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static boolean testCollision(String id)
	{
		return true;
	}
	
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

	public void renderMessageTouche()
	{
		JOptionPane.showMessageDialog(this, "Vous avez touché votre cible !\n Au tour de l'autre joueur","Touché",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void renderMessageRate()
	{
		JOptionPane.showMessageDialog(this, "Vous avez manqué votre cible !\n Au tour de l'autre joueur", "Raté",JOptionPane.INFORMATION_MESSAGE);
	}
}
