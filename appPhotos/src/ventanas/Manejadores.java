package ventanas;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import modelo.Publicacion;

/**
 * Manejadores comunes, utilizados en diversas ventanas y paneles
 */
public class Manejadores {
	
	/**
	 * Gestiona las animaciones del campo contraseña
	 * @param textPasswd Campo de contraseña
	 */
	public static void addManejadorContraseña(JPasswordField textPasswd, String defecto) {
		textPasswd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(new String(textPasswd.getPassword()).equals(defecto)) {
					textPasswd.setEchoChar('●');
					textPasswd.setText("");					
				}else {
					textPasswd.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(new String(textPasswd.getPassword()).equals("")) {
					textPasswd.setText(defecto);
					textPasswd.setEchoChar((char)0);
				}
			}
		});
	}
	
	/**
	 * Añade una animacion al introducir texto a los campos de texto
	 * @param texto Campo de texto
	 * @param defecto String que debe ser sustituida (por defecto)
	 */
	public static void addManejadorTextos(JTextField texto, String defecto) {
		texto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (texto.getText().equals(defecto)) {
					texto.setText("");
				} else {
					texto.selectAll();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (texto.getText().equals(""))
					texto.setText(defecto);
			}
		});
	}
	
	/**
	 * Gestiona que el texto se borre alhacer click o se seleccione
	 * @param descripcion Campo descripcion
	 */
	public static void addManejadorDescripcion(JEditorPane descripcion, String defecto) {
		descripcion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (descripcion.getText().equals(defecto)) {
					descripcion.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (descripcion.getText().equals("")) {
					descripcion.setText(defecto);
				}
			}
		});
	}
	
	/**
	 * Gestiona que el texto se borre alhacer click o se seleccione
	 * @param area Campo textArea
	 */
	public static void addManejadorArea(JTextArea area, String defecto) {
		area.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (area.getText().equals(defecto)) {
					area.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (area.getText().equals("")) {
					area.setText(defecto);
				}
			}
		});
	}
	
	public static void addManejadorBotonesMostrarContraseña(JButton boton, JPasswordField contraseña, String defecto){
		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				boton.setContentAreaFilled(true);
				if (!new String(contraseña.getPassword()).equals(defecto)) {					
					contraseña.setEchoChar((char) 0);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				boton.setContentAreaFilled(false);
				if (!new String(contraseña.getPassword()).equals(defecto)) {					
					contraseña.setEchoChar('●');
				}
			}
		});
	}
	
	/**
	 * Gestiona los cambios de color al pasar el raton encima de un boton
	 * @param boton Boton que se desea que aplique el efecto
	 */
	public static void addManejadorBotonColor(JButton boton) {
		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBackground(Colores.NARANJA);
				boton.setForeground(new Color(78,80,82));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(new Color(78,80,82));
				boton.setForeground(Colores.NARANJA);
				
			}
		});
	}
	public static void addManejadorClickToFoto(JLabel label, Publicacion p) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					VentanaPublicacion v = new VentanaPublicacion(p);
					v.mostrarVentana();
				}
			}
		});
	}
	public static void addManejadorResaltar(JComponent c) {
		c.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				c.setBorder(new LineBorder(Colores.NARANJA, 2, true));	
			}
			@Override
			public void mouseExited(MouseEvent e) {
				c.setBorder(new LineBorder(new Color(45, 42, 46), 2, true));		
			}
		});
	}
	
	public static void addManejadorAnimacionBoton(Component c, JButton boton, String icono_animado, String icono_estatico) {
		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setIcon(new ImageIcon(Manejadores.class.getResource(icono_animado)));
				c.repaint();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boton.setIcon(new ImageIcon(Manejadores.class.getResource(icono_estatico)));
				c.repaint();
			}
		});

	}
	
}
