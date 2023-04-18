package ventanas;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class PanelSeleccionarFotos extends PanelCuadriculaFotos {

	private static final long serialVersionUID = 1L;
	private HashMap<Integer, JLabel> seleccionados;
	private int portada;
	
	/**
	 * Create the panel.
	 */
	public PanelSeleccionarFotos(List<Integer> fotos) {
		super();
		addFotos(fotos);
		seleccionados = new HashMap<Integer, JLabel>();
		portada = -1;
		cargarManejadores();
	}
	
	private void cargarManejadores() {
		getLista().keySet().stream()
							.forEach(p -> addManejadorSeleccionar(getLista().get(p), p));

	}
	
	private void addManejadorSeleccionar(JLabel foto, int publicacion) {
		foto.setBorder(new LineBorder(new Color(60, 63, 65), 2, true));
		foto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(seleccionados.containsKey(publicacion)) {
					// Si la publicación esta contenida en seleccionados la quitamos
					seleccionados.remove(publicacion);				
					foto.setBorder(new LineBorder(new Color(60, 63, 65), 2, true));		
				} else if (publicacion == portada) {
					// Si la publicación es la portada la quitamos
					portada = -1;
					foto.setBorder(new LineBorder(new Color(60, 63, 65), 2, true));	
				} else {
					// Si no ha sido escogida
					if(portada == -1) {
						// Si la portada esta libre, la imagen es la portada
						portada = publicacion;		
						foto.setBorder(new LineBorder(new Color(249, 100, 100), 2, true));

					} else {
						// Si la portada está cogida, se mete en la lista de seleccionados
						seleccionados.put(publicacion, foto);
						foto.setBorder(new LineBorder(new Color(218, 200, 41), 2, true));
					}
				}
				/* DEBUG
				if(portada == null) {
					System.out.println("No portada");
				} else {
					System.out.println("portada = "+ portada.getTitulo());
				}
				System.out.println("seleccionados = "+ seleccionados.size());*/
			}
		});

	}
	
	public HashMap<Integer, JLabel> getSeleccionados() {
		return seleccionados;
	}
	
	public List<Integer> getListaSeleccionados() {
		return new ArrayList<Integer>(seleccionados.keySet());
	}
	
	public int getPortada() {
		return portada;
	}

}
