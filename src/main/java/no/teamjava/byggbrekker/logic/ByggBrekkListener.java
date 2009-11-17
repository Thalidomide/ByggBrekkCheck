package no.teamjava.byggbrekker.logic;

import java.util.ArrayList;

import no.teamjava.byggbrekker.entities.Build;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public interface ByggBrekkListener {
	void startCheckStatus();

	void stopCheckStatus();

	void setDemoMode(ArrayList<Build> demoBuilds, boolean demoDefault);
}
