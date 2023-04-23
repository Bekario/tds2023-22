package ventanas;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import modelo.Notificacion;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;

public class PanelNotificacion extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;

	/**
	 * Create the panel.
	 */
	public PanelNotificacion(Notificacion notificacion) {
		crearPanel(notificacion);
		this.setBorder(new LineBorder(new Color(45, 42, 46), 2, true));	
		Manejadores.addManejadorResaltar(this);
	}
	
	private void crearPanel(Notificacion n) {
		this.setSize(400, 50);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 15, 5, 15, 5, 0, 0};
		gridBagLayout.rowHeights = new int[]{15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblPerfil = new JLabel("");
		GridBagConstraints gbc_lblPerfil = new GridBagConstraints();
		gbc_lblPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_lblPerfil.gridx = 1;
		gbc_lblPerfil.gridy = 0;
		add(lblPerfil, gbc_lblPerfil);
		ImageIcon imagen = new ImageIcon(FotoPersonalizada.redondearFoto(n.getPublicacion().getUsuario().getPerfil()));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
		lblPerfil.setIcon(icono);
		
		JLabel lblTexto = new JLabel("¡"+n.getPublicacion().getUsuario().getUsuario()+" ha subido una publicacion!");
		lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTexto = new GridBagConstraints();
		gbc_lblTexto.insets = new Insets(0, 0, 0, 5);
		gbc_lblTexto.gridx = 3;
		gbc_lblTexto.gridy = 0;
		add(lblTexto, gbc_lblTexto);
		
		btnEliminar = new JButton("");
		btnEliminar.setContentAreaFilled(false);
		btnEliminar.setIcon(new ImageIcon(PanelNotificacion.class.getResource("/imagenes/eliminar.png")));
		GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
		gbc_btnEliminar.anchor = GridBagConstraints.EAST;
		gbc_btnEliminar.gridx = 5;
		gbc_btnEliminar.gridy = 0;
		add(btnEliminar, gbc_btnEliminar);
	}
	
	public JButton getBtnEliminar() {
		return btnEliminar;
	}
	
	

}
