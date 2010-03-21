package no.teamjava.byggbrekker.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import no.teamjava.byggbrekker.entities.BuildCategory;
import no.teamjava.byggbrekker.entities.BuildCheckResult;
import no.teamjava.byggbrekker.entities.BuildCheckResultProvider;
import no.teamjava.byggbrekker.entities.BuildUtil;
import no.teamjava.byggbrekker.entities.Credentials;
import no.teamjava.byggbrekker.logic.BobHtmlParser;
import no.teamjava.byggbrekker.logic.BuildChecker;
import no.teamjava.byggbrekker.logic.ByggBrekkListener;
import no.teamjava.byggbrekker.logic.CheckerListener;
import no.teamjava.byggbrekker.logic.CredentialsProvider;
import no.teamjava.byggbrekker.logic.PlayerThread;
import no.teamjava.byggbrekker.phidget.Phidget;

/**
 * @author Raymond Koteng, Olav Jensen
 * @since 20.okt.2009
 */
public class MainFrame extends JFrame implements ByggBrekkListener, CheckerListener, CredentialsFrameListener, CredentialsProvider {
	private StatusPanel statusPanel;
	private StartCheckPanel startCheckPanel;
	private CredentialsPanel credentialsPanel;
	private ConfigureDemoFrame configureDemoFrame;
	private boolean runningDemo = false;

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

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				onExit();
			}
		});
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setTitle("ByggBrekkSjekker3001 - TeamJava");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(panel);
		setUndecorated(true);

		startCheckPanel = new StartCheckPanel(this);
		statusPanel = new StatusPanel();
		credentialsPanel = new CredentialsPanel(this);
		configureDemoFrame = new ConfigureDemoFrame();
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
		panel.validate();
		panel.repaint();
		validateTree();
	}

	@Override
	public void gotCredentials(Credentials credentials) {
		this.credentials = credentials;
		addCheckerGui();
	}

	@Override
	public void startCheckStatus() {
		statusPanel.displayCheckMessage(0);
		phidget.start();
		checkStatus();
	}

	@Override
	public void stopCheckStatus() {
		if (buildChecker != null) {
			buildChecker.stopChecking();
			buildChecker = null;
		}
		phidget.stopAndClearOutputs();
		statusPanel.resetBuildInfo();
		statusPanel.displayNotRunningMessage();
	}

	@Override
	public void setDemoMode(boolean runningDemo) {
		stop();
		this.runningDemo = runningDemo;
		configureDemoFrame.setVisible(runningDemo);
		if (runningDemo) {
			startCheckStatus();
		} else {
			stopCheckStatus();
		}
	}

	@Override
	public void exit() {
		onExit();
		System.exit(0);
	}

	private void checkStatus() {
		if (buildChecker != null) {
			buildChecker.stopChecking();
		}

		buildChecker = new BuildChecker(this);
		buildChecker.start();
	}

	@Override
	public void updateStatus() {
		BuildCheckResult result = getNewStatus();
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
				statusPanel.resetBuildInfo();
				statusPanel.displayCheckErrorMessage();
				stopPlayer();
				break;
			default:
				throw new RuntimeException("Unhandled status: " + result.getBuildCheckStatus());
		}
	}

	@Override
	public void updateTimeToUpdate(long time) {
		statusPanel.displayCheckMessage(time);
	}

	private BuildCheckResult getNewStatus() {
		BuildCheckResultProvider provider = runningDemo ? configureDemoFrame : new BobHtmlParser(this);
		return provider.getResult();
	}

	private void presentResult(BuildCheckResult result) {
		if (BuildUtil.isBroken(BuildCategory.IMPORTANT, result.getFailedBuilds())) {
			startPlayer();
		} else {
			stopPlayer();
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
		if (playerThread != null) {
			return;
		}
		playerThread = new PlayerThread();
		playerThread.start();
	}

	private void stopPlayer() {
		if (playerThread == null) {
			return;
		}
		playerThread.stopPlayer();
		playerThread = null;
	}

	private void authorizationFailed() {
		stopCheckStatus();
		startCheckPanel.reset();
		getNewCredentials();
	}

	private void onExit() {
		try {
			phidget.stopAndClearOutputs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
