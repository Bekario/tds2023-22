package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JPanel;

public class Home {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatMonokaiProIJTheme.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
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
	public Home() {
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
	 * Inicializa el frame y el layout
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/imagenes/camara-de-fotos.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 50, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		
		establecerBotones();
		establecerBarraInferior();
		establecerUsuarioEmail();
	}
	
	
	
	/**
	 * Establece la barra inferior y labels
	 */
	private void establecerBarraInferior() {
		
		JPanel barraSuperior = new JPanel();
		barraSuperior.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_barraSuperior = new GridBagConstraints();
		gbc_barraSuperior.insets = new Insets(0, 0, 5, 0);
		gbc_barraSuperior.fill = GridBagConstraints.BOTH;
		gbc_barraSuperior.gridx = 0;
		gbc_barraSuperior.gridy = 0;
		frame.getContentPane().add(barraSuperior, gbc_barraSuperior);
		GridBagLayout gbl_barraSuperior = new GridBagLayout();
		gbl_barraSuperior.columnWidths = new int[]{10, 0, 0, 0, 10, 0};
		gbl_barraSuperior.rowHeights = new int[]{0, 0};
		gbl_barraSuperior.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_barraSuperior.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		barraSuperior.setLayout(gbl_barraSuperior);
		
		JLabel Titulo = new JLabel("appPhotos");
		Titulo.setForeground(new Color(0, 0, 0));
		Titulo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		GridBagConstraints gbc_Titulo = new GridBagConstraints();
		gbc_Titulo.anchor = GridBagConstraints.WEST;
		gbc_Titulo.insets = new Insets(0, 0, 0, 5);
		gbc_Titulo.gridx = 1;
		gbc_Titulo.gridy = 0;
		barraSuperior.add(Titulo, gbc_Titulo);
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 35, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Home.class.getResource("/imagenes/ParticipantImageServlet.jpg")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 255));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		panel_1.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{15, 0, 40, 0, 40, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel btnLike = new JLabel("");
		
		ImageIcon imagen = new ImageIcon(Register2.class.getResource("/imagenes/frezze.png"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		btnLike.setIcon(icono);
		

		GridBagConstraints gbc_btnLike = new GridBagConstraints();
		gbc_btnLike.insets = new Insets(0, 0, 0, 5);
		gbc_btnLike.gridx = 1;
		gbc_btnLike.gridy = 0;
		panel.add(btnLike, gbc_btnLike);
		
		JLabel numLikes = new JLabel("14 ");
		numLikes.setForeground(new Color(0, 0, 0));
		numLikes.setFont(new Font("Impact", Font.PLAIN, 11));
		GridBagConstraints gbc_numLikes = new GridBagConstraints();
		gbc_numLikes.anchor = GridBagConstraints.WEST;
		gbc_numLikes.insets = new Insets(0, 0, 0, 5);
		gbc_numLikes.gridx = 2;
		gbc_numLikes.gridy = 0;
		panel.add(numLikes, gbc_numLikes);
		
		JLabel btnComentario = new JLabel("");
		btnComentario.setIcon(new ImageIcon(Home.class.getResource("/imagenes/mensaje.png")));
		GridBagConstraints gbc_btnComentario = new GridBagConstraints();
		gbc_btnComentario.insets = new Insets(0, 0, 0, 5);
		gbc_btnComentario.gridx = 3;
		gbc_btnComentario.gridy = 0;
		panel.add(btnComentario, gbc_btnComentario);
		
		JLabel numComentarios = new JLabel("69");
		numComentarios.setForeground(new Color(0, 0, 0));
		numComentarios.setFont(new Font("Impact", Font.PLAIN, 11));
		GridBagConstraints gbc_numComentarios = new GridBagConstraints();
		gbc_numComentarios.anchor = GridBagConstraints.WEST;
		gbc_numComentarios.insets = new Insets(0, 0, 0, 5);
		gbc_numComentarios.gridx = 4;
		gbc_numComentarios.gridy = 0;
		panel.add(numComentarios, gbc_numComentarios);
		
		JLabel nombre_usuario = new JLabel("usuario_nombre");
		nombre_usuario.setForeground(new Color(0, 0, 0));
		GridBagConstraints gbc_nombre_usuario = new GridBagConstraints();
		gbc_nombre_usuario.insets = new Insets(0, 0, 0, 5);
		gbc_nombre_usuario.gridx = 5;
		gbc_nombre_usuario.gridy = 0;
		panel.add(nombre_usuario, gbc_nombre_usuario);
		
		JPanel barraInferior = new JPanel();
		barraInferior.setBackground(new Color(192, 192, 192));
		GridBagConstraints gbc_barraInferior = new GridBagConstraints();
		gbc_barraInferior.fill = GridBagConstraints.BOTH;
		gbc_barraInferior.gridx = 0;
		gbc_barraInferior.gridy = 2;
		frame.getContentPane().add(barraInferior, gbc_barraInferior);
		GridBagLayout gbl_barraInferior = new GridBagLayout();
		gbl_barraInferior.columnWidths = new int[]{10, 0, 0, 0, 10, 0};
		gbl_barraInferior.rowHeights = new int[]{0, 0};
		gbl_barraInferior.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_barraInferior.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		barraInferior.setLayout(gbl_barraInferior);
		
		JLabel btnSearch = new JLabel("");
		btnSearch.setIcon(new ImageIcon(Home.class.getResource("/imagenes/lupa.png")));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.VERTICAL;
		gbc_btnSearch.anchor = GridBagConstraints.WEST;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 0;
		barraInferior.add(btnSearch, gbc_btnSearch);
		
		JLabel btnSubirFoto = new JLabel("");
		btnSubirFoto.setIcon(new ImageIcon(Home.class.getResource("/imagenes/add.png")));
		GridBagConstraints gbc_btnSubirFoto = new GridBagConstraints();
		gbc_btnSubirFoto.fill = GridBagConstraints.VERTICAL;
		gbc_btnSubirFoto.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubirFoto.gridx = 2;
		gbc_btnSubirFoto.gridy = 0;
		barraInferior.add(btnSubirFoto, gbc_btnSubirFoto);
		
		JLabel btnPerfil = new JLabel("");
		btnPerfil.setIcon(new ImageIcon(Home.class.getResource("/imagenes/usuario.png")));
		GridBagConstraints gbc_btnPerfil = new GridBagConstraints();
		gbc_btnPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_btnPerfil.fill = GridBagConstraints.VERTICAL;
		gbc_btnPerfil.anchor = GridBagConstraints.EAST;
		gbc_btnPerfil.gridx = 3;
		gbc_btnPerfil.gridy = 0;
		barraInferior.add(btnPerfil, gbc_btnPerfil);
	}
	
	/**
	 * Establece los campos para introducir el usuario e email
	 */
	private void establecerUsuarioEmail() {
		ImageIcon imagen = new ImageIcon(Register2.class.getResource("/imagenes/mostrarcont.png"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
	}
	
	/**
	 * Gestiona las animaciones del campo usuario
	 * @param txtUser Campo de usuario
	 */
	private void addManejadorUsuario(JTextField txtUser) {
	}
	
	/**
	 * Gestiona las animaciones del campo contraseña
	 * @param textPasswd Campo de contraseña
	 */
	private void addManejadorContraseña(JPasswordField textPasswd) {
	}
	
	/**
	 * Establece los botones para iniciar sesion y registrarse
	 */
	private void establecerBotones() {
		
	}
	
	private void addManejadorBotonesMostrarContraseña(JButton boton, JPasswordField contraseña, String defecto){
	}

	private void addManejadorLogin(JButton boton) {
	}
	
	private void addManejadorRegistrarse(JButton boton) {
	}
	
	
	/**
	 * Gestiona los cambios de color al pasar el raton encima de un boton
	 * @param boton Boton que se desea que aplique el efecto
	 */
	private void addManejadorBotonColor(JButton boton) {
	}
	
	


}
