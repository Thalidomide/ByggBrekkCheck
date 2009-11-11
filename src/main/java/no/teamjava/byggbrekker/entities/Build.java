package no.teamjava.byggbrekker.entities;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class Build {

	private final BuildType type;
	private final BuildStatus status;
	private final boolean building;

	public Build(BuildType type, BuildStatus status, boolean building) {
		this.type = type;
		this.status = status;
		this.building = building;
	}

	public BuildType getType() {
		return type;
	}

	public BuildStatus getStatus() {
		return status;
	}

	public boolean isBuilding() {
		return building;
	}
}
