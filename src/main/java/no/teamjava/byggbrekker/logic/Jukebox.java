package no.teamjava.byggbrekker.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : Raymond Koteng
 * @since : 07.nov.2009
 */
public class Jukebox {
	private Long randomSeed = 2342343L;
	String pathToFolder;
	private File folder;
	private File mp3ToBePlayed = null;

	public Jukebox(String pathToFolder) {
		this.pathToFolder = pathToFolder;
		initFolder(pathToFolder);
		chooseRandomMp3File();
	}

	public File getMp3ToBePlayed() {
		return mp3ToBePlayed;
	}

	private void chooseRandomMp3File() {
		List<File> files = getAllMp3FilesInDirectory(folder.listFiles());

		Random randomFile = new Random(randomSeed); //random seed

		if (files.size() != 0) {
			int randomIndex = randomFile.nextInt(files.size());
			mp3ToBePlayed =  files.get(randomIndex);

		} else {
			throw new RuntimeException("Folder does not contains mp3 files!");
		}
	}

	private List<File> getAllMp3FilesInDirectory(File[] allFilesInDirectory) {
		List<File> mp3List = new ArrayList<File>();
		for (File file : allFilesInDirectory) {
			if (file.getName().endsWith(".mp3")) {
				mp3List.add(file);
			}
		}
		return mp3List;
	}

	private void initFolder(String pathToFolder) {
		folder = new File(pathToFolder);
	}
}
