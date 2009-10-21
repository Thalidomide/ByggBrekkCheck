package no.teamjava.byggbrekker.logic;

import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class PlayerThread extends Thread {

	private final String soundBuildDownPath = "src/main/resources/sounds/buildDown.mp3";

	private Player player;

	@Override
	public void run() {
		player = null;
		try {
			System.out.println(new File(soundBuildDownPath).getAbsoluteFile());
			player = new Player(new FileInputStream(new File(soundBuildDownPath)));
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
