package no.teamjava.byggbrekker.gui.widgets;

import java.awt.Font;
import javax.swing.JLabel;

/**
 * @author Olav Jensen
 * @since 24.okt.2009
 */
public class Label extends JLabel {

	public Label(String text) {
		this(text, LabelType.DEFAULT);
	}

	public Label(String text, LabelType type) {
		super(text);
		setStyles(type.getFont());
	}

	private void setStyles(Font font) {
		setFont(font);
	}
}
