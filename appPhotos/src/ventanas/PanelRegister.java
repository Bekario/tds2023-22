package ventanas;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import controlador.Controlador;

public class PanelRegister extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtEmail;
	private JTextField txtNombre;
	private JTextField txtUsuario;
	private JPasswordField txtContraseña;
	private JPasswordField txtConfirmar_contraseña;
	private JButton btnMostrarPass;
	private JButton btnMostrarPass2;
	private JButton btnLogin;
	private JDateChooser dateChooser;
	private JButton btnAtras;
	
	private Login padre;

	public PanelRegister(Login padre) {
		this.padre = padre;
		this.setSize(450, 600);
		crearPanel();
		
	}
	private void crearPanel() {
		crearLayout();
		establecerTitulo();
		establecerEmail();
		establecerNombre();
		establecerFechaNacim();
		establecerUsuario();
		establecerContraseñasYBotones();
		establecerBotonContinuar();
	}
	
	public void mostrar() {
		this.updateUI();
	}
	
	private void crearLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 50, 35, 0, 35, 50, 0 };
        gridBagLayout.rowHeights = new int[] { 15, 0, 25, 0, 0, 0, 5, 0, 10, 20, 50, 50, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,Double.MIN_VALUE };
        setLayout(gridBagLayout);
	}
	
	/**
	 * Crea el titulo
	 */
	private void establecerTitulo() {
		JLabel lblNombreApp = new JLabel("appPhotos");
		lblNombreApp.setForeground(new Color(233, 233, 233));
		lblNombreApp.setIcon(null);
		lblNombreApp.setFont(new Font("Serif", Font.BOLD, 40));
		GridBagConstraints gbc_lblNombreApp = new GridBagConstraints();
		gbc_lblNombreApp.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreApp.gridx = 2;
		gbc_lblNombreApp.gridy = 1;
		add(lblNombreApp, gbc_lblNombreApp);
	}
	
	/**
	 * Crea el email field
	 */
	private void establecerEmail() {
		
		txtEmail = new JTextField();
		txtEmail.setText("Email");
		txtEmail.setToolTipText("");

		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 2;
		gbc_txtEmail.gridy = 3;
		add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		
		addManejadorTextos(txtEmail, "Email");
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
		txtNombre = new JTextField();
		txtNombre.setText("Nombre");
		txtNombre.setToolTipText("");
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 2;
		gbc_txtNombre.gridy = 4;
		add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);
		
		addManejadorTextos(txtNombre, "Nombre");
	}
	
	/**
	 * Crea el usuario field
	 */
	private void establecerUsuario() {
		
		txtUsuario = new JTextField();
		txtUsuario.setText("Usuario");
		txtUsuario.setToolTipText("");
		GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
		gbc_txtUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsuario.gridx = 2;
		gbc_txtUsuario.gridy = 6;
		add(txtUsuario, gbc_txtUsuario);
		txtUsuario.setColumns(10);
		
		addManejadorTextos(txtUsuario, "Usuario");
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
		add(dateChooser, gbc_dateChooser);
	}
	
	/**
	 * Crea los field contraseña y confirmar contraseña y sus botones
	 */
	private void establecerContraseñasYBotones() {
		txtContraseña = new JPasswordField();
		txtContraseña.setText("Contraseña");
		txtContraseña.setEchoChar((char) 0);
		GridBagConstraints gbc_txtContraseña = new GridBagConstraints();
		gbc_txtContraseña.insets = new Insets(0, 0, 5, 5);
		gbc_txtContraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtContraseña.gridx = 2;
		gbc_txtContraseña.gridy = 7;
		add(txtContraseña, gbc_txtContraseña);

		txtConfirmar_contraseña = new JPasswordField();
		btnMostrarPass = new JButton("");
		btnMostrarPass.setFocusable(false);
		btnMostrarPass.setBorderPainted(false);
		btnMostrarPass.setContentAreaFilled(false);
		ImageIcon imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/mostrarcont.png"));
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        btnMostrarPass.setIcon(icono);
        
		GridBagConstraints gbc_btnMostrarPass = new GridBagConstraints();
		gbc_btnMostrarPass.anchor = GridBagConstraints.WEST;
		gbc_btnMostrarPass.insets = new Insets(0, 0, 5, 5);
		gbc_btnMostrarPass.gridx = 3;
		gbc_btnMostrarPass.gridy = 7;
		add(btnMostrarPass, gbc_btnMostrarPass);
		txtConfirmar_contraseña.setText("Confirmar Contraseña");
		txtConfirmar_contraseña.setEchoChar((char) 0);
		GridBagConstraints gbc_txtConfirmar_contraseña = new GridBagConstraints();
		gbc_txtConfirmar_contraseña.insets = new Insets(0, 0, 5, 5);
		gbc_txtConfirmar_contraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConfirmar_contraseña.gridx = 2;
		gbc_txtConfirmar_contraseña.gridy = 8;
		add(txtConfirmar_contraseña, gbc_txtConfirmar_contraseña);
		
		btnMostrarPass2 = new JButton("");
		btnMostrarPass2.setFocusable(false);
		btnMostrarPass2.setBorderPainted(false);
		btnMostrarPass2.setContentAreaFilled(false);
        btnMostrarPass2.setIcon(icono);
        
		GridBagConstraints gbc_btnMostrarPass2 = new GridBagConstraints();
		gbc_btnMostrarPass2.anchor = GridBagConstraints.WEST;
		gbc_btnMostrarPass2.insets = new Insets(0, 0, 5, 5);
		gbc_btnMostrarPass2.gridx = 3;
		gbc_btnMostrarPass2.gridy = 8;
		add(btnMostrarPass2, gbc_btnMostrarPass2);
		
		addManejadorConstraseña(txtContraseña, "Contraseña");
		addManejadorConstraseña(txtConfirmar_contraseña, "Confirmar Contraseña");
		
		addManejadorBotonesMostrarContraseña(btnMostrarPass, txtContraseña, "Contraseña");
		addManejadorBotonesMostrarContraseña(btnMostrarPass2, txtConfirmar_contraseña, "Confirmar Contraseña");
	}
	
	/**
	 * Añade una animacion a los campos de contraseña
	 * @param contraseña Campo de contraseña
	 */
	private void addManejadorConstraseña(JPasswordField contraseña, String defecto) {
		contraseña.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (new String(contraseña.getPassword()).equals(defecto)) {
					contraseña.setEchoChar('●');
					contraseña.setText("");
				} else {
					contraseña.selectAll();
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if (new String(contraseña.getPassword()).equals("")) {
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
		add(btnLogin, gbc_btnLogin);
		
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
		add(btnAtras, gbc_btnAtras);
		
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
				//Si todos los campos son correctos
				if (checkFields()) {
					//Comprobamos si el nombre de usuario ya esta registrado
					if (!Controlador.getInstancia().esUsuarioRegistrado(txtUsuario.getText())){
						padre.setPanelRegister2();
					} else { //Si falla el registro parcial es porque el nombre de usuario ya esta utilizado
						JOptionPane.showMessageDialog(padre.getFrame(), "Este nombre de usuario ya está registrado, prueba con otro distinto", "Usuario ya registrado", 0);
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
				padre.setPanelLogin();
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
		Matcher match = regexEmail.matcher(txtEmail.getText());
		
		String info = "";
		
		//Comprobamos si es un correo basico
		if(txtEmail.getText().equals("Email") /*|| !match.matches()*/) {
			estado = false;
			info = "¡El email no es valido!";
		}
		
		if(txtNombre.getText().equals("Nombre")) {
			estado = false;
			info = "¡El nombre no es valido!";
		}
		
		if(txtUsuario.getText().equals("Usuario")) {
			estado = false;
			info = "¡El nombre de usuario no es valido!";
		}
		if(txtUsuario.getText().matches(".*[@|¿|?|:| ].*")) {
			estado = false;
			info = "¡El nombre de usuario no es valido!";
		}
		
		match = regexPass.matcher(new String(txtContraseña.getPassword()));
		if(new String(txtContraseña.getPassword()).equals("Contraseña") /*|| !match.matches()*/) {
			estado = false;
			info = "¡La contraseña no es valida!\nUna contraseña valida contiene 3-16 caracteres y mínimo una minúscula, mayúscula y dígito.";
		}

		if(new String(txtConfirmar_contraseña.getPassword()).equals("Confirmar Contraseña") || !new String(txtConfirmar_contraseña.getPassword()).equals(new String(txtContraseña.getPassword()))) {
			estado = false;
			info = "¡Las contraseñas no son iguales!";
		}
		
		if(dateChooser.getDate() == null) {
			estado = false;
			info = "¡La fecha no es valida!";
		}
		
		if(!estado) {
			JOptionPane.showMessageDialog(this, info, "Rellene correctamente los campos", 0);
		}
		
		return estado;
	}
	
	public void rellenarCampos(String usuario, String contraseña, String email, String nombreCompleto, LocalDate fechaNacimiento) {
		txtContraseña.setText(contraseña);
		txtContraseña.setEchoChar('●'); //Ocultamos la contraseña
		txtUsuario.setText(usuario);
		txtEmail.setText(email);
		txtNombre.setText(nombreCompleto);
		dateChooser.setDate(Date.from(fechaNacimiento.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}
	
	public String getUsuario() {
		return txtUsuario.getText();
	}
	
	public String getNombre() {
		return txtNombre.getText();
	}
	
	public String getPass() {
		return new String(txtContraseña.getPassword());
	}
	
	public String getEmail() {
		return txtEmail.getText();
	}
	
	public LocalDate getFecha() {
		return dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
