package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import java.awt.Cursor;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JCalendarBeanInfo;

public class Register {

	private JFrame frame;
	private JButton botonRegister;
	private JTextField email;
	private JTextField nombre;
	private JTextField usuario;
	private JPasswordField contraseña;
	private JPasswordField confirmar_contraseña;
	private JButton lblNewLabel;
	private JButton lblNewLabel_1;
	private JTextField txtFechaNacimiento;
	private final JButton btnNewButton = new JButton("· · ·");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatMonokaiProIJTheme.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register window = new Register();
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
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/imagenes/camara-de-fotos.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 50, 35, 0, 0, 35, 50, 0 };
		gridBagLayout.rowHeights = new int[] { 15, 0, 25, 0, 0, 0, 5, 0, 10, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel nombreApp = new JLabel("appPhotos");
		nombreApp.setForeground(new Color(233, 233, 233));
		nombreApp.setIcon(null);
		nombreApp.setFont(new Font("Serif", Font.BOLD, 40));
		GridBagConstraints gbc_nombreApp = new GridBagConstraints();
		gbc_nombreApp.gridwidth = 2;
		gbc_nombreApp.insets = new Insets(0, 0, 5, 5);
		gbc_nombreApp.gridx = 2;
		gbc_nombreApp.gridy = 1;
		frame.getContentPane().add(nombreApp, gbc_nombreApp);

		email = new JTextField();
		email.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (email.getText().equals("Email")) {
					email.setText("");
				} else {
					email.selectAll();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (email.getText().equals(""))
					email.setText("Email");
			}
		});
		email.setText("Email");
		email.setToolTipText("");

		GridBagConstraints gbc_email = new GridBagConstraints();
		gbc_email.gridwidth = 2;
		gbc_email.insets = new Insets(0, 0, 5, 5);
		gbc_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_email.gridx = 2;
		gbc_email.gridy = 3;
		frame.getContentPane().add(email, gbc_email);
		email.setColumns(10);

		nombre = new JTextField();

		usuario = new JTextField();
		usuario = new JTextField();
		usuario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (usuario.getText().equals("Usuario")) {
					usuario.setText("");
				} else {
					usuario.selectAll();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (usuario.getText().equals(""))
					usuario.setText("Usuario");
			}
		});
		
				nombre = new JTextField();
				nombre.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						if (nombre.getText().equals("Nombre")) {
							nombre.setText("");
						} else {
							nombre.selectAll();
						}
					}

					@Override
					public void focusLost(FocusEvent e) {
						if (nombre.getText().equals(""))
							nombre.setText("Nombre");
					}
				});
				nombre.setText("Nombre");
				nombre.setToolTipText("");
				GridBagConstraints gbc_nombre = new GridBagConstraints();
				gbc_nombre.gridwidth = 2;
				gbc_nombre.insets = new Insets(0, 0, 5, 5);
				gbc_nombre.fill = GridBagConstraints.HORIZONTAL;
				gbc_nombre.gridx = 2;
				gbc_nombre.gridy = 4;
				frame.getContentPane().add(nombre, gbc_nombre);
				nombre.setColumns(10);
		
		txtFechaNacimiento = new JTextField();
		txtFechaNacimiento.setText("Fecha Nacimiento");
		txtFechaNacimiento.setEditable(false);
		GridBagConstraints gbc_txtFechaNacimiento = new GridBagConstraints();
		gbc_txtFechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaNacimiento.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFechaNacimiento.gridx = 2;
		gbc_txtFechaNacimiento.gridy = 5;
		frame.getContentPane().add(txtFechaNacimiento, gbc_txtFechaNacimiento);
		txtFechaNacimiento.setColumns(10);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 5;
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SelectorFechas fechas = new SelectorFechas();
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		usuario.setText("Usuario");
		usuario.setToolTipText("");
		GridBagConstraints gbc_usuario = new GridBagConstraints();
		gbc_usuario.gridwidth = 2;
		gbc_usuario.insets = new Insets(0, 0, 5, 5);
		gbc_usuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_usuario.gridx = 2;
		gbc_usuario.gridy = 6;
		frame.getContentPane().add(usuario, gbc_usuario);
		usuario.setColumns(10);

		contraseña = new JPasswordField();
		contraseña.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent e) {
				if (contraseña.getText().equals("Contraseña")) {
					contraseña.setEchoChar('●');
					contraseña.setText("");
				} else {
					contraseña.selectAll();
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent e) {
				if (contraseña.getText().equals("")) {
					contraseña.setText("Contraseña");
					contraseña.setEchoChar((char) 0);
				}
			}
		});
		contraseña.setText("Contraseña");
		contraseña.setEchoChar((char) 0);
		GridBagConstraints gbc_contraseña = new GridBagConstraints();
		gbc_contraseña.gridwidth = 2;
		gbc_contraseña.insets = new Insets(0, 0, 5, 5);
		gbc_contraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_contraseña.gridx = 2;
		gbc_contraseña.gridy = 7;
		frame.getContentPane().add(contraseña, gbc_contraseña);

		confirmar_contraseña = new JPasswordField();
		confirmar_contraseña.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent e) {
				if (confirmar_contraseña.getText().equals("Confirmar contraseña")) {
					confirmar_contraseña.setEchoChar('●');
					confirmar_contraseña.setText("");
				} else {
					confirmar_contraseña.selectAll();
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent e) {
				if (confirmar_contraseña.getText().equals("")) {
					confirmar_contraseña.setText("Confirmar contraseña");
					confirmar_contraseña.setEchoChar((char) 0);
				}
			}
		});
		
		lblNewLabel = new JButton("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mousePressed(MouseEvent e) {
				lblNewLabel.setContentAreaFilled(true);
				if (!contraseña.getText().equals("Contraseña")) {					
					contraseña.setEchoChar((char) 0);
				}
		
			}
			
			@SuppressWarnings("deprecation")
			@Override
			public void mouseReleased(MouseEvent e) {
				lblNewLabel.setContentAreaFilled(false);
				if (!contraseña.getText().equals("Contraseña")) {					
					contraseña.setEchoChar('●');
				}
				

			}
		});
		lblNewLabel.setBorderPainted(false);
		lblNewLabel.setContentAreaFilled(false);
		ImageIcon imagen = new ImageIcon(Register2.class.getResource("/imagenes/mostrarcont.png"));
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        lblNewLabel.setIcon(icono);
        
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 4;
		gbc_lblNewLabel.gridy = 7;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		confirmar_contraseña.setText("Confirmar contraseña");
		confirmar_contraseña.setEchoChar((char) 0);
		GridBagConstraints gbc_confirmar_contraseña = new GridBagConstraints();
		gbc_confirmar_contraseña.gridwidth = 2;
		gbc_confirmar_contraseña.insets = new Insets(0, 0, 5, 5);
		gbc_confirmar_contraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmar_contraseña.gridx = 2;
		gbc_confirmar_contraseña.gridy = 8;
		frame.getContentPane().add(confirmar_contraseña, gbc_confirmar_contraseña);
		
		lblNewLabel_1 = new JButton("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mousePressed(MouseEvent e) {
				lblNewLabel_1.setContentAreaFilled(true);
				if (!confirmar_contraseña.getText().equals("Confirmar contraseña")) {					
					confirmar_contraseña.setEchoChar((char) 0);
				}
			}
			
			@SuppressWarnings("deprecation")
			@Override
			public void mouseReleased(MouseEvent e) {
				lblNewLabel_1.setContentAreaFilled(false);
				if (!confirmar_contraseña.getText().equals("Confirmar contraseña")) {					
					confirmar_contraseña.setEchoChar('●');
				}
				

			}
		});

		lblNewLabel_1.setBorderPainted(false);
		lblNewLabel_1.setContentAreaFilled(false);
        lblNewLabel_1.setIcon(icono);
        
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 8;
		frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		botonRegister = new JButton("");
		botonRegister.setContentAreaFilled(false);
		botonRegister.setBorderPainted(false);
		botonRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonRegister.setIcon(new ImageIcon(Register.class.getResource("/imagenes/button (3).png")));
		GridBagConstraints gbc_botonRegister = new GridBagConstraints();
		gbc_botonRegister.gridwidth = 2;
		gbc_botonRegister.insets = new Insets(0, 0, 5, 5);
		gbc_botonRegister.gridx = 2;
		gbc_botonRegister.gridy = 10;
		frame.getContentPane().add(botonRegister, gbc_botonRegister);
	}

}
