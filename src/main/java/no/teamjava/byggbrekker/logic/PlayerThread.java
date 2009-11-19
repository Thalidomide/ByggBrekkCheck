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

	public PlayerThread() {
		jukebox = new Jukebox(Settings.BROKEN_BUILD_MP3_PATH);
	}

	@Override
	public void run() {
		try {
			System.out.println("Starte med å spille musikk..");
			File toBePlayed = jukebox.getMp3ToBePlayed();
			System.out.println("Skal spille: " + toBePlayed.getName() + ". Lage file input stream");
			FileInputStream inputStream = new FileInputStream(toBePlayed);
			System.out.println("Laget FileInputStream, lage player..");
			player = new Player(inputStream);
			System.out.println("Laget player, skal spille");
			player.play();
			System.out.println("Nå skal sangen være i gang");
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
