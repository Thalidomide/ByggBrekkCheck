package no.teamjava.byggbrekker.logic;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public interface CheckerListener {

	void gotStatus(BuildStatus status);

	void stop();

	Credentials getCredentials();
}
