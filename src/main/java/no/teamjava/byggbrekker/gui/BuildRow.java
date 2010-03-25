package no.teamjava.byggbrekker.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCategory;
import no.teamjava.byggbrekker.entities.BuildType;
import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.gui.widgets.Label;
import no.teamjava.byggbrekker.gui.widgets.LabelType;

/**
 * @author olj
 * @since 23.mar.2010
 */
public class BuildRow extends JPanel {

	private Label label;
	private Color OK_IMPORTANT = new Color(20, 150, 0);
	private Color OK_MINOR = new Color(10, 100, 0);
	private Color FAILURE_IMPORTANT = new Color(150, 50, 0);
	private Color FAILURE_MINOR = new Color(100, 30, 0);
	private Color UNKNOWN_IMPORTANT = new Color(0, 50, 150);
	private Color UNKNOWN_MINOR = new Color(0, 30, 100);

	private Build build;
	private int borderThickness = 5;
	private boolean initializedSizes;

	private BuildingIndication buildingIndicationLeft;
	private BuildingIndication buildingIndicationRight;
	private JPanel labelPanel;


	BuildRow() {
		label = new Label("", LabelType.BIG);
		label.setForeground(Color.WHITE);
		buildingIndicationLeft = new BuildingIndication(BuildingIndication.Style.TOP_RIGHT_TO_BOTTOM_LEFT);
		buildingIndicationRight = new BuildingIndication(BuildingIndication.Style.TOP_LEFT_TO_BOTTOM_RIGHT);

		initializeGui();
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
		labelPanel.setBackground(color);
		buildingIndicationLeft.setBackground(color);
		buildingIndicationRight.setBackground(color);
	}

	private void initializeGui() {
		setLayout(null);
		setBorder(new LineBorder(Settings.BACKGROUND, borderThickness));

		labelPanel = new JPanel();
		labelPanel.setBackground(Settings.BACKGROUND);

		labelPanel.add(label);

		add(buildingIndicationLeft);
		add(labelPanel);
		add(buildingIndicationRight);
	}

	private void setupSizes() {
		int height = getHeight() - borderThickness * 2;
		int width = getWidth();
		int buildingWidth = (int) (height * 3.5);
		int infoWidth = width - 2 * buildingWidth + 1;

		buildingIndicationLeft.setBounds(0, borderThickness, buildingWidth, height);
		labelPanel.setBounds(buildingWidth, borderThickness, infoWidth, height);
		buildingIndicationRight.setBounds(width - buildingWidth, borderThickness, buildingWidth, height);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (!initializedSizes) {
			setupSizes();
			initializedSizes = true;
		}

		if (build != null && build.isBuilding()) {
			buildingIndicationLeft.showIndication();
			buildingIndicationRight.showIndication();
		} else {
			buildingIndicationLeft.hideIndication();
			buildingIndicationRight.hideIndication();
		}
	}

	public void reset() {
		build = null;
		repaint();
		setBackground(Settings.BACKGROUND);
		label.setText("");
	}
}
