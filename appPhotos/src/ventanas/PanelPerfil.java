package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import modelo.Usuario;

import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;


public class PanelPerfil extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Home home;
	

	/**
	 * Create the panel.
	 */
	public PanelPerfil(Home home, Usuario usuario) {
		this.usuario=usuario;
		this.home = home;
		crearPanel();
		generarCabecera();
		
	}
	public Home getHome() {
		return home;
	}
	private void crearPanel() {
		this.setSize(450, 600);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 0, 15, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 15, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
	}
	private void generarCabecera() {
		PanelCabeceraPerfil panelCabeceraPerfil = new PanelCabeceraPerfil(home, usuario);
		GridBagConstraints gbc_panelCabeceraPerfil = new GridBagConstraints();
		gbc_panelCabeceraPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_panelCabeceraPerfil.fill = GridBagConstraints.BOTH;
		gbc_panelCabeceraPerfil.gridx = 1;
		gbc_panelCabeceraPerfil.gridy = 1;
		add(panelCabeceraPerfil, gbc_panelCabeceraPerfil);
		
		PanelFotoAlbum panelFotoAlbum = new PanelFotoAlbum();
		GridBagConstraints gbc_panelFotoAlbum = new GridBagConstraints();
		gbc_panelFotoAlbum.gridwidth = 2;
		gbc_panelFotoAlbum.fill = GridBagConstraints.BOTH;
		gbc_panelFotoAlbum.gridx = 1;
		gbc_panelFotoAlbum.gridy = 3;
		add(panelFotoAlbum, gbc_panelFotoAlbum);
	}
		

}
