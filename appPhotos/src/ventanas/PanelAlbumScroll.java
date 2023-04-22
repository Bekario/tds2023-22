package ventanas;

import javax.swing.JPanel;

import java.util.ListIterator;

import modelo.Publicacion;
import modelo.Foto;
import modelo.Album;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.Cursor;
import javax.swing.JComboBox;


public class PanelAlbumScroll extends PanelPublicacionScroll {

	private static final long serialVersionUID = 1L;
	private JButton btnIzquierda;
	private JButton btnDerecha;
	private static final int IZQUIERDA = 0;
	private static final int DERECHA = 1;
	private ListIterator<Foto> iterador;

	public PanelAlbumScroll(Publicacion publicacion) {
		super(publicacion);
		iterador = ((Album) publicacion).getFotos().listIterator();
		establecerPanelScroll();
		
		addManejadorClickBoton(btnIzquierda, IZQUIERDA);
		addManejadorClickBoton(btnDerecha, DERECHA);
	}
	
	private void establecerPanelScroll() {
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		btnIzquierda = new JButton("");
		GridBagConstraints gbc_btnIzquierda = new GridBagConstraints();
		gbc_btnIzquierda.insets = new Insets(0, 0, 0, 5);
		gbc_btnIzquierda.gridx = 0;
		gbc_btnIzquierda.gridy = 0;
		panel.add(btnIzquierda, gbc_btnIzquierda);
		btnIzquierda.setContentAreaFilled(false);
		btnIzquierda.setIcon(new ImageIcon(PanelAlbumScroll.class.getResource("/imagenes/proximo izq.png")));
		
		JComboBox<String> comboBox = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);
		
		btnDerecha = new JButton("");
		GridBagConstraints gbc_btnDerecha = new GridBagConstraints();
		gbc_btnDerecha.gridx = 2;
		gbc_btnDerecha.gridy = 0;
		panel.add(btnDerecha, gbc_btnDerecha);
		btnDerecha.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnDerecha.setContentAreaFilled(false);
		btnDerecha.setIcon(new ImageIcon(PanelAlbumScroll.class.getResource("/imagenes/proximo.png")));
	}
	
	private void addManejadorClickBoton(JButton boton, int direccion) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Comprobamos la direccion y movemos el iterador
				Foto foto = null;
				if(direccion == IZQUIERDA) {
					if(iterador.hasPrevious()) {
						foto = iterador.previous();
					}
				} else {
					if(iterador.hasNext()) {
						foto = iterador.next();						
					}
				}
				
				//Cambiamos el icono
				ImageIcon imagen = new ImageIcon(foto.getPath());
				Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(380, 380, Image.SCALE_SMOOTH));
				lblFoto.setIcon(icono);
				
				//Refrescamos
				updateUI();
			}
		});
	}
	
}
