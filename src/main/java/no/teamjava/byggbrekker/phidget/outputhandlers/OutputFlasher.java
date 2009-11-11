package no.teamjava.byggbrekker.phidget.outputhandlers;

import no.teamjava.byggbrekker.phidget.CanUpdateOutput;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public abstract class OutputFlasher extends OutputHandler {

	private int brokenRunCount = 0;
	private boolean lastStatus = true;

	public OutputFlasher(CanUpdateOutput canUpdateOutput, int[] outputs) {
		super(canUpdateOutput, outputs);
	}

	@Override
	protected boolean getCondition(boolean broken) {
		if (broken && lastStatus != broken) {
			brokenRunCount = 0;
		}

		boolean condition = broken && getConditionAtBrokenTime(brokenRunCount);

		lastStatus = broken;
		if (broken) {
			brokenRunCount ++;
		}

		return condition;
	}

	protected abstract boolean getConditionAtBrokenTime(long runTime);
}
