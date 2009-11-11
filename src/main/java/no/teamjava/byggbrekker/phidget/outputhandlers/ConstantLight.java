package no.teamjava.byggbrekker.phidget.outputhandlers;

import no.teamjava.byggbrekker.phidget.CanUpdateOutput;

/**
 * @author Olav Jensen
 * @since 09.nov.2009
 */
public class ConstantLight extends OutputHandler {

	public static enum LightWhenCondition{BROKEN, OK, ALWAYS}

	private final LightWhenCondition lightWhenCondition;


	public ConstantLight(CanUpdateOutput canUpdateOutput, int[] outputs, LightWhenCondition lightWhenBroken) {
		super(canUpdateOutput, outputs);
		this.lightWhenCondition = lightWhenBroken;
	}

	@Override
	protected boolean getCondition(boolean broken) {
		switch (lightWhenCondition) {
			case ALWAYS:
				return true;
			case OK:
				return !broken;
			case BROKEN:
				return broken;
			default:
				throw new RuntimeException("Unhandeled condition: " + lightWhenCondition);
		}
	}
}
