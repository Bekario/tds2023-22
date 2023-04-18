package ventanas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class PanelListaUsuarios extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private int y;
	
	private ArrayList<PanelUsuario> paneles;
	private Home home;
	
	/**
	 * Create the panel.
	 */
	public PanelListaUsuarios(Home home) {
		this.home = home;
		this.setSize(450, 490);
		paneles = new ArrayList<PanelUsuario>();
		y=0;
		
		crearPanel();
	}
	
	private void crearPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	
	public void addUsuario(String usuario) {
		PanelUsuario panelUsuario = new PanelUsuario(usuario);
		GridBagConstraints gbc_panelUsuario = new GridBagConstraints();
		gbc_panelUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_panelUsuario.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario.gridx = 0;
		gbc_panelUsuario.gridy = y;
		add(panelUsuario, gbc_panelUsuario);
		paneles.add(panelUsuario);
		y++;
		
		addManejadorClickUsuario(panelUsuario, usuario);
	}
	
	public void addListaUsuario(List<String> usuarios) {
		for (String usuario : usuarios) {
			addUsuario(usuario);
		}
	}

	
	public void quitarUsuarios() {
		removeAll();
		y=0;
	}
	
	private void addManejadorClickUsuario(PanelUsuario u, String usuario) {
		u.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				home.setPanel(new PanelPerfil(home, usuario));
			}
		});

	}
	
	/**
	 * Elimina el boton de seguir de aquellos usuario ya seguidos
	 * @param usuario
	 * @param seguidos
	 */
	public void comprobarSeguidos(String usuario, List<String> seguidos) {
		//Introducimos el usuario para que no aparezca el boton para seguirse a uno mismo
		seguidos.add(usuario);
		//MALENIA STREAM
		for (PanelUsuario p : paneles) {
			for (String u : seguidos) {
				if(p.getUsuario().equals(u)) {
					p.setVisibilidadBotonSeguir(false);
				}
			}
		}
	}
	

}
