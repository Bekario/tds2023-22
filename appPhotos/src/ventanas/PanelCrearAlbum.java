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
import java.util.List;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.Controlador;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class PanelCrearAlbum extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnContinuar;
	private boolean subido;
	private String fotoActual;
	private Home padre;
	private PanelCuadriculaFotos panelCuadriculaFotos;

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
		gridBagLayout.rowHeights = new int[]{0, 0, 15, 20, 15, 450, 15, 50, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		panelCuadriculaFotos = new PanelCuadriculaFotos();
		GridBagLayout gridBagLayout = (GridBagLayout) panelCuadriculaFotos.getLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		scrollPane.setViewportView(panelCuadriculaFotos);
		
		JButton btnContinuar_1 = new JButton("CONTINUAR");
		btnContinuar_1.setForeground(new Color(218, 200, 41));
		btnContinuar_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnContinuar_1.setBorderPainted(false);
		btnContinuar_1.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnContinuar_1 = new GridBagConstraints();
		gbc_btnContinuar_1.fill = GridBagConstraints.VERTICAL;
		gbc_btnContinuar_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnContinuar_1.gridx = 1;
		gbc_btnContinuar_1.gridy = 7;
		add(btnContinuar_1, gbc_btnContinuar_1);
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
}
