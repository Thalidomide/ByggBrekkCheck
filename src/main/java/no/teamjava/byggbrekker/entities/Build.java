package no.teamjava.byggbrekker.entities;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class Build {

	private final BuildType buildType;
	private final boolean successful;

	public Build(BuildType buildType, boolean successful) {
		this.buildType = buildType;
		this.successful = successful;
	}

	public BuildType getBuildType() {
		return buildType;
	}

	public boolean isSuccessful() {
		return successful;
	}
}
