package no.teamjava.byggbrekker.phidget.outputhandlers;

import no.teamjava.byggbrekker.phidget.CanUpdateOutput;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public abstract class OutputHandler {

	final CanUpdateOutput canUpdateOutput;
	final int[] outputs;

	public OutputHandler(CanUpdateOutput canUpdateOutput, int[] outputs) {
		this.canUpdateOutput = canUpdateOutput;
		this.outputs = outputs;
	}

	public void update(long runTime, boolean broken) {
		for (int output : outputs) {
			boolean condition = broken && getCondition(runTime);
			canUpdateOutput.setOutputState(output, condition);
		}
	}

	protected abstract boolean getCondition(long runTime);
}
