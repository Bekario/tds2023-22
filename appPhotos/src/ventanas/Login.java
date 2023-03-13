package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;

import java.awt.Toolkit;
import java.time.LocalDate;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;


public class Login {

	private JFrame frame;
	private JPanel panel;
	
	private PanelLogin panelLogin;
	private PanelRegister panelRegister;
	private PanelRegister2 panelRegister2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatMonokaiProIJTheme.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
					window.frame.getRootPane().requestFocus(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		panelLogin = null;
		initialize();
		prepararPaneles();
		setPanel(panelLogin);
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
		frame.setTitle("Login");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/imagenes/camara-de-fotos.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
	}
	
	private void prepararPaneles() {
		panelLogin = new PanelLogin(this);
		panelRegister = new PanelRegister(this);
		panelRegister2= new PanelRegister2(this);
	}
	
	private void setPanel(JPanel panel) {
		if (this.panel != null) {
			frame.getContentPane().remove(this.panel);			
		}
		this.panel = panel;
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		frame.getContentPane().add(panel, gbc);
		frame.getContentPane().repaint();
	}
	
	public void cambiarHome() {
		Home home = new Home();
		home.mostrarVentana(frame);
		frame.dispose();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void setPanelLogin() {
		setPanel(panelLogin);
		panelLogin.mostrar();
	}
	
	public void setPanelRegister() {
		// Creamos un panel limpio
		panelRegister = new PanelRegister(this);
		setPanel(panelRegister);
		// Lo mostramos
		panelRegister.mostrar();
	}
	
	public void setPanelRegister(String usuario, String contraseña, String email, String nombreCompleto, LocalDate fechaNacimiento) {
		setPanel(panelRegister);
		panelRegister.rellenarCampos(usuario, contraseña, email, nombreCompleto, fechaNacimiento);
		panelRegister.mostrar();
	}
	public void setPanelRegister2() {
		setPanel(panelRegister2);
		panelRegister2.mostrar();
	}
	
	public PanelRegister getPanelRegister() {
		return panelRegister;
	}
}
