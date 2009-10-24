package no.teamjava.byggbrekker.gui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import no.teamjava.byggbrekker.entities.Credentials;
import no.teamjava.byggbrekker.entities.Settings;
import no.teamjava.byggbrekker.gui.widgets.Button;
import no.teamjava.byggbrekker.gui.widgets.Label;
import no.teamjava.byggbrekker.gui.widgets.LabelType;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public class CredentialsPanel extends JPanel {

	private CredentialsFrameListener listener;
	private JTextField usernameWidget;
	private JPasswordField passwordWidget;
	private JPanel panel;

	public CredentialsPanel(CredentialsFrameListener listener) throws HeadlessException {
		super(null);
		this.listener = listener;

		initializeGui();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		int width = 400;
		int height = 200;
		int x = getWidth() / 2 - width / 2;
		int y = getHeight() / 2 - height / 2;
		panel.setBounds(new Rectangle(x, y, width, height));
	}

	private void initializeGui() {
		panel = new JPanel(new GridBagLayout());
		add(panel);

		setBackground(Settings.BACKGROUND);
		panel.setBackground(Settings.INPUT_PANEL);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		usernameWidget = new JTextField();
		passwordWidget = new JPasswordField();
		Button okButton = new Button("Ok");

		usernameWidget.setFont(Settings.DEFAULT);
		passwordWidget.setFont(Settings.DEFAULT);
		okButton.setFont(Settings.DEFAULT);

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		usernameWidget.addKeyListener(getLoginKeyListener());
		passwordWidget.addKeyListener(getLoginKeyListener());

		Label header = new Label("Oppgi brukernavn og passord", LabelType.HEADER);

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

	private void addLabelAndTextBox(String text, JTextField widget, JPanel panel, GridBagConstraints constraints) {
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
