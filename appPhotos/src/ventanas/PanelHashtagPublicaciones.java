package ventanas;

import javax.swing.JPanel;

import modelo.Publicacion;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;

public class PanelHashtagPublicaciones extends JPanel{

	private static final long serialVersionUID = 1L;
	private PanelCuadriculaPublicaciones panelCuadriculaPublicaciones;
	/**
	 * Create the panel.
	 */
	public PanelHashtagPublicaciones(Home padre, String hashtag) {
		crearPanel(padre, hashtag);
	}
	
	private void crearPanel(Home padre, String hash) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 0, 15, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNotificaciones = new JLabel("#"+hash);
		lblNotificaciones.setFont(new Font("Segoe UI", Font.BOLD, 19));
		GridBagConstraints gbc_lblNotificaciones = new GridBagConstraints();
		gbc_lblNotificaciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotificaciones.gridx = 1;
		gbc_lblNotificaciones.gridy = 1;
		add(lblNotificaciones, gbc_lblNotificaciones);
		
		panelCuadriculaPublicaciones = new PanelCuadriculaPublicaciones(padre);
		GridBagConstraints gbc_panelCuadriculaPublicaciones = new GridBagConstraints();
		gbc_panelCuadriculaPublicaciones.insets = new Insets(0, 0, 0, 5);
		gbc_panelCuadriculaPublicaciones.fill = GridBagConstraints.BOTH;
		gbc_panelCuadriculaPublicaciones.gridx = 1;
		gbc_panelCuadriculaPublicaciones.gridy = 3;
		add(panelCuadriculaPublicaciones, gbc_panelCuadriculaPublicaciones);

	}
	
	public void addPublicacion(Publicacion publicacion) {
		panelCuadriculaPublicaciones.addPublicacionConManejador(publicacion);

	}


}
