package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.JProgressBar;


public class TarjetaCredito {

	private JFrame frmPagoConTarjeta;
	private JTextField txtNumTarjeta;
	private JTextField txtTitular;
	private JTextField txtCVV;
	private JTextField txtFechaDeCaducidad;
	private JPanelBackground panel;
	private JLabel lblNumTarjeta;
	private JLabel lblTipoTarjeta;
	private JLabel lblFecha;
	private JLabel lblCVC;
	private JButton btnRegistrarse;
	
	private final String TITULAR = "Titular";
	private final String NUMEROTARJETA = "Numero de tarjeta";
	private final String CVV = "CVV / CVC";
	private final String FECHACADUCIDAD = "Fecha de caducidad";
	
	//Para determinar el tipo de tarjeta
	private final String MASTERCARD = "5";
	private final String VISA = "4";
	
	//Valores maximos que admiten los campos 
	private final int MAXNUMTARJETA = 16;
	private final int MAXFECHACADUCIDAD = 5;
	private final int MAXCVV = 3;
	private JProgressBar progressBar;

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
		gridBagLayout.columnWidths = new int[]{50, 128, 128, 50, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 60, 180, 0, 0, 0, 0, 30, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmPagoConTarjeta.getContentPane().setLayout(gridBagLayout);
		
		establecerBotones();
		establecerTitulo();
		establecerDatosTarjeta();
		establecerPanelTarjeta();
		establecerBarraDeCarga();
	}
	
