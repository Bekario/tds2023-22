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

public class PanelCrearAlbum extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnContinuar_1;
	private Home padre;
	private PanelSeleccionarFotos panelSeleccionarFotos;
	private JTextField textField;
	private JTextArea txtDescripcion;
	
	private final String TITULO = "Título";
	private final String DESCRIPCION = "Descripción";

	/**
	 * Create the panel.
	 */
	public PanelCrearAlbum(Home home) {
		padre = home;
		this.setSize(450, 800);
		crearPanel();
		
	}
	private void crearPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 285, 15, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 15, 20, 15, 450, 15, 0, 0, 15, 50, 15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		crearTextoYBotones();
		crearPanelFotos();
	}
	
	private void crearTextoYBotones() {
		JLabel lblTitulo = new JLabel("Crear Álbum");
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
		editorInfo.setText("<center>¡Crea un álbum con tus mejores fotos!<br>La imagen seleccionada en color rojo es la portada.<br>Haz click sobre las imagenes y serán seleccionadas.</center>");
		editorInfo.setEditable(false);
		
		textField = new JTextField();
		textField.setToolTipText("");
		textField.setText(TITULO);
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 7;
		add(textField, gbc_textField);
		
		Manejadores.addManejadorTextos(textField, TITULO);
		
		txtDescripcion = new JTextArea(3, 20);
		txtDescripcion.setWrapStyleWord(true);
		txtDescripcion.setText(DESCRIPCION);
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
		
		btnContinuar_1 = new JButton("CONTINUAR");
		btnContinuar_1.setForeground(new Color(218, 200, 41));
		btnContinuar_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnContinuar_1.setBorderPainted(false);
		btnContinuar_1.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnContinuar_1 = new GridBagConstraints();
		gbc_btnContinuar_1.fill = GridBagConstraints.VERTICAL;
		gbc_btnContinuar_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnContinuar_1.gridx = 1;
		gbc_btnContinuar_1.gridy = 10;
		add(btnContinuar_1, gbc_btnContinuar_1);
		
		addManejadorBotonContinuar(btnContinuar_1);
		Manejadores.addManejadorBotonColor(btnContinuar_1);
		
	}
	
	private void addManejadorBotonContinuar(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Comprobamos que exista como minimo una portada
				if (checkFields()) {
					Album album = Controlador.getInstancia().añadirAlbum(textField.getText(), txtDescripcion.getText(), panelSeleccionarFotos.getListaSeleccionados(), panelSeleccionarFotos.getPortada());
					//Subimos el album al perfil
					padre.subirPublicacion(album);
					
					//Mostramos el panelPerfil
					padre.setPanelPerfil();					
				}
				
			}
		});
	}
	
	private void crearPanelFotos() {
		panelSeleccionarFotos = new PanelSeleccionarFotos(Controlador.getInstancia().getUsuarioActual().getFotos());
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
	private boolean checkFields() {
		boolean estado = true;
		String info = "";
		
		//Comprobamos si es un correo basico
		if(textField.getText().equals(TITULO)) {
			estado = false;
			info = "¡Debes introducir un título al álbum!";
		}
		
		if (panelSeleccionarFotos.getPortada() == null) {
			info = "¡Debes seleccionar como minimo una portada (resaltada en rojo)!";
			estado = false;
		}
		
		if(!estado) {
			crearMessageDialog(info, "Rellene correctamente los campos", 0);
		}
		
		return estado;
	}
	
}
