package no.teamjava.byggbrekker.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCategory;
import no.teamjava.byggbrekker.entities.BuildType;

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
		Label label = new Label("Noe gikk galt ved sjekking av status!");
		label.setFont(new Font("Verdana", Font.BOLD, 18));
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

	private JLabel label;
	private Color OK_IMPORTANT = new Color(20, 150, 0);
	private Color OK_MINOR = new Color(10, 100, 0);
	private Color FAILURE_IMPORTANT = new Color(150, 50, 0);
	private Color FAILURE_MINOR = new Color(100, 30, 0);

	BuildRow() {
		label = new JLabel("IIT");
		label.setFont(new Font("Verdana", Font.PLAIN, 18));
		label.setForeground(Color.WHITE);

		setBackground(Color.YELLOW);
		setBorder(new BevelBorder(BevelBorder.RAISED, Color.gray, Color.DARK_GRAY));

		add(label);
	}

	public void setBuild(Build build) {
		if (build == null) {
			reset();
			return;
		}

		BuildType buildType = build.getBuildType();
		boolean important = BuildCategory.IMPORTANT.equals(buildType.getCategory());

		Color color;

		if (build.isSuccessful()) {
			color = important ? OK_IMPORTANT : OK_MINOR;
		} else {
			color = important ? FAILURE_IMPORTANT : FAILURE_MINOR;
		}

		label.setText(buildType.getText());

		setBackground(color);
	}

	public void reset() {
		setBackground(Color.BLACK);
		label.setText("");
	}
}
