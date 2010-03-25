package no.teamjava.byggbrekker.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
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
	private List<AnimatedColor> animatedColors = new ArrayList<AnimatedColor>();
	private final int sectionElements = 20;

	private boolean show = false;

	public BuildingIndication(Style style) {
		this.style = style;
		setBackground(Settings.BACKGROUND);

		for (int i = 0; i < sectionElements; i++) {
			animatedColors.add(new AnimatedColor(255, 255, 0, i * 2));
		}
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

		int topBottomSpace = 0;
		int width = getWidth();

		int x = 0;
		int yTop = topBottomSpace;
		int yBottom = getHeight() - topBottomSpace;
		int shift = (int) (width / 3.5);
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
		int sectionHeight = yBottom - yTop;
		int sectionShift = shift / sectionElements;
		for (int i = 0; i < sectionElements; i++) {
			int curX = (int) Math.round(x + (double) shift * i / sectionElements);
			int curYTop = (int) Math.round((double) sectionHeight * (sectionElements - i - 1) / sectionElements);
			int curYBottom = (int) Math.round((double) sectionHeight * (sectionElements - i) / sectionElements);

			drawSection(i, g, curX, width, sectionShift, curYTop, curYBottom);
		}

		int[] xPoints = new int[]{x, x + width, x + width + shift, x + shift};
		int[] yPoints = new int[]{yBottom, yBottom, yTop, yTop};
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);
		g.drawPolygon(xPoints, yPoints, 4);
	}

	private void drawSection(int sectionNr, Graphics g, int x, int width, int shift, int yTop, int yBottom) {
		int[] xPoints = new int[]{x, x + width, x + width + shift, x + shift};
		int[] yPoints = new int[]{yBottom, yBottom, yTop, yTop};
		
		g.setColor(animatedColors.get(sectionNr).getNext());
		g.fillPolygon(xPoints, yPoints, 4);
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
