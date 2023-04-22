package ventanas;

import javax.swing.JPanel;

import java.util.List;
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
import javax.swing.JLabel;


public class PanelAlbumScroll extends PanelPublicacionScroll {

	private static final long serialVersionUID = 1L;
	private JButton btnIzquierda;
	private JButton btnDerecha;
	private JLabel lblNombreFoto;
	private static final int IZQUIERDA = 0;
	private static final int DERECHA = 1;
	private int indice;

	public PanelAlbumScroll(Publicacion publicacion) {
		super(publicacion);
		indice = 0;
		establecerPanelScroll(publicacion);
		
		addManejadorClickBoton(btnIzquierda, IZQUIERDA, (Album) publicacion);
		addManejadorClickBoton(btnDerecha, DERECHA, (Album) publicacion);
	}
	
	private void establecerPanelScroll(Publicacion publicacion) {
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
		
		lblNombreFoto = new JLabel(((Album) publicacion).getPortada().getTitulo());
		GridBagConstraints gbc_lblNombreFoto = new GridBagConstraints();
		gbc_lblNombreFoto.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreFoto.gridx = 1;
		gbc_lblNombreFoto.gridy = 0;
		panel.add(lblNombreFoto, gbc_lblNombreFoto);
		
		
		btnDerecha = new JButton("");
		GridBagConstraints gbc_btnDerecha = new GridBagConstraints();
		gbc_btnDerecha.gridx = 2;
		gbc_btnDerecha.gridy = 0;
		panel.add(btnDerecha, gbc_btnDerecha);
		btnDerecha.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnDerecha.setContentAreaFilled(false);
		btnDerecha.setIcon(new ImageIcon(PanelAlbumScroll.class.getResource("/imagenes/proximo.png")));
	}
	
	private void addManejadorClickBoton(JButton boton, int direccion, Album album) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Foto> fotos = album.getFotos();
				
				//Comprobamos la direccion y movemos el indice
				Foto foto = null;
				if(direccion == IZQUIERDA) {
					if(indice > 0) {
						indice--;
					}
				} else if (direccion == DERECHA) {
					if (indice < fotos.size()) {
						indice++;
					}
				}

				//Si el indice es 0 es la portada, sino es el resto
				if(indice == 0) {
					foto = album.getPortada();
				} else {
					foto = fotos.get(indice-1);
				}

				//Cambiamos el icono si es necesario
				if(foto != null) {
					ImageIcon imagen = new ImageIcon(foto.getPath());
					Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(380, 380, Image.SCALE_SMOOTH));
					lblFoto.setIcon(icono);
					lblNombreFoto.setText(foto.getTitulo());
					
					//Refrescamos
					updateUI();
				}
			}
		});
	}
	
}
