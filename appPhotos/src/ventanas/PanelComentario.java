package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import controlador.Controlador;
import modelo.Comentario;
import modelo.Publicacion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelComentario extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean likePresionado;
	private JLabel lblNumLikes;
	private JLabel lblFotoPerfil;
	private JLabel lblLike;
	private JLabel lblNombreUsuario;
	private JLabel lblTitulo_1;
	private JTextArea lblTitulo_2;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JLabel comentario;
	private int y;
	private JButton lblEnviar;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	/**
	 * Create the panel.
	 */
	public PanelComentario(Publicacion publicacion) {
		likePresionado = false;
		y=0;
		crearPanel(publicacion);	
	}

	private void crearPanel(Publicacion publicacion) {
		this.setSize(400, 400);
		crearPanelEImagen();
		crearBarraInferior(String.valueOf(publicacion.getMegusta()));
		crearBarraSuperior(publicacion.getUsuario().getUsuario(), publicacion.getTitulo(), publicacion.getDescripcion(), publicacion.getUsuario().getPerfil(), publicacion.getFecha().format(DateTimeFormatter.ISO_DATE).toString());
		addComentario(publicacion.getComentarios());
		
		addManejadorComentarioTextfield(textField, publicacion);
		addManejadorComentarioBoton(lblEnviar, publicacion);
		addManejadorClickLike(lblLike, publicacion);
		
		updateUI();
	}
	
	private void crearPanelEImagen() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{5, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 45, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

	}
	
	private void crearBarraInferior(String likes) {
		
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		/*lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("New label");
		panel.add(lblNewLabel_2);*/
		
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(new Color(218, 200, 41));
		GridBagConstraints gbc_panelInferior = new GridBagConstraints();
		gbc_panelInferior.fill = GridBagConstraints.BOTH;
		gbc_panelInferior.gridx = 1;
		gbc_panelInferior.gridy = 2;
		add(panelInferior, gbc_panelInferior);
		GridBagLayout gbl_panelInferior = new GridBagLayout();
		gbl_panelInferior.columnWidths = new int[]{5, 0, 0, 0, 0, 0, 0, 5, 0};
		gbl_panelInferior.rowHeights = new int[]{0, 0};
		gbl_panelInferior.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelInferior.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelInferior.setLayout(gbl_panelInferior);
		
		ImageIcon imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/frezze.png"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		
		lblLike = new JLabel("");
		lblLike.setIcon(icono);
		GridBagConstraints gbc_lblLike = new GridBagConstraints();
		gbc_lblLike.insets = new Insets(0, 0, 0, 5);
		gbc_lblLike.anchor = GridBagConstraints.EAST;
		gbc_lblLike.gridx = 1;
		gbc_lblLike.gridy = 0;
		panelInferior.add(lblLike, gbc_lblLike);
		
		lblNumLikes = new JLabel(likes);
		lblNumLikes.setForeground(new Color(0, 0, 0));
		lblNumLikes.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblNumLikes = new GridBagConstraints();
		gbc_lblNumLikes.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumLikes.gridx = 2;
		gbc_lblNumLikes.gridy = 0;
		panelInferior.add(lblNumLikes, gbc_lblNumLikes);
		
		imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/ParticipantImageServlet.jpg"));
		icono = new ImageIcon(imagen.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		
		textField = new JTextField();
		
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 4;
		gbc_textField.gridy = 0;
		panelInferior.add(textField, gbc_textField);
		textField.setColumns(10);
		
		lblEnviar = new JButton("");
		lblEnviar.setContentAreaFilled(false);
		lblEnviar.setBorder(null);
		
		imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/enviar.png"));
		icono = new ImageIcon(imagen.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		lblEnviar.setIcon(icono);
		GridBagConstraints gbc_lblEnviar = new GridBagConstraints();
		gbc_lblEnviar.insets = new Insets(0, 0, 0, 5);
		gbc_lblEnviar.gridx = 6;
		gbc_lblEnviar.gridy = 0;
		panelInferior.add(lblEnviar, gbc_lblEnviar);
	}
	
	private void addManejadorComentarioBoton(JButton boton, Publicacion publicacion) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manejadorAddComentario(publicacion);
			}
		});
	}
	
	private void addManejadorComentarioTextfield(JTextField textfield, Publicacion publicacion) {
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					manejadorAddComentario(publicacion);
				}
			}
		});
	}
	
	
	
	private void manejadorAddComentario(Publicacion publicacion) {
		Controlador.getInstancia().addComentario(publicacion, textField.getText());
		addComentario(Controlador.getInstancia().obtenerUsuarioActual().getUsuario()+": "+textField.getText());
		updateUI();
		textField.setText("");
		
		//Bajamos la barra hasta abajo
		JScrollBar verScrBar= scrollPane.getVerticalScrollBar();
		verScrBar.setValue(verScrBar.getMaximum());

	}
	
	private void crearBarraSuperior(String usuario, String titulo, String descripcion, String perfil, String fecha) {
		JPanel panelSuperior = new JPanel();
		GridBagConstraints gbc_panelSuperior = new GridBagConstraints();
		gbc_panelSuperior.gridwidth = 2;
		gbc_panelSuperior.insets = new Insets(0, 0, 5, 0);
		gbc_panelSuperior.fill = GridBagConstraints.BOTH;
		gbc_panelSuperior.gridx = 0;
		gbc_panelSuperior.gridy = 0;
		add(panelSuperior, gbc_panelSuperior);
		GridBagLayout gbl_panelSuperior = new GridBagLayout();
		gbl_panelSuperior.columnWidths = new int[]{20, 0, 0, 0, 5};
		gbl_panelSuperior.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelSuperior.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE, 0.0, 0.0};
		gbl_panelSuperior.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelSuperior.setLayout(gbl_panelSuperior);
		
		lblNombreUsuario = new JLabel("<html><p style=\"width:300px\">"+usuario+"</p></html>");
		lblNombreUsuario.setForeground(new Color(0, 0, 0));
		lblNombreUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblNombreUsuario = new GridBagConstraints();
		gbc_lblNombreUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblNombreUsuario.gridx = 2;
		gbc_lblNombreUsuario.gridy = 0;
		panelSuperior.add(lblNombreUsuario, gbc_lblNombreUsuario);
				
		ImageIcon imagen = new ImageIcon(perfil);
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		
		lblFotoPerfil = new JLabel("");
		lblFotoPerfil.setIcon(icono);
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.anchor = GridBagConstraints.WEST;
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoPerfil.gridx = 1;
		gbc_lblFotoPerfil.gridy = 0;
		panelSuperior.add(lblFotoPerfil, gbc_lblFotoPerfil);
		
		lblNewLabel = new JLabel(fecha);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 0;
		panelSuperior.add(lblNewLabel, gbc_lblNewLabel);
		
		
		lblTitulo_1 = new JLabel("<html><p style=\"width:300px\">"+titulo+"</p></html>");
		lblTitulo_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblTitulo_1 = new GridBagConstraints();
		gbc_lblTitulo_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo_1.anchor = GridBagConstraints.WEST;
		gbc_lblTitulo_1.gridwidth = 3;
		gbc_lblTitulo_1.gridx = 1;
		gbc_lblTitulo_1.gridy = 1;
		panelSuperior.add(lblTitulo_1, gbc_lblTitulo_1);
		
		lblTitulo_2 = new JTextArea(descripcion);
		lblTitulo_2.setEditable(false);
		lblTitulo_2.setWrapStyleWord(true);
		lblTitulo_2.setLineWrap(true);
		lblTitulo_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTitulo_2 = new GridBagConstraints();
		gbc_lblTitulo_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitulo_2.gridwidth = 3;
		gbc_lblTitulo_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblTitulo_2.gridx = 1;
		gbc_lblTitulo_2.gridy = 2;
		panelSuperior.add(lblTitulo_2, gbc_lblTitulo_2);
	}
	
	public void addComentario(List<Comentario> comentarios) {
		comentarios.stream()
				   .forEachOrdered(c -> addComentario(c.getTexto()));
		
	}
	public void addComentario(String texto) {
		comentario = new JLabel(texto);
		panel.add(comentario);
		panel.add(Box.createVerticalStrut(5));
		y++;
	}
	
	private void addManejadorClickLike(JLabel label, Publicacion publicacion) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ImageIcon imagen;
				if(likePresionado) {
					imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/frezze.png"));
					likePresionado = false;
					Controlador.getInstancia().quitarMeGusta(publicacion);
				} else {
					imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/hotttt.png"));
					likePresionado = true;
					Controlador.getInstancia().darMeGusta(publicacion);
				}
				Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
				label.setIcon(icono);
				lblNumLikes.setText(String.valueOf(publicacion.getMegusta()));
			}
		});
	}
}
