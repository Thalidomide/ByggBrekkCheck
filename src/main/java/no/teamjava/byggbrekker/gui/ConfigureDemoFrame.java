package no.teamjava.byggbrekker.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildStatus;
import no.teamjava.byggbrekker.entities.BuildType;
import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.gui.widgets.Button;
import no.teamjava.byggbrekker.gui.widgets.CheckBox;
import no.teamjava.byggbrekker.gui.widgets.InputPanel;
import no.teamjava.byggbrekker.gui.widgets.Label;

/**
 * @author Olav Jensen
 * @since 17.nov.2009
 */
public class ConfigureDemoFrame extends JFrame {

	private List<BuildConfigureRow> rows = new ArrayList<BuildConfigureRow>();

	public ConfigureDemoFrame() throws HeadlessException {
		setLocation(50, 50);
		setSize(400, 350);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);

		initializeGui();
	}

	private void initializeGui() {
		InputPanel panel = new InputPanel(new GridBagLayout());
		getContentPane().add(panel);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(1, 10, 1, 10);

		int y = 0;

		panel.add(new Label(""), constraints);
		panel.add(new Label("Brukket"), constraints);
		panel.add(new Label("Bygger"), constraints);
		y++;

		for (BuildType type : BuildType.values()) {
			constraints.gridy = y++;
			BuildConfigureRow row = new BuildConfigureRow(type);

			panel.add(row.getLabel(), constraints);
			panel.add(row.getFailed(), constraints);
			panel.add(row.getBuilding(), constraints);

			rows.add(row);
		}

		constraints.gridy = y;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(20, 0, 0, 0);
		panel.add(getOkButton(), constraints);
	}

	private Button getOkButton() {
		Button confirmButton = new Button("Lagre");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		return confirmButton;
	}

	public void configureBuilds() {
		setVisible(true);
	}

	public ArrayList<Build> getDemoBuilds() {
		ArrayList<Build> list = new ArrayList<Build>(rows.size());

		for (BuildConfigureRow row : rows) {
			list.add(row.getBuild());
		}

		return list;
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
