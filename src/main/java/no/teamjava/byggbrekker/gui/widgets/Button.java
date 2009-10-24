package no.teamjava.byggbrekker.gui.widgets;

import javax.swing.JButton;

import no.teamjava.byggbrekker.entities.Settings;

/**
 * @author Olav Jensen
 * @since 24.okt.2009
 */
public class Button extends JButton {

	public Button(String text) {
		super(text);
		setStyles();
	}

	private void setStyles() {
		setFont(Settings.DEFAULT);
		setBackground(Settings.BUTTON);
	}
}
