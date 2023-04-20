package ventanas;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import controlador.Controlador;
import modelo.Publicacion;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.GridBagConstraints;

import java.awt.Insets;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

abstract class PanelCuadriculaPublicaciones extends JPanel {

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
		Manejadores.addManejadorClickToFoto(lblPublicacion, publi);
		x++;
		
		if(x > 2) {
			x=0;
			y++;
		}
		return lblPublicacion;
	}
	
	/**
	 * Añade una publicacion que puede ser interactuada con click derecho para eliminarse
	 * @param publi
	 */
	protected JLabel addPublicacionBorrable(Publicacion publi) {
		PopupMenu popup = new PopupMenu();
		popup.add("Eliminar");
		return (JLabel) addManejadorBorrarPublicacion(addPublicacion(publi), publi);
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
					//MALENIA
					//FALTA ACTUALIZAR EL PANEL
				}
				
				
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
		
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
	}
	
	

}
