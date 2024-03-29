package ventanas;

import controlador.Controlador;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class PanelLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtUser;
	private JPasswordField txtPasswd;
	private JLabel foto;
	private JButton btnLogin;
	private JButton btnRegistrarse;
	private JButton btnMostrarPass2;
	
	private Login padre;

	public PanelLogin(Login padre) {
		this.padre = padre;
		this.setSize(450, 600);
		initialize();
	}
	
	/**
	 * Inicializa el frame y el layout
	 */
	private void initialize() {
		establecerLayout();
		establecerBotones();
		establecerFotoTitulo();
		establecerUsuarioEmail();
	}
	
	public void mostrar() {
		this.updateUI();
	}
	
	private void establecerLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 35, 0, 35, 50, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 50, 0, 0, 5, 0, 10, 50, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
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
		add(nombreApp, gbc_nombreApp);
		
		foto = new JLabel("");
		foto.setIcon(new ImageIcon(PanelLogin.class.getResource("/imagenes/icono.gif")));
		GridBagConstraints gbc_foto = new GridBagConstraints();
		gbc_foto.insets = new Insets(0, 0, 5, 5);
		gbc_foto.gridx = 2;
		gbc_foto.gridy = 2;
		add(foto, gbc_foto);
	}
	
	/**
	 * Establece los campos para introducir el usuario e email
	 */
	private void establecerUsuarioEmail() {
		txtUser = new JTextField();
		txtUser.setText("Usuario/Email");
		txtUser.setToolTipText("");
		GridBagConstraints gbc_txtUser = new GridBagConstraints();
		gbc_txtUser.gridwidth = 3;
		gbc_txtUser.insets = new Insets(0, 0, 5, 5);
		gbc_txtUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUser.gridx = 1;
		gbc_txtUser.gridy = 4;
		add(txtUser, gbc_txtUser);
		txtUser.setColumns(10);
		
		txtPasswd = new JPasswordField();
		txtPasswd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					if (checkFields()) {
						if(Controlador.getInstancia().loginUsuario(txtUser.getText(), new String(txtPasswd.getPassword()))) {
							padre.cambiarHome();
						} else {
							JOptionPane.showMessageDialog(padre.getFrame(), "¡El nombre de usuario/email o la contraseña no es correcto!", "Rellene correctamente los campos", 0);
						}
					}
				}
			}
		});
		txtPasswd.setText("Contraseña");
		txtPasswd.setEchoChar((char)0);
		GridBagConstraints gbc_txtPasswd = new GridBagConstraints();
		gbc_txtPasswd.gridwidth = 3;
		gbc_txtPasswd.insets = new Insets(0, 0, 5, 5);
		gbc_txtPasswd.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPasswd.gridx = 1;
		gbc_txtPasswd.gridy = 6;
		add(txtPasswd, gbc_txtPasswd);
		btnMostrarPass2 = new JButton("");
		btnMostrarPass2.setFocusable(false);
		btnMostrarPass2.setContentAreaFilled(false);
		btnMostrarPass2.setBorderPainted(false);
		ImageIcon imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/mostrarcont.png"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
		btnMostrarPass2.setIcon(icono);
		
		GridBagConstraints gbc_btnMostrarPass2 = new GridBagConstraints();
		gbc_btnMostrarPass2.anchor = GridBagConstraints.WEST;
		gbc_btnMostrarPass2.insets = new Insets(0, 0, 5, 0);
		gbc_btnMostrarPass2.gridx = 4;
		gbc_btnMostrarPass2.gridy = 6;
		
		
		add(btnMostrarPass2, gbc_btnMostrarPass2);
		
		Manejadores.addManejadorContraseña(txtPasswd, "Contraseña");
		Manejadores.addManejadorTextos(txtUser, "Usuario/Email");
		Manejadores.addManejadorBotonesMostrarContraseña(btnMostrarPass2, txtPasswd, "Contraseña");
	}
	
	/**
	 * Establece los botones para iniciar sesion y registrarse
	 */
	private void establecerBotones() {
		
		btnLogin = new JButton("INICIAR SESIÓN");
		addManejadorLogin(btnLogin);
		btnLogin.setForeground(Colores.NARANJA);
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLogin.setBorderPainted(false);
		btnLogin.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.fill = GridBagConstraints.VERTICAL;
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 8;
		add(btnLogin, gbc_btnLogin);
		
		btnRegistrarse = new JButton("  REGISTRARSE  ");
		addManejadorRegistrarse(btnRegistrarse);
		
		btnRegistrarse.setForeground(Colores.NARANJA);
		btnRegistrarse.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnRegistrarse.setBorderPainted(false);
		btnRegistrarse.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnRegistrarse = new GridBagConstraints();
		gbc_btnRegistrarse.fill = GridBagConstraints.VERTICAL;
		gbc_btnRegistrarse.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrarse.gridx = 2;
		gbc_btnRegistrarse.gridy = 9;
		add(btnRegistrarse, gbc_btnRegistrarse);
		
		Manejadores.addManejadorBotonColor(btnLogin);
		Manejadores.addManejadorBotonColor(btnRegistrarse);
		
	}
	
	private void addManejadorLogin(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//checkfield
				if (checkFields()) {
					if(Controlador.getInstancia().loginUsuario(txtUser.getText(), new String(txtPasswd.getPassword()))) {
						padre.cambiarHome();
					} else {
						JOptionPane.showMessageDialog(padre.getFrame(), "¡El nombre de usuario/email o la contraseña no es correcto!", "Rellene correctamente los campos", 0);
					}
				}
			}
		});
	}
	
	private void addManejadorRegistrarse(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				padre.setPanelRegister();
			}
		});
	}
	
	private boolean checkFields() {
		boolean estado = true;
		
		String info = "";
		
		//Comprobamos si es un correo basico
		if(txtUser.getText().equals("Usuario")) {
			estado = false;
			info = "¡El usuario no es correcto!";
		}
		
		if(new String(txtPasswd.getPassword()).equals("Contraseña") /*|| !match.matches()*/) {
			estado = false;
			info = "¡La contraseña es incorrecta!";
		}
		
		
		if(!estado) {
			JOptionPane.showMessageDialog(this, info, "Rellene correctamente los campos", 0);
		}
		
		return estado;
	}
}
