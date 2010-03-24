package no.teamjava.byggbrekker.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import no.teamjava.byggbrekker.entities.Build;
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

