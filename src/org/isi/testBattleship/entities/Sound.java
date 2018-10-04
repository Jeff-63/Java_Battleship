package org.isi.testBattleship.entities;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;


public class Sound {

	public static void playSoundTouche()
	{
		
		URL url=null;
		try {
			url = new URL("file:sons/explosion.wav");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		AudioClip ac = Applet.newAudioClip(url);
        ac.play();

	}

	public static void playSoundMiss()
	{
		URL url=null;
		try {
			url = new URL("file:sons/splash.wav");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		AudioClip ac = Applet.newAudioClip(url);
        ac.play();
	}

}