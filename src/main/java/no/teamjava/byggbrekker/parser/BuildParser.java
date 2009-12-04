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

			if (lines.length > 0) {
				String line = lines[1];
				int successIndex = line.indexOf(successfulString);
				int failedIndex = line.indexOf(failedString);
				int buildingIndex = line.indexOf(buildingString);

				status = failedIndex == -1 || successIndex < failedIndex ? BuildStatus.SUCCESSFUL : BuildStatus.FAILED;

				building = buildingIndex > 0 && buildingIndex < 150;
			} else {
				status = BuildStatus.UNKNOWN;
			}


			builds.add(new Build(buildType, status, building));
		}

		return builds;
	}


}
