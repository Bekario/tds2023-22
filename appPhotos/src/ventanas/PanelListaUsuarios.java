package ventanas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import controlador.Controlador;
import modelo.Foto;
import modelo.Publicacion;
import modelo.Usuario;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PanelListaUsuarios extends JPanel {
	private int y;
	private ArrayList<PanelUsuario> paneles;
	
	/**
	 * Create the panel.
	 */
	public PanelListaUsuarios() {
		this.setSize(450, 490);
		paneles = new ArrayList<PanelUsuario>();
		y=0;
		
		crearPanel();
	}
	
	private void crearPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	
	public void addUsuario(Usuario usuario) {
		PanelUsuario panelUsuario = new PanelUsuario(usuario);
		GridBagConstraints gbc_panelUsuario = new GridBagConstraints();
		gbc_panelUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_panelUsuario.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario.gridx = 0;
		gbc_panelUsuario.gridy = y;
		y+=1;
		add(panelUsuario, gbc_panelUsuario);
		paneles.add(panelUsuario);
	}
	
	public void addListaUsuario(List<Usuario> usuarios) {
		for (Usuario usuario : usuarios) {
			addUsuario(usuario);
		}
	}
	
	public void quitarUsuarios() {
		removeAll();
		y=0;
	}
	
	/**
	 * Elimina el boton de seguir de aquellos usuario ya seguidos
	 * @param usuario
	 * @param seguidos
	 */
	public void comprobarSeguidos(String usuario, List<Usuario> seguidos) {
		ArrayList<String> seguidosNombre = new ArrayList<String>();
		seguidosNombre.add(usuario);
		seguidos.stream()
				.map(p -> p.getUsuario())
				.forEach(u -> seguidosNombre.add(u));
		//MALENIA STREAM
		for (PanelUsuario p : paneles) {
			for (String u : seguidosNombre) {
				if(p.getUsuario().equals(u)) {
					p.setVisibilidadBotonSeguir(false);
				}
			}
		}
	}
	

}
