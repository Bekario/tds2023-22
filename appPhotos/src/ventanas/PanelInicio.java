package ventanas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JPanel;

import modelo.Foto;
import modelo.Publicacion;
import modelo.Usuario;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PanelInicio extends JPanel {
	private Home ventana;
	private int y;
	
	/**
	 * Create the panel.
	 */
	public PanelInicio(Home ventana) {
		this.ventana = ventana;
		this.setSize(450, 490);
		y=0;
		
		crearPanel();
		addPublicacion(new Foto("caMPO", "HOLA", LocalDate.now(), null, new Usuario("adri", "1234", "a@g", "Adrian Pardo", LocalDate.now(), "C:\\Users\\anton\\Desktop\\ParticipantImageServlet2.jpeg",  ""), "C:\\Users\\anton\\Desktop\\a.jpg"));
		
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
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(icono);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		remove(lblNewLabel);
	}
	
	public void addPublicacion(Publicacion publicacion) {
		PanelPublicacion panelPublicacion = new PanelPublicacion(publicacion);
		GridBagConstraints gbc_panelPublicacion = new GridBagConstraints();
		gbc_panelPublicacion.insets = new Insets(0, 0, 5, 0);
		gbc_panelPublicacion.fill = GridBagConstraints.BOTH;
		gbc_panelPublicacion.gridx = 0;
		gbc_panelPublicacion.gridy = y;
		y+=5;
		add(panelPublicacion, gbc_panelPublicacion);
	}
	
	

}
