package no.teamjava.byggbrekker.gui;

import java.awt.Color;

/**
 * @author olj
 * @since 24.mar.2010
 */
public class AnimatedColor {

	private final ColorValue red, green, blue;
	private ColorValue animating;

	public AnimatedColor(int initialRed, int initialGreen, int initialBlue) {
		red = new ColorValue(initialRed);
		green = new ColorValue(initialGreen);
		blue = new ColorValue(initialBlue);

		red.setNext(green);
		green.setNext(red);

		animating = red;
	}

	public Color getNext() {
		animate();

		return new Color(red.getValue(), green.getValue(), blue.getValue());
	}

	private void animate() {
		boolean hitMaxLow = animating.animate();
		if (hitMaxLow) {
			animating = animating.getNext();
		}
	}
}
class ColorValue {
	private final int baseValue, minValue, maxValue;
	private final static int hiLowRange = 100;
	private final static int step = 2;

	private int value;
	private boolean up = true;
	private ColorValue next;

	ColorValue(int baseValue) {
		this.baseValue = baseValue;
		minValue = getMinValue();
		maxValue = getMaxValue();
		value = baseValue;
	}

	/**
	 * @return true if hitting max/low value
	 */
	public boolean animate() {
		boolean hitMaxLow = false;

		if (up) {
			value += step;
			if (value >= maxValue) {
				value = maxValue;
				up = false;
				hitMaxLow = true;
			}
		} else {
			value -= step;
			if (value <= minValue) {
				value = minValue;
				up = true;
				hitMaxLow = true;
			}
		}
		return hitMaxLow;
	}

	public int getValue() {
		return value;
	}

	public ColorValue getNext() {
		return next;
	}

	public void setNext(ColorValue next) {
		this.next = next;
	}

	private int getMaxValue() {
		int res = baseValue + hiLowRange;
		return res <= 255 ? res : 255;
	}

	private int getMinValue() {
		int res = baseValue - hiLowRange;
		return res >= 0 ? res : 255;
	}
}
