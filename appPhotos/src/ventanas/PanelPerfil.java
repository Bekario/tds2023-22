package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;

import controlador.Controlador;
import modelo.Usuario;

import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JSlider;

public class PanelPerfil extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Home home;
	


	/**
	 * Create the panel.
	 */
	public PanelPerfil(Home home, Usuario usuario) {
		this.usuario=usuario;
		this.home = home;
		crearPanel();
		generarPerfil();
		
	}
	public Home getHome() {
		return home;
	}
	private void crearPanel() {
		this.setSize(450, 600);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 0, 15, 0, 0, 0, 15, 0, 30, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 0, 0, 15, 40, 15, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
	}
	private void generarPerfil() {
		
		JLabel lblFotoPerfil = new JLabel("");
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.gridheight = 3;
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoPerfil.gridx = 1;
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
		gbc_lblUsuario.gridx = 3;
		gbc_lblUsuario.gridy = 1;
		add(lblUsuario, gbc_lblUsuario);
		
		JButton btnEditarPerfil = new JButton("Editar");
//		btnEditarPerfil.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				home.CambiarScrollPane(null);
//			}
//		});
		
		GridBagConstraints gbc_btnEditarPerfil = new GridBagConstraints();
		gbc_btnEditarPerfil.gridheight = 3;
		gbc_btnEditarPerfil.anchor = GridBagConstraints.EAST;
		gbc_btnEditarPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditarPerfil.gridx = 7;
		gbc_btnEditarPerfil.gridy = 1;
		add(btnEditarPerfil, gbc_btnEditarPerfil);
		
		home.addManejadorEdit(btnEditarPerfil, this);
		
		JLabel lblPublicaciones = new JLabel(String.valueOf(usuario.getNumeroPublicaciones()));
		GridBagConstraints gbc_lblPublicaciones = new GridBagConstraints();
		gbc_lblPublicaciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicaciones.gridx = 3;
		gbc_lblPublicaciones.gridy = 2;
		add(lblPublicaciones, gbc_lblPublicaciones);
		
		JLabel lblSeguidores = new JLabel(String.valueOf(usuario.getNumeroSeguidos()));
		GridBagConstraints gbc_lblSeguidores = new GridBagConstraints();
		gbc_lblSeguidores.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidores.gridx = 4;
		gbc_lblSeguidores.gridy = 2;
		add(lblSeguidores, gbc_lblSeguidores);
		
		JLabel lblSeguidos = new JLabel(String.valueOf(usuario.getNumeroSeguidores()));
		GridBagConstraints gbc_lblSeguidos = new GridBagConstraints();
		gbc_lblSeguidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidos.gridx = 5;
		gbc_lblSeguidos.gridy = 2;
		add(lblSeguidos, gbc_lblSeguidos);
		
		JLabel lblNewLabel = new JLabel("publicaciones");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 3;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblSeguidores_1 = new JLabel("seguidores");
		GridBagConstraints gbc_lblSeguidores_1 = new GridBagConstraints();
		gbc_lblSeguidores_1.anchor = GridBagConstraints.NORTH;
		gbc_lblSeguidores_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidores_1.gridx = 4;
		gbc_lblSeguidores_1.gridy = 3;
		add(lblSeguidores_1, gbc_lblSeguidores_1);
		
		JLabel lblSeguidos_1 = new JLabel("seguidos");
		GridBagConstraints gbc_lblSeguidos_1 = new GridBagConstraints();
		gbc_lblSeguidos_1.anchor = GridBagConstraints.NORTH;
		gbc_lblSeguidos_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidos_1.gridx = 5;
		gbc_lblSeguidos_1.gridy = 3;
		add(lblSeguidos_1, gbc_lblSeguidos_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText(usuario.getDescripcion());
		textArea.setLineWrap(true);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 7;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 5;
		add(textArea, gbc_textArea);
		
		PanelFotoAlbum panelFotoAlbum = new PanelFotoAlbum();
		GridBagConstraints gbc_panelFotoAlbum = new GridBagConstraints();
		gbc_panelFotoAlbum.gridwidth = 7;
		gbc_panelFotoAlbum.insets = new Insets(0, 0, 0, 5);
		gbc_panelFotoAlbum.fill = GridBagConstraints.BOTH;
		gbc_panelFotoAlbum.gridx = 1;
		gbc_panelFotoAlbum.gridy = 7;
		add(panelFotoAlbum, gbc_panelFotoAlbum);
	}
		

}
