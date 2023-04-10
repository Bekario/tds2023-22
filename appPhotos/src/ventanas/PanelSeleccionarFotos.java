package ventanas;

import java.util.HashMap;
import javax.swing.JLabel;

import modelo.Publicacion;

public class PanelSeleccionarFotos extends PanelCuadriculaFotos {

	private static final long serialVersionUID = 1L;
	private HashMap<Publicacion, JLabel> seleccionados;
	
	/**
	 * Create the panel.
	 */
	public PanelSeleccionarFotos() {
		super();
		seleccionados = new HashMap<Publicacion, JLabel>();
		cargarManejadores();
	}
	
	private void cargarManejadores() {
		//Cargamos el manejador en todas las publicaciones

	}
	
	private void addManejadorSeleccionar(JLabel foto, Publicacion publicacion) {
		// TODO Auto-generated method stub

	}

}
