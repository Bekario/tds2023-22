package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JTextField;


public class Register {

	private JFrame frame;
	private JButton botonRegister;
	private JTextField email;
	private JTextField nombre;
	private JTextField usuario;
	private JTextField contraseña;
	private JTextField confirmar_contraseña;

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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/imagenes/camara-de-fotos.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 35, 0, 35, 50, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 25, 0, 0, 5, 0, 10, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		email = new JTextField();
		email.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(email.getText().equals("email")) {
					email.setText("");
				}
				else {
					email.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(email.getText().equals(""))
					email.setText("email");
			}
		});
		email.setText("email");
		email.setToolTipText("");
		
		GridBagConstraints gbc_email = new GridBagConstraints();
		gbc_email.insets = new Insets(0, 0, 5, 5);
		gbc_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_email.gridx = 2;
		gbc_email.gridy = 3;
		frame.getContentPane().add(email, gbc_email);
		email.setColumns(10);
		
		nombre = new JTextField();
		
		nombre = new JTextField();
		nombre.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(nombre.getText().equals("nombre")) {
					nombre.setText("");
				}
				else {
					nombre.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(nombre.getText().equals(""))
					nombre.setText("nombre");
			}
		});
		nombre.setText("nombre");
		nombre.setToolTipText("");
		GridBagConstraints gbc_nombre = new GridBagConstraints();
		gbc_nombre.insets = new Insets(0, 0, 5, 5);
		gbc_nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombre.gridx = 2;
		gbc_nombre.gridy = 4;
		frame.getContentPane().add(nombre, gbc_nombre);
		nombre.setColumns(10);
		
		usuario = new JTextField();
		usuario = new JTextField();
		usuario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usuario.getText().equals("usuario")) {
					usuario.setText("");
				}
				else {
					usuario.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usuario.getText().equals(""))
					usuario.setText("usuario");
			}
		});
		usuario.setText("usuario");
		usuario.setToolTipText("");
		GridBagConstraints gbc_usuario = new GridBagConstraints();
		gbc_usuario.insets = new Insets(0, 0, 5, 5);
		gbc_usuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_usuario.gridx = 2;
		gbc_usuario.gridy = 5;
		frame.getContentPane().add(usuario, gbc_usuario);
		usuario.setColumns(10);
		
		contraseña = new JTextField();
		contraseña = new JTextField();
		contraseña.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(contraseña.getText().equals("contraseña")) {
					contraseña.setText("");
				}
				else {
					contraseña.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(contraseña.getText().equals(""))
					contraseña.setText("contraseña");
			}
		});
		contraseña.setText("contraseña");
		contraseña.setToolTipText("");
		GridBagConstraints gbc_contraseña = new GridBagConstraints();
		gbc_contraseña.insets = new Insets(0, 0, 5, 5);
		gbc_contraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_contraseña.gridx = 2;
		gbc_contraseña.gridy = 6;
		frame.getContentPane().add(contraseña, gbc_contraseña);
		contraseña.setColumns(10);
		
		confirmar_contraseña = new JTextField();
		confirmar_contraseña = new JTextField();
		confirmar_contraseña.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(confirmar_contraseña.getText().equals("confirmar contraseña")) {
					confirmar_contraseña.setText("");
				}
				else {
					confirmar_contraseña.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(confirmar_contraseña.getText().equals(""))
					confirmar_contraseña.setText("confirmar contraseña");
			}
		});
		confirmar_contraseña.setText("confirmar contraseña");
		confirmar_contraseña.setToolTipText("");
		GridBagConstraints gbc_confirmar_contraseña = new GridBagConstraints();
		gbc_confirmar_contraseña.insets = new Insets(0, 0, 5, 5);
		gbc_confirmar_contraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmar_contraseña.gridx = 2;
		gbc_confirmar_contraseña.gridy = 7;
		frame.getContentPane().add(confirmar_contraseña, gbc_confirmar_contraseña);
		confirmar_contraseña.setColumns(10);
		
		botonRegister = new JButton("");
		botonRegister.setContentAreaFilled(false);
		botonRegister.setBorderPainted(false);
		botonRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonRegister.setIcon(new ImageIcon(Register.class.getResource("/imagenes/button (3).png")));
		GridBagConstraints gbc_botonRegister = new GridBagConstraints();
		gbc_botonRegister.insets = new Insets(0, 0, 5, 5);
		gbc_botonRegister.gridx = 2;
		gbc_botonRegister.gridy = 9;
		frame.getContentPane().add(botonRegister, gbc_botonRegister);
	}

}
