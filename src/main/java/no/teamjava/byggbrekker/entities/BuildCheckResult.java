package no.teamjava.byggbrekker.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class BuildCheckResult {

	private List<Build> builds = new ArrayList<Build>();
	private BuildCheckStatus buildCheckStatus;

	public List<Build> getBuilds() {
		return builds;
	}

	public List<Build> getFailedBuilds() {
		List<Build> failedBuilds = new ArrayList<Build>();

		for (Build build : builds) {
			if (!build.isSuccessful()) {
				builds.add(build);
			}
		}
		return failedBuilds;
	}

	public void setBuilds(List<Build> builds) {
		this.builds = builds;
	}

	public BuildCheckStatus getBuildCheckStatus() {
		return buildCheckStatus;
	}

	public void setBuildCheckStatus(BuildCheckStatus buildCheckStatus) {
		this.buildCheckStatus = buildCheckStatus;
	}
}
