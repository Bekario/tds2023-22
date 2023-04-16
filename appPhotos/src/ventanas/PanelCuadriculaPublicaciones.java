package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import modelo.Album;
import modelo.Foto;
import modelo.Publicacion;

import java.awt.Insets;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PanelCuadriculaPublicaciones extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int RESOLUCION_PUBLICACION = 125;
	private int x;
	private int y;
	private HashMap<Publicacion, JLabel> lista;

	/**
	 * Create the panel.
	 */
	public PanelCuadriculaPublicaciones() {
		lista = new HashMap<Publicacion, JLabel>();
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
	protected void addPublicacion(Publicacion publicacion) {
		ImageIcon imagen;
		//Si es una foto, mostramos el path
		if (publicacion.getClass().getName() == "modelo.Foto") {
			imagen = new ImageIcon(((Foto) publicacion).getPath());			
		} else { //Si es un album, mostramos el path de la primera foto
			imagen = new ImageIcon(((Album) publicacion).getPortada().getPath());	
		}
		
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(RESOLUCION_PUBLICACION, RESOLUCION_PUBLICACION, Image.SCALE_SMOOTH));
		
		JLabel lblPublicacion = new JLabel("");
		lblPublicacion.setIcon(icono);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = x;
		gbc_lblNewLabel.gridy = y;
		add(lblPublicacion, gbc_lblNewLabel);
		
		lista.put(publicacion, lblPublicacion);
		
		x++;
		
		if(x > 2) {
			x=0;
			y++;
		}
	}
	
	public HashMap<Publicacion, JLabel> getLista() {
		return lista;
	}
	
	protected void borrarTodasPublicaciones() {
		//Quitamos todas las publicaciones
		lista.clear();
		
		//Reiniciamos las posiciones
		x = 0;
		y = 0;
		
		//Lista vacia
		lista = new HashMap<Publicacion, JLabel>();
	}
	
	

}
