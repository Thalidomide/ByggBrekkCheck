package no.teamjava.byggbrekker.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import no.teamjava.byggbrekker.entities.Settings;

/**
 * @author olj
 * @since 23.mar.2010
 */
public class BuildingIndication extends JPanel {

	public static enum Style{TOP_LEFT_TO_BOTTOM_RIGHT, TOP_RIGHT_TO_BOTTOM_LEFT}

	private PaintTimer timer;
	private final Style style;
	private AnimatedColor animatedColor = new AnimatedColor(255, 255, 0);
	private boolean show = false;

	public BuildingIndication(Style style) {
		this.style = style;
		setBackground(Settings.BACKGROUND);
		timer = new PaintTimer();
		timer.start();
	}

	public void showIndication() {
		show = true;
		if (timer == null) {
			timer = new PaintTimer();
			timer.start();
		}
	}

	public void hideIndication() {
		show = false;
		if (timer != null) {
			timer.stopTimer();
			timer = null;
		}
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (!show) {
			return;
		}

//		int topBottomSpace = borderThickness + 5;
		int topBottomSpace = 0;
		int width = getWidth();

		int x = 0;
		int yTop = topBottomSpace;
		int yBottom = getHeight() - topBottomSpace;
		int shift = (int) (width / 3.5);
//		int shift = yBottom - yTop;
		int elements = 3;
		int elementWidth = (int) (shift * 0.5);
		int elementSpace = elementWidth * 2;


		for (int i = 0; i < elements; i++) {
			if (style == Style.TOP_LEFT_TO_BOTTOM_RIGHT) {
				drawBuildingElement(g, width - x - elementWidth, elementWidth, - shift, yTop, yBottom);
			} else {
				drawBuildingElement(g, x, elementWidth, shift, yTop, yBottom);
			}

			x += elementSpace;
		}
	}

	private void drawBuildingElement(Graphics g, int x, int width, int shift, int yTop, int yBottom) {
		int[] xPoints = new int[]{x, x + width, x + width + shift, x + shift};
		int[] yPoints = new int[]{yBottom, yBottom, yTop, yTop};

		g.setColor(animatedColor.getNext());
		g.fillPolygon(xPoints, yPoints, 4);

		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);
		g.drawPolygon(xPoints, yPoints, 4);
	}

	class PaintTimer extends Thread {

		private boolean run = true;

		@Override
		public void run() {
			try {
				while (run) {
					sleep(50);
					repaint();
				}
			} catch (InterruptedException e) {/**/}
		}

		public void stopTimer() {
			run = false;
			interrupt();
		}
	}
}
