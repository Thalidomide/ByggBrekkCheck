package no.teamjava.byggbrekker.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class BuildCheckResult {

	private final List<Build> builds;
	private final BuildCheckStatus buildCheckStatus;

	public BuildCheckResult(List<Build> builds, BuildCheckStatus buildCheckStatus) {
		this.builds = builds;
		this.buildCheckStatus = buildCheckStatus;
	}

	public List<Build> getBuilds() {
		return builds;
	}

	public List<Build> getFailedBuilds() {
		List<Build> failedBuilds = new ArrayList<Build>();

		for (Build build : builds) {
			if (BuildStatus.FAILED.equals(build.getStatus())) {
				failedBuilds.add(build);
			}
		}
		
		return failedBuilds;
	}

	public BuildCheckStatus getBuildCheckStatus() {
		return buildCheckStatus;
	}
}
