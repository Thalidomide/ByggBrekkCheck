package no.teamjava.byggbrekker.entities;

import java.util.List;

/**
 * @author Olav Jensen
 * @since 09.nov.2009
 */
public class BuildUtil {

	public static boolean isBroken(BuildCategory category, List<Build> builds) {
		for (Build build : builds) {
			if (category.equals(build.getType().getCategory())) {
				return true;
			}
		}
		return false;
	}
}
