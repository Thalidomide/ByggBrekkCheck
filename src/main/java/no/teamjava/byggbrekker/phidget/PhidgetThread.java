package no.teamjava.byggbrekker.phidget;

import java.util.List;

import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCategory;
import no.teamjava.byggbrekker.entities.BuildUtil;
import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.phidget.outputhandlers.ConstantFlasher;
import no.teamjava.byggbrekker.phidget.outputhandlers.ConstantLight;
import no.teamjava.byggbrekker.phidget.outputhandlers.OutputHandler;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
class PhidgetThread extends Thread {

	private final PhidgetThreadListener listener;
	private InterfaceKitPhidgetMockable kit;
	private boolean running;
	private boolean stop;
	private boolean attached;

	private OutputHandler importantHandler0;
	private OutputHandler importantHandler1;
	private OutputHandler minorHandler;
	private OutputHandler okHandler;

	public PhidgetThread(PhidgetThreadListener listener) {
		this.listener = listener;

		kit = new InterfaceKitPhidgetMockable(false);
		importantHandler0 = new ConstantFlasher(kit, Settings.OUTPUTS_IMPORTANT0, 2, 0);
		importantHandler1 = new ConstantFlasher(kit, Settings.OUTPUTS_IMPORTANT1, 2, 1);
		minorHandler = new ConstantLight(kit, Settings.OUTPUTS_MINOR, ConstantLight.LightWhenCondition.BROKEN);
		okHandler = new ConstantLight(kit, Settings.OUTPUTS_OK, ConstantLight.LightWhenCondition.OK);

		System.out.println("Phidget - legge til AttachListener og vente på attach..");
		kit.addAttachListener(new AttachListener() {
			@Override
			public void attached(AttachEvent attachEvent) {
				attached = true;
				System.out.println("Phidget - attached!");
			}
		});
	}

	@Override
	public void run() {
		System.out.println("Phidget - kjøre.. Running = " + running + ", stop = " + stop);
		if (running || stop) {
			return;
		}
		running = true;
		System.out.println("Phidget - openAndWaitForAttachment");
		kit.openAndWaitForAttachment();
		System.out.println("Phidget - done openAndWaitForAttachment! Kjøre..");

		while (running && !stop) {
			updateOutput();

			try {
				sleep(Settings.LIGHT_UPDATE_INTERVAL);
			} catch (InterruptedException e) {
				running = false;
				break;
			}
		}
	}

	public void stopAndClearOutputs() {
		stop = true;
		interrupt();
		if (attached) {
			clearOutputs();
			kit.close();
		}
	}

	private synchronized void updateOutput() {
		if (!attached) {
			return;
		}

		List<Build> failedBuilds = listener.getFailedBuilds();

		boolean importantBroken = BuildUtil.isBroken(BuildCategory.IMPORTANT, failedBuilds);
		boolean minorBroken = BuildUtil.isBroken(BuildCategory.MINOR, failedBuilds);
		boolean someBroken = importantBroken || minorBroken;

		importantHandler0.update(importantBroken);
		importantHandler1.update(importantBroken);
		minorHandler.update(minorBroken);
		okHandler.update(someBroken);
	}

	private void clearOutputs() {
		for (int output = 0; output < Settings.OUPUT_COUNT; output++) {
			kit.setOutputState(output, false);
		}
	}
}
