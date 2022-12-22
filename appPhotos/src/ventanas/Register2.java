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


public class Register2 {

	private JFrame frame;
	private JScrollPane scrollPane;
	private JEditorPane descripcion;
	private JButton btnborrar;
	private JButton btnMeterImg;
	private JLabel perfil;
	private JButton btnRegistrarse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatMonokaiProIJTheme.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register2 window = new Register2();
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
	public Register2() {
		initialize();
	}
	
	/**
	 * Muestra la ventana
	 */
	public void mostrarVentana() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.getRootPane().requestFocus(false);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Register2.class.getResource("/imagenes/camara-de-fotos.png")));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 35, 0, 0, 35, 50, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 60, 0, 25, 0, 10, 30, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
	}
	
	private 
		
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
		
		btnMeterImg = new JButton("Añadir Imagen");
		btnMeterImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selector = new JFileChooser();
				selector.showOpenDialog(selector); //Esto no se porque
				File fichero = selector.getSelectedFile();
			}
		});
		btnMeterImg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		GridBagConstraints gbc_btnMeterImg = new GridBagConstraints();
		gbc_btnMeterImg.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMeterImg.insets = new Insets(0, 0, 5, 5);
		gbc_btnMeterImg.gridx = 2;
		gbc_btnMeterImg.gridy = 3;
		frame.getContentPane().add(btnMeterImg, gbc_btnMeterImg);
		
		perfil = new JLabel("");
		ImageIcon imagen = new ImageIcon(Register2.class.getResource("/imagenes/face-detection (1).png"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
		
		perfil.setIcon(icono);

		GridBagConstraints gbc_perfil = new GridBagConstraints();
		gbc_perfil.anchor = GridBagConstraints.EAST;
		gbc_perfil.insets = new Insets(0, 0, 5, 5);
		gbc_perfil.gridx = 3;
		gbc_perfil.gridy = 3;
		frame.getContentPane().add(perfil, gbc_perfil);
		
		scrollPane = new JScrollPane();
		scrollPane.setToolTipText("Introduce la descripción que será mostrada en tu perfil");
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 5;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		descripcion = new JEditorPane();
		descripcion.setText("Introduce una breve descripción sobre ti...");
		descripcion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (descripcion.getText().equals("Introduce una breve descripción sobre ti...")) {
					descripcion.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (descripcion.getText().equals("")) {
					descripcion.setText("Introduce una breve descripción sobre ti...");
				}
			}
		});
		scrollPane.setViewportView(descripcion);
		
		btnborrar = new JButton("Borrar Texto");
		//En caso de hacer click en el boton
		btnborrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Borramos el texto de la descripcion
				descripcion.setText("");
				//Lo ponemos en focus para que el usuario no tenga que hacer click para empezar a escribir
				descripcion.grabFocus();
			}
		});
		btnborrar.setToolTipText("Elimina todo el texto contenido en el campo de la descripción");
		GridBagConstraints gbc_btnborrar = new GridBagConstraints();
		gbc_btnborrar.anchor = GridBagConstraints.EAST;
		gbc_btnborrar.insets = new Insets(0, 0, 5, 5);
		gbc_btnborrar.gridx = 3;
		gbc_btnborrar.gridy = 6;
		frame.getContentPane().add(btnborrar, gbc_btnborrar);
		
		btnRegistrarse = new JButton("REGISTRARSE");
		btnRegistrarse.setBorderPainted(false);
		btnRegistrarse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRegistrarse.setBackground(new Color(218,200,41));
				btnRegistrarse.setForeground(new Color(78,80,82));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnRegistrarse.setBackground(new Color(78,80,82));
				btnRegistrarse.setForeground(new Color(218,200,41));
				
			}
		});
		btnRegistrarse.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnRegistrarse.setBackground(new Color(78,80,82));
		btnRegistrarse.setForeground(new Color(218, 200, 41));
		GridBagConstraints gbc_btnRegistrarse = new GridBagConstraints();
		gbc_btnRegistrarse.fill = GridBagConstraints.VERTICAL;
		gbc_btnRegistrarse.gridwidth = 2;
		gbc_btnRegistrarse.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrarse.gridx = 2;
		gbc_btnRegistrarse.gridy = 8;
		frame.getContentPane().add(btnRegistrarse, gbc_btnRegistrarse);
	}

}
