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
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.Controlador;
import modelo.Usuario;

import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class PanelEditar extends JPanel {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private JFrame frame;
	private JTextField txtEmail;
	private JTextField txtNombre;
	private JTextField txtUsuario;
	private JPasswordField txtContraseña;
	private JPasswordField txtConfirmar_contraseña;
	private JButton btnMostrarPass;
	private JButton btnMostrarPass2;
	private JButton btnGuardar;
	private JLabel lblCabecera;
	private JButton btnAtras;
	private PanelCabeceraPerfil padre;
	private JLabel lblFotoPerfil;
	private JButton btnCambiarFoto;
	private JScrollPane scrollPane;
	private JTextArea txtDescripcion;

	/**
	 * Create the panel.
	 */
	public PanelEditar(Usuario usuario, JPanel padre) {
		this.usuario=usuario;
		this.padre=(PanelCabeceraPerfil)padre;
		this.setSize(450, 600);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 50, 35, 0, 35, 50, 0 };
		gridBagLayout.rowHeights = new int[] { 15, 0, 25, 0, 0, 0, 0, 0, 5, 0, 0, 10, 20, 50, 50, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);
		
		establecerTitulo();
	
		establecerEmail();
		establecerNombre();
		establecerUsuario();
		establecerContraseñasYBotones();
		establecerBotonGuardar();
		establecerBotonAtras();
		establecerDescripcion();
		establecerImagenSubida(usuario.getPerfil());
	}
	
	/**
	 * Crea el titulo
	 */
	private void establecerTitulo() {
		lblCabecera = new JLabel("Editar perfil");
		GridBagConstraints gbc_lblCabecera = new GridBagConstraints();
		gbc_lblCabecera.insets = new Insets(0, 0, 5, 5);
		gbc_lblCabecera.gridx = 2;
		gbc_lblCabecera.gridy = 1;
		add(lblCabecera, gbc_lblCabecera);
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
		gbc_txtEmail.gridy = 5;
		add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		
		Manejadores.addManejadorTextos(txtEmail, "Email");
	}
	
	/**
	 * Crea el nombre field
	 */
	private void establecerNombre() {
		txtNombre = new JTextField();
		txtNombre.setText(usuario.getNombreCompleto());
		txtNombre.setToolTipText("");
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 2;
		gbc_txtNombre.gridy = 6;
		add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);
		
		Manejadores.addManejadorTextos(txtNombre, "Nombre");
	}
	
	private void establecerDescripcion() {
		txtDescripcion = new JTextArea(2, 20);
		txtDescripcion.setWrapStyleWord(true);
		txtDescripcion.setLineWrap(true);
		if(usuario.getDescripcion().equals("")){
			txtDescripcion.setText("Introduce una breve descripción sobre ti..."); 
		}else txtDescripcion.setText(usuario.getDescripcion());
		
		scrollPane = new JScrollPane(txtDescripcion);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 9;
		add(scrollPane, gbc_scrollPane);
		
		Manejadores.addManejadorArea(txtDescripcion, "Introduce una breve descripción sobre ti...");
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
		gbc_txtUsuario.gridy = 8;
		add(txtUsuario, gbc_txtUsuario);
		txtUsuario.setColumns(10);
		
		Manejadores.addManejadorTextos(txtUsuario, "Usuario");
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
		gbc_txtContraseña.gridy = 10;
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
		gbc_btnMostrarPass.gridy = 10;
		add(btnMostrarPass, gbc_btnMostrarPass);
		txtConfirmar_contraseña.setText("Confirmar Contraseña");
		txtConfirmar_contraseña.setEchoChar((char) 0);
		GridBagConstraints gbc_txtConfirmar_contraseña = new GridBagConstraints();
		gbc_txtConfirmar_contraseña.insets = new Insets(0, 0, 5, 5);
		gbc_txtConfirmar_contraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConfirmar_contraseña.gridx = 2;
		gbc_txtConfirmar_contraseña.gridy = 11;
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
		gbc_btnMostrarPass2.gridy = 11;
		add(btnMostrarPass2, gbc_btnMostrarPass2);
		
		Manejadores.addManejadorContraseña(txtContraseña, "Contraseña");
		Manejadores.addManejadorContraseña(txtConfirmar_contraseña, "Confirmar Contraseña");
		
		Manejadores.addManejadorBotonesMostrarContraseña(btnMostrarPass, txtContraseña, "Contraseña");
		Manejadores.addManejadorBotonesMostrarContraseña(btnMostrarPass2, txtConfirmar_contraseña, "Confirmar Contraseña");
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
		gbc_btnGuardar.gridy = 13;
		add(btnGuardar, gbc_btnGuardar);
		
		Manejadores.addManejadorBotonColor(btnGuardar);
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
		gbc_btnAtras.gridy = 14;
		add(btnAtras, gbc_btnAtras);
		
		Manejadores.addManejadorBotonColor(btnAtras);
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
					Controlador.getInstancia().modificarUsuario(txtUsuario.getText(), new String(txtContraseña.getPassword()) , txtEmail.getText(), txtNombre.getText(), txtDescripcion.getText());
					padre.getHome().setPanelPerfil();
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
				padre.getHome().setPanelPerfil();
			}
		});
	}

	private void establecerImagenSubida(String ruta) {
		lblFotoPerfil = new JLabel(new ImageIcon(FotoPersonalizada.redondearFoto(ruta)));
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoPerfil.gridx = 2;
		gbc_lblFotoPerfil.gridy = 3;
		add(lblFotoPerfil, gbc_lblFotoPerfil);
		
		btnCambiarFoto = new JButton("Cambiar");
		GridBagConstraints gbc_btnCambiarFoto = new GridBagConstraints();
		gbc_btnCambiarFoto.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_btnCambiarFoto.insets = new Insets(0, 0, 5, 5);
		gbc_btnCambiarFoto.gridx = 2;
		gbc_btnCambiarFoto.gridy = 4;
		add(btnCambiarFoto, gbc_btnCambiarFoto);
		addManejadorBotonInsertarImagen(btnCambiarFoto);
		//fotoActual = usuario.getPerfil();
	}
	
	//MALENIA lo usamos 3 4 veces en todo el programa, unificar?
	private void addManejadorBotonInsertarImagen(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pattern regexpPng = Pattern.compile(".+\\.png", Pattern.CASE_INSENSITIVE);
				Pattern regexpJpg = Pattern.compile(".+\\.jpg", Pattern.CASE_INSENSITIVE);
				Pattern regexpJpeg = Pattern.compile(".+\\.jpeg", Pattern.CASE_INSENSITIVE);
				
				
				//Creamos el selector de archivos con su filtro
				JFileChooser selector = new JFileChooser();
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("PNG, JPG, JPEG", "jpg", "png", "jpeg");
				selector.setFileFilter(filtro);
				selector.removeChoosableFileFilter(null);
				
				int resp = selector.showOpenDialog(selector);
				File fichero = selector.getSelectedFile();
				if (fichero != null) {
					
					//Comprobamos que la extension sea correcta
					if(!regexpPng.matcher(fichero.getName()).matches() && !regexpJpg.matcher(fichero.getName()).matches() && !regexpJpeg.matcher(fichero.getName()).matches()) {
						JOptionPane.showMessageDialog(frame, "¡El fichero debe tener una extensión válida!", "Rellene correctamente los campos", 0);
					} else if(resp == JFileChooser.APPROVE_OPTION) { //En caso de ser valida, introducimos la imagen temporalmente
						//Esto hay que hacerlo
						//Controlador.getInstancia().insertarFotoSubida(fichero.getAbsolutePath(), fichero.getName());
						String ruta = fichero.getAbsolutePath();
						remove(lblFotoPerfil);
						usuario.setPerfil(ruta);
						//modificar usuario MALENIA
						establecerImagenSubida(ruta);
						updateUI();
						
					}
				}
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
		//Comprobamos si hay una descripcion
		if(txtDescripcion.getText().equals("Introduce una breve descripción sobre ti...")) {
			txtDescripcion.setText("");
		}
		if(txtDescripcion.getText().length()>200) {
			estado = false;
			info = "¡Sabemos que eres muy interesante, pero el máximo de carácteres son 200 :( !";
		}
		
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
