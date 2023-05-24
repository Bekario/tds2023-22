package ventanas;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import modelo.IDescuento;

import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;

public class PanelDescuento extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnDescuento;
	/**
	 * Create the panel.
	 */
	public PanelDescuento(IDescuento descuento) {
		this.setSize(262, 32);
		crearPanel(descuento);
		this.setBorder(new LineBorder(new Color(45, 42, 46), 2, true));	
		Manejadores.addManejadorResaltar(this);
		
		
	}
	
	private void crearPanel(IDescuento d) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{5, 200, 10, 0, 0};
		gridBagLayout.rowHeights = new int[]{15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblDescuento = new JLabel(d.getNombre());
		lblDescuento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDescuento = new GridBagConstraints();
		gbc_lblDescuento.anchor = GridBagConstraints.WEST;
		gbc_lblDescuento.insets = new Insets(0, 0, 0, 5);
		gbc_lblDescuento.gridx = 1;
		gbc_lblDescuento.gridy = 0;
		add(lblDescuento, gbc_lblDescuento);
		
		btnDescuento = new JButton("");
		btnDescuento.setContentAreaFilled(false);
		btnDescuento.setIcon(new ImageIcon(PanelDescuento.class.getResource("/imagenes/descuento.png")));
		GridBagConstraints gbc_btnDescuento = new GridBagConstraints();
		gbc_btnDescuento.gridx = 3;
		gbc_btnDescuento.gridy = 0;
		add(btnDescuento, gbc_btnDescuento);
		
		Manejadores.addManejadorAnimacionBoton(this, btnDescuento, "/imagenes/descuento.gif", "/imagenes/descuento.png");
	
	}
	
	public JButton getBtnDescuento() {
		return btnDescuento;
	}

}
