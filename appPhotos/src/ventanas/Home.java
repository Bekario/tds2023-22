package ventanas;


import javax.swing.JFrame;

import controlador.Controlador;
import modelo.Publicacion;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.EventObject;
import java.util.regex.Pattern;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import pulsador.IEncendidoListener;
import pulsador.Luz;

public class Home implements IEncendidoListener{
	private JFrame frame;
	private JScrollPane scrollPane;
	private PanelBuscar panelBusqueda;
	private PanelInicio panelInicio;
	private PanelPerfil panelPerfil;
	private PanelSubir panelSubir;
	private PanelNotificaciones panelNotificaciones;
	
	private JButton btnCampana;

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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/imagenes/icono.png")));
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
		barraInferior.setBackground(Colores.NARANJA);
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
		btnPrincipal.setContentAreaFilled(false);
		btnPrincipal.setIcon(new ImageIcon(Home.class.getResource("/imagenes/casa.png")));
		GridBagConstraints gbc_btnPrincipal = new GridBagConstraints();
		gbc_btnPrincipal.insets = new Insets(0, 0, 0, 5);
		gbc_btnPrincipal.gridx = 1;
		gbc_btnPrincipal.gridy = 0;
		barraInferior.add(btnPrincipal, gbc_btnPrincipal);
		btnPrincipal.setBorder(null);
		
		Manejadores.addManejadorAnimacionBoton(frame, btnPrincipal, "/imagenes/casa.gif", "/imagenes/casa.png");
		
		JButton btnSearch = new JButton("");
		btnSearch.setContentAreaFilled(false);
		btnSearch.setIcon(new ImageIcon(Home.class.getResource("/imagenes/lupa.png")));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.VERTICAL;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 2;
		gbc_btnSearch.gridy = 0;
		barraInferior.add(btnSearch, gbc_btnSearch);
		btnSearch.setBorder(null);
		
		Manejadores.addManejadorAnimacionBoton(frame, btnSearch, "/imagenes/lupa.gif", "/imagenes/lupa.png");
		
		JButton btnSubirFoto = new JButton("");
		btnSubirFoto.setContentAreaFilled(false);
		btnSubirFoto.setIcon(new ImageIcon(Home.class.getResource("/imagenes/add.png")));
		GridBagConstraints gbc_btnSubirFoto = new GridBagConstraints();
		gbc_btnSubirFoto.fill = GridBagConstraints.VERTICAL;
		gbc_btnSubirFoto.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubirFoto.gridx = 3;
		gbc_btnSubirFoto.gridy = 0;
		barraInferior.add(btnSubirFoto, gbc_btnSubirFoto);
		btnSubirFoto.setBorder(null);
		
		Manejadores.addManejadorAnimacionBoton(frame, btnSubirFoto, "/imagenes/add.gif", "/imagenes/add.png");
		
		JButton btnPerfil = new JButton("");
		btnPerfil.setContentAreaFilled(false);
		btnPerfil.setIcon(new ImageIcon(Home.class.getResource("/imagenes/usuario.png")));
		GridBagConstraints gbc_btnPerfil = new GridBagConstraints();
		gbc_btnPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_btnPerfil.fill = GridBagConstraints.VERTICAL;
		gbc_btnPerfil.gridx = 4;
		gbc_btnPerfil.gridy = 0;
		barraInferior.add(btnPerfil, gbc_btnPerfil);
		btnPerfil.setBorder(null);
		
		Manejadores.addManejadorAnimacionBoton(frame, btnPerfil, "/imagenes/usuario.gif", "/imagenes/usuario.png");
		
		addManejadorBotonBusqueda(btnSearch);
		addManejadorBotonInicio(btnPrincipal);
		addManejadorBotonPerfil(btnPerfil);
		addManejadorBotonSubir(btnSubirFoto);
		
