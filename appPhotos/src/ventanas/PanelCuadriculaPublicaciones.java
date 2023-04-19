package ventanas;

import javax.swing.JPanel;

import controlador.Controlador;
import modelo.Publicacion;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PanelCuadriculaPublicaciones extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int RESOLUCION_PUBLICACION = 125;
	private int x;
	private int y;

	/**
	 * Create the panel.
	 */
	public PanelCuadriculaPublicaciones() {
		y = 0;
		x = 0;
		crearPanel();
	}
	
	private void crearPanel() {
		crearLayout();
	}
	
	private void crearLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{125, 125, 125, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	
	/**
	 * Añade una unica publicacion al panel
	 * @param publicacion
	 */
	protected void addPublicacion(Publicacion publi) {
		ImageIcon imagen = new ImageIcon(Controlador.getInstancia().obtenerPortadaPublicacion(publi));			
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(RESOLUCION_PUBLICACION, RESOLUCION_PUBLICACION, Image.SCALE_SMOOTH));
		
		JLabel lblPublicacion = new JLabel("");
		lblPublicacion.setIcon(icono);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = x;
		gbc_lblNewLabel.gridy = y;
		add(lblPublicacion, gbc_lblNewLabel);
		
		x++;
		
		if(x > 2) {
			x=0;
			y++;
		}
	}
	
	protected void borrarTodasPublicaciones() {	
		//Reiniciamos las posiciones
		x = 0;
		y = 0;
	}
	
	

}
