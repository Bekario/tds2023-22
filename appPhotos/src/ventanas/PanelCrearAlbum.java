package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;

import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.Controlador;
import modelo.Foto;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import java.awt.Component;

public class PanelCrearAlbum extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnContinuar;
	private boolean subido;
	private String fotoActual;
	private Home padre;
	private PanelSeleccionarFotos panelSeleccionarFotos;
	private JTextField textField;
	private JTextArea txtDescripcion;

	/**
	 * Create the panel.
	 */
	public PanelCrearAlbum(Home home) {
		padre = home;
		subido = false;
		fotoActual = null;
		this.setSize(450, 800);
		crearPanel();
		
	}
	private void crearPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 285, 15, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 15, 20, 15, 450, 15, 0, 0, 15, 50, 15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		editorInfo.setText("<center>¡Crea un álbum con tus mejores fotos!<br>La primera imagen será la portada.<br>Haz click sobre las imagenes y serán seleccionadas.</center>");
		editorInfo.setEditable(false);
		
		textField = new JTextField();
		textField.setToolTipText("");
		textField.setText("Título");
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 7;
		add(textField, gbc_textField);
		
		addManejadorTexto(textField, "Título");
		
		txtDescripcion = new JTextArea(3, 20);
		txtDescripcion.setWrapStyleWord(true);
		txtDescripcion.setText("Descripción");
		txtDescripcion.setLineWrap(true);
		
		addManejadorArea(txtDescripcion, "Descripción");

		JScrollPane scrollPane = new JScrollPane(txtDescripcion);
		scrollPane.setViewportBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 8;
		add(scrollPane, gbc_scrollPane);
		
		JButton btnContinuar_1 = new JButton("CONTINUAR");
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
		
	}
	
	private void addManejadorBotonContinuar(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Foto> fotos = new ArrayList<Foto>();
				Controlador.getInstancia().añadirAlbum(textField.getText(), txtDescripcion.getText(), fotos);
			}
		});
	}
	
	private void crearPanelFotos() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 5;
		add(scrollPane, gbc_scrollPane);
		
		panelSeleccionarFotos = new PanelSeleccionarFotos(Controlador.getInstancia().getUsuarioActual().getFotos());
		GridBagLayout gridBagLayout = (GridBagLayout) panelSeleccionarFotos.getLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		scrollPane.setViewportView(panelSeleccionarFotos);	
	}
	
	
	
	private void crearMessageDialog(String descripcion, String titulo, int icono) {
		JOptionPane.showMessageDialog(this, descripcion, titulo, icono);
	}
	
	/**MALENIA SE USA TOO MUCH
	 * Gestiona los cambios de color al pasar el raton encima de un boton
	 * @param boton Boton que se desea que aplique el efecto
	 */
	private void addManejadorBotonColor(JButton boton) {
		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBackground(new Color(218,200,41));
				boton.setForeground(new Color(78,80,82));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(new Color(78,80,82));
				boton.setForeground(new Color(218,200,41));
			}
		});
	}
	
	/**
	 * Gestiona las animaciones de los campos de texto
	 * @param texto Campo de texto
	 */
	private void addManejadorTexto(JTextField texto, String cadena) {
		texto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(texto.getText().equals(cadena)) {
					texto.setText("");
				}
				else {
					texto.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(texto.getText().equals(""))
					texto.setText(cadena);
			}
		});
	}
	
	/**
	 * Gestiona las animaciones de los campos de text area
	 * @param texto Campo de texto
	 */
	private void addManejadorArea(JTextArea texto, String cadena) {
		texto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(texto.getText().equals(cadena)) {
					texto.setText("");
				}
				else {
					texto.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(texto.getText().equals(""))
					texto.setText(cadena);
			}
		});
	}
}
