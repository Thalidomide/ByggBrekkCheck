package no.teamjava.byggbrekker.phidget.outputhandlers;

import no.teamjava.byggbrekker.phidget.CanUpdateOutput;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public class ConstantFlasher extends OutputFlasher {

	private final int onDuration;
	private final int offDuration;
	private final int phase;

	public ConstantFlasher(CanUpdateOutput canUpdateOutput, int[] outputs, int onDuration, int offDuration, int phase) {
		super(canUpdateOutput, outputs);
		this.onDuration = onDuration;
		this.offDuration = offDuration;
		this.phase = phase;
	}

	@Override
	protected boolean getConditionAtBrokenTime(long runTime) {
		int totalDur = onDuration + offDuration;
		return (runTime + phase) % (totalDur) < onDuration;
	}
}
