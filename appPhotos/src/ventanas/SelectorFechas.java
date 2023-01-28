package ventanas;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;

import com.toedter.calendar.JCalendar;

//import umu.tds.gui.RegistroView;


public class SelectorFechas extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton btnEstablecer;
	private JCalendar calendar;
	private LocalDate fecha;

	/**
	 * Create the application.
	 */
	public SelectorFechas(JFrame owner) {
		super(owner, true);
		initialize();
	}
	
	/**
	 * Muestra la ventana
	 */
	public void mostrarVentana() {
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	@Override
	 public void actionPerformed(ActionEvent e) {
	 this.setVisible(false);
	 } 

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Seleccione una fecha");
		setType(Type.NORMAL);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SelectorFechas.class.getResource("/imagenes/camara-de-fotos.png")));
		setBounds(100, 100, 380, 490);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		establecerNombre();
		establecerBoton();
		establecerCalendario();
	}
	/**
	 * Crea el nombre
	 */
	private void establecerNombre() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 0, 50, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 20, 0, 50, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel nombreApp = new JLabel("SELECCIONE UNA FECHA");
		nombreApp.setForeground(new Color(218, 200, 41));
		nombreApp.setIcon(null);
		nombreApp.setFont(new Font("Segoe UI", Font.BOLD, 20));
		GridBagConstraints gbc_nombreApp = new GridBagConstraints();
		gbc_nombreApp.insets = new Insets(0, 0, 5, 5);
		gbc_nombreApp.gridx = 1;
		gbc_nombreApp.gridy = 1;
		getContentPane().add(nombreApp, gbc_nombreApp);
	}
	
	/**
	 * Crea el boton establecer
	 */
	private void establecerBoton() {
		btnEstablecer = new JButton("ESTABLECER");
		btnEstablecer.setBorderPainted(false);
		btnEstablecer.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnEstablecer.setBackground(new Color(78,80,82));
		btnEstablecer.setForeground(new Color(218, 200, 41));
		GridBagConstraints gbc_btnEstablecer = new GridBagConstraints();
		gbc_btnEstablecer.fill = GridBagConstraints.VERTICAL;
		gbc_btnEstablecer.insets = new Insets(0, 0, 5, 5);
		gbc_btnEstablecer.gridx = 1;
		gbc_btnEstablecer.gridy = 4;
		getContentPane().add(btnEstablecer, gbc_btnEstablecer);

		addManejadorBoton(btnEstablecer);
		addManejadorEstablecer(btnEstablecer, calendar);
	}
	
	/**
	 * Añade el manejador de animacion del boton
	 * @param boton boton establecer
	 */
	private void addManejadorBoton(JButton boton) {
		//Color
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
	
	/**
	 * Cierra la ventana y almacena los 
	 * @param boton
	 */
	//JDate
	private void addManejadorEstablecer(JButton boton, JCalendar calendar) {
		//Fecha seleccionada
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendar.getDate();
				setFecha(calendar.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				
				dispose();
			}
		});
	}
	
	/**
	 * Crea el calendario
	 */
	private void establecerCalendario() {
		calendar = new JCalendar();
		calendar.getDayChooser().setAlwaysFireDayProperty(true);
		calendar.getDayChooser().setForeground(new Color(255, 255, 255));
		calendar.getDayChooser().setDecorationBackgroundColor(new Color(255, 255, 255));
		calendar.getYearChooser().getSpinner().setBackground(new Color(229, 229, 229));
		calendar.getMonthChooser().getComboBox().setForeground(new Color(0, 0, 0));
		calendar.getMonthChooser().getComboBox().setBackground(new Color(229, 229, 229));
		calendar.setForeground(new Color(255, 255, 255));
		calendar.getYearChooser().setForeground(new Color(0, 0, 0));
		calendar.getYearChooser().getSpinner().setForeground(new Color(0, 0, 0));
		calendar.setMaxDayCharacters(3);
		calendar.setWeekdayForeground(new Color(0, 0, 0));
		calendar.setWeekOfYearVisible(false);
		calendar.setSundayForeground(new Color(255, 255, 255));
		calendar.setDecorationBackgroundColor(new Color(187, 187, 187));
		GridBagConstraints gbc_calendar = new GridBagConstraints();
		gbc_calendar.insets = new Insets(0, 0, 5, 5);
		gbc_calendar.fill = GridBagConstraints.BOTH;
		gbc_calendar.gridx = 1;
		gbc_calendar.gridy = 3;
		getContentPane().add(calendar, gbc_calendar);
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}

}
