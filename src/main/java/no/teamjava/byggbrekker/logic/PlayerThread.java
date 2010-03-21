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
					if (player != null) {
						player.close();
					}
					File toBePlayed = jukebox.getMp3ToBePlayed();
					playMp3(toBePlayed);
				}
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					play = false;
					break;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void playMp3(File toBePlayed) throws FileNotFoundException, JavaLayerException {
		System.out.println("Skal spille: " + toBePlayed.getName() + ". Lage file input stream");
		FileInputStream inputStream = new FileInputStream(toBePlayed);
		System.out.println("Laget FileInputStream, lage player..");
		player = new Player(inputStream);
		System.out.println("Laget player, skal spille");
		player.play();
	}

	public void stopPlayer() {
		System.out.println("Skal stoppe musikk, spiller: " + play);
		play = false;
		if (player == null) {
			return;
		}
		interrupt();
		player.close();
	}
}
