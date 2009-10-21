package no.teamjava.byggbrekker.logic;

import no.teamjava.byggbrekker.entities.BuildCheckResult;
import no.teamjava.byggbrekker.entities.Credentials;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public interface CheckerListener {

	void gotStatus(BuildCheckResult result);

	void stop();

	Credentials getCredentials();
}
