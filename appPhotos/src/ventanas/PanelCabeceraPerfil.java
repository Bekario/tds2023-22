package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import controlador.Controlador;
import modelo.Usuario;


public class PanelCabeceraPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	private Home home;
	private JLabel lblPublicaciones;
	private JLabel lblSeguidores;
	private JLabel lblSeguidos;
	private JLabel lblFotoPerfil;
	private JTextArea textAreaDescripcion;
	private JLabel lblUsuario;
	private JButton btn;
	private JLabel lblSeguido;

	/**
	 * Create the panel.
	 */
	public PanelCabeceraPerfil(Home home, Usuario usuario) {
		this.home = home;
		crearPanel(usuario);
		
	}
	public Home getHome() {
		return home;
	}
	private void crearPanel(Usuario usuario) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 15, 0, 0, 0, 15, 80, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 0, 0, 15, 0, 15, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		generarPerfil(usuario);
		
	}
	private void generarPerfil(Usuario usuario) {
		
		lblFotoPerfil = new JLabel("");
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.gridheight = 3;
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoPerfil.gridx = 0;
		gbc_lblFotoPerfil.gridy = 1;
		add(lblFotoPerfil, gbc_lblFotoPerfil);
		ImageIcon imagen = new ImageIcon(FotoPersonalizada.redondearFoto(usuario.getPerfil()));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
		lblFotoPerfil.setIcon(icono);
		
		lblUsuario = new JLabel(usuario.getUsuario());
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.gridwidth = 3;
		gbc_lblUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 2;
		gbc_lblUsuario.gridy = 1;
		add(lblUsuario, gbc_lblUsuario);
		
		GridBagConstraints gbc_btn = new GridBagConstraints();
		gbc_btn.gridheight = 3;
		gbc_btn.insets = new Insets(0, 0, 5, 0);
		gbc_btn.gridx = 6;
		gbc_btn.gridy = 1;
		
		
		// Si este perfil no es del usuarioActual, no mostramos el boton editar
		if (usuario.equals(Controlador.getInstancia().obtenerUsuarioActual())) {
			btn = new JButton("Editar");
			
			home.addManejadorEdit(btn, this);	
		} else { //Si no es el actual, ponemos el boton de seguir
			//Por defecto el label no se muestra
			lblSeguido = new JLabel("Seguido");
			add(lblSeguido, gbc_btn);
			addManejadorDejarDeSeguir(lblSeguido, usuario);
			
			btn = new JButton("Seguir");
			addManejadorBotonSeguir(btn, usuario);
			
			//Comprobamos si el usuario ya esta siendo seguido
			setVisibilidadBotonSeguir(!Controlador.getInstancia().comprobarSeguido(usuario));
		}
		add(btn, gbc_btn);
		
		lblPublicaciones = new JLabel(String.valueOf(usuario.getNumeroPublicaciones()));
		GridBagConstraints gbc_lblPublicaciones = new GridBagConstraints();
		gbc_lblPublicaciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicaciones.gridx = 2;
		gbc_lblPublicaciones.gridy = 2;
		add(lblPublicaciones, gbc_lblPublicaciones);
		
		lblSeguidores = new JLabel(String.valueOf(usuario.getNumeroSeguidores()));
		GridBagConstraints gbc_lblSeguidores = new GridBagConstraints();
		gbc_lblSeguidores.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidores.gridx = 3;
		gbc_lblSeguidores.gridy = 2;
		add(lblSeguidores, gbc_lblSeguidores);
		
		lblSeguidos = new JLabel(String.valueOf(usuario.getNumeroSeguidos()));
		GridBagConstraints gbc_lblSeguidos = new GridBagConstraints();
		gbc_lblSeguidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidos.gridx = 4;
		gbc_lblSeguidos.gridy = 2;
		add(lblSeguidos, gbc_lblSeguidos);
		
		JLabel lblPublicaciones_1 = new JLabel("publicaciones");
		GridBagConstraints gbc_lblPublicaciones_1 = new GridBagConstraints();
		gbc_lblPublicaciones_1.anchor = GridBagConstraints.NORTH;
		gbc_lblPublicaciones_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicaciones_1.gridx = 2;
		gbc_lblPublicaciones_1.gridy = 3;
		add(lblPublicaciones_1, gbc_lblPublicaciones_1);
		
		JLabel lblSeguidores_1 = new JLabel("seguidores");
		GridBagConstraints gbc_lblSeguidores_1 = new GridBagConstraints();
		gbc_lblSeguidores_1.anchor = GridBagConstraints.NORTH;
		gbc_lblSeguidores_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidores_1.gridx = 3;
		gbc_lblSeguidores_1.gridy = 3;
		add(lblSeguidores_1, gbc_lblSeguidores_1);
		
		JLabel lblSeguidos_1 = new JLabel("seguidos");
		GridBagConstraints gbc_lblSeguidos_1 = new GridBagConstraints();
		gbc_lblSeguidos_1.anchor = GridBagConstraints.NORTH;
		gbc_lblSeguidos_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidos_1.gridx = 4;
		gbc_lblSeguidos_1.gridy = 3;
		add(lblSeguidos_1, gbc_lblSeguidos_1);
		
		
		textAreaDescripcion = new JTextArea(3, 10);
		textAreaDescripcion.setFocusable(false);
		textAreaDescripcion.setWrapStyleWord(true);
		textAreaDescripcion.setLineWrap(true);
		textAreaDescripcion.setEditable(false);
		textAreaDescripcion.setText(usuario.getDescripcion());
		textAreaDescripcion.setBackground(new Color(45, 42, 46));

		JScrollPane scrollPane = new JScrollPane(textAreaDescripcion);
		scrollPane.setViewportBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 5;
		add(scrollPane, gbc_scrollPane);

	}
	
	private void addManejadorBotonSeguir(JButton boton, Usuario usuario) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Comprobamos que el boton este visible, esto significa que no este ya seguido por el usuario
				if(boton.isVisible()) {
					Controlador.getInstancia().seguirUsuario(usuario);
					setVisibilidadBotonSeguir(false);
					//Cambiamos los seguidores
					lblSeguidores.setText(String.valueOf(usuario.getNumeroSeguidores()));
				}
			}
		});
	}
	
	private void addManejadorDejarDeSeguir(JLabel label, Usuario usuario) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Comprobamos que el label este visible, si esta visible es porque esta siendo seguido
				if(label.isVisible()) {
					Controlador.getInstancia().dejarDeSeguirUsuario(usuario);
					setVisibilidadBotonSeguir(true);
					//Cambiamos los seguidores
					lblSeguidores.setText(String.valueOf(usuario.getNumeroSeguidores()));
				}
			}
		});
	}
	
	/**
	 * Establece si se debe ver el boton o no
	 * @param visibilidad
	 */
	public void setVisibilidadBotonSeguir(boolean visibilidad) {
		btn.setVisible(visibilidad);
		lblSeguido.setVisible(!visibilidad);
	}
	
	public void actualizarCampos(Usuario usuario) {
		ImageIcon imagen = new ImageIcon(FotoPersonalizada.redondearFoto(usuario.getPerfil()));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
		lblFotoPerfil.setIcon(icono);
		lblUsuario.setText(usuario.getUsuario());
		lblPublicaciones.setText(String.valueOf(usuario.getNumeroPublicaciones()));
		lblSeguidores.setText(String.valueOf(usuario.getNumeroSeguidores()));
		lblSeguidos.setText(String.valueOf(usuario.getNumeroSeguidos()));
		textAreaDescripcion.setText(usuario.getDescripcion());
		
		this.updateUI();
	}
}
