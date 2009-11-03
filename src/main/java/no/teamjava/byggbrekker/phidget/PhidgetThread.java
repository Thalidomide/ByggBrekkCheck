package no.teamjava.byggbrekker.phidget;

import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCategory;
import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.phidget.outputhandlers.ConstantFlasher;
import no.teamjava.byggbrekker.phidget.outputhandlers.OutputHandler;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
class PhidgetThread extends Thread {

	private final static int[] OUTPUT_IMPORTANT0 = new int[]{0};
	private final static int[] OUTPUT_IMPORTANT1 = new int[]{1};
	private final static int[] OUTPUT_MINOR = new int[]{7};

	private final PhidgetThreadListener listener;
	private InterfaceKitPhidgetMockable kit;
	private boolean attached;
	private boolean running;

	private OutputHandler importantHandler0;
	private OutputHandler importantHandler1;
	private OutputHandler minorHandler;

	public PhidgetThread(PhidgetThreadListener listener) {
		this.listener = listener;

		kit = new InterfaceKitPhidgetMockable(false);
		kit.addAttachListener(new AttachListener() {
			@Override
			public void attached(AttachEvent attachEvent) {
				attached = true;
				start();
			}
		});

		importantHandler0 = new ConstantFlasher(kit, OUTPUT_IMPORTANT0, 0);
		importantHandler1 = new ConstantFlasher(kit, OUTPUT_IMPORTANT1, 1);
		minorHandler = new ConstantFlasher(kit, OUTPUT_MINOR, 0);
	}

	@Override
	public void run() {
		if (!attached || running) {
			return;
		}
		running = true;
		kit.openAndWaitForAttachment();
		long runs = 0;

		while (true) {
			updateOutput(runs++);

			try {
				sleep(Settings.LIGHT_UPDATE_INTERVAL);
			} catch (InterruptedException e) {
				break;
			}
		}

		kit.close();
		running = false;
	}

	private synchronized void updateOutput(long runTime) {
		if (!attached) {
			return;
		}

		boolean importantBroken = isBroken(BuildCategory.IMPORTANT);
		boolean minorBroken = isBroken(BuildCategory.MINOR);

		importantHandler0.update(runTime, importantBroken);
		importantHandler1.update(runTime, importantBroken);
		minorHandler.update(runTime, minorBroken);
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
