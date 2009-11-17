package no.teamjava.byggbrekker.gui.widgets;

import javax.swing.JCheckBox;

import no.teamjava.byggbrekker.entities.Settings;

/**
 * @author Olav Jensen
 * @since 17.nov.2009
 */
public class CheckBox extends JCheckBox {

	public CheckBox() {
		initialize();
	}

	private void initialize() {
		setBackground(Settings.INPUT_PANEL);
		setForeground(Settings.BUTTON);
	}
}
