package ventanas;

import javax.swing.JPanel;

import modelo.Album;
import modelo.Foto;
import modelo.Publicacion;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PanelPublicacionScroll extends JPanel {

	private static final long serialVersionUID = 1L;
	protected JLabel lblFoto;
	
	public PanelPublicacionScroll(Publicacion publicacion) {
		this.setSize(380, 500);
	
		crearPanel(publicacion);
	}
	
	private void crearPanel(Publicacion publicacion) {
		establecerLayout();
		establecerFoto(publicacion);
	}
	
	private void establecerLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{380, 0};
		gridBagLayout.rowHeights = new int[]{380, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	
	private void establecerFoto(Publicacion publicacion) {
		lblFoto = new JLabel("");
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.insets = new Insets(0, 0, 5, 0);
		gbc_lblFoto.gridx = 0;
		gbc_lblFoto.gridy = 0;
		add(lblFoto, gbc_lblFoto);
		ImageIcon imagen = null;
		//Comprobamos si es un album o una foto
		if(publicacion.getClass().getName().equals("modelo.Foto")) {
			imagen = new ImageIcon(((Foto) publicacion).getPath());			
		} else {
			imagen = new ImageIcon(((Album) publicacion).getPortada().getPath());
		}
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(380, 380, Image.SCALE_SMOOTH));
		lblFoto.setIcon(icono);
	}
}
