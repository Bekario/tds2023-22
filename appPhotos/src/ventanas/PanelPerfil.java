package ventanas;

import javax.swing.JPanel;

import controlador.Controlador;

import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;

import java.awt.Insets;


public class PanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	private Home home;
	private PanelCabeceraPerfil panelCabeceraPerfil;
	private PanelFotoAlbum panelFotoAlbum;
	
	private String usuario;

	/**
	 * Create the panel.
	 */
	public PanelPerfil(Home home, String usuario) {
		this.home = home;
		this.usuario = usuario;
		crearPanel();
		generarCabecera();
		
	}
	
	public Home getHome() {
		return home;
	}
	
	private void crearPanel() {
		this.setSize(450, 600);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 0, 15, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 15, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
	}
	
	private void generarCabecera() {
		panelCabeceraPerfil = new PanelCabeceraPerfil(home, usuario);
		GridBagConstraints gbc_panelCabeceraPerfil = new GridBagConstraints();
		gbc_panelCabeceraPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_panelCabeceraPerfil.fill = GridBagConstraints.BOTH;
		gbc_panelCabeceraPerfil.gridx = 1;
		gbc_panelCabeceraPerfil.gridy = 1;
		add(panelCabeceraPerfil, gbc_panelCabeceraPerfil);
		
		panelFotoAlbum = new PanelFotoAlbum(home, usuario);
		GridBagConstraints gbc_panelFotoAlbum = new GridBagConstraints();
		gbc_panelFotoAlbum.gridwidth = 2;
		gbc_panelFotoAlbum.fill = GridBagConstraints.BOTH;
		gbc_panelFotoAlbum.gridx = 1;
		gbc_panelFotoAlbum.gridy = 3;
		add(panelFotoAlbum, gbc_panelFotoAlbum);
	}
	
	/**
	 * Añade una unica publicacion, si es album se añade al PanelCuadriculaAlbum, en caso contrario se añade al PanelCuadriculaFotos
	 * @param publicacion
	 */
	protected void addPublicacion(int publicacion) {
		//Si es una foto, añadimos una foto
		if (publicacion.getClass().getName() == "modelo.Foto") {
			panelFotoAlbum.addFoto((Foto) publicacion);
		} else { //Si es un album, añadimos un album
			panelFotoAlbum.addAlbum((Album) publicacion);
		}
	}
	
	/**
	 * Actualiza la cabecera y el panel de fotos y albumes
	 */
	public void actualizarCompleto() {
		actualizarCabecera();
		actualizarFotoAlbum();
	}
	
	public void actualizarCabecera() {
		panelCabeceraPerfil.actualizarCampos(usuario);
		this.updateUI();
	}
	
	public void actualizarFotoAlbum() {
		panelFotoAlbum.actualizarPanel();
		this.updateUI();
	}
		

}
