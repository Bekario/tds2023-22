package ventanas;

import modelo.Foto;
import modelo.Publicacion;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PanelCuadriculaFotos extends PanelCuadriculaPublicaciones {

	private static final long serialVersionUID = 1L;
	private static final String NO_FOTOS = "no";
	private static final String SI_FOTOS = "si";
	private String estado;
	private JLabel lblImagen;

	/**
	 * Create the panel.
	 */
	public PanelCuadriculaFotos() {
		super();
		estado = NO_FOTOS;
		crearFotoDefault();
	}
	
	/**
	 * Crea una imagen por defecto, indicando que no hay subida ninguna publicacion
	 */
	private void crearFotoDefault() {
		lblImagen = new JLabel("");
		lblImagen.setIcon(new ImageIcon(PanelCuadriculaPublicaciones.class.getResource("/imagenes/ninguna_foto.png")));
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
	public void addFotos(List<Foto> fotos) {
		//Comprobamos si ha de quitar la imagen por defecto
		if (estado == NO_FOTOS && fotos.size() > 0) {
			estado = SI_FOTOS;

			remove(lblImagen);
		}
		
		for (Publicacion p: fotos) {
			addPublicacion(p);
		}
	}
	
	@Override
	protected void addPublicacion(Publicacion publicacion) {
		if (estado == NO_FOTOS) {
			estado = SI_FOTOS;
			remove(lblImagen);
		}
		super.addPublicacion(publicacion);
	}

	public void limpiar() {
		if(estado == SI_FOTOS) {
			estado = NO_FOTOS;
			crearFotoDefault();			
		}
		super.borrarTodasPublicaciones();
		
	}


}
