package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Cursor;


public class Login {

	private JFrame frame;
	private JTextField txtUser;
	private JPasswordField textPasswd;
	private JButton botonLogin;
	private JButton botonRegister;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatMonokaiProIJTheme.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
					window.frame.getRootPane().requestFocus(false);
//					window.frame.setFocusableWindowState(false);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/imagenes/camara-de-fotos.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 35, 0, 35, 50, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 50, 0, 0, 5, 0, 10, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel nombreApp = new JLabel("appPhotos");
		nombreApp.setForeground(new Color(233, 233, 233));
		nombreApp.setIcon(null);
		nombreApp.setFont(new Font("Serif", Font.BOLD, 40));
		GridBagConstraints gbc_nombreApp = new GridBagConstraints();
		gbc_nombreApp.insets = new Insets(0, 0, 5, 5);
		gbc_nombreApp.gridx = 2;
		gbc_nombreApp.gridy = 1;
		frame.getContentPane().add(nombreApp, gbc_nombreApp);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/imagenes/61-camera-gradient (1).gif")));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 2;
		frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtUser = new JTextField();
		txtUser.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtUser.getText().equals("Usuario")) {
					txtUser.setText("");
				}
				else {
					txtUser.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtUser.getText().equals(""))
					txtUser.setText("Usuario");
			}
		});
		txtUser.setText("Usuario");


		txtUser.setToolTipText("");
		GridBagConstraints gbc_txtUser = new GridBagConstraints();
		gbc_txtUser.gridwidth = 3;
		gbc_txtUser.insets = new Insets(0, 0, 5, 5);
		gbc_txtUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUser.gridx = 1;
		gbc_txtUser.gridy = 4;
		frame.getContentPane().add(txtUser, gbc_txtUser);
		txtUser.setColumns(10);
		
		textPasswd = new JPasswordField();
		textPasswd.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent e) {
				if(textPasswd.getText().equals("Contraseña")) {
					textPasswd.setEchoChar('●');
					textPasswd.setText("");					
				}else {
					textPasswd.selectAll();
			}
			}
			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent e) {
				if(textPasswd.getText().equals("")) {
					textPasswd.setText("Contraseña");
					textPasswd.setEchoChar((char)0);
				}
			}
		});
		textPasswd.setText("Contraseña");
		textPasswd.setEchoChar((char)0);
		GridBagConstraints gbc_textPasswd = new GridBagConstraints();
		gbc_textPasswd.gridwidth = 3;
		gbc_textPasswd.insets = new Insets(0, 0, 5, 5);
		gbc_textPasswd.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPasswd.gridx = 1;
		gbc_textPasswd.gridy = 6;
		frame.getContentPane().add(textPasswd, gbc_textPasswd);
		
		botonLogin = new JButton("");
		botonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		botonLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonLogin.setContentAreaFilled(false);
		botonLogin.setBorderPainted(false);
		botonLogin.setBorder(null);
		botonLogin.setIcon(new ImageIcon(Login.class.getResource("/imagenes/button.png")));
		GridBagConstraints gbc_botonLogin = new GridBagConstraints();
		gbc_botonLogin.fill = GridBagConstraints.VERTICAL;
		gbc_botonLogin.insets = new Insets(0, 0, 5, 5);
		gbc_botonLogin.gridx = 2;
		gbc_botonLogin.gridy = 8;
		frame.getContentPane().add(botonLogin, gbc_botonLogin);
		
		botonRegister = new JButton("");
		botonRegister.setContentAreaFilled(false);
		botonRegister.setBorderPainted(false);
		botonRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonRegister.setIcon(new ImageIcon(Login.class.getResource("/imagenes/button (3).png")));
		GridBagConstraints gbc_botonRegister = new GridBagConstraints();
		gbc_botonRegister.insets = new Insets(0, 0, 5, 5);
		gbc_botonRegister.gridx = 2;
		gbc_botonRegister.gridy = 9;
		frame.getContentPane().add(botonRegister, gbc_botonRegister);
	}

}
