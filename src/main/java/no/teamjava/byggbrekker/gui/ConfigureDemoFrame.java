package no.teamjava.byggbrekker.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCheckResult;
import no.teamjava.byggbrekker.entities.BuildCheckResultProvider;
import no.teamjava.byggbrekker.entities.BuildCheckStatus;
import no.teamjava.byggbrekker.entities.BuildStatus;
import no.teamjava.byggbrekker.entities.BuildType;
import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.gui.widgets.CheckBox;
import no.teamjava.byggbrekker.gui.widgets.InputPanel;
import no.teamjava.byggbrekker.gui.widgets.Label;
import no.teamjava.byggbrekker.gui.widgets.LabelType;

/**
 * @author Olav Jensen
 * @since 17.nov.2009
 */
public class ConfigureDemoFrame extends JFrame implements BuildCheckResultProvider {

	private List<BuildConfigureRow> rows = new ArrayList<BuildConfigureRow>();

	public ConfigureDemoFrame() throws HeadlessException {
		setLocation(50, 50);
		setSize(400, 450);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);

		initializeGui();
	}

	private void initializeGui() {
		InputPanel panel = new InputPanel(new GridBagLayout());
		getContentPane().add(panel);

		GridBagConstraints constraints = new GridBagConstraints();

		int y = 0;

		constraints.gridy = y++;
		constraints.gridwidth = 3;
		constraints.weighty = 1;
		constraints.insets = new Insets(4, 0, 0, 0);
		panel.add(getInfoLabel(), constraints);

		constraints.gridy = y++;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(10, 10, 1, 10);
		panel.add(new Label(""), constraints);
		panel.add(new Label("Brukket"), constraints);
		panel.add(new Label("Bygger"), constraints);

		for (BuildType type : BuildType.values()) {
			constraints.gridy = y++;
			BuildConfigureRow row = new BuildConfigureRow(type);

			panel.add(row.getLabel(), constraints);
			panel.add(row.getFailed(), constraints);
			panel.add(row.getBuilding(), constraints);

			rows.add(row);
		}
	}

	private Label getInfoLabel() {
		return new Label("Status oppdateres hvert " + (Settings.CHECK_INTERVAL / 1000) + ". sekund", LabelType.HEADER);
	}

	@Override
	public BuildCheckResult getResult() {
		ArrayList<Build> list = new ArrayList<Build>(rows.size());

		for (BuildConfigureRow row : rows) {
			list.add(row.getBuild());
		}

		return new BuildCheckResult(list, BuildCheckStatus.OK);
	}
}

class BuildConfigureRow {

	private final BuildType buildType;
	private Label label;
	private CheckBox failed;
	private CheckBox building;

	public BuildConfigureRow(BuildType buildType) {
		this.buildType = buildType;

		label = new Label(buildType.getText());
		failed = new CheckBox();
		building = new CheckBox();

		failed.setSelected(Settings.DEFAULT_BROKEN_DEMO_BUILDS.contains(buildType));
		building.setSelected(Settings.DEFAULT_BUILDING_DEMO_BUILDS.contains(buildType));
	}

	public Label getLabel() {
		return label;
	}

	public JCheckBox getFailed() {
		return failed;
	}

	public JCheckBox getBuilding() {
		return building;
	}

	public Build getBuild() {
		BuildStatus status = failed.isSelected() ? BuildStatus.FAILED : BuildStatus.SUCCESSFUL;
		
		return new Build(buildType, status, building.isSelected());
	}
}
