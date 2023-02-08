package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.Usuario;

import javax.swing.JButton;

public class PanelUsuario extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnSeguir;
	private Usuario usuario;
	
	/**
	 * Create the panel.
	 */
	public PanelUsuario(Usuario usuario) {
		this.usuario = usuario;
		crearPanel();
	}
	
	private void crearPanel() {
		this.setSize(450, 64);
		crearPanelEImagen();
	}
	
	private void crearPanelEImagen() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 60, 15, 160, 15, 0, 15, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblFoto = new JLabel("");
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.insets = new Insets(0, 0, 0, 5);
		gbc_lblFoto.gridx = 1;
		gbc_lblFoto.gridy = 0;
		add(lblFoto, gbc_lblFoto);
		
		ImageIcon imagen = new ImageIcon(usuario.getPerfil());
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		
		lblFoto.setIcon(icono);
		
		JLabel lblNewLabel = new JLabel(usuario.getUsuario());
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		btnSeguir = new JButton("Seguir");
		GridBagConstraints gbc_btnSeguir = new GridBagConstraints();
		gbc_btnSeguir.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSeguir.insets = new Insets(0, 0, 0, 5);
		gbc_btnSeguir.gridx = 5;
		gbc_btnSeguir.gridy = 0;
		add(btnSeguir, gbc_btnSeguir);
	}
	
	/**
	 * Establece si se debe ver el boton o no
	 * @param visibilidad
	 */
	private void setVisibilidadBotonSeguir(boolean visibilidad) {
		btnSeguir.setVisible(visibilidad);
	}
}
