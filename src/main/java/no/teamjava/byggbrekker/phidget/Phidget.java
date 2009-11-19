package no.teamjava.byggbrekker.phidget;

import java.util.ArrayList;
import java.util.List;

import no.teamjava.byggbrekker.entities.Build;

/**
 * @author Olav Jensen
 * @since 30.okt.2009
 */
public class Phidget implements PhidgetThreadListener {
	private List<Build> failedBuilds = new ArrayList<Build>();
	private PhidgetThread phidgetThread;

	public void resetOutput() {
		failedBuilds.clear();
	}

	public void start() {
		if (phidgetThread != null) {
			stopAndClearOutputs();
		}
		phidgetThread = new PhidgetThread(this);
	}

	public void stopAndClearOutputs() {
		if (phidgetThread == null) {
			return;
		}
		phidgetThread.stopAndClearOutputs();
		phidgetThread = null;
	}

	public void setBuildStatus(List<Build> failedBuilds) {
		this.failedBuilds = failedBuilds;
	}

	@Override
	public List<Build> getFailedBuilds() {
		return new ArrayList<Build>(failedBuilds);
	}
}
