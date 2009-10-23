package no.teamjava.byggbrekker.entities;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class Build {

	private final BuildType type;
	private final BuildStatus status;

	public Build(BuildType type, BuildStatus status) {
		this.type = type;
		this.status = status;
	}

	public BuildType getType() {
		return type;
	}

	public BuildStatus getStatus() {
		return status;
	}
}
