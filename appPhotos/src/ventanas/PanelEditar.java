package ventanas;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;

import modelo.Usuario;

import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;


public class PanelEditar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private JFrame frame;
	private JTextField txtEmail;
	private JTextField nombre;
	private JTextField txtNombre;
	private JTextField txtUsuario;
	private JPasswordField txtContraseña;
	private JPasswordField txtConfirmar_contraseña;
	private JButton btnMostrarPass;
	private JButton btnMostrarPass2;
	private JButton btnGuardar;
	private JLabel lblNewLabel;
	private JButton btnAtras;
	private PanelPerfil padre;

	/**
	 * Create the panel.
	 */
	public PanelEditar(Usuario usuario, JPanel padre) {
		this.usuario=usuario;
		this.padre=(PanelPerfil)padre;
		this.setSize(450, 600);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 50, 35, 0, 35, 50, 0 };
		gridBagLayout.rowHeights = new int[] { 15, 0, 25, 0, 0, 0, 5, 0, 10, 20, 50, 50, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);
		
		establecerTitulo();
		establecerEmail();
		establecerNombre();
		establecerUsuario();
		establecerContraseñasYBotones();
		establecerBotonGuardar();
		establecerBotonAtras();
	}
	
	/**
	 * Crea el titulo
	 */
	private void establecerTitulo() {
		lblNewLabel = new JLabel("Editar perfil");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
	}
	
	/**
	 * Crea el email field
	 */
	private void establecerEmail() {
		
		
		txtEmail = new JTextField();
		txtEmail.setText(usuario.getEmail());
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
		nombre = new JTextField();
		txtNombre = new JTextField();
		txtNombre.setText(usuario.getNombreCompleto());
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
		txtUsuario.setText(usuario.getUsuario());
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
	 * Crea los field contraseña y confirmar contraseña y sus botones
	 */
	private void establecerContraseñasYBotones() {
		txtContraseña = new JPasswordField();
		txtContraseña.setText(usuario.getContraseña());
		GridBagConstraints gbc_txtContraseña = new GridBagConstraints();
		gbc_txtContraseña.insets = new Insets(0, 0, 5, 5);
		gbc_txtContraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtContraseña.gridx = 2;
		gbc_txtContraseña.gridy = 7;
		add(txtContraseña, gbc_txtContraseña);

		txtConfirmar_contraseña = new JPasswordField();
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
	private void establecerBotonGuardar() {
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setForeground(new Color(218, 200, 41));
		btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnGuardar.setBorderPainted(false);
		btnGuardar.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.fill = GridBagConstraints.VERTICAL;
		gbc_btnGuardar.insets = new Insets(0, 0, 5, 5);
		gbc_btnGuardar.gridx = 2;
		gbc_btnGuardar.gridy = 10;
		add(btnGuardar, gbc_btnGuardar);
		
		addManejadorBotonColor(btnGuardar);
		
		addManejadorBotonGuardar(btnGuardar);	
	}
	
	
	/**
	 * Crea el boton continuar
	 */
	private void establecerBotonAtras() {
		btnAtras = new JButton("ATRAS");
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
		addManejadorBotonColor(btnAtras);
		addManejadorBotonAtras(btnAtras);
	}
	/**
	 * Controlamos el evento de registro
	 * @param boton
	 */
	private void addManejadorBotonGuardar(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Si todos los campos son correctos
				if (checkFields()) {
					//Intentamos registrar parcialmente el usuario
//					if (Controlador.getInstancia().registroUsuarioParcial(txtUsuario.getText(), txtContraseña.getText(), txtEmail.getText(), txtNombre.getText(), dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
//						Register2 registro2 = new Register2();
//						registro2.mostrarVentana(frame);
//						frame.dispose();
//					} else { //Si falla el registro parcial es porque el nombre de usuario ya esta utilizado
//						JOptionPane.showMessageDialog(frame, "Este nombre de usuario ya está registrado, prueba con otro distinto", "Usuario ya registrado", 0);
//					}
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
				padre.getHome().addManejadorClickBoton(boton, padre);
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
				boton.setBackground(UIManager.getColor("Button.background"));
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
		
		match = regexPass.matcher(new String(txtContraseña.getPassword()));
		if(new String(txtContraseña.getPassword()).equals("Contraseña") /*|| !match.matches()*/) {
			estado = false;
			info = "¡La contraseña no es valida!\nUna contraseña valida contiene 3-16 caracteres y mínimo una minúscula, mayúscula y dígito.";
		}
		
		if(new String(txtConfirmar_contraseña.getPassword()).equals("Confirmar Contraseña") || !new String(txtConfirmar_contraseña.getPassword()).equals(new String(txtContraseña.getPassword()))) {
			estado = false;
			info = "¡Las contraseñas no son iguales!";
		}
	
		
		if(!estado) {
			JOptionPane.showMessageDialog(frame, info, "Rellene correctamente los campos", 0);
		}
		
		return estado;
	}
	
	public void rellenarCampos(String usuario, String contraseña, String email, String nombreCompleto, LocalDate fechaNacimiento) {
		txtContraseña.setText(contraseña);
		txtContraseña.setEchoChar('●'); //Ocultamos la contraseña
		txtUsuario.setText(usuario);
		txtEmail.setText(email);
		txtNombre.setText(nombreCompleto);
	}
	

	

}