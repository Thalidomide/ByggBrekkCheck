package no.teamjava.byggbrekker.gui.widgets;

import java.awt.LayoutManager;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import no.teamjava.byggbrekker.entities.Settings;

/**
 * @author Olav Jensen
 * @since 17.nov.2009
 */
public class InputPanel extends JPanel {

	public InputPanel() {
		init();
	}

	public InputPanel(LayoutManager layout) {
		super(layout);
		init();
	}

	private void init() {
		setBackground(Settings.INPUT_PANEL);
		setBorder(new BevelBorder(BevelBorder.LOWERED));
	}
}
