package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.awt.GridLayout;

import modelo.Foto;
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.Usuario;

public class PanelNotificaciones extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	
	private int y;
	/**
	 * Create the panel.
	 */
	public PanelNotificaciones() {
		y=0;
		crearPanel();
	}
	
	private void crearPanel() {
		this.setSize(450, 600);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 0, 15, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 20, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNotificaciones = new JLabel("NOTIFICACIONES");
		lblNotificaciones.setFont(new Font("Segoe UI", Font.BOLD, 19));
		GridBagConstraints gbc_lblNotificaciones = new GridBagConstraints();
		gbc_lblNotificaciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotificaciones.gridx = 1;
		gbc_lblNotificaciones.gridy = 1;
		add(lblNotificaciones, gbc_lblNotificaciones);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
	}
	
	public void addNotificacion(Notificacion n) {
		PanelNotificacion panelNotificacion = new PanelNotificacion(n);
		GridBagConstraints gbc_panelNotificacion = new GridBagConstraints();
		gbc_panelNotificacion.insets = new Insets(0, 0, 5, 0);
		gbc_panelNotificacion.gridx = 0;
		gbc_panelNotificacion.gridy = y;
		panel.add(panelNotificacion, gbc_panelNotificacion);
		y++;
	}
	
	private void addManejadorEliminarNotificacion(JButton boton, PanelNotificacion p) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.setVisible(false);
				
			}
		});
	}

}
