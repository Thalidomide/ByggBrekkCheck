package no.teamjava.byggbrekker.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.JFrame;

import no.teamjava.byggbrekker.logic.ByggBrekkListener;
import no.teamjava.byggbrekker.logic.BuildStatus;
import no.teamjava.byggbrekker.logic.Checker;
import no.teamjava.byggbrekker.logic.CheckerListener;
import no.teamjava.byggbrekker.logic.Credentials;
import no.teamjava.byggbrekker.logic.PlayerThread;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class MainFrame extends JFrame implements ByggBrekkListener, CheckerListener, CredentialsFrameListener {
	private StatusPanel statusPanel;
	private Checker checker;
	private Credentials credentials;

	private PlayerThread playerThread;
	private StartCheckPanel startCheckPanel;

	public MainFrame() throws HeadlessException {
		addGUI();

//		getNewCredentials();
		gotCredentials(new Credentials("", ""));
	}

	private void getNewCredentials() {
		new CredentialsFrame(this);
	}

	private void addGUI() {
		startCheckPanel = new StartCheckPanel(this);
		statusPanel = new StatusPanel();

		this.add(startCheckPanel, BorderLayout.NORTH);
		this.add(statusPanel, BorderLayout.CENTER);

		setDefaultBackground();
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
		if (checker != null) {
			checker.stopChecking();
			checker = null;
		}
		setDefaultBackground();
	}

	private void setDefaultBackground() {
		statusPanel.setBackgroundColor(Color.black);
	}

	private void checkStatus() {
		if (checker != null) {
			return;
		}
		checker = new Checker(this);
		checker.start();
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
	public void gotStatus(BuildStatus status) {
		Color color;

		switch (status) {
			case ok:
				color = Color.GREEN;
				stopPlayer();
				break;
			case broken:
				color = Color.RED;
				startPlayer();
				break;
			case unknown:
				color = Color.ORANGE;
				stopPlayer();
				break;
			case authorizationFailed:
				authorizationFailed();
				return;
			default:
				throw new RuntimeException("Uhandled status: " + status);
		}

		statusPanel.setBackgroundColor(color);
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
