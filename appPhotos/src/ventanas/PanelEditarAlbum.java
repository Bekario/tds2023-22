package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;

import java.awt.Insets;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.UIManager;

import controlador.Controlador;
import modelo.Album;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class PanelEditarAlbum extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private Home padre;
	private PanelSeleccionarFotos panelSeleccionarFotos;
	private JTextField txtTitulo;
	private JTextArea txtDescripcion;
	
	private final String TITULO = "Título";
	private final String DESCRIPCION = "Descripción";

	/**
	 * Create the panel.
	 */
	public PanelEditarAlbum(Home home, Album album) {
		padre = home;
		this.setSize(450, 800);
		crearPanel(album);
		
	}
	private void crearPanel(Album album) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 285, 15, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 15, 20, 15, 0, 15, 0, 0, 15, 50, 15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		crearTextoYBotones(album);
		crearPanelFotos(album);
	}
	
	private void crearTextoYBotones(Album album) {
		JLabel lblTitulo = new JLabel("Editar Álbum: "+album.getTitulo());
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 19));
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.anchor = GridBagConstraints.SOUTH;
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 1;
		add(lblTitulo, gbc_lblTitulo);
		
		JEditorPane editorInfo = new JEditorPane();
		editorInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		GridBagConstraints gbc_editorInfo = new GridBagConstraints();
		gbc_editorInfo.insets = new Insets(0, 0, 5, 5);
		gbc_editorInfo.fill = GridBagConstraints.BOTH;
		gbc_editorInfo.gridx = 1;
		gbc_editorInfo.gridy = 3;
		add(editorInfo, gbc_editorInfo);
		
		editorInfo.setContentType("text/html");
		editorInfo.setText("<center>¡Recuerda!<br>La imagen seleccionada en color rojo es la portada.<br>Haz click sobre las imagenes y serán seleccionadas.</center>");
		editorInfo.setEditable(false);
		
		txtTitulo = new JTextField();
		txtTitulo.setToolTipText("");
		txtTitulo.setText(album.getTitulo());
		txtTitulo.setColumns(10);
		GridBagConstraints gbc_txtTitulo = new GridBagConstraints();
		gbc_txtTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_txtTitulo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitulo.gridx = 1;
		gbc_txtTitulo.gridy = 7;
		add(txtTitulo, gbc_txtTitulo);
		
		Manejadores.addManejadorTextos(txtTitulo, TITULO);
		
		txtDescripcion = new JTextArea(3, 20);
		txtDescripcion.setWrapStyleWord(true);
		txtDescripcion.setText(album.getDescripcion());
		txtDescripcion.setLineWrap(true);
		
		Manejadores.addManejadorArea(txtDescripcion, DESCRIPCION);

		JScrollPane scrollPane = new JScrollPane(txtDescripcion);
		scrollPane.setViewportBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 8;
		add(scrollPane, gbc_scrollPane);
		
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setForeground(new Color(218, 200, 41));
		btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnGuardar.setBorderPainted(false);
		btnGuardar.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.fill = GridBagConstraints.VERTICAL;
		gbc_btnGuardar.insets = new Insets(0, 0, 5, 5);
		gbc_btnGuardar.gridx = 1;
		gbc_btnGuardar.gridy = 10;
		add(btnGuardar, gbc_btnGuardar);
		
		addManejadorBotonGuardar(btnGuardar, album);
		Manejadores.addManejadorBotonColor(btnGuardar);
		
	}
	
	private void addManejadorBotonGuardar(JButton boton, Album album) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Comprobamos que exista como minimo una portada
				if (checkFields(album)) {
					Controlador.getInstancia().modificarAlbum(album, txtTitulo.getText(), txtDescripcion.getText());
					//Recargamos el panel perfil
					padre.recargarPanelPerfil();
					
					//Mostramos el panelPerfil
					padre.setPanelPerfil();					
				}
				
			}
		});
	}
	
	private void crearPanelFotos(Album album) {
		panelSeleccionarFotos = new PanelSeleccionarFotos(padre, Controlador.getInstancia().obtenerUsuarioActual().getFotos(), album.getPortada(), album.getFotos());
		GridBagConstraints gbc_panelSeleccionarFotos_1 = new GridBagConstraints();
		gbc_panelSeleccionarFotos_1.insets = new Insets(0, 0, 5, 5);
		gbc_panelSeleccionarFotos_1.fill = GridBagConstraints.BOTH;
		gbc_panelSeleccionarFotos_1.gridx = 1;
		gbc_panelSeleccionarFotos_1.gridy = 5;
		add(panelSeleccionarFotos, gbc_panelSeleccionarFotos_1);
		
	}
	
	
	
	private void crearMessageDialog(String descripcion, String titulo, int icono) {
		JOptionPane.showMessageDialog(this, descripcion, titulo, icono);
	}
	
	/**
	 * Comprueba que todos los campos tengan un dato valido
	 * @return booleano indicando si estan correctos los campos
	 */
	private boolean checkFields(Album album) {
		boolean estado = true;
		String info = "";
		
		if(txtTitulo.getText().equals(TITULO)) {
			estado = false;
			info = "¡Debes introducir un título al álbum!";
		}
		
		if (Controlador.getInstancia().getPortadaSeleccionada() == null) {
			info = "¡Debes seleccionar como minimo una portada (resaltada en rojo)!";
			estado = false;
		}
		
		if (Controlador.getInstancia().comprobarAlbum(txtTitulo.getText())) {
			if(!album.getTitulo().equals(txtTitulo.getText())) {
				info = "¡Ya has creado un album con este nombre, prueba con otro!";
				estado = false;				
			}
		}
		
		if(!estado) {
			crearMessageDialog(info, "Rellene correctamente los campos", 0);
		}
		
		return estado;
	}
	
}
