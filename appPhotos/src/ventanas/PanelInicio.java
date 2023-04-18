package ventanas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;

import javax.swing.JPanel;

import controlador.Controlador;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PanelInicio extends JPanel {
	private static final String NO_PUBLI = "no";
	private static final String SI_PUBLI = "si";
	
	private static final long serialVersionUID = 1L;
	private Home padre;
	private int y;
	private String estado;
	private JLabel lblNoPublis;
	
	/**
	 * Create the panel.
	 * @wbp.parser.constructor
	 */
	public PanelInicio(Home home) {
		this.padre = home;
		this.setSize(450, 490);
		estado = NO_PUBLI;
		y=0;
		
		crearPanel();	
		cargarFotos();
	}
	
	/**
	 * Crea un panel de inicio utilizando las fotos (panel top 10 publicaciones)
	 * @param home
	 * @param fotos
	 */
	public PanelInicio(Home home, List<Integer> fotos) {
		this.padre = home;
		this.setSize(450, 490);
		estado = NO_PUBLI;
		y=0;
		
		crearPanel();
		cargarFotos(fotos);
	}
	private void crearPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		ImageIcon imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/noPublicaciones.png"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH));
		
		lblNoPublis = new JLabel("");
		lblNoPublis.setIcon(icono);
		GridBagConstraints gbc_lblNoPublis = new GridBagConstraints();
		gbc_lblNoPublis.fill = GridBagConstraints.VERTICAL;
		gbc_lblNoPublis.insets = new Insets(0, 0, 5, 0);
		gbc_lblNoPublis.gridx = 0;
		gbc_lblNoPublis.gridy = 0;
		add(lblNoPublis, gbc_lblNoPublis);
		
	}
	
	/**
	 * Carga la feed del usuario con las fotos subidas
	 */
	private void cargarFotos() {
		Controlador.getInstancia().getPublicacionesSubidasSeguidores().stream()
															.forEachOrdered(p -> addPublicacion(p));
	}
	private void cargarFotos(List<Integer> fotos) {
		fotos.stream()
					.forEachOrdered(p -> addPublicacion(p));
	}
	
	public void addPublicacion(int publicacion) {
		if(estado == NO_PUBLI) {
			estado = SI_PUBLI;
			remove(lblNoPublis);
		}
		
		PanelPublicacion panelPublicacion = new PanelPublicacion(padre, publicacion);
		GridBagConstraints gbc_panelPublicacion = new GridBagConstraints();
		gbc_panelPublicacion.insets = new Insets(0, 0, 5, 0);
		gbc_panelPublicacion.fill = GridBagConstraints.BOTH;
		gbc_panelPublicacion.gridx = 0;
		gbc_panelPublicacion.gridy = y;
		y+=5;
		add(panelPublicacion, gbc_panelPublicacion);
	}
	
	

}
