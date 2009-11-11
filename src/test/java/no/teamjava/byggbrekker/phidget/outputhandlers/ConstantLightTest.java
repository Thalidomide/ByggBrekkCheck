package no.teamjava.byggbrekker.phidget.outputhandlers;

import junit.framework.TestCase;

/**
 * @author Olav Jensen
 * @since 09.nov.2009
 */
public class ConstantLightTest extends TestCase {

	public void testGetCondition() {
		int[] outputs = {0};
		ConstantLight light = new ConstantLight(null, outputs, ConstantLight.LightWhenCondition.OK);

		assertEquals(true, light.getCondition(false));
		assertEquals(false, light.getCondition(true));

		light = new ConstantLight(null, outputs, ConstantLight.LightWhenCondition.BROKEN);

		assertEquals(true, light.getCondition(true));
		assertEquals(false, light.getCondition(false));

		light = new ConstantLight(null, outputs, ConstantLight.LightWhenCondition.ALWAYS);

		assertEquals(true, light.getCondition(true));
		assertEquals(true, light.getCondition(false));
	}
}
