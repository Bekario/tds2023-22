package ventanas;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import controlador.Controlador;
import modelo.Foto;
import modelo.Publicacion;

public class PanelSeleccionarFotos extends PanelCuadriculaFotos {

	private static final long serialVersionUID = 1L;

	
	/**
	 * Create the panel.
	 */
	public PanelSeleccionarFotos(List<Foto> fotos) {
		super();
		addFotos(fotos);
	}
	
	@Override
	public void addFotos(List<Foto> fotos) {
		
		super.addFotos(fotos);
	}
	
	private void cargarManejadores(List<Foto> fotos) {
		fotos.stream()
			 .forEach(p -> addManejadorSeleccionar(getLista().get(p), p));

	}
	
	private void addManejadorSeleccionar(JLabel foto, Publicacion publicacion) {
		foto.setBorder(new LineBorder(new Color(60, 63, 65), 2, true));
		foto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<Publicacion> seleccionados = Controlador.getInstancia().getSeleccionados();
				if(seleccionados.contains(publicacion)) {
					// Si la publicación esta contenida en seleccionados la quitamos
					Controlador.getInstancia().removeSeleccionado(publicacion);			
					foto.setBorder(new LineBorder(new Color(60, 63, 65), 2, true));		
				} else if (publicacion.equals(Controlador.getInstancia().getPortadaSeleccionada())) {
					// Si la publicación es la portada la quitamos
					Controlador.getInstancia().setPortadaSeleccionada(null);
					foto.setBorder(new LineBorder(new Color(60, 63, 65), 2, true));	
				} else {
					// Si no ha sido escogida
					if(Controlador.getInstancia().getPortadaSeleccionada().equals(null)) {
						// Si la portada esta libre, la imagen es la portada
						Controlador.getInstancia().setPortadaSeleccionada(publicacion);		
						foto.setBorder(new LineBorder(new Color(249, 100, 100), 2, true));

					} else {
						// Si la portada está cogida, se mete en la lista de seleccionados
						Controlador.getInstancia().addSeleccionado(publicacion);
						
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
}
