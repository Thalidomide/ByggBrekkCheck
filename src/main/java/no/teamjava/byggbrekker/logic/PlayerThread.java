package no.teamjava.byggbrekker.logic;

import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;
import no.teamjava.byggbrekker.entities.Settings;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class PlayerThread extends Thread {

	private Player player;
	private Jukebox jukebox;

	@Override
	public void run() {
		jukebox = null;
		player = null;

		try {
			jukebox = new Jukebox(Settings.BROKEN_BUILD_MP3_PATH);
			player = new Player(new FileInputStream(jukebox.getMp3ToBePlayed()));
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
