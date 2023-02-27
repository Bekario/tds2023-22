package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;

import controlador.Controlador;
import modelo.Publicacion;
import modelo.Usuario;

import java.awt.Insets;
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

public class PanelFotoAlbum extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String FOTO = "foto";
	private static final String ALBUM = "album";
	
	private PanelCuadriculaPublicaciones panelFotos;
	private PanelCuadriculaPublicaciones panelAlbum;
	private String estadoPanel;


	/**
	 * Create the panel.
	 */
	public PanelFotoAlbum() {
		this.setSize(450, 600);
		crearPanel();
		
	}
	
	private void crearPanel() {
		crearLayout();
		crearBotones();
		crearPanelesPublicaciones();
	}
	
	private void crearLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 5, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	
	private void crearBotones() {
		JButton btnFotos = new JButton("Fotos");
		btnFotos.setBackground(new Color(45, 42, 46));
		btnFotos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnFotos = new GridBagConstraints();
		gbc_btnFotos.anchor = GridBagConstraints.EAST;
		gbc_btnFotos.insets = new Insets(0, 0, 5, 5);
		gbc_btnFotos.gridx = 1;
		gbc_btnFotos.gridy = 0;
		add(btnFotos, gbc_btnFotos);
		btnFotos.setBorder(null);
		
		JButton btnAlbum = new JButton("Album");
		btnAlbum.setBackground(new Color(42, 45, 46));
		btnAlbum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnAlbum = new GridBagConstraints();
		gbc_btnAlbum.anchor = GridBagConstraints.WEST;
		gbc_btnAlbum.insets = new Insets(0, 0, 5, 5);
		gbc_btnAlbum.gridx = 3;
		gbc_btnAlbum.gridy = 0;
		add(btnAlbum, gbc_btnAlbum);
		btnAlbum.setBorder(null);
	
	}
	
	private void crearPanelesPublicaciones() {
		//Creamos el panel de fotos, que es el que se muestra primero por defecto
		panelFotos = new PanelCuadriculaPublicaciones();
		GridBagConstraints gbc_panelFotos = new GridBagConstraints();
		gbc_panelFotos.gridwidth = 3;
		gbc_panelFotos.insets = new Insets(0, 0, 0, 5);
		gbc_panelFotos.fill = GridBagConstraints.BOTH;
		gbc_panelFotos.gridx = 1;
		gbc_panelFotos.gridy = 2;
		add(panelFotos, gbc_panelFotos);
		//Por defecto comienza con el panel de fotos
		estadoPanel = FOTO;
		
		//Creamos el panel de albums
		panelAlbum = new PanelCuadriculaPublicaciones();
		GridBagConstraints gbc_panelAlbum = new GridBagConstraints();
		gbc_panelAlbum.gridwidth = 3;
		gbc_panelAlbum.insets = new Insets(0, 0, 0, 5);
		gbc_panelAlbum.fill = GridBagConstraints.BOTH;
		gbc_panelAlbum.gridx = 1;
		gbc_panelAlbum.gridy = 2;
		add(panelAlbum, gbc_panelAlbum);
	}
	
	private void cargarPublicaciones() {
		Usuario u = Controlador.getInstancia().getUsuarioActual();
		addPublicaciones(panelAlbum, u.getFotos());
		addPublicaciones(panelFotos, u.getAlbums());
	}
	
	/**
	 * Añade al panel una lista de publicaciones
	 * @param panel
	 * @param publicaciones
	 */
	private void addPublicaciones(PanelCuadriculaPublicaciones panel, List <Publicacion> publicaciones) {
		panel.addPublicaciones(publicaciones);
	}

	

}
