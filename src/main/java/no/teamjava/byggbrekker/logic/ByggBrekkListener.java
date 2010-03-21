package no.teamjava.byggbrekker.logic;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public interface ByggBrekkListener {

	void startCheck();

	void stopCheck();

	void startDemo();

	void stopDemo();

	void exit();
}
