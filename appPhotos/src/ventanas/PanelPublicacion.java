package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelPublicacion extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean likePresionado;
	
	/**
	 * Create the panel.
	 */
	public PanelPublicacion() {
		this.setSize(450, 600);
		likePresionado = false;
		crearPanel();
	}
	
	private void crearPanel() {
		crearPanelEImagen();
		crearBarraInferior();
	}
	
	private void crearPanelEImagen() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 50, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel Imagen = new JPanel();
		GridBagConstraints gbc_Imagen = new GridBagConstraints();
		gbc_Imagen.insets = new Insets(0, 0, 5, 0);
		gbc_Imagen.fill = GridBagConstraints.BOTH;
		gbc_Imagen.gridx = 0;
		gbc_Imagen.gridy = 0;
		add(Imagen, gbc_Imagen);
		
		ImageIcon imagen = new ImageIcon(Register2.class.getResource("/imagenes/ParticipantImageServlet.jpg"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(425, 425, Image.SCALE_SMOOTH));
		
		JLabel lblFoto = new JLabel("");
		lblFoto.setIcon(icono);
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.insets = new Insets(0, 0, 5, 0);
		gbc_lblFoto.gridx = 0;
		gbc_lblFoto.gridy = 0;
		Imagen.add(lblFoto, gbc_lblFoto);	

	}
	
	private void crearBarraInferior() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(218, 200, 41));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{25, 0, 0, 25, 0, 0, 25, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		ImageIcon imagen = new ImageIcon(Register2.class.getResource("/imagenes/frezze.png"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		
		JLabel lblLike = new JLabel("");
		lblLike.setIcon(icono);
		GridBagConstraints gbc_lblLike = new GridBagConstraints();
		gbc_lblLike.insets = new Insets(0, 0, 0, 5);
		gbc_lblLike.anchor = GridBagConstraints.EAST;
		gbc_lblLike.gridx = 1;
		gbc_lblLike.gridy = 0;
		panel.add(lblLike, gbc_lblLike);
		
		JLabel lblNumLikes = new JLabel("69");
		lblNumLikes.setForeground(new Color(0, 0, 0));
		lblNumLikes.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblNumLikes = new GridBagConstraints();
		gbc_lblNumLikes.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumLikes.gridx = 2;
		gbc_lblNumLikes.gridy = 0;
		panel.add(lblNumLikes, gbc_lblNumLikes);
		
		JLabel lblComentario = new JLabel("");
		lblComentario.setIcon(new ImageIcon(PanelPublicacion.class.getResource("/imagenes/mensaje.png")));
		GridBagConstraints gbc_lblComentario = new GridBagConstraints();
		gbc_lblComentario.insets = new Insets(0, 0, 0, 5);
		gbc_lblComentario.gridx = 4;
		gbc_lblComentario.gridy = 0;
		panel.add(lblComentario, gbc_lblComentario);
		
		JLabel lblNumComentarios = new JLabel("420");
		lblNumComentarios.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumComentarios.setForeground(new Color(0, 0, 0));
		GridBagConstraints gbc_lblNumComentarios = new GridBagConstraints();
		gbc_lblNumComentarios.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumComentarios.gridx = 5;
		gbc_lblNumComentarios.gridy = 0;
		panel.add(lblNumComentarios, gbc_lblNumComentarios);
		
		JLabel lblNombreUsuario = new JLabel("user.getNombre()");
		lblNombreUsuario.setForeground(new Color(0, 0, 0));
		lblNombreUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblNombreUsuario = new GridBagConstraints();
		gbc_lblNombreUsuario.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreUsuario.gridx = 7;
		gbc_lblNombreUsuario.gridy = 0;
		panel.add(lblNombreUsuario, gbc_lblNombreUsuario);
		
		imagen = new ImageIcon(Register2.class.getResource("/imagenes/ParticipantImageServlet.jpg"));
		icono = new ImageIcon(imagen.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		
		JLabel lblFotoPerfil = new JLabel("");
		lblFotoPerfil.setIcon(icono);
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_lblFotoPerfil.gridx = 8;
		gbc_lblFotoPerfil.gridy = 0;
		panel.add(lblFotoPerfil, gbc_lblFotoPerfil);
		
		addManejadorClickLike(lblLike);
	}
	
	private void addManejadorClickLike(JLabel label) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ImageIcon imagen;
				if(likePresionado) {
					imagen = new ImageIcon(Register2.class.getResource("/imagenes/frezze.png"));
					likePresionado = false;
				} else {
					imagen = new ImageIcon(Register2.class.getResource("/imagenes/hotttt.png"));
					likePresionado = true;
				}
				Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
				label.setIcon(icono);
			}
		});
	}

}