		JButton btnAjustes = new JButton("");
		btnAjustes.setContentAreaFilled(false);
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
				if(Controlador.getInstancia().comprobarPremium()) {
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
				if(Controlador.getInstancia().comprobarPremium()) {
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
				if(Controlador.getInstancia().comprobarPremium()) {
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
		barraSuperior.setBackground(new Color(45,42,46));
		GridBagConstraints gbc_barraSuperior = new GridBagConstraints();
		gbc_barraSuperior.insets = new Insets(0, 0, 5, 0);
		gbc_barraSuperior.fill = GridBagConstraints.BOTH;
		gbc_barraSuperior.gridx = 0;
		gbc_barraSuperior.gridy = 0;
		frame.getContentPane().add(barraSuperior, gbc_barraSuperior);
		GridBagLayout gbl_barraSuperior = new GridBagLayout();
		gbl_barraSuperior.columnWidths = new int[]{10, 0, 0, 5, 0, 5, 0, 10, 0};
		gbl_barraSuperior.rowHeights = new int[]{0, 0};
		gbl_barraSuperior.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_barraSuperior.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		barraSuperior.setLayout(gbl_barraSuperior);
		
		JLabel Titulo = new JLabel("appPhotos");
		Titulo.setForeground(Colores.NARANJA);
		Titulo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		GridBagConstraints gbc_Titulo = new GridBagConstraints();
		gbc_Titulo.anchor = GridBagConstraints.WEST;
		gbc_Titulo.insets = new Insets(0, 0, 0, 5);
		gbc_Titulo.gridx = 1;
		gbc_Titulo.gridy = 0;
		barraSuperior.add(Titulo, gbc_Titulo);
		
		
		
		Luz luz = new Luz();
		luz.setColor(Colores.NARANJA_CLARO);
		//Añadimos Home como listener
		luz.addEncendidoListener(this);
		GridBagConstraints gbc_luz = new GridBagConstraints();
		gbc_luz.insets = new Insets(0, 0, 0, 5);
		gbc_luz.gridx = 2;
		gbc_luz.gridy = 0;
		barraSuperior.add(luz, gbc_luz);
		
		btnCampana = new JButton("");
		btnCampana.setContentAreaFilled(false);
		if(Controlador.getInstancia().obtenerUsuarioActual().getNotificaciones().size() == 0) {
			btnCampana.setIcon(new ImageIcon(Home.class.getResource("/imagenes/campana.png")));
		} else {
			btnCampana.setIcon(new ImageIcon(Home.class.getResource("/imagenes/campana.gif")));
		}
		GridBagConstraints gbc_btnCampana = new GridBagConstraints();
		gbc_btnCampana.insets = new Insets(0, 0, 0, 5);
		gbc_btnCampana.gridx = 4;
		gbc_btnCampana.gridy = 0;
		barraSuperior.add(btnCampana, gbc_btnCampana);
		addManejadorBotonCampana(btnCampana);
		
		
		JButton btnRecargar = new JButton("");
		btnRecargar.setContentAreaFilled(false);
		btnRecargar.setIcon(new ImageIcon(Home.class.getResource("/imagenes/recargar.png")));
		GridBagConstraints gbc_btnRecargar = new GridBagConstraints();
		gbc_btnRecargar.insets = new Insets(0, 0, 0, 5);
		gbc_btnRecargar.gridx = 6;
		gbc_btnRecargar.gridy = 0;
		barraSuperior.add(btnRecargar, gbc_btnRecargar);
		
		addManejadorBotonRecargar(btnRecargar);
		Manejadores.addManejadorAnimacionBoton(frame, btnRecargar, "/imagenes/recargar.gif", "/imagenes/recargar.png");
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
		panelPerfil = new PanelPerfil(this, Controlador.getInstancia().obtenerUsuarioActual());
		panelSubir = new PanelSubir(this);
		panelNotificaciones = new PanelNotificaciones(this, Controlador.getInstancia().obtenerUsuarioActual().getNotificaciones());
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
	private void addManejadorBotonCampana(JButton boton) {
		boton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setPanelNotificaciones();
			}
		});
	}
	
