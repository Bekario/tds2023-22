package ventanas;

import javax.swing.JPanel;

import modelo.Publicacion;
import modelo.Usuario;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class PanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	private Home home;
	private PanelCabeceraPerfil panelCabeceraPerfil;
	private PanelFotoAlbum panelFotoAlbum;

	/**
	 * Create the panel.
	 */
	public PanelPerfil(Home home, Usuario usuario) {
		this.home = home;
		crearPanel();
		generarCabecera(usuario);
		
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
	
	private void generarCabecera(Usuario usuario) {
		panelCabeceraPerfil = new PanelCabeceraPerfil(home, usuario);
		GridBagConstraints gbc_panelCabeceraPerfil = new GridBagConstraints();
		gbc_panelCabeceraPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_panelCabeceraPerfil.fill = GridBagConstraints.VERTICAL;
		gbc_panelCabeceraPerfil.gridx = 1;
		gbc_panelCabeceraPerfil.gridy = 1;
		add(panelCabeceraPerfil, gbc_panelCabeceraPerfil);
		
		panelFotoAlbum = new PanelFotoAlbum(home, usuario);
		GridBagConstraints gbc_panelFotoAlbum = new GridBagConstraints();
		gbc_panelFotoAlbum.fill = GridBagConstraints.VERTICAL;
		gbc_panelFotoAlbum.gridx = 1;
		gbc_panelFotoAlbum.gridy = 3;
		add(panelFotoAlbum, gbc_panelFotoAlbum);
	}
	
	/**
	 * Añade una unica foto
	 * @param publicacion
	 */
	protected void addFoto(Publicacion publicacion, boolean borrable) {
		panelFotoAlbum.addFoto(publicacion, borrable);
	}
	
	/**
	 * Añade un unico album
	 * @param publicacion
	 */
	protected void addAlbum(Publicacion publicacion, boolean borrable) {
		panelFotoAlbum.addAlbum(publicacion, borrable);
	}
	
	/**
	 * Actualiza la cabecera y el panel de fotos y albumes
	 */
	
	public void actualizarCabecera(Usuario usuario) {
		panelCabeceraPerfil.actualizarCampos(usuario);
		this.updateUI();
	}

	
		

}
