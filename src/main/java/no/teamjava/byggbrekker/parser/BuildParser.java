package no.teamjava.byggbrekker.parser;

import java.util.ArrayList;
import java.util.List;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildStatus;
import no.teamjava.byggbrekker.entities.BuildType;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class BuildParser {

	private String successfulString = "SUCCESSFUL";
	private String failedString = "FAILED";
	private String buildingString = "BUILD IN PROGRESS";

	public List<Build> checkBuilds(String buildText) {
		List<Build> builds = new ArrayList<Build>();
		buildText = buildText.toUpperCase();

		for (BuildType buildType : BuildType.values()) {
			BuildStatus status;

			String[] lines = buildText.split(buildType.getLookup().toUpperCase());
			boolean building = false;

			if (lines.length > 1) {
				String line = lines[1];
				int successIndex = line.indexOf(successfulString);
				int failedIndex = line.indexOf(failedString);
				int buildingIndex = line.indexOf(buildingString);

				status = failedIndex == -1 || successIndex < failedIndex ? BuildStatus.SUCCESSFUL : BuildStatus.FAILED;

				building = buildingIndex > 0 && buildingIndex < 150;
			} else {
				printLinesWhenParsingFailed(buildType, lines);
				status = BuildStatus.UNKNOWN;
			}

			builds.add(new Build(buildType, status, building));
		}

		return builds;
	}

	private void printLinesWhenParsingFailed(BuildType buildType, String[] lines) {
		String msg = "Parsing feilet av bygg " + buildType.getText() + "! Innhold i 'lines':";
		for (int i = 0; i < lines.length; i++) {
			msg += "\n" + i + ". line - " + lines[i];
		}
		System.out.println(msg);
	}


}
