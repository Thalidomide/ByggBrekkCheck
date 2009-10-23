package no.teamjava.byggbrekker.widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.logic.ByggBrekkListener;

/**
 * @author : Raymond Koteng
 * @since : 20.okt.2009
 */
public class StartCheckPanel extends JPanel {
	boolean start = false;
	JButton startOrCancleButton;
	JLabel feedBackLabel;
	private final ByggBrekkListener listener;


	public StartCheckPanel(ByggBrekkListener listener) throws HeadlessException {
		super(new GridBagLayout());
		this.listener = listener;

		setBackground(Settings.INPUT_PANEL);
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		feedBackLabel = new JLabel("Klar til bruk");
		startOrCancleButton = new JButton("Start");

		feedBackLabel.setFont(Settings.DEFAULT);

		startOrCancleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startOrStopAction();
			}
		});

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.CENTER;

		constraints.gridy = 0;
		constraints.weightx = 1;
		add(startOrCancleButton, constraints);

		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.ipady = 10;
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
}

