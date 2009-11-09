package no.teamjava.byggbrekker.phidget.outputhandlers;

import no.teamjava.byggbrekker.phidget.CanUpdateOutput;

/**
 * @author Olav Jensen
 * @since 09.nov.2009
 */
public abstract class OutputHandler {

	private final CanUpdateOutput canUpdateOutput;
	private final int[] outputs;

	public OutputHandler(CanUpdateOutput canUpdateOutput, int[] outputs) {
		this.canUpdateOutput = canUpdateOutput;
		this.outputs = outputs;
	}

	public void update(boolean broken) {
		setOutputs(getCondition(broken));
	}

	protected abstract boolean getCondition(boolean broken);

	protected void setOutputs(boolean condition) {
		for (int output : outputs) {
			canUpdateOutput.setOutputState(output, condition);
		}
	}
}
