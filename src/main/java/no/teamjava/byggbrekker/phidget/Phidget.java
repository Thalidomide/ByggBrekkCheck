package no.teamjava.byggbrekker.phidget;

import java.util.ArrayList;
import java.util.List;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCategory;

/**
 * @author Olav Jensen
 * @since 30.okt.2009
 */
public class Phidget {
	private InterfaceKitPhidget kit;
	private List<Build> failedBuilds = new ArrayList<Build>();
	private boolean attached;

	private final static int[] OUTPUT_IMPORTANT = new int[]{0};
	private final static int[] OUTPUT_MINOR = new int[]{1};
	private final static int OUPUT_COUNT = 8;

	public Phidget() {
		try {
			kit = new InterfaceKitPhidget();
		} catch (PhidgetException e) {
			throw new RuntimeException("Error ved oppretting av InterfaceKitPhidget", e);
		}
		kit.addAttachListener(new AttachListener() {
			@Override
			public void attached(AttachEvent attachEvent) {
				attached = true;
				updateOutput();
			}
		});

		try {
			kit.openAny();
			kit.waitForAttachment();
		} catch (PhidgetException e) {
			throw new RuntimeException("Error ved åpning av InterfaceKitPhidget", e);
		}

		/*
		TODO At the end of your program, don’t forget to call close to free any locks on the Phidget.
		ik.close();
		ik = null;
		*/
	}

	public void resetOutput() {
		failedBuilds.clear();
		updateOutput();
	}

	private synchronized void updateOutput() {
		if (!attached) {
			return;
		}
		
		boolean importantBroken = isBroken(BuildCategory.IMPORTANT);
		boolean minorBroken = isBroken(BuildCategory.MINOR);

		for (int output = 0; output < OUPUT_COUNT; output++) {
			boolean condition = false;
			if (importantBroken && checkOutput(output, OUTPUT_IMPORTANT)) {
				condition = true;
			} else if (minorBroken && checkOutput(output, OUTPUT_MINOR)) {
				condition = true;
			}
			setOutputState(output, condition);
		}
	}

	private boolean checkOutput(int output, int[] checkValues) {
		for (int checkValue : checkValues) {
			if (output == checkValue) {
				return true;
			}
		}
		return false;
	}

	private boolean isBroken(BuildCategory category) {
		for (Build build : failedBuilds) {
			if (category.equals(build.getType().getCategory())) {
				return true;
			}
		}
		return false;
	}

	private void setOutputState(int output, boolean condition) {
		try {
			kit.setOutputState(output, condition);
		} catch (PhidgetException e) {
			throw new RuntimeException("Error ved setting av output status på port " + output, e);
		}
	}

	public void setBuildStatus(List<Build> failedBuilds) {
		this.failedBuilds = failedBuilds;
		updateOutput();
	}
}
