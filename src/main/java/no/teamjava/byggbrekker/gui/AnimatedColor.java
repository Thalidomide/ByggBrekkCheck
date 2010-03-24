package no.teamjava.byggbrekker.gui;

import java.awt.Color;

/**
 * @author olj
 * @since 24.mar.2010
 */
public class AnimatedColor {

	private final int initialRed, initialGreen, initialBlue;
	private int red, green, blue;

	private boolean up = true;
	private int step = 5;

	public AnimatedColor(int initialRed, int initialGreen, int initialBlue) {
		this.initialRed = initialRed;
		this.initialGreen = initialGreen;
		this.initialBlue = initialBlue;

		red = initialRed;
		green = initialGreen;
		blue = initialBlue;
	}

	public Color getNext() {
		if (up) {
			red += step;
			if (red >= 255) {
				red = 255;
				up = false;
			}
		} else {
			red -= step;
			if (red <= 0) {
				red = 0;
				up = true;
			}
		}

		return new Color(red, green, blue);
	}
}
