package no.teamjava.byggbrekker.logic;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import no.teamjava.byggbrekker.entities.BuildCheckResult;
import no.teamjava.byggbrekker.entities.BuildCheckStatus;
import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.parser.BuildParser;

/**
 * @author Olav Jensen, Raymond Koteng
 * @since 21.okt.2009
 */
public class BuildChecker extends Thread {

	private boolean run = true;
	private CheckerListener listener;
	private BuildParser parser;

	public BuildChecker(CheckerListener listener) {
		this.listener = listener;
		parser = new BuildParser();
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

	private BuildCheckResult getBobStatus() {
		String responeBodyAsString = null;
		HttpClient client = new HttpClient();

		HostConfiguration configuration = new HostConfiguration();

		configuration.setHost(Settings.HOST);

		client.setHostConfiguration(configuration);

		GetMethod getMethod = new GetMethod(Settings.GET_METHOD);
		PostMethod postMethod = new PostMethod(Settings.POST_METHOD);

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

		BuildCheckResult result = new BuildCheckResult();
		BuildCheckStatus status;

		if (responeBodyAsString == null) {
			status = BuildCheckStatus.CHECK_FAILED;
		} else if (responeBodyAsString.contains("Login")) {
			status = BuildCheckStatus.AUTHORIZATION_FAILED;
		} else {
			status = BuildCheckStatus.OK;
			result.setBuilds(parser.checkBuilds(responeBodyAsString));
		}
		result.setBuildCheckStatus(status);
		return result;
	}
}
