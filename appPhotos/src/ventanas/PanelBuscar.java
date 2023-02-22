package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.ImageIcon;

public class PanelBuscar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public PanelBuscar() {
		this.setSize(450, 600);
		crearPanel();
		
	}
	private void crearPanel() {
		crearPanelBuscar();
	}
	private void crearPanelBuscar() {
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{5, 0, 40, 5, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 40, 10, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		JLabel lblNewLabel = new JLabel("Búsqueda");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(PanelBuscar.class.getResource("/imagenes/buscar.png")));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 2;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		PanelListaUsuarios panelListaUsuarios = new PanelListaUsuarios((Home) null);
		GridBagConstraints gbc_panelListaUsuarios = new GridBagConstraints();
		gbc_panelListaUsuarios.gridwidth = 2;
		gbc_panelListaUsuarios.insets = new Insets(0, 0, 0, 5);
		gbc_panelListaUsuarios.fill = GridBagConstraints.BOTH;
		gbc_panelListaUsuarios.gridx = 1;
		gbc_panelListaUsuarios.gridy = 4;
		add(panelListaUsuarios, gbc_panelListaUsuarios);

	}

}
