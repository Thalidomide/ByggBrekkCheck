package no.teamjava.byggbrekker.widgets;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		this.listener = listener;
		feedBackLabel = new JLabel("Klar til bruk");
		startOrCancleButton = new JButton("Start");
		startOrCancleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startOrStopAction();
			}
		});

		this.add(feedBackLabel);
		this.add(startOrCancleButton);
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

