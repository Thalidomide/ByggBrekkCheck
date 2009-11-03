package no.teamjava.byggbrekker.phidget.outputhandlers;

import junit.framework.TestCase;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public class ConstantFlasherTest extends TestCase {

	public void testGetCondition() {
		ConstantFlasher flasher0 = new ConstantFlasher(null, new int[]{1}, 1, 0);
		ConstantFlasher flasher1 = new ConstantFlasher(null, new int[]{1}, 1, 1);

		assertEquals(true, flasher0.getCondition(0));
		assertEquals(false, flasher1.getCondition(0));

		assertEquals(false, flasher0.getCondition(1));
		assertEquals(true, flasher1.getCondition(1));

		flasher0 = new ConstantFlasher(null, new int[]{1}, 3, 0);
		flasher1 = new ConstantFlasher(null, new int[]{1}, 3, 1);

		assertEquals(true, flasher0.getCondition(0));
		assertEquals(true, flasher1.getCondition(0));

		assertEquals(true, flasher0.getCondition(1));
		assertEquals(true, flasher1.getCondition(1));

		assertEquals(true, flasher0.getCondition(2));
		assertEquals(false, flasher1.getCondition(2));

		assertEquals(false, flasher0.getCondition(3));
		assertEquals(false, flasher1.getCondition(3));

		assertEquals(false, flasher0.getCondition(4));
		assertEquals(false, flasher1.getCondition(4));

		assertEquals(false, flasher0.getCondition(5));
		assertEquals(true, flasher1.getCondition(5));

		assertEquals(true, flasher0.getCondition(6));
		assertEquals(true, flasher1.getCondition(6));
	}
}
