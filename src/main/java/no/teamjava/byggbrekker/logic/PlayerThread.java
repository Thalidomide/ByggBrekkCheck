package no.teamjava.byggbrekker.logic;

import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class PlayerThread extends Thread {
	private Player player;

	@Override
	public void run() {
		player = null;
		try {
			player = new Player(new FileInputStream(new File("C:/mp3.mp3")));
			player.play();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void stopPlayer() {
		if (player == null) {
			return;
		}
		player.close();
	}
}
