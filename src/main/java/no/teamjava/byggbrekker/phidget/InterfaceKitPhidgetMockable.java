package no.teamjava.byggbrekker.phidget;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.AttachListener;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public class InterfaceKitPhidgetMockable implements CanUpdateOutput {

	private InterfaceKitPhidget kit;

	private final boolean mock;

	public InterfaceKitPhidgetMockable(boolean mock) {
		this.mock = mock;
		if (mock) {
			return;
		}

		try {
			kit = new InterfaceKitPhidget();
		} catch (PhidgetException e) {
			e.printStackTrace();
		}
	}

	public void addAttachListener(AttachListener attachListener) {
		if (mock) {
			attachListener.attached(null);
			return;
		}
		
		kit.addAttachListener(attachListener);
	}

	public void openAndWaitForAttachment() {
		if (mock) {
			return;
		}

		try {
			kit.openAny();
			kit.waitForAttachment();
		} catch (PhidgetException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (mock) {
			return;
		}

		try {
			kit.close();
		} catch (PhidgetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setOutputState(int output, boolean condition) {
		if (mock) {
			System.out.println(output + ". utgang: " + condition);
			return;
		}

		try {
			kit.setOutputState(output, condition);
		} catch (PhidgetException e) {
			throw new RuntimeException("Error ved setting av output status på port " + output, e);
		}
	}
}
