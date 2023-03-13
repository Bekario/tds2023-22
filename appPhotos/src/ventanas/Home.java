package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;

import modelo.Usuario;

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
import java.time.LocalDate;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home {
	private JFrame frame;
	private JScrollPane scrollPane;
	private PanelBuscar panelBusqueda;
	private PanelInicio panelInicio;
	private PanelPerfil panelPerfil;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatMonokaiProIJTheme.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frame.setVisible(true);
					window.frame.getRootPane().requestFocus(false);
					//RepoUsuarios.getUnicaInstancia().addUsuario(new Usuario("adrian", "", "", "", LocalDate.now(), "C:\\Users\\anton\\Desktop\\ParticipantImageServlet2.jpeg", " "));
					//RepoUsuarios.getUnicaInstancia().addUsuario(new Usuario("antonio", "", "", "", LocalDate.now(), "C:\\Users\\anton\\Desktop\\a.jpg", " "));
					
//					window.frame.setFocusableWindowState(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		
		addManejadorClickBoton(btnSearch, (JPanel) panelBusqueda);
		addManejadorClickBoton(btnPrincipal, (JPanel) panelInicio);
		addManejadorClickBoton(btnPerfil, (JPanel) panelPerfil);
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
		panelBusqueda = new PanelBuscar();
		panelInicio = new PanelInicio(this);  //MALENIA OJO CON EL CONSTRUCTOR
		panelPerfil = new PanelPerfil(this, new Usuario("adrian", "LOMO", "adrianpar@um.es", "Antonio Feo", LocalDate.now(), "C:\\Users\\adri2\\Desktop\\sexiest man in earth.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce a dolor sit amet diam lacinia accumsan. Etiam pulvinar nisl malesuada."));
	}
	
	protected void addManejadorClickBoton(JButton boton, JPanel panel) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setViewportView(panel);
			}
		});
	}
	protected void CambiarScrollPane(JPanel panel) {
				scrollPane.setViewportView(panel);

		}
	protected void addManejadorEdit(JButton boton, 	JPanel panel) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setViewportView(new PanelEditar(new Usuario("adrian", "LOMO", "adrianpar@um.es", "Antonio Feo", LocalDate.now(), "C:\\Users\\adri2\\Desktop\\sexiest man in earth.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce a dolor sit amet diam lacinia accumsan. Etiam pulvinar nisl malesuada."), panel));
			}
		});
	}
	
}
