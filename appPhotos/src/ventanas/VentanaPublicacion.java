package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Publicacion;

import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class VentanaPublicacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VentanaPublicacion(Publicacion p) {
		crearVentana(p);
		this.setSize(new Dimension(800,470));
		setLocationRelativeTo(null);
	}
	
	private void crearVentana(Publicacion p) {
		setTitle(p.getTitulo());
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPublicacion.class.getResource("/imagenes/camara-de-fotos.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		//Comprobamos si es un album o una foto
		if(p.getClass().getName().equals("modelo.Foto")) {
			PanelPublicacionScroll scroll = new PanelPublicacionScroll(p);
			
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPane.add(scroll, gbc_lblNewLabel);
		} else {
			PanelAlbumScroll scroll = new PanelAlbumScroll(p);
			
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPane.add(scroll, gbc_lblNewLabel);
		}
		
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
