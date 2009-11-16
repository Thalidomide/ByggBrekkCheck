package no.teamjava.byggbrekker.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

import no.teamjava.byggbrekker.entities.Build;
import no.teamjava.byggbrekker.entities.BuildCategory;
import no.teamjava.byggbrekker.entities.BuildCheckResult;
import no.teamjava.byggbrekker.entities.BuildCheckStatus;
import no.teamjava.byggbrekker.entities.BuildStatus;
import no.teamjava.byggbrekker.entities.BuildType;
import no.teamjava.byggbrekker.entities.BuildUtil;
import no.teamjava.byggbrekker.entities.Credentials;
import no.teamjava.byggbrekker.logic.BuildChecker;
import no.teamjava.byggbrekker.logic.ByggBrekkListener;
import no.teamjava.byggbrekker.logic.CheckerListener;
import no.teamjava.byggbrekker.logic.PlayerThread;
import no.teamjava.byggbrekker.phidget.Phidget;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class MainFrame extends JFrame implements ByggBrekkListener, CheckerListener, CredentialsFrameListener {
	private StatusPanel statusPanel;
	private StartCheckPanel startCheckPanel;
	private CredentialsPanel credentialsPanel;

	private BuildChecker buildChecker;
	private Credentials credentials;
	private PlayerThread playerThread;
	private JPanel panel;
	private Phidget phidget;

	public MainFrame() throws HeadlessException {
		phidget = new Phidget();
		initializeGui();
		getNewCredentials();
		this.setVisible(true);
	}

	private void initializeGui() {
		panel = new JPanel(new BorderLayout());
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocation(0, 0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("ByggBrekkSjekker3001 - KnowIT");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(panel);
		setUndecorated(true);

		startCheckPanel = new StartCheckPanel(this);
		statusPanel = new StatusPanel();
		credentialsPanel = new CredentialsPanel(this);
	}

	private void addCheckerGui() {
		panel.removeAll();
		panel.add(startCheckPanel, BorderLayout.NORTH);
		panel.add(statusPanel, BorderLayout.CENTER);
		refreshPanel();
	}

	private void getNewCredentials() {
		panel.removeAll();
		panel.add(credentialsPanel, BorderLayout.CENTER);
		refreshPanel();
	}

	private void refreshPanel() {
		validateTree();
		panel.repaint();
		panel.validate();
	}

	@Override
	public void gotCredentials(Credentials credentials) {
		this.credentials = credentials;
		addCheckerGui();
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
	}

	@Override
	public void setDemoDefault(boolean demoDefault) {
		stop();
		if (demoDefault) {
			BuildCheckResult result = new BuildCheckResult();
			result.setBuildCheckStatus(BuildCheckStatus.OK);
			ArrayList<Build> builds = new ArrayList<Build>();

			for (BuildType buildType : BuildType.values()) {
				boolean isDefault = BuildType.DEFAULT.equals(buildType);
				BuildStatus status = isDefault ? BuildStatus.FAILED : BuildStatus.SUCCESSFUL;
				builds.add(new Build(buildType, status, isDefault));
			}

			result.setBuilds(builds);
			gotStatus(result);
		}
	}

	private void checkStatus() {
		if (buildChecker != null) {
			return;
		}
		buildChecker = new BuildChecker(this);
		buildChecker.start();
	}

	@Override
	public void gotStatus(BuildCheckResult result) {
		phidget.setBuildStatus(result.getFailedBuilds());
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
		if (BuildUtil.isBroken(BuildCategory.IMPORTANT, result.getFailedBuilds())) {
			startPlayer();
		}
		statusPanel.displayBuilds(result.getBuilds());
	}

	@Override
	public void stop() {
		stopPlayer();
		phidget.resetOutput();
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
		getNewCredentials();
	}
}
