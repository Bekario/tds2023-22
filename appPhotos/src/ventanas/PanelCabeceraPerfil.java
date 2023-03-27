package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import modelo.Usuario;

import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;


public class PanelCabeceraPerfil extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Home home;
	

	/**
	 * Create the panel.
	 */
	public PanelCabeceraPerfil(Home home, Usuario usuario) {
		this.usuario=usuario;
		this.home = home;
		crearPanel();
		generarPerfil();
		
	}
	public Home getHome() {
		return home;
	}
	private void crearPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 15, 0, 0, 0, 15, 0, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 0, 0, 15, 0, 15, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
	}
	private void generarPerfil() {
		
		JLabel lblFotoPerfil = new JLabel("");
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.gridheight = 3;
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoPerfil.gridx = 0;
		gbc_lblFotoPerfil.gridy = 1;
		add(lblFotoPerfil, gbc_lblFotoPerfil);
		ImageIcon imagen = new ImageIcon(FotoPersonalizada.redondearFoto(usuario.getPerfil()));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
		lblFotoPerfil.setIcon(icono);
		
		JLabel lblUsuario = new JLabel(usuario.getUsuario());
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.gridwidth = 3;
		gbc_lblUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 2;
		gbc_lblUsuario.gridy = 1;
		add(lblUsuario, gbc_lblUsuario);
		
		JButton btnEditarPerfil = new JButton("Editar");
		
		GridBagConstraints gbc_btnEditarPerfil = new GridBagConstraints();
		gbc_btnEditarPerfil.gridheight = 3;
		gbc_btnEditarPerfil.anchor = GridBagConstraints.EAST;
		gbc_btnEditarPerfil.insets = new Insets(0, 0, 5, 0);
		gbc_btnEditarPerfil.gridx = 6;
		gbc_btnEditarPerfil.gridy = 1;
		add(btnEditarPerfil, gbc_btnEditarPerfil);
		
		home.addManejadorEdit(btnEditarPerfil, this);
		
		JLabel lblPublicaciones = new JLabel(String.valueOf(usuario.getNumeroPublicaciones()));
		GridBagConstraints gbc_lblPublicaciones = new GridBagConstraints();
		gbc_lblPublicaciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicaciones.gridx = 2;
		gbc_lblPublicaciones.gridy = 2;
		add(lblPublicaciones, gbc_lblPublicaciones);
		
		JLabel lblSeguidores = new JLabel(String.valueOf(usuario.getNumeroSeguidores()));
		GridBagConstraints gbc_lblSeguidores = new GridBagConstraints();
		gbc_lblSeguidores.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidores.gridx = 3;
		gbc_lblSeguidores.gridy = 2;
		add(lblSeguidores, gbc_lblSeguidores);
		
		JLabel lblSeguidos = new JLabel(String.valueOf(usuario.getNumeroSeguidos()));
		GridBagConstraints gbc_lblSeguidos = new GridBagConstraints();
		gbc_lblSeguidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidos.gridx = 4;
		gbc_lblSeguidos.gridy = 2;
		add(lblSeguidos, gbc_lblSeguidos);
		
		JLabel lblPublicaciones_1 = new JLabel("publicaciones");
		GridBagConstraints gbc_lblPublicaciones_1 = new GridBagConstraints();
		gbc_lblPublicaciones_1.anchor = GridBagConstraints.NORTH;
		gbc_lblPublicaciones_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicaciones_1.gridx = 2;
		gbc_lblPublicaciones_1.gridy = 3;
		add(lblPublicaciones_1, gbc_lblPublicaciones_1);
		
		JLabel lblSeguidores_1 = new JLabel("seguidores");
		GridBagConstraints gbc_lblSeguidores_1 = new GridBagConstraints();
		gbc_lblSeguidores_1.anchor = GridBagConstraints.NORTH;
		gbc_lblSeguidores_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidores_1.gridx = 3;
		gbc_lblSeguidores_1.gridy = 3;
		add(lblSeguidores_1, gbc_lblSeguidores_1);
		
		JLabel lblSeguidos_1 = new JLabel("seguidos");
		GridBagConstraints gbc_lblSeguidos_1 = new GridBagConstraints();
		gbc_lblSeguidos_1.anchor = GridBagConstraints.NORTH;
		gbc_lblSeguidos_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidos_1.gridx = 4;
		gbc_lblSeguidos_1.gridy = 3;
		add(lblSeguidos_1, gbc_lblSeguidos_1);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 7;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 5;
		add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JTextArea textArea = new JTextArea(usuario.getDescripcion());
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setSize(380, 200);
		panel.add(textArea);
	}
}
