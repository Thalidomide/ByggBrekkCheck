package no.teamjava.byggbrekker.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author olj
 * @since 24.mar.2010
 */
public class AnimatedColor {

	private final ColorValue red, green, blue;
	private List<ColorValue> animationSequence = new ArrayList<ColorValue>();
	private int animating;

	public AnimatedColor(int initialRed, int initialGreen, int initialBlue) {
		red = new ColorValue(initialRed);
		green = new ColorValue(initialGreen);
		blue = new ColorValue(initialBlue);

		animationSequence.add(red);
		animationSequence.add(red);
		animationSequence.add(green);
		animationSequence.add(green);

		animating = 0;
	}

	public Color getNext() {
		animate();

		return new Color(red.getValue(), green.getValue(), blue.getValue());
	}

	private void animate() {
		boolean hitMaxLow = animationSequence.get(animating).animate();
		if (hitMaxLow) {
			setNextToAnimate();
		}
	}

	private void setNextToAnimate() {
		animating++;
		if (animating >= animationSequence.size()) {
			animating = 0;
		}
	}
}
class ColorValue {
	private final int baseValue, minValue, maxValue;
	private final static int hiLowRange = 100;
	private final static int step = 1;

	private int value;
	private boolean up = true;

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

	private int getMaxValue() {
		int res = baseValue + hiLowRange;
		return res <= 255 ? res : 255;
	}

	private int getMinValue() {
		int res = baseValue - hiLowRange;
		return res >= 0 ? res : 255;
	}
}
