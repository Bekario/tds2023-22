package ventanas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controlador.Controlador;


public class PanelHashTag extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create the panel.
	 */
	public PanelHashTag (String s) {
		crearPanel(s);
	}
	
	private void crearPanel(String s) {
		this.setSize(450, 64);
		crearPanelHashTag(s);
		
		
		// Comprobamos si debemos mostrar que el usuario es seguido o no
		
	}
	
	private void crearPanelHashTag(String hashtag) {	
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 0, 15, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel(hashtag);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(String.valueOf(Controlador.getInstancia().getNumPublicacionesHashTags(hashtag)));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 0;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		

	}
	
	/**
	 * Establece si se debe ver el boton o no
	 * @param visibilidad
	 */

}
