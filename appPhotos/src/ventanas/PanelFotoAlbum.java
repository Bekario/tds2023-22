package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Font;

import modelo.Usuario;

import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

public class PanelFotoAlbum extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String FOTO = "foto";
	private static final String ALBUM = "album";
	
	private Usuario usuario;
	
	private PanelCuadriculaFotos panelFotos;
	private PanelCuadriculaAlbums panelAlbum;
	private String estadoPanel;
	private GridBagConstraints gbc_panelFotos;
	private JButton btnAlbum;
	private JButton btnFotos;
	private JButton btnAlbum_1;
	private JPanel panel_2;
	
	private Home padre;


	/**
	 * Create the panel.
	 */
	public PanelFotoAlbum(Home home, Usuario usuario) {
		padre = home;
		this.setSize(450, 600);
		this.usuario = usuario;
		crearPanel();
		cargarPublicaciones();
	}
	
	private void crearPanel() {
		crearLayout();
		crearPanelesPublicaciones();
		crearBotones();
	}
	
	private void crearLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 5, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 15, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	
	private void crearBotones() {
		btnFotos = new JButton("Fotos");
		GridBagConstraints gbc_btnFotos = new GridBagConstraints();
		gbc_btnFotos.anchor = GridBagConstraints.EAST;
		gbc_btnFotos.insets = new Insets(0, 0, 5, 5);
		gbc_btnFotos.gridx = 1;
		gbc_btnFotos.gridy = 0;
		add(btnFotos, gbc_btnFotos);
		btnFotos.setBackground(new Color(66, 61, 67));
		btnFotos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFotos.setBorder(null);
		
		addManejadorBotonPaneles(btnFotos, panelFotos, FOTO);
		
		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.WEST;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.VERTICAL;
		gbc_panel_2.gridx = 3;
		gbc_panel_2.gridy = 0;
		add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnAlbum = new JButton("Album");
		panel_2.add(btnAlbum);
		btnAlbum.setBackground(new Color(42, 45, 46));
		btnAlbum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAlbum.setBorder(null);
		addManejadorBotonPaneles(btnAlbum, panelAlbum, ALBUM);
		
		btnAlbum_1 = new JButton("");
		
		panel_2.add(btnAlbum_1);
		ImageIcon imagen = new ImageIcon(PanelRegister2.class.getResource("/imagenes/add.png"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
		btnAlbum_1.setIcon(icono);
		btnAlbum_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAlbum_1.setBorder(null);
		btnAlbum_1.setBackground(new Color(42, 45, 46));
		
		addManejadorBotonAñadirAlbum(btnAlbum_1);
	}
	
	private void addManejadorBotonAñadirAlbum(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				padre.setPanel(new PanelCrearAlbum(padre));
			}
		});

	}
	
	/**
	 * Permite cambiar de un panel de publicaciones a otro
	 * @param btn boton que lleva a cabo la accion
	 * @param panel panel que se desea asociar a ese boton
	 * @param tipo tipo del panel
	 */
	private void addManejadorBotonPaneles(JButton btn, JPanel panel, String tipo) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipo != estadoPanel) {
					if (tipo == FOTO) {
						estadoPanel = FOTO;
						remove(panelAlbum);
						add(panelFotos, gbc_panelFotos);
						btnAlbum.setBackground(new Color(42, 45, 46));
						
						panelFotos.updateUI();
					} else {
						estadoPanel = ALBUM;
						remove(panelFotos);
						add(panelAlbum, gbc_panelFotos);
						btnFotos.setBackground(new Color(42, 45, 46));
						
						panelAlbum.updateUI();
					}
					btn.setBackground(new Color(66, 61, 67));
				}
			}
		});
	}
	
	private void crearPanelesPublicaciones() {
		//Creamos el panel de fotos, que es el que se muestra primero por defecto
		panelFotos = new PanelCuadriculaFotos();
		gbc_panelFotos = new GridBagConstraints();
		gbc_panelFotos.anchor = GridBagConstraints.EAST;
		gbc_panelFotos.gridwidth = 3;
		gbc_panelFotos.insets = new Insets(0, 0, 0, 5);
		gbc_panelFotos.fill = GridBagConstraints.VERTICAL;
		gbc_panelFotos.gridx = 1;
		gbc_panelFotos.gridy = 2;
		add(panelFotos, gbc_panelFotos);
		//Por defecto comienza con el panel de fotos
		estadoPanel = FOTO;
		
		//Creamos el panel de albums
		panelAlbum = new PanelCuadriculaAlbums();
		GridBagConstraints gbc_panelAlbum = new GridBagConstraints();
		gbc_panelAlbum.anchor = GridBagConstraints.EAST;
		gbc_panelAlbum.gridwidth = 3;
		gbc_panelAlbum.insets = new Insets(0, 0, 0, 5);
		gbc_panelAlbum.fill = GridBagConstraints.VERTICAL;
		gbc_panelAlbum.gridx = 1;
		gbc_panelAlbum.gridy = 2;
		add(panelAlbum, gbc_panelAlbum);
	}
	
	private void cargarPublicaciones() {
		panelAlbum.addAlbums(usuario.getAlbums());
		panelFotos.addFotos(usuario.getFotos());
	}
	
	private void limpiarPanelesPublicaciones() {
		panelAlbum.limpiar();
		panelFotos.limpiar();
	}
	
	public void actualizarPanel(){
		limpiarPanelesPublicaciones();
		cargarPublicaciones();
	}
	

	

}
