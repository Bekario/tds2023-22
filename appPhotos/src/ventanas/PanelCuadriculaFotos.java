package ventanas;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import modelo.Foto;
import modelo.Publicacion;

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
	 * @param borrable indica si la publicacion puede ser borrable o no
	 */
	public List<JLabel> addFotos(List<Foto> fotos, boolean borrable) {
		List<JLabel> labels = new ArrayList<JLabel>();
		//Comprobamos si ha de quitar la imagen por defecto
		if (estado == NO_FOTOS && fotos.size() > 0) {
			estado = SI_FOTOS;

			remove(lblImagen);
		}
		if (borrable) {
			fotos.stream()
			.forEachOrdered(f -> labels.add(addPublicacionBorrable(f)));			
		} else {
			fotos.stream()
			.forEachOrdered(f -> labels.add(addPublicacion(f)));		
		}

		return labels;
	}
	
	/**
	 * Añade una unica foto
	 * @param borrable indica si la publicacion puede ser borrable o no
	 * @param publi
	 */
	public void addFoto(Publicacion publi, boolean borrable) {
		//Comprobamos si ha que quitar la imagen por defecto
		if (estado == NO_FOTOS) {
			estado = SI_FOTOS;
			remove(lblImagen);
		}
		
		if(borrable) {
			addPublicacionBorrable(publi);	
		} else {
			addPublicacion(publi);	
		}
	}

	public void limpiar() {
		if(estado == SI_FOTOS) {
			estado = NO_FOTOS;
			crearFotoDefault();			
		}
		super.borrarTodasPublicaciones();
		
	}


}
