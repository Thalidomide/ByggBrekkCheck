package no.teamjava.byggbrekker.phidget.outputhandlers;

import no.teamjava.byggbrekker.phidget.CanUpdateOutput;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public class ConstantFlasher extends OutputFlasher {

	private final int duration;
	private final int phase;

	public ConstantFlasher(CanUpdateOutput canUpdateOutput, int[] outputs, int duration, int phase) {
		super(canUpdateOutput, outputs);
		this.duration = duration;
		this.phase = phase;
	}

	@Override
	protected boolean getConditionAtBrokenTime(long runTime) {
		return (runTime + phase) % (duration * 2) < duration;
	}
}
