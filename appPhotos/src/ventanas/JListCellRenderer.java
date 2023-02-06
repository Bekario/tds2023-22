package ventanas;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import modelo.Usuario;

public class JListCellRenderer extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListCellRenderer<? super Usuario> createListRenderer() {
		return new DefaultListCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private Color background = new Color(0, 100, 255, 15);
			private Color defaultBackground = (Color) UIManager.get("List.background");

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (c instanceof JLabel) {
					JLabel label = (JLabel) c;
					Usuario usuario = (Usuario) value;
					label.setIcon(new ImageIcon(Home.class.getResource(usuario.getPerfil())));
					label.setText(String.format("%s [%s]", usuario.getUsuario(), LocalDate.now().toString()));
					if (!isSelected) {
						label.setBackground(index % 2 == 0 ? background : defaultBackground);
					}
				}
				return c;
			}
		};

	}
}
