package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Publicacion;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class VentanaPublicacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;



	/**
	 * Create the frame.
	 */
	public VentanaPublicacion(Publicacion p) {
		setTitle(p.getTitulo());
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPublicacion.class.getResource("/imagenes/camara-de-fotos.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		ImageIcon imagen = new ImageIcon(Controlador.getInstancia().obtenerPortadaPublicacion(p));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(380, 380, Image.SCALE_SMOOTH));
		lblNewLabel.setIcon(icono);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		PanelComentario panelComentario = new PanelComentario(p);
		GridBagConstraints gbc_panelComentario= new GridBagConstraints();
		gbc_panelComentario.gridwidth = 2;
		gbc_panelComentario.fill = GridBagConstraints.BOTH;
		gbc_panelComentario.gridx = 1;
		gbc_panelComentario.gridy = 0;
		getContentPane().add(panelComentario, gbc_panelComentario);
	}
	/**
	 * Muestra la ventana
	 */
	public void mostrarVentana() {
		setVisible(true);
		getRootPane().requestFocus(false);
	}
	

}
