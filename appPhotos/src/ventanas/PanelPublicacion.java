package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlador.Controlador;
import modelo.Publicacion;
import modelo.Usuario;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelPublicacion extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean likePresionado;
	private JLabel lblFoto;
	private JLabel lblNumLikes;
	private JLabel lblFotoPerfil;
	private JLabel lblLike;
	private JLabel lblTitulo;
	private JLabel lblNombreUsuario;
	private JLabel lblNumComentarios;
	private JButton btnComentario;
	private Home home;
	
	/**
	 * Create the panel.
	 */
	public PanelPublicacion(Home padre, Publicacion publicacion) {
		home = padre;
		likePresionado = false;
		crearPanel(publicacion);
	}
	
	private void crearPanel(Publicacion p) {
		this.setSize(450, 490);
		crearPanelEImagen(Controlador.getInstancia().obtenerPortadaPublicacion(p));
		crearBarraInferior(p.getMegusta(), p.getNumComentarios(), p.getUsuario().getUsuario(), p.getUsuario().getPerfil());
		crearBarraSuperior(p.getTitulo());
		addManejadorClickUsuario(lblNombreUsuario, p.getUsuario());
		addManejadorClickUsuario(lblFotoPerfil, p.getUsuario());
		addManejadorClickLike(lblLike, p);
		Manejadores.addManejadorClickToFotoBoton(btnComentario, p);
		Manejadores.addManejadorClickToFoto(lblFoto, p);

	}
	
	private void crearPanelEImagen(String portada) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 45, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panelImagen = new JPanel();
		GridBagConstraints gbc_panelImagen = new GridBagConstraints();
		gbc_panelImagen.insets = new Insets(0, 0, 5, 0);
		gbc_panelImagen.fill = GridBagConstraints.BOTH;
		gbc_panelImagen.gridx = 0;
		gbc_panelImagen.gridy = 1;
		add(panelImagen, gbc_panelImagen);
		
		ImageIcon imagen = new ImageIcon(portada);
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(380, 380, Image.SCALE_SMOOTH));
		
		lblFoto = new JLabel("");
		lblFoto.setIcon(icono);
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.insets = new Insets(0, 0, 5, 0);
		gbc_lblFoto.gridx = 0;
		gbc_lblFoto.gridy = 0;
		panelImagen.add(lblFoto, gbc_lblFoto);	

	}
	
	private void crearBarraInferior(int likes, int comentarios, String user, String perfil) {
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(new Color(247, 211, 131));
		GridBagConstraints gbc_panelInferior = new GridBagConstraints();
		gbc_panelInferior.fill = GridBagConstraints.BOTH;
		gbc_panelInferior.gridx = 0;
		gbc_panelInferior.gridy = 2;
		add(panelInferior, gbc_panelInferior);
		GridBagLayout gbl_panelInferior = new GridBagLayout();
		gbl_panelInferior.columnWidths = new int[]{25, 0, 0, 25, 0, 0, 25, 0, 10, 0, 0, 0};
		gbl_panelInferior.rowHeights = new int[]{0, 0};
		gbl_panelInferior.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		
		lblNumLikes = new JLabel(String.valueOf(likes));
		lblNumLikes.setForeground(new Color(0, 0, 0));
		lblNumLikes.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblNumLikes = new GridBagConstraints();
		gbc_lblNumLikes.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumLikes.gridx = 2;
		gbc_lblNumLikes.gridy = 0;
		panelInferior.add(lblNumLikes, gbc_lblNumLikes);
		
		btnComentario = new JButton("");
		btnComentario.setContentAreaFilled(false);
		btnComentario.setIcon(new ImageIcon(PanelPublicacion.class.getResource("/imagenes/mensaje.png")));
		GridBagConstraints gbc_btnComentario = new GridBagConstraints();
		gbc_btnComentario.insets = new Insets(0, 0, 0, 5);
		gbc_btnComentario.gridx = 4;
		gbc_btnComentario.gridy = 0;
		panelInferior.add(btnComentario, gbc_btnComentario);
		
		Manejadores.addManejadorAnimacionBoton(this, btnComentario, "/imagenes/mensaje.gif", "/imagenes/mensaje.png");
		
		lblNumComentarios = new JLabel(String.valueOf(comentarios));
		lblNumComentarios.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumComentarios.setForeground(new Color(0, 0, 0));
		GridBagConstraints gbc_lblNumComentarios = new GridBagConstraints();
		gbc_lblNumComentarios.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumComentarios.gridx = 5;
		gbc_lblNumComentarios.gridy = 0;
		panelInferior.add(lblNumComentarios, gbc_lblNumComentarios);
		
		lblNombreUsuario = new JLabel(user);
		lblNombreUsuario.setForeground(new Color(0, 0, 0));
		lblNombreUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblNombreUsuario = new GridBagConstraints();
		gbc_lblNombreUsuario.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreUsuario.gridx = 7;
		gbc_lblNombreUsuario.gridy = 0;
		panelInferior.add(lblNombreUsuario, gbc_lblNombreUsuario);
		
		lblFotoPerfil = new JLabel("");
		lblFotoPerfil.setIcon(new ImageIcon(FotoPersonalizada.redondearFoto(perfil).getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_lblFotoPerfil.gridx = 9;
		gbc_lblFotoPerfil.gridy = 0;
		panelInferior.add(lblFotoPerfil, gbc_lblFotoPerfil);
		
		
	}


	private void addManejadorClickUsuario(JLabel label, Usuario usuario) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				home.setPanel(new PanelPerfil(home, usuario));
			}
		});

	}
	
	private void crearBarraSuperior(String titulo) {
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBackground(new Color(247, 211, 131));
		GridBagConstraints gbc_panelSuperior = new GridBagConstraints();
		gbc_panelSuperior.insets = new Insets(0, 0, 5, 0);
		gbc_panelSuperior.fill = GridBagConstraints.BOTH;
		gbc_panelSuperior.gridx = 0;
		gbc_panelSuperior.gridy = 0;
		add(panelSuperior, gbc_panelSuperior);
		GridBagLayout gbl_panelSuperior = new GridBagLayout();
		gbl_panelSuperior.columnWidths = new int[]{20, 0, 0};
		gbl_panelSuperior.rowHeights = new int[]{0, 0};
		gbl_panelSuperior.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelSuperior.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelSuperior.setLayout(gbl_panelSuperior);
		
		lblTitulo = new JLabel(titulo);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTitulo.setForeground(new Color(0, 0, 0));
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 0;
		panelSuperior.add(lblTitulo, gbc_lblTitulo);
	}
	
	private void addManejadorClickLike(JLabel label, Publicacion p) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ImageIcon imagen;
				if(likePresionado) {
					imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/frezze.png"));
					likePresionado = false;
					p.quitarMeGusta();
				} else {
					imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/hotttt.png"));
					likePresionado = true;
					p.darMeGusta();
				}
				Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
				label.setIcon(icono);
				lblNumLikes.setText(String.valueOf(p.getMegusta()));
			}
		});
	}
	
}
