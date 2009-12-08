package no.teamjava.byggbrekker.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import no.teamjava.byggbrekker.entities.Settings;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class PlayerThread extends Thread {

	private Player player;
	private Jukebox jukebox;
	private boolean play;

	public PlayerThread() {
		jukebox = new Jukebox(Settings.BROKEN_BUILD_MP3_PATH);
	}

	@Override
	public void run() {
		play = true;

		try {
			while (play) {
				if (player == null || player.isComplete()) {
					System.out.println("Spille ny sang..");
					File toBePlayed = jukebox.getMp3ToBePlayed();
					playMp3(toBePlayed);
				}
				sleep(1000);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			play = false;
		}
	}

	private void playMp3(File toBePlayed) throws FileNotFoundException, JavaLayerException {
		System.out.println("Skal spille: " + toBePlayed.getName() + ". Lage file input stream");
		FileInputStream inputStream = new FileInputStream(toBePlayed);
		System.out.println("Laget FileInputStream, lage player..");
		player = new Player(inputStream);
		System.out.println("Laget player, skal spille");
		player.play();
		System.out.println("Nå skal sangen være i gang");
	}

	public void stopPlayer() {
		if (player == null) {
			return;
		}
		play = false;
		player.close();
	}
}
