package no.teamjava.byggbrekker.widgets;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import no.teamjava.byggbrekker.entities.Credentials;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class CredentialsPanel extends JFrame {

	private CredentialsFrameListener listener;
	private TextField usernameWidget;
	private TextField passwordWidget;

	public CredentialsPanel(CredentialsFrameListener listener) throws HeadlessException {
		this.listener = listener;

		setLocation(200, 200);
		setSize(250, 100);
		setTitle("Oppgi brukernavn og passord");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		initGui();

		setVisible(true);
	}

	private void initGui() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		usernameWidget = new TextField();
		passwordWidget = new TextField();
		Button okButton = new Button("Ok");

		passwordWidget.setEchoChar('*');

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		usernameWidget.addKeyListener(getLoginKeyListener());
		passwordWidget.addKeyListener(getLoginKeyListener());

		constraints.fill = GridBagConstraints.HORIZONTAL;

		constraints.gridy = 0;
		addLabelAndTextBox("Brukernavn:", usernameWidget, panel, constraints);

		constraints.gridy = 1;
		addLabelAndTextBox("Passord:", passwordWidget, panel, constraints);

		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		panel.add(okButton, constraints);

		getContentPane().add(panel);
	}

	private void login() {
		listener.gotCredentials(new Credentials(usernameWidget.getText(), passwordWidget.getText()));
		dispose();
	}

	private void addLabelAndTextBox(String label, TextField widget, JPanel panel, GridBagConstraints constraints) {
		constraints.weightx = 0.2;
		constraints.gridx = 0;
		panel.add(new Label(label), constraints);
		constraints.gridx = 1;
		constraints.weightx = 1;
		panel.add(widget, constraints);
	}

	private KeyListener getLoginKeyListener() {
		return new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		};
	}
}
