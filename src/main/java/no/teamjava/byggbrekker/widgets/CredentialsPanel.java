package no.teamjava.byggbrekker.widgets;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import no.teamjava.byggbrekker.entities.Credentials;
import no.teamjava.byggbrekker.entities.Settings;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class CredentialsPanel extends JPanel {

	private CredentialsFrameListener listener;
	private TextField usernameWidget;
	private TextField passwordWidget;
	private JPanel panel;

	public CredentialsPanel(CredentialsFrameListener listener) {
		super(null);
		this.listener = listener;

		initializeGui();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		int width = 300;
		int height = 200;
		int x = getWidth() / 2 - width / 2;
		int y = getHeight() / 2 - height / 2;
		panel.setBounds(new Rectangle(x, y, width, height));
	}

	private void initializeGui() {
		panel = new JPanel(new GridBagLayout());
		add(panel);

		setBackground(Color.BLACK);
		panel.setBackground(Settings.INPUT_PANEL);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		usernameWidget = new TextField();
		passwordWidget = new TextField();
		Button okButton = new Button("Ok");

		usernameWidget.setFont(Settings.DEFAULT);
		passwordWidget.setFont(Settings.DEFAULT);
		okButton.setFont(Settings.DEFAULT);

		passwordWidget.setEchoChar('*');

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		usernameWidget.addKeyListener(getLoginKeyListener());
		passwordWidget.addKeyListener(getLoginKeyListener());

		Label header = new Label("Oppgi brukernavn og passord");
		header.setFont(Settings.HEADER);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		int gridY = 0;

		constraints.gridy = gridY++;
		constraints.weighty = 0.1;
		constraints.gridwidth = 2;
		panel.add(header, constraints);

		constraints.weighty = 0.01;
		constraints.gridy = gridY++;
		addLabelAndTextBox("Brukernavn:", usernameWidget, panel, constraints);

		constraints.gridy = gridY++;
		addLabelAndTextBox("Passord:", passwordWidget, panel, constraints);

		constraints.fill = GridBagConstraints.EAST;
		constraints.gridy = gridY++;
		constraints.weighty = 0.1;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		panel.add(okButton, constraints);

	}

	private void login() {
		listener.gotCredentials(new Credentials(usernameWidget.getText(), passwordWidget.getText()));
	}

	private void addLabelAndTextBox(String text, TextField widget, JPanel panel, GridBagConstraints constraints) {
		constraints.gridwidth = 1;
		constraints.gridx = 0;

		Label label = new Label(text);
		label.setFont(Settings.DEFAULT);
		panel.add(label, constraints);

		constraints.gridx = 1;
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
