package no.teamjava.byggbrekker.logic;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class Credentials {

	String username, password;

	public Credentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
