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
	String pathToFolder;
	private File folder;

	public Jukebox(String pathToFolder) {
		this.pathToFolder = pathToFolder;
		initFolder(pathToFolder);
	}

	public File getMp3ToBePlayed() {
		return chooseRandomMp3File();
	}

	private File chooseRandomMp3File() {
		List<File> files = getAllMp3FilesInDirectory(folder.listFiles());

		Random randomFile = new Random();

		if (files.size() != 0) {
			int randomIndex = randomFile.nextInt(files.size());
			return files.get(randomIndex);

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