	/**
	 * Establece la foto y el titulo de la aplicación
	 */
	private void establecerTitulo() {
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
	 * Establece el panel con la imagen de la tarjeta y las label que se pondran sobre la foto
	 */
	private void establecerPanelTarjeta() {
		//Panel con foto de tarjeta de fondo
		panel = new JPanelBackground();
		panel.setBackground(Login.class.getResource("/imagenes/tarjeta-de-credito-default.png"));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		frmPagoConTarjeta.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{35, 90, 90, 10, 68, 5, 0};
		gbl_panel.rowHeights = new int[]{79, 27, 26, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		//Numero de tarjeta
		lblNumTarjeta = new JLabel("");
		lblNumTarjeta.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNumTarjeta.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblNumTarjeta = new GridBagConstraints();
		gbc_lblNumTarjeta.gridwidth = 2;
		gbc_lblNumTarjeta.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNumTarjeta.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumTarjeta.gridx = 1;
		gbc_lblNumTarjeta.gridy = 1;
		panel.add(lblNumTarjeta, gbc_lblNumTarjeta);
		
		//Fecha de caducidad
		lblFecha = new JLabel("");
		lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblFecha.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.anchor = GridBagConstraints.SOUTH;
		gbc_lblFecha.gridwidth = 3;
		gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha.gridx = 1;
		gbc_lblFecha.gridy = 2;
		panel.add(lblFecha, gbc_lblFecha);
		
		//Tipo de tarjeta (estetico)
		lblTipoTarjeta = new JLabel("");
		GridBagConstraints gbc_lblTipoTarjeta = new GridBagConstraints();
		gbc_lblTipoTarjeta.gridheight = 3;
		gbc_lblTipoTarjeta.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoTarjeta.gridx = 4;
		gbc_lblTipoTarjeta.gridy = 1;
		panel.add(lblTipoTarjeta, gbc_lblTipoTarjeta);
		
		//CVV
		lblCVC = new JLabel("");
		lblCVC.setForeground(new Color(255, 255, 255));
		lblCVC.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblCVC = new GridBagConstraints();
		gbc_lblCVC.anchor = GridBagConstraints.WEST;
		gbc_lblCVC.insets = new Insets(0, 0, 0, 5);
		gbc_lblCVC.gridx = 1;
		gbc_lblCVC.gridy = 3;
		panel.add(lblCVC, gbc_lblCVC);
		
		addManejadorDatosTarjetaGrafico(txtCVV, lblCVC, MAXCVV, false, CVV);
		addManejadorDatosTarjetaGrafico(txtNumTarjeta, lblNumTarjeta, MAXNUMTARJETA, true, NUMEROTARJETA);
		addManejadorDatosTarjetaGrafico(txtFechaDeCaducidad, lblFecha, MAXFECHACADUCIDAD, false, FECHACADUCIDAD);
		
		addManejadorTipoTarjeta(txtNumTarjeta, panel, lblTipoTarjeta);
	}
	
	
	/**
	 * Establece los campos para introducir los datos de la tarjeta
	 */
	private void establecerDatosTarjeta() {
		//Numero de tarjeta
		txtNumTarjeta = new JTextField();
		txtNumTarjeta.setToolTipText("");
		txtNumTarjeta.setText(NUMEROTARJETA);
		txtNumTarjeta.setColumns(10);
		GridBagConstraints gbc_txtNumTarjeta = new GridBagConstraints();
		gbc_txtNumTarjeta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNumTarjeta.gridwidth = 2;
		gbc_txtNumTarjeta.insets = new Insets(0, 0, 5, 5);
		gbc_txtNumTarjeta.gridx = 1;
		gbc_txtNumTarjeta.gridy = 5;
		frmPagoConTarjeta.getContentPane().add(txtNumTarjeta, gbc_txtNumTarjeta);
		
		//Titular
		txtTitular = new JTextField();
		txtTitular.setToolTipText("");
		txtTitular.setText(TITULAR);
		txtTitular.setColumns(10);
		GridBagConstraints gbc_txtTitular = new GridBagConstraints();
		gbc_txtTitular.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitular.gridwidth = 2;
		gbc_txtTitular.insets = new Insets(0, 0, 5, 5);
		gbc_txtTitular.gridx = 1;
		gbc_txtTitular.gridy = 6;
		frmPagoConTarjeta.getContentPane().add(txtTitular, gbc_txtTitular);
		
		//CVV
		txtCVV = new JTextField();
		txtCVV.setToolTipText("");
		txtCVV.setText(CVV);
		txtCVV.setColumns(10);
		GridBagConstraints gbc_txtCVV = new GridBagConstraints();
		gbc_txtCVV.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCVV.insets = new Insets(0, 0, 5, 5);
		gbc_txtCVV.gridx = 1;
		gbc_txtCVV.gridy = 7;
		frmPagoConTarjeta.getContentPane().add(txtCVV, gbc_txtCVV);
		
		//Fecha de caducidad
		txtFechaDeCaducidad = new JTextField();
		txtFechaDeCaducidad.setToolTipText("");
		txtFechaDeCaducidad.setText(FECHACADUCIDAD);
		txtFechaDeCaducidad.setColumns(10);
		GridBagConstraints gbc_txtFechaDeCaducidad = new GridBagConstraints();
		gbc_txtFechaDeCaducidad.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFechaDeCaducidad.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaDeCaducidad.gridx = 2;
		gbc_txtFechaDeCaducidad.gridy = 7;
		frmPagoConTarjeta.getContentPane().add(txtFechaDeCaducidad, gbc_txtFechaDeCaducidad);
		
		//Animacion de los text field
		addManejadorTextField(txtFechaDeCaducidad, FECHACADUCIDAD);
		addManejadorTextField(txtCVV, CVV);
		addManejadorTextField(txtNumTarjeta, NUMEROTARJETA);
		addManejadorTextField(txtTitular, TITULAR);
	}
	
	/**
	 * Dependiendo del primer numero de la tarjeta, cambia el color y el logo
	 * @param texto
	 * @param panel
	 * @param etiqueta
	 */
	private void addManejadorTipoTarjeta(JTextField texto, JPanelBackground panel, JLabel etiqueta) {
		texto.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if(texto.getText().length() > 0) {
					String cadena = texto.getText().substring(0, 1);
					if(cadena.equals(VISA)) {
						etiqueta.setIcon(new ImageIcon(TarjetaCredito.class.getResource("/imagenes/visa.png")));
						panel.setBackground(Login.class.getResource("/imagenes/tarjeta-de-credito-azul.png"));
					} else if(cadena.equals(MASTERCARD)) {
						etiqueta.setIcon(new ImageIcon(TarjetaCredito.class.getResource("/imagenes/mastercard.png")));
						panel.setBackground(Login.class.getResource("/imagenes/tarjeta-de-credito-rojo.png"));
					} else {
						etiqueta.setIcon(null);
						panel.setBackground(Login.class.getResource("/imagenes/tarjeta-de-credito-default.png"));
					}
				} else {
					etiqueta.setIcon(null);
					panel.setBackground(Login.class.getResource("/imagenes/tarjeta-de-credito-default.png"));
				}
			}
		});
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
	 * Permite cambiar dependiendo el contenido de un jlabel dependiendo de un textfield
	 * @param texto textfield del que se obtiene el texto
	 * @param etiqueta etiquetaa la que se le cambia el texto
	 * @param maxDigitos numero maximo de digitos
	 * @param esNumTarjeta indica si el numero de la tarjeta
	 */
	private void addManejadorDatosTarjetaGrafico(JTextField texto, JLabel etiqueta, int maxDigitos, Boolean esNumTarjeta, String cadenaDefecto) {
		texto.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				String cadena = texto.getText();
				
				//Si la cadena introducida es igual a la que viene por defecto no hacemos nada
				if(cadena.equals(cadenaDefecto)) {
					return;
				}
				
				//Cogemos los ultimos maxDigitos digitos como mucho
				if (cadena.length() > maxDigitos) {
					cadena = cadena.substring(0, maxDigitos);					
				} else {
					cadena = cadena.substring(0, cadena.length());		
				}
				
				//Si es el numero de la tarjeta utilizamos como texto para el label la version convertida
				if(esNumTarjeta) {
					etiqueta.setText(convertirFormatoNumeroTarjeta(cadena));
				} else {
					etiqueta.setText(cadena);					
				}
			}
		});
	}
	
	/**
	 * Establece el boton para procesar el pago
	 */
	private void establecerBotones() {
		btnRegistrarse = new JButton("PROCESAR PAGO");
		btnRegistrarse.setForeground(new Color(218, 200, 41));
		btnRegistrarse.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnRegistrarse.setBorderPainted(false);
		btnRegistrarse.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnRegistrarse = new GridBagConstraints();
		gbc_btnRegistrarse.fill = GridBagConstraints.VERTICAL;
		gbc_btnRegistrarse.gridwidth = 2;
		gbc_btnRegistrarse.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrarse.gridx = 1;
		gbc_btnRegistrarse.gridy = 9;
		frmPagoConTarjeta.getContentPane().add(btnRegistrarse, gbc_btnRegistrarse);
		
		//Animacion de color
		addManejadorBotonColor(btnRegistrarse);
		
		//Procesamiento del pago
		addManejadorPago(btnRegistrarse);
	}
	
	/**
	 * Establece la barra de carga para procesar el pago
	 */
	private void establecerBarraDeCarga() {
		progressBar = new JProgressBar();
		progressBar.setMaximum(10);
		progressBar.setForeground(new Color(218, 200, 41));
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridwidth = 2;
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 4;
		frmPagoConTarjeta.getContentPane().add(progressBar, gbc_progressBar);
	}
	
	private void simularPago() {
		progressBar.setIndeterminate(true);
	}
	
	/**
	 * Gestiona los cambios de color al pasar el raton encima de un boton
	 * @param boton Boton que se desea que aplique el efecto
	 */
	private void addManejadorBotonColor(JButton boton) {
		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBackground(new Color(218,200,41));
				boton.setForeground(new Color(78,80,82));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(new Color(78,80,82));
				boton.setForeground(new Color(218,200,41));
				
			}
		});
	}
	
	private void addManejadorPago(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//checkfield
				if (checkFields()) {
					simularPago();
					JOptionPane.showMessageDialog(frmPagoConTarjeta, "Pago realizado correctamente", "", 1);
					
					//Poner que si el pago es correcto se haga premium y volver a ventana
				}
			}
		});
	}
	
	private boolean checkFields() {
		boolean estado = true;
		
		String info = "";
		
		//Comprobamos si se ha introducido un titular
		if(txtTitular.getText().equals(TITULAR)) {
			estado = false;
			info = "¡Introduce un titular valido!";
		}	
		
		//Comprobamos si la fecha de caducidad tiene un formato valido
		if(txtFechaDeCaducidad.getText().equals(FECHACADUCIDAD) || txtFechaDeCaducidad.getText().length() > MAXFECHACADUCIDAD || !txtFechaDeCaducidad.getText().contains("/")) {
			estado = false;
			info = "¡Introduce una fecha de caducidad válida!\nFormato: mm/yy";
		}	
		
		//Comprobamos si el cvv tiene un formato valido
		if(txtCVV.getText().equals(CVV) || txtCVV.getText().length() > MAXCVV) {
			estado = false;
			info = "¡Introduce un CVV o CVC válido!";
		}	
		
		//Comprobamos si el numero de tarjeta es valido
		if(txtNumTarjeta.getText().equals(NUMEROTARJETA) || txtNumTarjeta.getText().length() > MAXNUMTARJETA) {
			estado = false;
			info = "¡Introduce un número de tarjeta válido!";
		}	
		
		if(!estado) {
			JOptionPane.showMessageDialog(frmPagoConTarjeta, info, "Rellene correctamente los campos", 0);
		}
		
		return estado;
	}
	
	//Funcion auxiliar
	
	/**
	 * Transforma el string en un formato de tarjeta de credito, con separacion cada 4 digitos
	 * @param numeroTarjeta
	 * @return
	 */
	private String convertirFormatoNumeroTarjeta(String numeroTarjeta) {
		String cadena = "";
		int num = numeroTarjeta.length()/4;
		
		for(int i=0; i < num; i++) {
			cadena+=numeroTarjeta.substring(i*4, (i*4)+4)+" ";
		}
		cadena+=numeroTarjeta.substring((num*4), numeroTarjeta.length());
		
		return cadena;
	}

}