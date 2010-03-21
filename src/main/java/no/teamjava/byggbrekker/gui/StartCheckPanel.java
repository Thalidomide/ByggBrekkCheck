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
	private Button startOrStopButton;
	private final ByggBrekkListener listener;


	public StartCheckPanel(ByggBrekkListener listener) throws HeadlessException {
		super(new GridBagLayout());
		this.listener = listener;

		startOrStopButton = new Button("");
		startOrStopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startOrStopAction();
			}
		});

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BASELINE_LEADING;
		constraints.insets = new Insets(1, 10, 1, 10);

		constraints.gridy = 0;
		constraints.weightx = 0;
		add(startOrStopButton, constraints);
		add(getToggleDemoButton(), constraints);
		add(getExitButton(), constraints);

		constraints.weightx = 1;

		updateGui();
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

	private Button getToggleDemoButton() {
		Button button = new Button("Start/stop demo");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleDemo();
			}
		});
		return button;
	}

	public void reset() {
		running = false;
		updateGui();
	}

	private void startOrStopAction() {
		running = !running;
		updateGui();
		if (running) {
			listener.startCheckStatus();
		} else {
			listener.stopCheckStatus();
		}
	}

	private void updateGui() {
		startOrStopButton.setText(running ? "Stop" : "Start");
	}

	private boolean runningDemo = false;

	private void toggleDemo() {
		runningDemo = !runningDemo;

		listener.setDemoMode(runningDemo);
	}
}

