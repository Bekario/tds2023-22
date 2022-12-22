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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import java.io.File;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Window.Type;
import com.toedter.calendar.JCalendar;


public class SelectorFechas {

	private JFrame frame;
	private JButton btnEstablecer;
	private JCalendar calendar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatMonokaiProIJTheme.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectorFechas window = new SelectorFechas();
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
	public SelectorFechas() {
		initialize();
	}
	
	/**
	 * Muestra la ventana
	 */
	public void mostrarVentana() {
		frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		this.frame.getRootPane().requestFocus(false);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setType(Type.POPUP);
		frame.setAlwaysOnTop(true);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(SelectorFechas.class.getResource("/imagenes/camara-de-fotos.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		establecerNombre();
		establecerBoton();
		establecerCalendario();
	}
	/**
	 * Crea el nombre
	 */
	private void establecerNombre() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 35, 0, 35, 50, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 60, 0, 10, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel nombreApp = new JLabel("appPhotos");
		nombreApp.setForeground(new Color(233, 233, 233));
		nombreApp.setIcon(null);
		nombreApp.setFont(new Font("Serif", Font.BOLD, 40));
		GridBagConstraints gbc_nombreApp = new GridBagConstraints();
		gbc_nombreApp.insets = new Insets(0, 0, 5, 5);
		gbc_nombreApp.gridx = 2;
		gbc_nombreApp.gridy = 1;
		frame.getContentPane().add(nombreApp, gbc_nombreApp);
	}
	/**
	 * Crea el boton establecer
	 */
	private void establecerBoton() {
		
		btnEstablecer = new JButton("ESTABLECER");
		btnEstablecer.setBorderPainted(false);
		addManejadorBotonColor(btnEstablecer);
	}
	/**
	 * Añade el manejador de animacion del boton
	 * @param boton boton establecer
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
		gbc_calendar.gridx = 2;
		gbc_calendar.gridy = 3;
		frame.getContentPane().add(calendar, gbc_calendar);
		btnEstablecer.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnEstablecer.setBackground(new Color(78,80,82));
		btnEstablecer.setForeground(new Color(218, 200, 41));
		GridBagConstraints gbc_btnEstablecer = new GridBagConstraints();
		gbc_btnEstablecer.fill = GridBagConstraints.VERTICAL;
		gbc_btnEstablecer.insets = new Insets(0, 0, 5, 5);
		gbc_btnEstablecer.gridx = 2;
		gbc_btnEstablecer.gridy = 5;
		frame.getContentPane().add(btnEstablecer, gbc_btnEstablecer);
	}

}
