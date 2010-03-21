package no.teamjava.byggbrekker.logic;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCheckResult;
import no.teamjava.byggbrekker.entities.BuildCheckResultProvider;
import no.teamjava.byggbrekker.entities.BuildCheckStatus;
import no.teamjava.byggbrekker.entities.Credentials;
import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.parser.BuildParser;

/**
 * @author Olav Jensen
 * @since Mar 21, 2010
 */
public class BobHtmlParser implements BuildCheckResultProvider {

	private BuildParser parser;
	private CredentialsProvider credentialsProvider;

	public BobHtmlParser(CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		parser = new BuildParser();
	}

	@Override
	public BuildCheckResult getResult() {
		String responeBodyAsString = null;
		HttpClient client = new HttpClient();

		HostConfiguration configuration = new HostConfiguration();

		configuration.setHost(Settings.HOST);

		client.setHostConfiguration(configuration);

		GetMethod getMethod = new GetMethod(Settings.GET_METHOD);
		PostMethod postMethod = new PostMethod(Settings.POST_METHOD);

		Credentials credentials = credentialsProvider.getCredentials();

		postMethod.setParameter("checkBoxFields", "os_cookie");
		postMethod.setParameter("os_destination", "/start.action");
		postMethod.setParameter("os_username", credentials.getUsername());
		postMethod.setParameter("os_password", credentials.getPassword());
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

		BuildCheckStatus status;
		List<Build> buildList = new ArrayList<Build>();

		if (responeBodyAsString == null) {
			status = BuildCheckStatus.CHECK_FAILED;
		} else if (responeBodyAsString.contains("Login")) {
			status = BuildCheckStatus.AUTHORIZATION_FAILED;
		} else {
			status = BuildCheckStatus.OK;
			buildList = parser.checkBuilds(responeBodyAsString);
		}

		return new BuildCheckResult(buildList, status);
	}
}
