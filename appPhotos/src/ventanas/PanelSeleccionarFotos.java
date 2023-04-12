package ventanas;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import modelo.Foto;
import modelo.Publicacion;

public class PanelSeleccionarFotos extends PanelCuadriculaFotos {

	private static final long serialVersionUID = 1L;
	private HashMap<Publicacion, JLabel> seleccionados;
	
	/**
	 * Create the panel.
	 */
	public PanelSeleccionarFotos(List<Foto> fotos) {
		super();
		addFotos(fotos);
		seleccionados = new HashMap<Publicacion, JLabel>();
		cargarManejadores();
	}
	
	private void cargarManejadores() {
		getLista().keySet().stream()
							.forEach(p -> addManejadorSeleccionar(getLista().get(p), p));

	}
	
	private void addManejadorSeleccionar(JLabel foto, Publicacion publicacion) {
		foto.setBorder(new LineBorder(new Color(60, 63, 65), 2, true));
		foto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(seleccionados.containsKey(publicacion)) {
					seleccionados.remove(publicacion);
					foto.setBorder(new LineBorder(new Color(60, 63, 65), 2, true));
				} else {
					seleccionados.put(publicacion, foto);
					foto.setBorder(new LineBorder(new Color(218, 200, 41), 2, true));
				}
			}
		});

	}
	
	public HashMap<Publicacion, JLabel> getSeleccionados() {
		return seleccionados;
	}

}