package no.teamjava.byggbrekker.parser;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import junit.framework.TestCase;
import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildStatus;
import no.teamjava.byggbrekker.entities.BuildType;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class BuildParserTest extends TestCase {

	public void testParseMavenSiteIsDown() {
		BuildParser parser = new BuildParser();
		String buildString = getBuildString("src/main/resources/buildexamples/failedMavenSite.txt");

		List<Build> builds = parser.checkBuilds(buildString);

		int brokenBuilds = 0;
		BuildType brokenBuildType = null;
		for (Build build : builds) {
			if (BuildStatus.FAILED.equals(build.getStatus())) {
				brokenBuilds ++;
				brokenBuildType = build.getType();
			}
		}

		assertEquals(BuildType.values().length, builds.size());
		assertEquals(1, brokenBuilds);
		assertEquals(BuildType.MAVEN_SITE, brokenBuildType);
	}

	public void testParseDefaultIsBuilding() {
		BuildParser parser = new BuildParser();
		String buildString = getBuildString("src/main/resources/buildexamples/buildingTrunk.txt");

		List<Build> builds = parser.checkBuilds(buildString);

		for (Build build : builds) {
			boolean trunk = BuildType.DEFAULT.equals(build.getType());

			assertEquals(trunk, build.isBuilding());
		}
	}

	private String getBuildString(String fileName) {
		File file = new File(fileName);
		System.out.println(file.getAbsoluteFile());
		FileInputStream fis;
		BufferedInputStream bis;
		DataInputStream dis;

		try {
			fis = new FileInputStream(file);

			// Here BufferedInputStream is added for fast reading.
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			String result = "";
			// dis.available() returns 0 if the file does not have more lines.
			while (dis.available() != 0) {
				// this statement reads the line from the file and print it to
				// the console.
				String line = dis.readLine();
//				System.out.println(line);
				result += line;
			}

			// dispose all the resources after using them.
			fis.close();
			bis.close();
			dis.close();

			return result;

		} catch (Exception e) {
			throw new RuntimeException("Could read file " + fileName, e);
		}
	}
}
