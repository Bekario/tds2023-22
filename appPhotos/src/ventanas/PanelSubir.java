package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;

import controlador.Controlador;
import modelo.Usuario;

import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class PanelSubir extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Create the panel.
	 */
	public PanelSubir() {
		this.setSize(450, 600);
		crearPanel();
		
	}
	private void crearPanel() {
		crearPanelSubir();
	}
	
	private void crearPanelSubir() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{5, 0, 5, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		JLabel lblTexto = new JLabel("Subir foto");
		lblTexto.setFont(new Font("Segoe UI", Font.BOLD, 19));
		GridBagConstraints gbc_lblTexto = new GridBagConstraints();
		gbc_lblTexto.insets = new Insets(0, 0, 5, 5);
		gbc_lblTexto.anchor = GridBagConstraints.SOUTH;
		gbc_lblTexto.gridx = 1;
		gbc_lblTexto.gridy = 1;
		add(lblTexto, gbc_lblTexto);
		
		
	}
	

	

	

}
