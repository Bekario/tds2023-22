package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import controlador.Controlador;

public class Register {
	private JFrame frame;
	private JTextField email;
	private JTextField nombre;
	private JTextField usuario;
	private JPasswordField contraseña;
	private JPasswordField confirmar_contraseña;
	private JButton btnMostrarPass;
	private JButton btnMostrarPass2;
	private JButton btnLogin;
	private JDateChooser dateChooser;
	private JButton btnAtras;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatMonokaiProIJTheme.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register window = new Register();
					window.frame.setVisible(true);
					window.frame.getRootPane().requestFocus(false);
					window.frame.setLocationRelativeTo(null);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Register() {
		initialize();
	}
	
	/**
	 * Muestra la ventana
	 */
	public void mostrarVentana(JFrame padre) {
		frame.setLocationRelativeTo(padre);
		frame.setVisible(true);
		frame.getRootPane().requestFocus(false);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/imagenes/camara-de-fotos.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 50, 35, 0, 35, 50, 0 };
		gridBagLayout.rowHeights = new int[] { 15, 0, 25, 0, 0, 0, 5, 0, 10, 20, 50, 50, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		
		establecerTitulo();
		establecerEmail();
		establecerNombre();
		establecerFechaNacim();
		establecerUsuario();
		establecerContraseñasYBotones();
		establecerBotonContinuar();
	}
	
	/**
	 * Crea el titulo
	 */
	private void establecerTitulo() {
		
		JLabel nombreApp = new JLabel("appPhotos");
		nombreApp.setForeground(new Color(233, 233, 233));
		nombreApp.setIcon(null);
		nombreApp.setFont(new Font("Serif", Font.BOLD, 40));
		GridBagConstraints gbc_nombreApp = new GridBagConstraints();
		gbc_nombreApp.insets = new Insets(0, 0, 5, 5);
		gbc_nombreApp.gridx = 2;
		gbc_nombreApp.gridy = 1;
		frame.getContentPane().add(nombreApp, gbc_nombreApp);
	}
	
	/**
	 * Crea el email field
	 */
	private void establecerEmail() {
		
		email = new JTextField();
		email.setText("Email");
		email.setToolTipText("");

		GridBagConstraints gbc_email = new GridBagConstraints();
		gbc_email.insets = new Insets(0, 0, 5, 5);
		gbc_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_email.gridx = 2;
		gbc_email.gridy = 3;
		frame.getContentPane().add(email, gbc_email);
		email.setColumns(10);
		
		addManejadorTextos(email, "Email");
	}
	
