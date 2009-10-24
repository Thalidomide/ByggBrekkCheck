package no.teamjava.byggbrekker.gui;

import java.awt.Color;
import java.awt.GridLayout;
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

	public StatusPanel() {
		int rowCount = BuildType.values().length;
		setLayout(new GridLayout(rowCount, 1));

		for (int i = 0; i < rowCount; i++) {
			BuildRow row = new BuildRow();

			rows.add(row);
			add(row);
		}
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

	public void displayFailedCheck() {
		reset();
		Label label = new Label("Noe gikk galt ved sjekking av status!", LabelType.BIG);
		label.setForeground(Color.YELLOW);

		add(label);
	}

	public void reset() {
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

	BuildRow() {
		label = new Label("", LabelType.BIG);
		label.setForeground(Color.WHITE);

		setBackground(Settings.BACKGROUND);
		setBorder(new LineBorder(Settings.BACKGROUND, 5));

		add(label);
	}

	public void setBuild(Build build) {
		if (build == null) {
			reset();
			return;
		}

		BuildType buildType = build.getType();
		boolean important = BuildCategory.IMPORTANT.equals(buildType.getCategory());

		Color color;
		String postFix = "";

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

	public void reset() {
		setBackground(Settings.BACKGROUND);
		label.setText("");
	}
}