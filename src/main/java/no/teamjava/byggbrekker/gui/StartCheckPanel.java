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
import no.teamjava.byggbrekker.gui.widgets.Label;
import no.teamjava.byggbrekker.gui.widgets.LabelType;
import no.teamjava.byggbrekker.logic.ByggBrekkListener;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class StartCheckPanel extends JPanel {
	boolean start = false;
	Button startOrCancleButton;
	Button demoDefaultBroken;
	Label feedBackLabel;
	private final ByggBrekkListener listener;


	public StartCheckPanel(ByggBrekkListener listener) throws HeadlessException {
		super(new GridBagLayout());
		this.listener = listener;

		setBackground(Settings.INPUT_PANEL);
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		feedBackLabel = new Label("Klar til bruk", LabelType.BIG);
		startOrCancleButton = new Button("Start");

		startOrCancleButton.addActionListener(new ActionListener() {
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
		add(startOrCancleButton, constraints);
		add(demoDefaultBroken, constraints);

		constraints.weightx = 1;
		add(feedBackLabel, constraints);
	}

	public void reset() {
		start = false;
		updateGui();
	}

	private void startOrStopAction() {
		start = !start;
		updateGui();
		if (start) {
			listener.startCheckStatus();
		} else {
			listener.stopCheckStatus();
		}
	}

	private void updateGui() {
		if (start) {
			startOrCancleButton.setText("Stop");
			feedBackLabel.setText("Er i gang med ByggBrekkSjekking!");
		} else {
			startOrCancleButton.setText("Start");
			feedBackLabel.setText("Klar til bruk");
		}
	}

	private boolean demoDefault = false;
	private void demoDefault() {
		demoDefault = !demoDefault;

		listener.setDemoDefault(demoDefault);
	}
}

