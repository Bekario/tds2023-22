package ventanas;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import controlador.Controlador;
import modelo.Album;
import modelo.Publicacion;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.GridBagConstraints;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PanelCuadriculaPublicaciones extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int RESOLUCION_PUBLICACION = 125;
	private int x;
	private int y;
	private List<JLabel> publicaciones;
	private Home padre;

	/**
	 * Create the panel.
	 */
	public PanelCuadriculaPublicaciones(Home padre) {
		this.padre = padre;
		y = 0;
		x = 0;
		publicaciones = new ArrayList<JLabel>();
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
	 * @param manejador true si se desea añadir un manejador a la publicacion
	 */
	protected JLabel addPublicacion(Publicacion publi) {
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
		publicaciones.add(lblPublicacion);
		return lblPublicacion;
	}
	
	/**
	 * Añade una publicacion con manejador de click
	 * @param publi
	 */
	public JLabel addPublicacionConManejador(Publicacion publi) {
		JLabel label = addPublicacion(publi);
		Manejadores.addManejadorClickToFoto(label, publi);
		return label;
	}
	
	/**
	 * Añade una publicacion que puede ser interactuada con click derecho para eliminarse
	 * @param publi
	 */
	protected JLabel addPublicacionBorrable(Publicacion publi) {
		return (JLabel) addManejadorBorrarPublicacion(addPublicacionConManejador(publi), publi);
	}
	
	private Component addManejadorBorrarPublicacion(JLabel label, Publicacion p) {
		JPopupMenu popupMenu = new JPopupMenu();
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
					super.mouseClicked(e);
				}
			}
		});
		addPopup(label, popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Eliminar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int seleccion = JOptionPane.showConfirmDialog(null, "¿Estas seguro que quieres eliminar esta publicacion?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (seleccion == 0) {
					Controlador.getInstancia().borrarPublicacion(p);
					//Actualizamos el panel
					padre.recargarPanelPerfil();
					padre.recargarPanelInicio();
					padre.setPanelPerfil();
					updateUI();
				}
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
		if(p.getClass().getName().equals("modelo.Album")) {
			JMenuItem mntmNewMenuItem2 = new JMenuItem("Modificar");
			mntmNewMenuItem2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Establecemos el panel seleccionar fotos con la foto anterior
					padre.setPanel(new PanelEditarAlbum(padre, (Album) p));
					//Actualizamos el panel
					updateUI();	
				}
			});
			popupMenu.add(mntmNewMenuItem2);
		}
		
		return label;
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	
	protected void borrarTodasPublicaciones() {	
		//Reiniciamos las posiciones
		x = 0;
		y = 0;
		publicaciones.stream().parallel()
							  .forEach(p -> remove(p));
		publicaciones = new ArrayList<JLabel>();
	}
	
	

}
