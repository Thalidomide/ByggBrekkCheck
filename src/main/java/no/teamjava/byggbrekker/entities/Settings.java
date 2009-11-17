package no.teamjava.byggbrekker.entities;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class Settings {

	public static final long CHECK_INTERVAL = 10000;

	public static final String BROKEN_BUILD_MP3_PATH = "C:/jukebox/";

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

	public final static int OUPUT_COUNT = 16; // The number of outputs. These will be reset on exit
	public final static int[] OUTPUTS_IMPORTANT0 = new int[]{0};
	public final static int[] OUTPUTS_IMPORTANT1 = new int[]{1};
	public final static int[] OUTPUTS_MINOR = new int[]{5};
	public final static int[] OUTPUTS_OK = new int[]{7};

	public static final List<BuildType> DEFAULT_BROKEN_DEMO_BUILDS = Arrays.asList(BuildType.DEFAULT);
	public static final List<BuildType> DEFAULT_BUILDING_DEMO_BUILDS = Arrays.asList(BuildType.DEFAULT);
}
