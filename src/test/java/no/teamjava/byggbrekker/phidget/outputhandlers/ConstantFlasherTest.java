package no.teamjava.byggbrekker.phidget.outputhandlers;

import junit.framework.TestCase;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public class ConstantFlasherTest extends TestCase {

	public void testGetConditionEqualOnOff() {
		ConstantFlasher flasher0 = new ConstantFlasher(null, new int[]{1}, 1, 1, 0);
		ConstantFlasher flasher1 = new ConstantFlasher(null, new int[]{1}, 1, 1, 1);

		assertEquals(true, flasher0.getConditionAtBrokenTime(0));
		assertEquals(false, flasher1.getConditionAtBrokenTime(0));

		assertEquals(false, flasher0.getConditionAtBrokenTime(1));
		assertEquals(true, flasher1.getConditionAtBrokenTime(1));

		flasher0 = new ConstantFlasher(null, new int[]{1}, 3, 1, 0);
		flasher1 = new ConstantFlasher(null, new int[]{1}, 3, 1, 1);

		assertEquals(true, flasher0.getConditionAtBrokenTime(0));
		assertEquals(true, flasher1.getConditionAtBrokenTime(0));

		assertEquals(true, flasher0.getConditionAtBrokenTime(1));
		assertEquals(true, flasher1.getConditionAtBrokenTime(1));

		assertEquals(true, flasher0.getConditionAtBrokenTime(2));
		assertEquals(false, flasher1.getConditionAtBrokenTime(2));

		assertEquals(false, flasher0.getConditionAtBrokenTime(3));
		assertEquals(false, flasher1.getConditionAtBrokenTime(3));

		assertEquals(false, flasher0.getConditionAtBrokenTime(4));
		assertEquals(false, flasher1.getConditionAtBrokenTime(4));

		assertEquals(false, flasher0.getConditionAtBrokenTime(5));
		assertEquals(true, flasher1.getConditionAtBrokenTime(5));

		assertEquals(true, flasher0.getConditionAtBrokenTime(6));
		assertEquals(true, flasher1.getConditionAtBrokenTime(6));
	}

	public void testGetConditionDifferentOnOff() {
		ConstantFlasher flasher0 = new ConstantFlasher(null, new int[]{1}, 3, 1, 0);
		ConstantFlasher flasher1 = new ConstantFlasher(null, new int[]{1}, 3, 1, 1);

		assertEquals(true, flasher0.getConditionAtBrokenTime(0));
		assertEquals(true, flasher1.getConditionAtBrokenTime(0));

		assertEquals(true, flasher0.getConditionAtBrokenTime(1));
		assertEquals(true, flasher1.getConditionAtBrokenTime(1));

		assertEquals(true, flasher0.getConditionAtBrokenTime(2));
		assertEquals(false, flasher1.getConditionAtBrokenTime(2));

		assertEquals(false, flasher0.getConditionAtBrokenTime(3));
		assertEquals(true, flasher1.getConditionAtBrokenTime(3));

		assertEquals(true, flasher0.getConditionAtBrokenTime(4));
		assertEquals(true, flasher1.getConditionAtBrokenTime(4));

		assertEquals(true, flasher0.getConditionAtBrokenTime(5));
		assertEquals(true, flasher1.getConditionAtBrokenTime(5));

		assertEquals(true, flasher0.getConditionAtBrokenTime(6));
		assertEquals(false, flasher1.getConditionAtBrokenTime(6));

		assertEquals(false, flasher0.getConditionAtBrokenTime(7));
		assertEquals(true, flasher1.getConditionAtBrokenTime(7));
	}
}
