package no.teamjava.byggbrekker.phidget.outputhandlers;

import no.teamjava.byggbrekker.phidget.CanUpdateOutput;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public abstract class OutputHandler {

	private final CanUpdateOutput canUpdateOutput;
	private final int[] outputs;

	private int brokenRunCount = 0;
	private boolean lastStatus = true;

	public OutputHandler(CanUpdateOutput canUpdateOutput, int[] outputs) {
		this.canUpdateOutput = canUpdateOutput;
		this.outputs = outputs;
	}

	public void update(boolean broken) {
		if (broken && lastStatus != broken) {
			brokenRunCount = 0;
		}

		for (int output : outputs) {
			boolean condition = broken && getCondition(brokenRunCount);
			canUpdateOutput.setOutputState(output, condition);
		}

		lastStatus = broken;
		if (broken) {
			brokenRunCount ++;
		}
	}

	protected abstract boolean getCondition(long runTime);
}
