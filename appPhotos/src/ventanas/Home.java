package ventanas;


import javax.swing.JFrame;

import controlador.Controlador;
import modelo.Publicacion;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Home {
	private JFrame frame;
	private JScrollPane scrollPane;
	private PanelBuscar panelBusqueda;
	private PanelInicio panelInicio;
	private PanelPerfil panelPerfil;
	private PanelSubir panelSubir;

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}
	
	/**
	 * Muestra la ventana
	 */
	public void mostrarVentana(JFrame padre) {
		frame.setLocationRelativeTo(padre);
		frame.setVisible(true);
		frame.getRootPane().requestFocus(false);
	}
	
	/**
	 * Inicializa el frame y el layout
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/imagenes/camara-de-fotos.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 50, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		prepararTodosPaneles();
		establecerBarraSuperior();
		establecerBarraInferior();
		establecerPanelMedio();
	}
	
	
	
	/**
	 * Establece la barra inferior y labels
	 */
	private void establecerBarraInferior() {
		JPanel barraInferior = new JPanel();
		barraInferior.setBackground(new Color(192, 192, 192));
		GridBagConstraints gbc_barraInferior = new GridBagConstraints();
		gbc_barraInferior.fill = GridBagConstraints.BOTH;
		gbc_barraInferior.gridx = 0;
		gbc_barraInferior.gridy = 2;
		frame.getContentPane().add(barraInferior, gbc_barraInferior);
		GridBagLayout gbl_barraInferior = new GridBagLayout();
		gbl_barraInferior.columnWidths = new int[]{10, 0, 0, 0, 0, 10, 0};
		gbl_barraInferior.rowHeights = new int[]{0, 0};
		gbl_barraInferior.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_barraInferior.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		barraInferior.setLayout(gbl_barraInferior);
		
		JButton btnPrincipal = new JButton("");
		btnPrincipal.setBackground(new Color(192, 192, 192));
		btnPrincipal.setIcon(new ImageIcon(Home.class.getResource("/imagenes/casa.png")));
		GridBagConstraints gbc_btnPrincipal = new GridBagConstraints();
		gbc_btnPrincipal.insets = new Insets(0, 0, 0, 5);
		gbc_btnPrincipal.gridx = 1;
		gbc_btnPrincipal.gridy = 0;
		barraInferior.add(btnPrincipal, gbc_btnPrincipal);
		btnPrincipal.setBorder(null);
		
		JButton btnSearch = new JButton("");
		btnSearch.setBackground(new Color(192, 192, 192));
		btnSearch.setIcon(new ImageIcon(Home.class.getResource("/imagenes/lupa.png")));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.VERTICAL;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 2;
		gbc_btnSearch.gridy = 0;
		barraInferior.add(btnSearch, gbc_btnSearch);
		btnSearch.setBorder(null);
		
		JButton btnSubirFoto = new JButton("");
		btnSubirFoto.setBackground(new Color(192, 192, 192));
		btnSubirFoto.setIcon(new ImageIcon(Home.class.getResource("/imagenes/add.png")));
		GridBagConstraints gbc_btnSubirFoto = new GridBagConstraints();
		gbc_btnSubirFoto.fill = GridBagConstraints.VERTICAL;
		gbc_btnSubirFoto.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubirFoto.gridx = 3;
		gbc_btnSubirFoto.gridy = 0;
		barraInferior.add(btnSubirFoto, gbc_btnSubirFoto);
		btnSubirFoto.setBorder(null);
		
		JButton btnPerfil = new JButton("");
		btnPerfil.setBackground(new Color(192, 192, 192));
		btnPerfil.setIcon(new ImageIcon(Home.class.getResource("/imagenes/usuario.png")));
		GridBagConstraints gbc_btnPerfil = new GridBagConstraints();
		gbc_btnPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_btnPerfil.fill = GridBagConstraints.VERTICAL;
		gbc_btnPerfil.gridx = 4;
		gbc_btnPerfil.gridy = 0;
		barraInferior.add(btnPerfil, gbc_btnPerfil);
		btnPerfil.setBorder(null);
		
		addManejadorBotonBusqueda(btnSearch);
		addManejadorBotonInicio(btnPrincipal);
		addManejadorBotonPerfil(btnPerfil);
		addManejadorBotonSubir(btnSubirFoto);
		
		JButton btnAjustes = new JButton("");
		btnAjustes.setBackground(new Color(192, 192, 192));
		btnAjustes.setIcon(new ImageIcon(Home.class.getResource("/imagenes/elipsis.png")));
		
		JPopupMenu popupMenu = new JPopupMenu();
		btnAjustes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
				super.mouseClicked(e);
			}
		});
		addPopup(btnAjustes, popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Premium");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPanelPremium();
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Exportar Excel");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Controlador.getInstancia().getUsuarioActual().getIsPremium()) {
					Controlador.getInstancia().generarEXCEL();
					JOptionPane.showMessageDialog(null, "¡Documento excel generado con exito!", "Excel generado", 1);
				}else {	
					JOptionPane.showMessageDialog(null, "Esta es una función premium", "Debes ser premium", 2);
				}
			}
		});
		popupMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Exportar PDF");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Controlador.getInstancia().getUsuarioActual().getIsPremium()) {
					Controlador.getInstancia().generarPDF();
					JOptionPane.showMessageDialog(null, "¡Documento pdf generado con exito!", "PDF generado", 1);
				}else {
					JOptionPane.showMessageDialog(null, "Esta es una función premium", "Debes ser premium", 2);
				}
			}
		});

		popupMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Top Me Gusta");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Controlador.getInstancia().getUsuarioActual().getIsPremium()) {
					setPanelTop();
				}else {
					JOptionPane.showMessageDialog(null, "Esta es una función premium", "Debes ser premium", 2);
				}
			}
		});
		popupMenu.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Cerrar sesión");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarLogin();
			}
		});
		popupMenu.add(mntmNewMenuItem_4);
		GridBagConstraints gbc_btnAjustes = new GridBagConstraints();
		gbc_btnAjustes.gridx = 5;
		gbc_btnAjustes.gridy = 0;
		barraInferior.add(btnAjustes, gbc_btnAjustes);
		btnAjustes.setBorder(null);
		

	}
	

	private void establecerBarraSuperior() {
		JPanel barraSuperior = new JPanel();
		barraSuperior.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_barraSuperior = new GridBagConstraints();
		gbc_barraSuperior.insets = new Insets(0, 0, 5, 0);
		gbc_barraSuperior.fill = GridBagConstraints.BOTH;
		gbc_barraSuperior.gridx = 0;
		gbc_barraSuperior.gridy = 0;
		frame.getContentPane().add(barraSuperior, gbc_barraSuperior);
		GridBagLayout gbl_barraSuperior = new GridBagLayout();
		gbl_barraSuperior.columnWidths = new int[]{10, 0, 0, 0, 10, 0};
		gbl_barraSuperior.rowHeights = new int[]{0, 0};
		gbl_barraSuperior.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_barraSuperior.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		barraSuperior.setLayout(gbl_barraSuperior);
		
		JLabel Titulo = new JLabel("appPhotos");
		Titulo.setForeground(new Color(0, 0, 0));
		Titulo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		GridBagConstraints gbc_Titulo = new GridBagConstraints();
		gbc_Titulo.anchor = GridBagConstraints.WEST;
		gbc_Titulo.insets = new Insets(0, 0, 0, 5);
		gbc_Titulo.gridx = 1;
		gbc_Titulo.gridy = 0;
		barraSuperior.add(Titulo, gbc_Titulo);
	}
	
	private void establecerPanelMedio() {
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		scrollPane.setViewportView(panelInicio);
	}
	
	private void prepararTodosPaneles() {
		panelBusqueda = new PanelBuscar(this);
		panelInicio = new PanelInicio(this);
		panelPerfil = new PanelPerfil(this, Controlador.getInstancia().getUsuarioActual());
		panelSubir = new PanelSubir(this);
	}
	
	private void addManejadorBotonInicio(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPanelPublicaciones();
			}
		});
	}
	
	private void addManejadorBotonBusqueda(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPanelBusqueda();
			}
		});
	}
	
	private void addManejadorBotonPerfil(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPanelPerfil();
			}
		});
	}
	
	private void addManejadorBotonSubir(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPanelSubir();
			}
		});
	}
	
	private void cambiarScrollPane(JPanel panel) {
		scrollPane.setViewportView(panel);

	}
	
	public void setPanelEdit() {
		//El panel editar debe contener los datos por defecto al accederse
		cambiarScrollPane(new PanelEditar(Controlador.getInstancia().getUsuarioActual(), panelPerfil));
	}
	
	
	public void setPanelPublicaciones() {
		cambiarScrollPane(panelInicio);
	}
	
	public void setPanelBusqueda() {
		panelBusqueda.limpiarPanel();
		cambiarScrollPane(panelBusqueda);
	}
	public void setPanelSubir() {
		panelSubir.reiniciarPanel();
		cambiarScrollPane(panelSubir);
	}
	
	public void setPanelPerfil() {
		// Cada vez que cambiemos al panel perfil hay que actualizar la cabecera
		panelPerfil.actualizarCabecera();
		cambiarScrollPane(panelPerfil);
	}
	
	protected void setPanelTop() {		
		setPanel(new PanelInicio(this, Controlador.getInstancia().getPublicacionesTop()));
		
	}
	public void setPanelPremium() {
		cambiarScrollPane(new PanelPremium(this));
	}
	
	public void setPanel(JPanel panel) {
		cambiarScrollPane(panel);
	}
	
	private void cambiarLogin() {
		Controlador.getInstancia().cerrarSesion();
		Login login = new Login();
		login.mostrarVentana(frame);
		frame.dispose();
	}
	
	protected void addManejadorEdit(JButton boton, 	JPanel panel) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setViewportView(new PanelEditar(Controlador.getInstancia().getUsuarioActual(), panel));
			}
		});
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	/**
	 * Actualiza el panelPerfil con la nueva publicacion subida
	 */
	public void subirPublicacion(Publicacion publicacion) {
		panelPerfil.addPublicacion(publicacion);
	}
	
	
}
