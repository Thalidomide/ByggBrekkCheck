package no.teamjava.byggbrekker.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.gui.widgets.Button;
import no.teamjava.byggbrekker.logic.ByggBrekkListener;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class StartCheckPanel extends JPanel {
	boolean running = false;
	Button startOrStopButton;
	Button demoDefaultBroken;
	private final ByggBrekkListener listener;


	public StartCheckPanel(ByggBrekkListener listener) throws HeadlessException {
		super(new GridBagLayout());
		this.listener = listener;

		setBackground(Settings.INPUT_PANEL);
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		startOrStopButton = new Button("");

		startOrStopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startOrStopAction();
			}
		});

		demoDefaultBroken = new Button("Demo: Default brukket");
		demoDefaultBroken.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				demoDefault();
			}
		});

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BASELINE_LEADING;
		constraints.insets = new Insets(1, 10, 1, 10);

		constraints.gridy = 0;
		constraints.weightx = 0;
		add(startOrStopButton, constraints);
		add(demoDefaultBroken, constraints);

		constraints.weightx = 1;

		updateGui();
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

	private boolean demoDefault = false;
	private void demoDefault() {
		demoDefault = !demoDefault;

		listener.setDemoDefault(demoDefault);
	}
}

