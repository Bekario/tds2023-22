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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.util.List;

public class PanelCuadriculaPublicaciones extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int RESOLUCION_PUBLICACION = 125;
	private int x;
	private int y;

	/**
	 * Create the panel.
	 */
	public PanelCuadriculaPublicaciones() {
		this.setSize(460, 600);
		y = 0;
		x = 0;
		crearPanel();
		
	}
	private void crearPanel() {
		crearLayout();
	}
	
	private void crearLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		ImageIcon imagen = new ImageIcon(Register2.class.getResource("/imagenes/ParticipantImageServlet.jpg"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(icono);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		lblNewLabel_1.setIcon(icono);
		
		JLabel lblNewLabel_2 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 2;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		lblNewLabel_2.setIcon(icono);
	}
	
	/**
	 * Añade una lista de publicaciones al panel
	 * @param publicaciones publicaciones que se van a añadir
	 */
	public void addAlbums(List<Album> publicaciones) {
		for (Publicacion p: publicaciones) {
			addPublicacion(p);
		}
	}
	
	/**
	 * Añade una lista de publicaciones al panel
	 * @param publicaciones publicaciones que se van a añadir
	 */
	public void addFotos(List<Foto> publicaciones) {
		for (Publicacion p: publicaciones) {
			addPublicacion(p);
		}
	}
	
	/**
	 * Añade una unica publicacion al panel
	 * @param publicacion
	 */
	private void addPublicacion(Publicacion publicacion) {
		ImageIcon imagen;
		//Si es una foto, mostramos el path
		if (publicacion.getClass().getName() == "modelo.Foto") {
			imagen = new ImageIcon(((Foto) publicacion).getPath());			
		} else { //Si es un album, mostramos el path de la primera foto
			imagen = new ImageIcon(((Album) publicacion).getFotos().get(0).getPath());	
		}
		
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

	

}
