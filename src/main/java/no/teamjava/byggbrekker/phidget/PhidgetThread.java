package no.teamjava.byggbrekker.phidget;

import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCategory;
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
	private boolean attached;
	private boolean running;

	private OutputHandler importantHandler0;
	private OutputHandler importantHandler1;
	private OutputHandler minorHandler;
	private OutputHandler okHandler;

	public PhidgetThread(PhidgetThreadListener listener) {
		this.listener = listener;

		kit = new InterfaceKitPhidgetMockable(false);

		importantHandler0 = new ConstantFlasher(kit, Settings.OUTPUTS_IMPORTANT0, 2, 0);
		importantHandler1 = new ConstantFlasher(kit, Settings.OUTPUTS_IMPORTANT1, 2, 1);
		minorHandler = new ConstantFlasher(kit, Settings.OUTPUTS_MINOR, 1, 0);
		okHandler = new ConstantLight(kit, Settings.OUTPUTS_OK, ConstantLight.LightWhenCondition.OK);

		start();
	}

	@Override
	public void run() {
		if (running) {
			return;
		}
		running = true;

		if (!attached) {
			kit.addAttachListener(new AttachListener() {
				@Override
				public void attached(AttachEvent attachEvent) {
					attached = true;
				}
			});
			kit.openAndWaitForAttachment();
			if (!attached) {
				throw new RuntimeException("Should be attached by now!");
			}
		}

		while (true) {
			updateOutput();

			try {
				sleep(Settings.LIGHT_UPDATE_INTERVAL);
			} catch (InterruptedException e) {
				break;
			}
		}

		kit.close();
		running = false;
	}

	private synchronized void updateOutput() {
		if (!attached) {
			return;
		}

		boolean importantBroken = isBroken(BuildCategory.IMPORTANT);
		boolean minorBroken = isBroken(BuildCategory.MINOR);
		boolean someBroken = importantBroken || minorBroken;

		importantHandler0.update(importantBroken);
		importantHandler1.update(importantBroken);
		minorHandler.update(minorBroken);
		okHandler.update(someBroken);
	}

	private boolean isBroken(BuildCategory category) {
		for (Build build : listener.getFailedBuilds()) {
			if (category.equals(build.getType().getCategory())) {
				return true;
			}
		}
		return false;
	}
}