	/**
	 * Añade una animacion al introducir texto a los campos de texto
	 * @param texto Campo de texto
	 * @param defecto String que debe ser sustituida (por defecto)
	 */
	private void addManejadorTextos(JTextField texto, String defecto) {
		texto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (texto.getText().equals(defecto)) {
					texto.setText("");
				} else {
					texto.selectAll();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (texto.getText().equals(""))
					texto.setText(defecto);
			}
		});
	}
	
	/**
	 * Crea el nombre field
	 */
	private void establecerNombre() {
		nombre = new JTextField();
		nombre = new JTextField();
		nombre.setText("Nombre");
		nombre.setToolTipText("");
		GridBagConstraints gbc_nombre = new GridBagConstraints();
		gbc_nombre.insets = new Insets(0, 0, 5, 5);
		gbc_nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombre.gridx = 2;
		gbc_nombre.gridy = 4;
		frame.getContentPane().add(nombre, gbc_nombre);
		nombre.setColumns(10);
		
		addManejadorTextos(nombre, "Nombre");
	}
	
	/**
	 * Crea el usuario field
	 */
	private void establecerUsuario() {
		
		usuario = new JTextField();
		usuario.setText("Usuario");
		usuario.setToolTipText("");
		GridBagConstraints gbc_usuario = new GridBagConstraints();
		gbc_usuario.insets = new Insets(0, 0, 5, 5);
		gbc_usuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_usuario.gridx = 2;
		gbc_usuario.gridy = 6;
		frame.getContentPane().add(usuario, gbc_usuario);
		usuario.setColumns(10);
		
		addManejadorTextos(usuario, "Usuario");
	}
	
	/**
	 * Crea el campo para la fecha de nacimiento incluyendo el boton
	 */
	private void establecerFechaNacim() {
		JCalendar calendar = new JCalendar();
		calendar.getDayChooser().setAlwaysFireDayProperty(true);
		calendar.getDayChooser().setForeground(new Color(255, 255, 255));
		calendar.getDayChooser().setDecorationBackgroundColor(new Color(255, 255, 255));
		calendar.getYearChooser().getSpinner().setBackground(new Color(229, 229, 229));
		calendar.getMonthChooser().getComboBox().setForeground(new Color(0, 0, 0));
		calendar.getMonthChooser().getComboBox().setBackground(new Color(229, 229, 229));
		calendar.setForeground(new Color(255, 255, 255));
		calendar.getYearChooser().setForeground(new Color(0, 0, 0));
		calendar.getYearChooser().getSpinner().setForeground(new Color(0, 0, 0));
		calendar.setMaxDayCharacters(3);
		calendar.setWeekdayForeground(new Color(0, 0, 0));
		calendar.setWeekOfYearVisible(false);
		calendar.setSundayForeground(new Color(255, 255, 255));
		calendar.setDecorationBackgroundColor(new Color(187, 187, 187));
		
		JTextFieldDateEditor campoFecha = new JTextFieldDateEditor();
		campoFecha.setBackground(new Color(201, 201, 201));
		campoFecha.setDateFormatString("dd/MM/yyyy");
		campoFecha.setToolTipText("dd/MM/yyyy");
		campoFecha.setCaretColor(new Color(218, 200, 41));
		campoFecha.setForeground(new Color(255, 255, 255));
		
		dateChooser = new JDateChooser(calendar, null, "dd/MM/yyyy", campoFecha);
		
		dateChooser.setForeground(new Color(255, 255, 255));
		dateChooser.getCalendarButton().setText("Fecha Nacimiento   ");
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 5;
		frame.getContentPane().add(dateChooser, gbc_dateChooser);
	}
	
	/**
	 * Crea los field contraseña y confirmar contraseña y sus botones
	 */
	private void establecerContraseñasYBotones() {
		contraseña = new JPasswordField();
		contraseña.setText("Contraseña");
		contraseña.setEchoChar((char) 0);
		GridBagConstraints gbc_contraseña = new GridBagConstraints();
		gbc_contraseña.insets = new Insets(0, 0, 5, 5);
		gbc_contraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_contraseña.gridx = 2;
		gbc_contraseña.gridy = 7;
		frame.getContentPane().add(contraseña, gbc_contraseña);

		confirmar_contraseña = new JPasswordField();
		btnMostrarPass = new JButton("");
		btnMostrarPass.setBorderPainted(false);
		btnMostrarPass.setContentAreaFilled(false);
		ImageIcon imagen = new ImageIcon(Register2.class.getResource("/imagenes/mostrarcont.png"));
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        btnMostrarPass.setIcon(icono);
        
		GridBagConstraints gbc_btnMostrarPass = new GridBagConstraints();
		gbc_btnMostrarPass.anchor = GridBagConstraints.WEST;
		gbc_btnMostrarPass.insets = new Insets(0, 0, 5, 5);
		gbc_btnMostrarPass.gridx = 3;
		gbc_btnMostrarPass.gridy = 7;
		frame.getContentPane().add(btnMostrarPass, gbc_btnMostrarPass);
		confirmar_contraseña.setText("Confirmar Contraseña");
		confirmar_contraseña.setEchoChar((char) 0);
		GridBagConstraints gbc_confirmar_contraseña = new GridBagConstraints();
		gbc_confirmar_contraseña.insets = new Insets(0, 0, 5, 5);
		gbc_confirmar_contraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmar_contraseña.gridx = 2;
		gbc_confirmar_contraseña.gridy = 8;
		frame.getContentPane().add(confirmar_contraseña, gbc_confirmar_contraseña);
		
		btnMostrarPass2 = new JButton("");
		btnMostrarPass2.setBorderPainted(false);
		btnMostrarPass2.setContentAreaFilled(false);
        btnMostrarPass2.setIcon(icono);
        
		GridBagConstraints gbc_btnMostrarPass2 = new GridBagConstraints();
		gbc_btnMostrarPass2.anchor = GridBagConstraints.WEST;
		gbc_btnMostrarPass2.insets = new Insets(0, 0, 5, 5);
		gbc_btnMostrarPass2.gridx = 3;
		gbc_btnMostrarPass2.gridy = 8;
		frame.getContentPane().add(btnMostrarPass2, gbc_btnMostrarPass2);
		
		addManejadorConstraseña(contraseña, "Contraseña");
		addManejadorConstraseña(confirmar_contraseña, "Confirmar Contraseña");
		
		addManejadorBotonesMostrarContraseña(btnMostrarPass, contraseña, "Contraseña");
		addManejadorBotonesMostrarContraseña(btnMostrarPass2, confirmar_contraseña, "Confirmar Contraseña");
	}
	
	/**
	 * Añade una animacion a los campos de contraseña
	 * @param contraseña Campo de contraseña
	 */
	private void addManejadorConstraseña(JPasswordField contraseña, String defecto) {
		contraseña.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent e) {
				if (contraseña.getText().equals(defecto)) {
					contraseña.setEchoChar('●');
					contraseña.setText("");
				} else {
					contraseña.selectAll();
				}
			}
			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent e) {
				if (contraseña.getText().equals("")) {
					contraseña.setText(defecto);
					contraseña.setEchoChar((char) 0);
				}
			}
		});
	}
	
	/**
	 * Añade funcionalidad a los botones de mostrar contraseña
	 * @param boton Boton
	 * @param contraseña Campo de contraseña
	 * @param defecto String por defecto
	 */
	private void addManejadorBotonesMostrarContraseña(JButton boton, JPasswordField contraseña, String defecto){
		boton.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mousePressed(MouseEvent e) {
				boton.setContentAreaFilled(true);
				if (!contraseña.getText().equals(defecto)) {					
					contraseña.setEchoChar((char) 0);
				}
			}
			@SuppressWarnings("deprecation")
			@Override
			public void mouseReleased(MouseEvent e) {
				boton.setContentAreaFilled(false);
				if (!contraseña.getText().equals(defecto)) {					
					contraseña.setEchoChar('●');
				}
			}
		});
	}
	
	/**
	 * Crea el boton continuar
	 */
	private void establecerBotonContinuar() {
		btnLogin = new JButton("CONTINUAR");
		btnLogin.setForeground(new Color(218, 200, 41));
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLogin.setBorderPainted(false);
		btnLogin.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.fill = GridBagConstraints.VERTICAL;
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 10;
		frame.getContentPane().add(btnLogin, gbc_btnLogin);
		
		btnAtras = new JButton("     ATRÁS     ");
		btnAtras.setForeground(new Color(218, 200, 41));
		btnAtras.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnAtras.setBorderPainted(false);
		btnAtras.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnAtras = new GridBagConstraints();
		gbc_btnAtras.fill = GridBagConstraints.VERTICAL;
		gbc_btnAtras.insets = new Insets(0, 0, 5, 5);
		gbc_btnAtras.gridx = 2;
		gbc_btnAtras.gridy = 11;
		frame.getContentPane().add(btnAtras, gbc_btnAtras);
		
		addManejadorBotonColor(btnLogin);
		addManejadorBotonColor(btnAtras);
		
		addManejadorBotonContinuar(btnLogin);
		addManejadorBotonAtras(btnAtras);
		
	}
	
	/**
	 * Controlamos el evento de registro
	 * @param boton
	 */
	private void addManejadorBotonContinuar(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkFields()) {
					if (Controlador.getInstancia().registroUsuarioParcial(usuario.getText(), contraseña.getText(), null, null, null)){
						Register2 registro2 = new Register2();
						registro2.mostrarVentana(frame);
						frame.dispose();
					}
				}
			}
		});
		
	}
	
	/**
	 * Controlamos el evento de volver a la ventana anterior
	 * @param boton
	 */
	private void addManejadorBotonAtras(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.mostrarVentana(frame);
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
	
	/**
	 * Comprueba que todos los campos tengan un dato valido
	 * @return booleano indicando si estan correctos los campos
	 */
	private boolean checkFields() {
		boolean estado = true;
		Pattern regexEmail = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
		Pattern regexPass = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{3,16}$");
		Matcher match = regexEmail.matcher(email.getText());
		
		String info = "";
		
		//Comprobamos si es un correo basico
		if(email.getText().equals("Email") /*|| !match.matches()*/) {
			estado = false;
			info = "¡El email no es valido!";
		}
		
		if(nombre.getText().equals("Nombre")) {
			estado = false;
			info = "¡El nombre no es valido!";
		}
		
		if(usuario.getText().equals("Usuario")) {
			estado = false;
			info = "¡El nombre de usuario no es valido!";
		}
		
		match = regexPass.matcher(new String(contraseña.getPassword()));
		if(new String(contraseña.getPassword()).equals("Contraseña") /*|| !match.matches()*/) {
			estado = false;
			info = "¡La contraseña no es valida!\nUna contraseña valida contiene 3-16 caracteres y mínimo una minúscula, mayúscula y dígito.";
		}
		
		if(new String(confirmar_contraseña.getPassword()).equals("Confirmar Contraseña") || confirmar_contraseña.getPassword().equals(contraseña.getPassword())) {
			estado = false;
			info = "¡Las contraseñas no son iguales!";
		}
		
		if(dateChooser.getDate() == null) {
			estado = false;
			info = "¡La fecha no es valida!";
		}
		
		if(!estado) {
			JOptionPane.showMessageDialog(frame, info, "Rellene correctamente los campos", 0);
		}
		
		return estado;
	}
	
}
