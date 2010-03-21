package no.teamjava.byggbrekker.logic;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public interface ByggBrekkListener {
	void startCheckStatus();

	void stopCheckStatus();

	void setDemoMode(boolean demoDefault);

	void exit();
}