	private void addManejadorBotonRecargar(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recargarPanelInicio();
			}
		});

	}
	
	private void cambiarScrollPane(JPanel panel) {
		scrollPane.setViewportView(panel);

	}
	
	public void setPanelEdit() {
		//El panel editar debe contener los datos por defecto al accederse
		cambiarScrollPane(new PanelEditar(panelPerfil));
	}
	
	public void setPanelPublicaciones() {
		cambiarScrollPane(panelInicio);
	}
	
	public void recargarPanelInicio() {
		panelInicio = new PanelInicio(this);	
		setPanelPublicaciones();
	}
	public void recargarPanelPerfil() {
		panelPerfil = new PanelPerfil(this, Controlador.getInstancia().obtenerUsuarioActual());	
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
		panelPerfil.actualizarCabecera(Controlador.getInstancia().obtenerUsuarioActual());
		cambiarScrollPane(panelPerfil);
	}
	
	protected void setPanelTop() {
		JOptionPane pane = new JOptionPane();
		JDialog dialog = pane.createDialog("Publicaciones populares");
		dialog.setSize(440,510);
		JScrollPane s = new JScrollPane(new PanelInicio(this, Controlador.getInstancia().getPublicacionesTop()));
		s.getVerticalScrollBar().setUnitIncrement(16);
		dialog.setContentPane(s);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		
	}
	public void setPanelPremium() {
		cambiarScrollPane(new PanelPremium(this));
	}
	
	public void setPanelNotificaciones() {
		cambiarScrollPane(panelNotificaciones);
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
				scrollPane.setViewportView(new PanelEditar(panel));
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
	
	public void actualizarIconoNotificacion() {
		if(Controlador.getInstancia().obtenerUsuarioActual().getNotificaciones().size() == 0) {
			btnCampana.setIcon(new ImageIcon(Home.class.getResource("/imagenes/campana.png")));
		} else {
			btnCampana.setIcon(new ImageIcon(Home.class.getResource("/imagenes/campana.gif")));
		}
	}
	
	/**
	 * Actualiza el panelPerfil con la nueva foto subida
	 */
	public void subirFoto(Publicacion publicacion) {
		//Esta publicacion se puede borrar, ya que es subida por el usuario
		panelPerfil.addFoto(publicacion, true);
		Controlador.getInstancia().notificarSeguidores(publicacion);
	}
	
	/**
	 * Actualiza el panelPerfil con el nuevo album subido
	 */
	public void subirAlbum(Publicacion publicacion) {
		//Esta publicacion se puede borrar, ya que es subida por el usuario
		panelPerfil.addAlbum(publicacion, true);
		Controlador.getInstancia().notificarSeguidores(publicacion);
	}
	
	@Override
	public void enteradoCambioEncendido(EventObject arg0) {
		Pattern regexpXml = Pattern.compile(".+\\.xml", Pattern.CASE_INSENSITIVE);
		
		//Creamos el selector de archivos con su filtro
		JFileChooser selector = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("xml", "xml");
		selector.setFileFilter(filtro);
		selector.removeChoosableFileFilter(null);
		
		int resp = selector.showOpenDialog(selector);
		File fichero = selector.getSelectedFile();
		if (fichero != null) {
			//Comprobamos que la extension sea correcta
			if(!regexpXml.matcher(fichero.getName()).matches()) {
				JOptionPane.showMessageDialog(frame, "¡El fichero debe tener una extensión válida!", "Rellene correctamente los campos", 0);
			} else if(resp == JFileChooser.APPROVE_OPTION) { //En caso de ser valida, ejecutamos cargarfotos
				Controlador.getInstancia().cargarFotos(fichero.getAbsolutePath());
				recargarPanelInicio();
				recargarPanelPerfil();
			}
		}
		
	}
	
}
