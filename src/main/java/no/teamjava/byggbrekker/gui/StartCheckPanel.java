package no.teamjava.byggbrekker.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import no.teamjava.byggbrekker.gui.widgets.Button;
import no.teamjava.byggbrekker.gui.widgets.InputPanel;
import no.teamjava.byggbrekker.logic.ByggBrekkListener;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class StartCheckPanel extends InputPanel {

	private boolean running = false;
	private boolean runningDemo = false;

	private Button startOrStopButton;
	private final ByggBrekkListener listener;
	private Button demoButton;

	public StartCheckPanel(ByggBrekkListener listener) throws HeadlessException {
		super(new GridBagLayout());
		this.listener = listener;

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BASELINE_LEADING;
		constraints.insets = new Insets(1, 10, 1, 10);

		constraints.gridy = 0;
		constraints.weightx = 0;
		add(getStartButton(), constraints);
		add(getToggleDemoButton(), constraints);
		add(getExitButton(), constraints);

		constraints.weightx = 1;

		updateStartButtonText();
		updateDemoButtonText();
	}

	private Button getStartButton() {
		startOrStopButton = new Button("");
		startOrStopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startOrStop();
			}
		});
		return startOrStopButton;
	}

	private Button getToggleDemoButton() {
		demoButton = new Button("");
		demoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startOrStopDemo();
			}
		});
		return demoButton;
	}

	private Button getExitButton() {
		Button button = new Button("EXIT");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.exit();
			}
		});
		return button;
	}

	public void reset() {
		running = false;
		updateStartButtonText();
	}

	private void startOrStop() {
		running = !running;
		updateStartButtonText();
		demoButton.setEnabled(!running);

		if (running) {
			listener.startCheck();
		} else {
			listener.stopCheck();
		}
	}

	private void startOrStopDemo() {
		runningDemo = !runningDemo;
		updateDemoButtonText();
		startOrStopButton.setEnabled(!runningDemo);

		if (runningDemo) {
			listener.startDemo();
		} else {
			listener.stopDemo();
		}
	}

	private void updateStartButtonText() {
		startOrStopButton.setText(running ? "Stop" : "Start");
	}

	private void updateDemoButtonText() {
		demoButton.setText((runningDemo ? "Stop" : "Start") + " demo");
	}
}

