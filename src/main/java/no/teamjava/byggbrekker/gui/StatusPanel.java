package no.teamjava.byggbrekker.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCategory;
import no.teamjava.byggbrekker.entities.BuildType;
import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.gui.widgets.Label;
import no.teamjava.byggbrekker.gui.widgets.LabelType;

/**
 * @author : Raymond Koteng, Olav Jensen
 * @since : 20.okt.2009
 */
public class StatusPanel extends JPanel {

	private List<BuildRow> rows = new ArrayList<BuildRow>();
	private Label infoLabel;

	public StatusPanel() {
		int buildCount = BuildType.values().length;
		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = 1;
		constraints.weightx = 1;
		constraints.weighty = 0.01;

		infoLabel = new Label("", LabelType.BIG);
		infoLabel.setForeground(Color.YELLOW);
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Settings.BACKGROUND);
		infoPanel.add(infoLabel);
		add(infoPanel, constraints);

		constraints.weighty = 0.1;
		int y = 1;
		for (int i = 0; i < buildCount; i++) {
			BuildRow row = new BuildRow();

			constraints.gridy = y++;
			rows.add(row);
			add(row, constraints);
		}
		displayNotRunningMessage();
	}

	public void displayBuilds(List<Build> builds) {
		if (builds.size() != rows.size()) {
			throw new RuntimeException("Invalid number of rows: " + builds.size() + ", should have been: " + rows.size());
		}

		int row = 0;
		for (Build build : builds) {
			rows.get(row++).setBuild(build);
		}
		validateTree();
	}

	public void displayCheckMessage(long time) {
		DecimalFormat df = new DecimalFormat("#.#");
		String secsUntilNextUpdate = df.format((double) time / 1000);
		displayMessage("Sjekker status (neste oppdatering om: " + secsUntilNextUpdate + " sekunder)..");
	}

	public void displayCheckErrorMessage() {
		displayMessage("Noe gikk galt ved sjekking av status!");
	}

	public void displayNotRunningMessage() {
		displayMessage("Sjekking av status er ikke startet.");
	}

	private void displayMessage(String text) {
		infoLabel.setText(text);
	}

	public void resetBuildInfo() {
		for (BuildRow row : rows) {
			row.reset();
		}
	}
}
class BuildRow extends JPanel {

	private Label label;
	private Color OK_IMPORTANT = new Color(20, 150, 0);
	private Color OK_MINOR = new Color(10, 100, 0);
	private Color FAILURE_IMPORTANT = new Color(150, 50, 0);
	private Color FAILURE_MINOR = new Color(100, 30, 0);
	private Color UNKNOWN_IMPORTANT = new Color(0, 50, 150);
	private Color UNKNOWN_MINOR = new Color(0, 30, 100);

	private Build build;
	private int borderThickness = 5;


	BuildRow() {
		label = new Label("", LabelType.BIG);
		label.setForeground(Color.WHITE);

		setBackground(Settings.BACKGROUND);
		setBorder(new LineBorder(Settings.BACKGROUND, borderThickness));

		add(label);
	}

	public void setBuild(Build build) {
		this.build = build;
		if (build == null) {
			reset();
			return;
		}

		repaint();

		BuildType buildType = build.getType();
		boolean important = BuildCategory.IMPORTANT.equals(buildType.getCategory());

		Color color;
		String postFix = build.isBuilding() ? " (Bygger...)" : "";

		switch (build.getStatus()) {
			case SUCCESSFUL:
				color = important ? OK_IMPORTANT : OK_MINOR;
				break;
			case FAILED:
				color = important ? FAILURE_IMPORTANT : FAILURE_MINOR;
				break;
			case UNKNOWN:
				color = important ? UNKNOWN_IMPORTANT : UNKNOWN_MINOR;
				postFix = " - Ukjent status!!";
				break;
			default:
				throw new RuntimeException("Unhandeled status: " + build.getStatus());
		}

		label.setText(buildType.getText() + postFix);

		setBackground(color);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (build != null && build.isBuilding()) {
			drawBuildingGui(g);
		}
	}

	private void drawBuildingGui(Graphics g) {
		int topBottomSpace = borderThickness + 5;
		int width = getWidth();

		int x = 20;
		int yTop = topBottomSpace;
		int yBottom = getHeight() - topBottomSpace;
		int shift = yBottom - yTop;
		int elements = 3;
		int elementWidth = (int) (shift * 0.5);
		int elementSpace = elementWidth * 2;

		for (int i = 0; i < elements; i++) {
			drawBuildingElement(g, x, elementWidth, shift, yTop, yBottom);
			drawBuildingElement(g, width - x - elementWidth, elementWidth, - shift, yTop, yBottom);

			x += elementSpace;
		}
	}

	private void drawBuildingElement(Graphics g, int x, int width, int shift, int yTop, int yBottom) {
		int[] xPoints = new int[]{x, x + width, x + width + shift, x + shift};
		int[] yPoints = new int[]{yBottom, yBottom, yTop, yTop};

		g.setColor(Color.yellow);
		g.fillPolygon(xPoints, yPoints, 4);

		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);
		g.drawPolygon(xPoints, yPoints, 4);
	}

	public void reset() {
		build = null;
		repaint();
		setBackground(Settings.BACKGROUND);
		label.setText("");
	}
}
