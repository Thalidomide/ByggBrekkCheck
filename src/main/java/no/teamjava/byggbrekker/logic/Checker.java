package no.teamjava.byggbrekker.logic;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author Olav Jensen, Raymond Koteng
 * @since 21.okt.2009
 */
public class Checker extends Thread {

	private boolean run = true;
	private CheckerListener listener;

	public Checker(CheckerListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		while (run) {
			listener.gotStatus(getBobStatus());
			try {
				sleep(Settings.CHECK_INTERVAL);
			} catch (InterruptedException e) {
				run = false;
			}
		}
	}

	public void stopChecking() {
		run = false;
		interrupt();
		listener.stop();
	}

	private CheckStatus getBobStatus() {
		String responeBodyAsString = null;
		HttpClient client = new HttpClient();

		HostConfiguration configuration = new HostConfiguration();
		configuration.setHost("projects.knowit.no");

		client.setHostConfiguration(configuration);

		GetMethod getMethod = new GetMethod("/byggmesterbob/browse/DSB");

		PostMethod postMethod = new PostMethod("/byggmesterbob/userlogin.action");

		postMethod.setParameter("checkBoxFields", "os_cookie");
		postMethod.setParameter("os_destination", "/start.action");
		postMethod.setParameter("os_username", listener.getCredentials().getUsername());
		postMethod.setParameter("os_password", listener.getCredentials().getPassword());
		postMethod.setParameter("save", "Login");

		try {
			responeBodyAsString = getMethod.getResponseBodyAsString();

			client.executeMethod(postMethod);
			client.executeMethod(getMethod);

			responeBodyAsString = getMethod.getResponseBodyAsString();
//			System.out.println(responeBodyAsString);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (responeBodyAsString == null) {
			return CheckStatus.unknown;
		} else if (responeBodyAsString.contains("Login")) {
			return CheckStatus.authorizationFailed;
		} else if (responeBodyAsString.contains("Failed")) {
			return CheckStatus.bad;
		} else {
			return CheckStatus.good;
		}
	}
}
