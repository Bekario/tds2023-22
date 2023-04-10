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

	/**
	 * Create the panel.
	 */
	public PanelCrearAlbum(Home home) {
		padre = home;
		subido = false;
		fotoActual = null;
		this.setSize(450, 600);
		crearPanel();
		
	}
	private void crearPanel() {
		crearPanelSubir();
		establecerCamposTexto();
	}
	
	private void crearPanelSubir() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 285, 15, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 15, 20, 10, 285, 15, 0, 80, 15, 50, 15, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
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
	
	private void establecerCamposTexto() {
	}
	
	/**
	 * Gestiona las animaciones de los campos de texto
	 * @param texto Campo de texto
	 */
	private void addManejadorTexto(JTextField texto, String cadena) {
	}
	
	/**
	 * Gestiona las animaciones de los campos de text area
	 * @param texto Campo de texto
	 */
	private void addManejadorArea(JTextArea texto, String cadena) {
	}
	
	public void reiniciarPanel() {
		panelFoto.setBackground(PanelCrearAlbum.class.getResource("/imagenes/foto_defecto.png"));
		if(subido == true)
			remove(btnContinuar);
		subido = false;
		fotoActual = null;
		txtDescripcion.setText("Descripción");
		txtTitulo.setText("Título");
	}
	//MALENIA lo usamos 3 4 veces en todo el programa, unificar?
	private void addManejadorDragAndDrop(JEditorPane editor) {

	}
	
	/**
	 * Encargado de seleccionar la imagen del sistema con un JFileChooser
	 * MALENIA lo usamos 3 4 veces en todo el programa, unificar?
	 */
	private void addManejadorBotonInsertarImagen(JEditorPane editor) {
	}
	
	private void crearMessageDialog(String descripcion, String titulo, int icono) {
		JOptionPane.showMessageDialog(this, descripcion, titulo, icono);
	}
	
	private void seleccionarFoto(String ruta) {
		try {
			panelFoto.setBackground(new URL("file:/" + ruta));
			subido = true;
			btnContinuar = new JButton("CONTINUAR");
			btnContinuar.setForeground(new Color(218, 200, 41));
			btnContinuar.setFont(new Font("Segoe UI", Font.BOLD, 14));
			btnContinuar.setBorderPainted(false);
			btnContinuar.setBackground(UIManager.getColor("Button.background"));
			GridBagConstraints gbc_btnContinuar = new GridBagConstraints();
			gbc_btnContinuar.fill = GridBagConstraints.VERTICAL;
			gbc_btnContinuar.insets = new Insets(0, 0, 5, 5);
			gbc_btnContinuar.gridx = 1;
			gbc_btnContinuar.gridy = 10;
			add(btnContinuar, gbc_btnContinuar);
			
			fotoActual = ruta;
			
			addManejadorContinuar(btnContinuar);
			addManejadorBotonColor(btnContinuar);
			this.updateUI();
		} catch (MalformedURLException e) {
			System.err.println("La ruta esta mal formada");
			e.printStackTrace();
		}
	}
	
	private void addManejadorContinuar(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//checkfield
				if (checkFields()) {
					if (Controlador.getInstancia().añadirFoto(txtTitulo.getText(), txtDescripcion.getText(), fotoActual)) {
						padre.setPanelPerfil();
					}
				}
			}
		});
		
		
	}
	
	private boolean checkFields() {
		boolean estado = true;
		
		String info = "";
		
		//Comprobamos si es un correo basico
		if(txtTitulo.getText().equals("Título")) {
			estado = false;
			info = "¡Este título no es válido!";
		}
		
		if(txtDescripcion.getText().equals("Descripción") || txtDescripcion.getText().length() >= 200) {
			estado = false;
			info = "¡Introduce una descripción válida de menos de 200 caracteres!";
		}
		
		if (fotoActual.equals(null)) {
			estado = false;
			info = "¡Debes seleccionar una foto!";
		}
		
		if(!estado) {
			JOptionPane.showMessageDialog(this, info, "Rellene correctamente los campos", 0);
		}
		
		return estado;
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
