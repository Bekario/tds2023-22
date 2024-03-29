package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlador.Controlador;
import modelo.Usuario;

import javax.swing.JButton;

public class PanelUsuario extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnSeguir;
	/**
	 * Create the panel.
	 */
	public PanelUsuario(Usuario usuario) {
		crearPanel(usuario);
	}
	
	private void crearPanel(Usuario usuario) {
		this.setSize(450, 64);
		crearPanelEImagen(usuario.getPerfil(), usuario.getUsuario());
		addManejadorBotonSeguir(btnSeguir, usuario);
		// Comprobamos si debemos mostrar que el usuario es seguido o no
		setVisibilidadBotonSeguir(!Controlador.getInstancia().obtenerUsuarioActual().comprobarSeguido(usuario));
	}
	
	private void crearPanelEImagen(String perfil, String usuario) {	
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 60, 15, 160, 15, 0, 15, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblFoto = new JLabel("");
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.insets = new Insets(0, 0, 0, 5);
		gbc_lblFoto.gridx = 1;
		gbc_lblFoto.gridy = 0;
		add(lblFoto, gbc_lblFoto);
		
		ImageIcon imagen = new ImageIcon(FotoPersonalizada.redondearFoto(perfil));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		
		lblFoto.setIcon(icono);
		
		JLabel lblNombreUsuario = new JLabel(usuario);
		lblNombreUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblNombreUsuario.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblNombreUsuario = new GridBagConstraints();
		gbc_lblNombreUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblNombreUsuario.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreUsuario.gridx = 3;
		gbc_lblNombreUsuario.gridy = 0;
		add(lblNombreUsuario, gbc_lblNombreUsuario);
		
		//Utilizamos la misma posicion para el boton y para el label
		GridBagConstraints gbc_btnSeguir_lbl_seguido = new GridBagConstraints();
		gbc_btnSeguir_lbl_seguido.anchor = GridBagConstraints.EAST;
		gbc_btnSeguir_lbl_seguido.insets = new Insets(0, 0, 0, 5);
		gbc_btnSeguir_lbl_seguido.gridx = 5;
		gbc_btnSeguir_lbl_seguido.gridy = 0;
		
		btnSeguir = new JButton("Seguir");
		add(btnSeguir, gbc_btnSeguir_lbl_seguido);
		btnSeguir.setContentAreaFilled(true);

	}
	
	/**
	 * Establece si se debe ver el boton o no
	 * @param visibilidad
	 */
	public void setVisibilidadBotonSeguir(boolean visibilidad) {
		if(visibilidad) {
			btnSeguir.setText("Seguir ");
		}else {
			btnSeguir.setText("Seguido");
		}
		btnSeguir.setContentAreaFilled(visibilidad);

	}
	
	private void addManejadorBotonSeguir(JButton boton, Usuario usuario) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Comprobamos que el boton este visible, esto significa que no este ya seguido por el usuario
				if(boton.isContentAreaFilled()) {
					Controlador.getInstancia().seguirUsuario(usuario);
					setVisibilidadBotonSeguir(false);
				}else {
					Controlador.getInstancia().dejarDeSeguirUsuario(usuario);
					setVisibilidadBotonSeguir(true);
				}
			}
		});
	}
	
}
