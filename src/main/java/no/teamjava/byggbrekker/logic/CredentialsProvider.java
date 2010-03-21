package no.teamjava.byggbrekker.logic;

import no.teamjava.byggbrekker.entities.Credentials;

/**
 * @author Olav Jensen
 * @since Mar 21, 2010
 */
public interface CredentialsProvider {

	Credentials getCredentials();
}
