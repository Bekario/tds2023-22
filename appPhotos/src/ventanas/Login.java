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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import javax.swing.UIManager;


public class Login {

	private JFrame frame;
	private JTextField txtUser;
	private JPasswordField textPasswd;
	private JLabel foto;
	private JButton btnLogin;
	private JButton btnRegistrarse;

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
	 * Muestra la ventana
	 */
	public void mostrarVentana() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.getRootPane().requestFocus(false);
	}
	
	/**
	 * Inicializa el frame y el layout
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Login");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/imagenes/camara-de-fotos.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 35, 0, 35, 50, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 50, 0, 0, 5, 0, 10, 50, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		establecerBotones();
		establecerFotoTitulo();
		establecerUsuarioEmail();
	}
	
	/**
	 * Establece la foto y el titulo de la aplicación
	 */
	private void establecerFotoTitulo() {
		JLabel nombreApp = new JLabel("appPhotos");
		nombreApp.setForeground(new Color(233, 233, 233));
		nombreApp.setIcon(null);
		nombreApp.setFont(new Font("Serif", Font.BOLD, 40));
		GridBagConstraints gbc_nombreApp = new GridBagConstraints();
		gbc_nombreApp.insets = new Insets(0, 0, 5, 5);
		gbc_nombreApp.gridx = 2;
		gbc_nombreApp.gridy = 1;
		frame.getContentPane().add(nombreApp, gbc_nombreApp);
		
		foto = new JLabel("");
		foto.setIcon(new ImageIcon(Login.class.getResource("/imagenes/61-camera-gradient (1).gif")));
		GridBagConstraints gbc_foto = new GridBagConstraints();
		gbc_foto.insets = new Insets(0, 0, 5, 5);
		gbc_foto.gridx = 2;
		gbc_foto.gridy = 2;
		frame.getContentPane().add(foto, gbc_foto);
	}
	
	/**
	 * Establece los campos para introducir el usuario e email
	 */
	private void establecerUsuarioEmail() {
		txtUser = new JTextField();
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
		textPasswd.setText("Contraseña");
		textPasswd.setEchoChar((char)0);
		GridBagConstraints gbc_textPasswd = new GridBagConstraints();
		gbc_textPasswd.gridwidth = 3;
		gbc_textPasswd.insets = new Insets(0, 0, 5, 5);
		gbc_textPasswd.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPasswd.gridx = 1;
		gbc_textPasswd.gridy = 6;
		frame.getContentPane().add(textPasswd, gbc_textPasswd);
		
		addManejadorContraseña(textPasswd);
		addManejadorUsuario(txtUser);
	}
	
	/**
	 * Gestiona las animaciones del campo usuario
	 * @param txtUser Campo de usuario
	 */
	private void addManejadorUsuario(JTextField txtUser) {
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
	}
	
	/**
	 * Gestiona las animaciones del campo contraseña
	 * @param textPasswd Campo de contraseña
	 */
	private void addManejadorContraseña(JPasswordField textPasswd) {
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
	}
	
	/**
	 * Establece los botones para iniciar sesion y registrarse
	 */
	private void establecerBotones() {
		btnLogin = new JButton("INICIAR SESIÓN");
		btnLogin.setForeground(new Color(218, 200, 41));
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLogin.setBorderPainted(false);
		btnLogin.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.fill = GridBagConstraints.VERTICAL;
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 8;
		frame.getContentPane().add(btnLogin, gbc_btnLogin);
		
		btnRegistrarse = new JButton("  REGISTRARSE  ");
		addManejadorRegistrarse(btnRegistrarse);
		
		btnRegistrarse.setForeground(new Color(218, 200, 41));
		btnRegistrarse.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnRegistrarse.setBorderPainted(false);
		btnRegistrarse.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnRegistrarse = new GridBagConstraints();
		gbc_btnRegistrarse.fill = GridBagConstraints.VERTICAL;
		gbc_btnRegistrarse.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrarse.gridx = 2;
		gbc_btnRegistrarse.gridy = 9;
		frame.getContentPane().add(btnRegistrarse, gbc_btnRegistrarse);
		
		addManejadorBotonColor(btnLogin);
		addManejadorBotonColor(btnRegistrarse);
	}
	
	private void addManejadorRegistrarse(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register ventana = new Register();
				ventana.mostrarVentana();
				frame.dispose();
			}
		});
	}
	
	
	/**
	 * Gestiona los cambios de color al pasar el raton encima de un boton
	 * @param boton Boton que se desea que aplique el efecto
	 */
	private void addManejadorBotonColor(JButton boton) {
		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBackground(new Color(218,200,41));
				boton.setForeground(new Color(78,80,82));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(new Color(78,80,82));
				boton.setForeground(new Color(218,200,41));
				
			}
		});
	}

}
