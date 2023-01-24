package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Cursor;
import javax.swing.UIManager;
import javax.swing.JPanel;


public class TarjetaCredito {

	private JFrame frmPagoConTarjeta;
	private JTextField txtNumTarjeta;
	private JTextField txtTitular;
	private JTextField txtCVV;
	private JTextField txtFechaDeCaducidad;
	private static JPanelBackground panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatMonokaiProIJTheme.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TarjetaCredito window = new TarjetaCredito();
					window.frmPagoConTarjeta.setVisible(true);
					window.frmPagoConTarjeta.getRootPane().requestFocus(false);
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
	public TarjetaCredito() {
		initialize();
	}
	
	/**
	 * Muestra la ventana
	 */
	public void mostrarVentana(JFrame padre) {
		frmPagoConTarjeta.setLocationRelativeTo(padre);
		frmPagoConTarjeta.setVisible(true);
		frmPagoConTarjeta.getRootPane().requestFocus(false);
	}
	
	/**
	 * Inicializa el frame y el layout
	 */
	private void initialize() {
		frmPagoConTarjeta = new JFrame();
		frmPagoConTarjeta.setTitle("Pago con tarjeta de crédito");
		frmPagoConTarjeta.setIconImage(Toolkit.getDefaultToolkit().getImage(TarjetaCredito.class.getResource("/imagenes/camara-de-fotos.png")));
		frmPagoConTarjeta.setBounds(100, 100, 450, 600);
		frmPagoConTarjeta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 0, 0, 50, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 0, 0, 50, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmPagoConTarjeta.getContentPane().setLayout(gridBagLayout);
		
		establecerBotones();
		establecerFotoTitulo();
		establecerDatosTarjeta();
	}
	
	/**
	 * Establece la foto y el titulo de la aplicación
	 */
	private void establecerFotoTitulo() {
		JLabel nombreApp = new JLabel("appPhotos");
		nombreApp.setForeground(new Color(233, 233, 233));
		nombreApp.setIcon(null);
		nombreApp.setFont(new Font("Serif", Font.BOLD, 40));
		GridBagConstraints gbc_nombreApp = new GridBagConstraints();
		gbc_nombreApp.gridwidth = 2;
		gbc_nombreApp.insets = new Insets(0, 0, 5, 5);
		gbc_nombreApp.gridx = 1;
		gbc_nombreApp.gridy = 1;
		frmPagoConTarjeta.getContentPane().add(nombreApp, gbc_nombreApp);
		
		
	}
	
	/**
	 * Establece los campos para introducir los datos de la tarjeta
	 */
	private void establecerDatosTarjeta() {
		panel = new JPanelBackground();
		panel.setBackground("/imagenes/tarjeta-de-credito-azul.png");
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 3;
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		frmPagoConTarjeta.getContentPane().add(panel, gbc_panel);
		
		txtNumTarjeta = new JTextField();
		txtNumTarjeta.setToolTipText("");
		txtNumTarjeta.setText("Numero de tarjeta");
		txtNumTarjeta.setColumns(10);
		GridBagConstraints gbc_txtNumTarjeta = new GridBagConstraints();
		gbc_txtNumTarjeta.gridwidth = 2;
		gbc_txtNumTarjeta.insets = new Insets(0, 0, 5, 5);
		gbc_txtNumTarjeta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNumTarjeta.gridx = 1;
		gbc_txtNumTarjeta.gridy = 6;
		frmPagoConTarjeta.getContentPane().add(txtNumTarjeta, gbc_txtNumTarjeta);
		
		txtTitular = new JTextField();
		txtTitular.setToolTipText("");
		txtTitular.setText("Titular");
		txtTitular.setColumns(10);
		GridBagConstraints gbc_txtTitular = new GridBagConstraints();
		gbc_txtTitular.gridwidth = 2;
		gbc_txtTitular.insets = new Insets(0, 0, 5, 5);
		gbc_txtTitular.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitular.gridx = 1;
		gbc_txtTitular.gridy = 7;
		frmPagoConTarjeta.getContentPane().add(txtTitular, gbc_txtTitular);
		
		txtCVV = new JTextField();
		txtCVV.setToolTipText("");
		txtCVV.setText("CVV / CVC");
		txtCVV.setColumns(10);
		GridBagConstraints gbc_txtCVV = new GridBagConstraints();
		gbc_txtCVV.insets = new Insets(0, 0, 5, 5);
		gbc_txtCVV.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCVV.gridx = 1;
		gbc_txtCVV.gridy = 8;
		frmPagoConTarjeta.getContentPane().add(txtCVV, gbc_txtCVV);
		
		txtFechaDeCaducidad = new JTextField();
		txtFechaDeCaducidad.setToolTipText("");
		txtFechaDeCaducidad.setText("Fecha de caducidad");
		txtFechaDeCaducidad.setColumns(10);
		GridBagConstraints gbc_txtFechaDeCaducidad = new GridBagConstraints();
		gbc_txtFechaDeCaducidad.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaDeCaducidad.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFechaDeCaducidad.gridx = 2;
		gbc_txtFechaDeCaducidad.gridy = 8;
		frmPagoConTarjeta.getContentPane().add(txtFechaDeCaducidad, gbc_txtFechaDeCaducidad);
		
		addManejadorTextField(txtFechaDeCaducidad, "Fecha de caducidad");
		addManejadorTextField(txtCVV, "CVV / CVC");
		addManejadorTextField(txtNumTarjeta, "Numero de tarjeta");
		addManejadorTextField(txtTitular, "Titular");
			
	}
	
	/**
	 * Gestiona las animaciones del campo usuario
	 * @param txtUser Campo de usuario
	 */
	private void addManejadorTextField(JTextField textField, String texto) {
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField.getText().equals(texto)) {
					textField.setText("");
				}
				else {
					textField.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField.getText().equals(""))
					textField.setText(texto);
			}
		});
	}
	
	
	/**
	 * Establece el boton para procesar el pago
	 */
	private void establecerBotones() {
		
	}
	
	/**
	 * Gestiona los cambios de color al pasar el raton encima de un boton
	 * @param boton Boton que se desea que aplique el efecto
	 */
	private void addManejadorBotonColor(JButton boton) {
	}
	
	private boolean checkFields() {
		boolean estado = true;
		
		String info = "";
		
		//Comprobamos si es un correo basico
		if(txtUser.getText().equals("Usuario")) {
			estado = false;
			info = "¡El usuario no es correcto!";
		}
		
		if(new String(textPasswd.getPassword()).equals("Contraseña") /*|| !match.matches()*/) {
			estado = false;
			info = "¡La contraseña es incorrecta!";
		}
		
		
		if(!estado) {
			JOptionPane.showMessageDialog(frmPagoConTarjeta, info, "Rellene correctamente los campos", 0);
		}
		
		return estado;
	}
	


}
