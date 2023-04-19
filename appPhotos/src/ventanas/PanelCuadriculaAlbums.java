package ventanas;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import modelo.Album;

public class PanelCuadriculaAlbums extends PanelCuadriculaPublicaciones {

	private static final long serialVersionUID = 1L;
	private static final String NO_ALBUM = "no";
	private static final String SI_ALBUM = "si";
	private String estado;
	private JLabel lblImagen;

	/**
	 * Create the panel.
	 */
	public PanelCuadriculaAlbums() {
		super();
		estado = NO_ALBUM;
		crearAlbumDefault();
	}
	
	/**
	 * Crea una imagen por defecto, indicando que no hay subida ninguna publicacion
	 */
	private void crearAlbumDefault() {
		lblImagen = new JLabel("");
		lblImagen.setIcon(new ImageIcon(PanelCuadriculaPublicaciones.class.getResource("/imagenes/ningun_album.png")));
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.gridwidth = 3;
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 0;
		gbc_lblImagen.gridy = 0;
		add(lblImagen, gbc_lblImagen);
	}
	
	/**
	 * Añade una lista de publicaciones al panel
	 * @param publicaciones publicaciones que se van a añadir
	 */
	public void addAlbums(List<Album> albums) {
		//Comprobamos si ha que quitar la imagen por defecto
		if (estado == NO_ALBUM && albums.size() > 0) {
			estado = SI_ALBUM;
			remove(lblImagen);
		}
		
		for (Album a: albums) {
			addPublicacion(a);
		}
	}
	
	public void limpiar() {
		if (estado == SI_ALBUM) {
			estado = NO_ALBUM;
			crearAlbumDefault();			
		}

		super.borrarTodasPublicaciones();
	}

	

}
