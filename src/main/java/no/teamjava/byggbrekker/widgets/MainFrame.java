package no.teamjava.byggbrekker.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JFrame;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCheckResult;
import no.teamjava.byggbrekker.entities.Credentials;
import no.teamjava.byggbrekker.logic.BuildChecker;
import no.teamjava.byggbrekker.logic.ByggBrekkListener;
import no.teamjava.byggbrekker.logic.CheckerListener;
import no.teamjava.byggbrekker.logic.PlayerThread;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class MainFrame extends JFrame implements ByggBrekkListener, CheckerListener, CredentialsFrameListener {
	private StatusPanel statusPanel;
	private BuildChecker buildChecker;
	private Credentials credentials;

	private PlayerThread playerThread;
	private StartCheckPanel startCheckPanel;

	public MainFrame() throws HeadlessException {
		addGUI();

		getNewCredentials();
	}

	private void getNewCredentials() {
		new CredentialsFrame(this);
	}

	private void addGUI() {
		startCheckPanel = new StartCheckPanel(this);
		statusPanel = new StatusPanel();

		this.add(startCheckPanel, BorderLayout.NORTH);
		this.add(statusPanel, BorderLayout.CENTER);

		resetStatusPanel();
	}

	@Override
	public void gotCredentials(Credentials credentials) {
		this.credentials = credentials;
		initDefaultFrameValues();
	}

	@Override
	public void startCheckStatus() {
		checkStatus();
	}

	@Override
	public void stopCheckStatus() {
		if (buildChecker != null) {
			buildChecker.stopChecking();
			buildChecker = null;
		}
		resetStatusPanel();
	}

	private void resetStatusPanel() {
		statusPanel.reset();
	}

	private void checkStatus() {
		if (buildChecker != null) {
			return;
		}
		buildChecker = new BuildChecker(this);
		buildChecker.start();
	}

	private void initDefaultFrameValues() {
		this.setSize(600, 600);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("ByggBrekkSjekker3000 - KnowIT");
		this.getContentPane().setBackground(Color.WHITE);
		this.setVisible(true);
	}

	@Override
	public void gotStatus(BuildCheckResult result) {
		switch (result.getBuildCheckStatus()) {
			case OK:
				presentResult(result);
				break;
			case AUTHORIZATION_FAILED:
				authorizationFailed();
				stopPlayer();
				break;
			case CHECK_FAILED:
				statusPanel.displayFailedCheck();
				stopPlayer();
				break;
			default:
				throw new RuntimeException("Unhandled status: " + result.getBuildCheckStatus());
		}
	}

	private void presentResult(BuildCheckResult result) {
		List<Build> failedBuilds = result.getFailedBuilds();
		if (!failedBuilds.isEmpty()) {
			startPlayer();
		}
		statusPanel.displayBuilds(result.getBuilds());
	}

	@Override
	public void stop() {
		stopPlayer();
	}

	@Override
	public Credentials getCredentials() {
		return credentials;
	}

	private void startPlayer() {
		if (playerThread == null) {
			playerThread = new PlayerThread();
			playerThread.start();
		}
	}

	private void stopPlayer() {
		if (playerThread != null) {
			playerThread.stopPlayer();
			playerThread = null;
		}
	}

	private void authorizationFailed() {
		stopCheckStatus();
		startCheckPanel.reset();
		setVisible(false);
		getNewCredentials();
	}
}
