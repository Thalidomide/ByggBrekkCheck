package no.teamjava.byggbrekker.entities;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class Settings {

	public static final long CHECK_INTERVAL = 10000;

	public static final String BROKEN_BUILD_MP3_PATH = "src/main/resources/sounds/buildDown.mp3";

	public static final String HOST = "projects.knowit.no";
	public static final String GET_METHOD = "/byggmesterbob/browse/DSB";
	public static final String POST_METHOD = "/byggmesterbob/userlogin.action";

	public static Color INPUT_PANEL = new Color(255, 150, 0);
	public static Color BACKGROUND = Color.BLACK;
	public static Color BUTTON = new Color(255, 170, 0);

	public static final Font DEFAULT = new Font("Verdana", Font.PLAIN, 13);
	public static final Font BIG = new Font("Verdana", Font.PLAIN, 16);
	public static final Font HEADER = new Font("Verdana", Font.BOLD, 18);

	public final static long LIGHT_UPDATE_INTERVAL = 1000;

}
