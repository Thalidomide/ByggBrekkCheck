package no.teamjava.byggbrekker.phidget.outputhandlers;

import no.teamjava.byggbrekker.phidget.CanUpdateOutput;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public class ConstantFlasher extends OutputHandler {

	private final long phase;

	public ConstantFlasher(CanUpdateOutput canUpdateOutput, int[] outputs, long phase) {
		super(canUpdateOutput, outputs);
		this.phase = phase;
	}

	@Override
	protected boolean getCondition(long runTime) {
		return runTime % 2 == phase;
	}
}
