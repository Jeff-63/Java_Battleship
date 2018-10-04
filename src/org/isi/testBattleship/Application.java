package org.isi.testBattleship;

import org.isi.testBattleship.entities.Client;
import org.isi.testBattleship.entities.Server;

public class Application {

	public static void main(String[] args) {
		
		Thread threadServer = new Thread(new Server("Server"));
		Thread threadClient = new Thread(new Client("Client"));
		
		threadServer.start();
		threadClient.start();
	}

}
