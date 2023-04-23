package ventanas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import modelo.Usuario;

public class PanelListaUsuarios extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private int y;
	
	private List<JPanel> paneles;
	private Home home;
	
	/**
	 * Create the panel.
	 */
	public PanelListaUsuarios(Home home) {
		this.home = home;
		this.setSize(450, 490);
		paneles = new ArrayList<JPanel>();
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
	
	public void addUsuario(Usuario usuario) {
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
	public void addUsuario(String hashtag) {
		PanelHashTag panelUsuario = new PanelHashTag(hashtag);
		GridBagConstraints gbc_panelUsuario = new GridBagConstraints();
		gbc_panelUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_panelUsuario.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario.gridx = 0;
		gbc_panelUsuario.gridy = y;
		add(panelUsuario, gbc_panelUsuario);
		paneles.add(panelUsuario);
		y++;
		
//		addManejadorClickUsuario(panelUsuario, hashtag);
	}
	
	public void addListaUsuario(List<Usuario> usuarios) {
		for (Usuario usuario : usuarios) {
			addUsuario(usuario);
		}
	}

	public void addListaHashTag(List<String> hashtags) {
		for (String hashtag: hashtags) {
			addUsuario(hashtag);
		}
	}
	
	public void quitarUsuarios() {
		removeAll();
		y=0;
	}
	
	private void addManejadorClickUsuario(PanelUsuario u, Usuario usuario) {
		u.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				home.setPanel(new PanelPerfil(home, usuario));
			}
		});

	}

}
