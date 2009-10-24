package no.teamjava.byggbrekker.gui.widgets;

import java.awt.Font;

import no.teamjava.byggbrekker.entities.Settings;

/**
 * @author Olav Jensen
 * @since 24.okt.2009
 */
public enum LabelType {
	DEFAULT(Settings.DEFAULT),
	BIG(Settings.BIG),
	HEADER(Settings.HEADER);
	private Font font;


	private LabelType(Font font) {
		this.font = font;
	}

	public Font getFont() {
		return font;
	}
}
