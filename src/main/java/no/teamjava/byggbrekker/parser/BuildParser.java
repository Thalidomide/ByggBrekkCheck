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

	public List<Build> checkBuilds(String buildText) {
		List<Build> builds = new ArrayList<Build>();
		buildText = buildText.toUpperCase();

		for (BuildType buildType : BuildType.values()) {
			BuildStatus status;

			String[] lines = buildText.split(buildType.getLookup().toUpperCase());

			if (lines.length > 0) {
				String line = lines[1];
				int successIndex = line.indexOf(successfulString);
				int failedIndex = line.indexOf(failedString);

				status = failedIndex == -1 || successIndex < failedIndex ? BuildStatus.SUCCESSFUL : BuildStatus.FAILED;
			} else {
				status = BuildStatus.UNKNOWN;
			}

			builds.add(new Build(buildType, status));
		}

		return builds;
	}


}
