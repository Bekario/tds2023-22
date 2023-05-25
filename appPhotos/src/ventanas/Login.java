package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controlador.Controlador;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;


public class Login {

	private JFrame frame;
	private JPanel panel;
	
	private PanelLogin panelLogin;
	private PanelRegister panelRegister;
	private PanelRegister2 panelRegister2;

	/**
	 * Create the application.
	 */
	public Login() {
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
		frame.setTitle("Bienvenido a appPhotos");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/imagenes/icono.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
	}
	
	/**
	 * Iniciamos los 3 paneles
	 */
	private void prepararPaneles() {
		panelLogin = new PanelLogin(this);
		panelRegister = new PanelRegister(this);
		panelRegister2= new PanelRegister2(this);
	}
	
	/**
	 * Cambia el panel mostrado en la ventana por otro
	 * @param panel que se va a establecer
	 */
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
	
	/**
	 * Cierra esta ventana y cambia a la ventana Home
	 */
	public void cambiarHome() {
		Home home = new Home();
		home.mostrarVentana(frame);
		frame.dispose();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	/**
	 * Intenta registrar un usuario
	 * @return booleano indicando si ha sido posible registrar el usuario
	 */
	public boolean registrarUsuario() {
		return Controlador.getInstancia().registroUsuario(panelRegister.getUsuario(), panelRegister.getPass(), panelRegister.getEmail(), panelRegister.getNombre(), panelRegister.getFecha(), panelRegister2.getPerfil(), panelRegister2.getDescripcion());
	}
	
	/**
	 * Cambia el panel actual por el de login
	 */
	public void setPanelLogin() {
		setPanel(panelLogin);
		panelLogin.mostrar();
	}
	
	/**
	 * Cambia el panel actual por el de register
	 */
	public void setPanelRegister() {
		// Creamos un panel limpio
		panelRegister = new PanelRegister(this);
		setPanel(panelRegister);
		// Lo mostramos
		panelRegister.mostrar();
	}
	
	/**
	 * Cambia el panel actual por el de register con los datos rellenados anteriormente
	 */
	public void setPanelRegisterRellenado() {
		setPanel(panelRegister);
		panelRegister.rellenarCampos(panelRegister.getUsuario(), panelRegister.getPass(), panelRegister.getEmail(), panelRegister.getNombre(), panelRegister.getFecha());
		panelRegister.mostrar();
	}
	
	/**
	 * Cambia el panel actual por el de register2
	 */
	public void setPanelRegister2() {
		panelRegister2 = new PanelRegister2(this);
		setPanel(panelRegister2);
		panelRegister2.mostrar();
	}
	
	public PanelRegister getPanelRegister() {
		return panelRegister;
	}
}
