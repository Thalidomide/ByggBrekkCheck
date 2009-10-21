package no.teamjava.byggbrekker.widgets;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import no.teamjava.byggbrekker.logic.Credentials;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class CredentialsFrame extends JFrame {

	private CredentialsFrameListener listener;
	private TextField usernameWidget;
	private TextField passwordWidget;

	public CredentialsFrame(CredentialsFrameListener listener) throws HeadlessException {
		this.listener = listener;

		setLocation(200, 200);
		setSize(350, 100);
		setTitle("Oppgi brukernavn og passord");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initGui();

		setVisible(true);
	}

	private void initGui() {
		JPanel panel = new JPanel(new GridLayout(3, 2));

		usernameWidget = new TextField();
		passwordWidget = new TextField();
		Button okButton = new Button("Ok");
		Button cancelButton = new Button("Avbryt (avslutter)");

		passwordWidget.setEchoChar('*');

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.gotCredentials(new Credentials(usernameWidget.getText(), passwordWidget.getText()));
				dispose();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		panel.add(new Label("Brukernavn:"));
		panel.add(usernameWidget);
		panel.add(new Label("Passord:"));
		panel.add(passwordWidget);
		panel.add(okButton);
		panel.add(cancelButton);

		getContentPane().add(panel);
	}
}
