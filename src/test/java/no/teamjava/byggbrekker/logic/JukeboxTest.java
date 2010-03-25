package no.teamjava.byggbrekker.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import no.teamjava.byggbrekker.entities.Settings;

/**
 * @author : Raymond Koteng
 * @since : 07.nov.2009
 */
public class JukeboxTest extends TestCase {
	private Random randomFile = new Random();

	public void testGetRandomMp3File() {
		Jukebox jukebox = new Jukebox(Settings.BROKEN_BUILD_MP3_PATH);
		File fileToBePlayed = jukebox.getMp3ToBePlayed();

		assertNotNull(fileToBePlayed);
		assertTrue(fileToBePlayed.getName().endsWith(".mp3"));
	}

	public void testPathDoesNotContainsMp3Files() {
		Jukebox jukebox = new Jukebox("src/main/resources/");
		File file = jukebox.getMp3ToBePlayed();
		assertNull(file);
	}

	public void testJukeboxWithOneFile() {
		File f = chooseRandomMp3FileFromAListWithOneFile();

		assertNotNull(f);
	}

	public void testJukeboxWithTreFilesMakeSureThatAllTheWillBePlayed() {
		int fileOne = 0;
		int fileTwo = 0;
		int fileThree = 0;

		for (int i = 0; i < 20; i++) {
			int randomInt = chooseRandomMp3FileFromAListWithThreeFiles();

			if (randomInt == 0) {
				fileOne++;
			} else if (randomInt == 1) {
				fileTwo++;
			} else if (randomInt == 2) {
				fileThree++;
			} else {
				throw new RuntimeException("Unexpected value!");
			}
		}

		assertTrue(fileOne > 0);
		assertTrue(fileTwo > 0);
		assertTrue(fileThree > 0);
	}

	private File chooseRandomMp3FileFromAListWithOneFile() {
		List<File> files = new ArrayList<File>();
		files.add(new File("www.vg.no"));

		int randomIndex = getRandomNumber(files);

		return files.get(randomIndex);
	}

	private int chooseRandomMp3FileFromAListWithThreeFiles() {
		List<File> files = new ArrayList<File>();
		files.add(new File("www.vg.no"));
		files.add(new File("someFile"));
		files.add(new File("SomeFile2"));

		return getRandomNumber(files);

	}

	private int getRandomNumber(List<File> files) {

		return randomFile.nextInt(files.size());
	}
}
