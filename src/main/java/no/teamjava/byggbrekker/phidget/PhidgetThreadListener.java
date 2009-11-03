package no.teamjava.byggbrekker.phidget;

import java.util.List;

import no.teamjava.byggbrekker.entities.Build;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public interface PhidgetThreadListener {

	List<Build> getFailedBuilds();
}
