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
import modelo.Foto;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class PanelSubir extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanelBackground panelFoto;
	private JButton btnContinuar;
	private boolean subido;
	private JTextField txtTitulo;
	private JScrollPane scrollPane;
	private JTextArea txtDescripcion;
	private String fotoActual;
	private Home padre;

	/**
	 * Create the panel.
	 */
	public PanelSubir(Home home) {
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
		JLabel lblTitulo = new JLabel("Subir foto");
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
		editorInfo.setText("<center>Anímate a compartir una foto con tus amigos.<br> Puedes arrastrar la foto sobre la imagen.<br>También puedes hacer click sobre ella.</center>");
		editorInfo.setEditable(false);
		
		
		panelFoto = new JPanelBackground();
		panelFoto.setBackground(PanelSubir.class.getResource("/imagenes/foto_defecto.png"));
		GridBagConstraints gbc_panelFoto = new GridBagConstraints();
		gbc_panelFoto.insets = new Insets(0, 0, 5, 5);
		gbc_panelFoto.fill = GridBagConstraints.BOTH;
		gbc_panelFoto.gridx = 1;
		gbc_panelFoto.gridy = 5;
		add(panelFoto, gbc_panelFoto);
		GridBagLayout gbl_panelFoto = new GridBagLayout();
		gbl_panelFoto.columnWidths = new int[]{0, 0};
		gbl_panelFoto.rowHeights = new int[]{0, 0, 0};
		gbl_panelFoto.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelFoto.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panelFoto.setLayout(gbl_panelFoto);
		
		JEditorPane subir = new JEditorPane();
		subir.setBackground(new Color(0, 0, 0, 0));
		subir.setEditable(false);
		GridBagConstraints gbc_subir = new GridBagConstraints();
		gbc_subir.insets = new Insets(0, 0, 5, 0);
		gbc_subir.fill = GridBagConstraints.BOTH;
		gbc_subir.gridx = 0;
		gbc_subir.gridy = 0;
		panelFoto.add(subir, gbc_subir);
		subir.setContentType("text/html");
		panelFoto.repaint();
		
		addManejadorBotonInsertarImagen(subir);
		addManejadorDragAndDrop(subir);
	}
	
	private void establecerCamposTexto() {
		txtTitulo = new JTextField();
		txtTitulo.setToolTipText("");
		txtTitulo.setText("Título");
		txtTitulo.setColumns(10);
		GridBagConstraints gbc_txtTitulo = new GridBagConstraints();
		gbc_txtTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_txtTitulo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitulo.gridx = 1;
		gbc_txtTitulo.gridy = 7;
		add(txtTitulo, gbc_txtTitulo);
		
		
		txtDescripcion = new JTextArea(3, 20);
		txtDescripcion.setText("Descripción");
		txtDescripcion.setWrapStyleWord(true);
		txtDescripcion.setLineWrap(true);
		
		scrollPane = new JScrollPane(txtDescripcion);
		scrollPane.setViewportBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 8;
		add(scrollPane, gbc_scrollPane);
		
		Manejadores.addManejadorTextos(txtTitulo, "Título");
		Manejadores.addManejadorArea(txtDescripcion, "Descripción");
	}
	
	public void reiniciarPanel() {
		panelFoto.setBackground(PanelSubir.class.getResource("/imagenes/foto_defecto.png"));
		if(subido == true)
			remove(btnContinuar);
		subido = false;
		fotoActual = null;
		txtDescripcion.setText("Descripción");
		txtTitulo.setText("Título");
	}
	//MALENIA lo usamos 3 4 veces en todo el programa, unificar?
	private void addManejadorDragAndDrop(JEditorPane editor) {
		editor.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					Pattern regexpPng = Pattern.compile(".+\\.png", Pattern.CASE_INSENSITIVE);
					Pattern regexpJpg = Pattern.compile(".+\\.jpg", Pattern.CASE_INSENSITIVE);
					Pattern regexpJpeg = Pattern.compile(".+\\.jpeg", Pattern.CASE_INSENSITIVE);
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>)
					evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						if(!regexpPng.matcher(file.getName()).matches() && !regexpJpg.matcher(file.getName()).matches() && !regexpJpeg.matcher(file.getName()).matches()) {
							crearMessageDialog("¡El fichero debe tener una extensión válida!", "Rellene correctamente los campos", 0);
						} else {
							seleccionarFoto(file.getPath());
						}
					}
				} catch (Exception ex) {}
			}
		});	

	}
	
	/**
	 * Encargado de seleccionar la imagen del sistema con un JFileChooser
	 * MALENIA lo usamos 3 4 veces en todo el programa, unificar?
	 */
	private void addManejadorBotonInsertarImagen(JEditorPane editor) {
		editor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern regexpPng = Pattern.compile(".+\\.png", Pattern.CASE_INSENSITIVE);
				Pattern regexpJpg = Pattern.compile(".+\\.jpg", Pattern.CASE_INSENSITIVE);
				Pattern regexpJpeg = Pattern.compile(".+\\.jpeg", Pattern.CASE_INSENSITIVE);
				
				//Creamos el selector de archivos con su filtro
				JFileChooser selector = new JFileChooser();
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("PNG, JPG, JPEG", "jpg", "png", "jpeg");
				selector.setFileFilter(filtro);
				selector.removeChoosableFileFilter(null);
				
				int resp = selector.showOpenDialog(selector);
				File fichero = selector.getSelectedFile();
				if (fichero != null) {
					//Comprobamos que la extension sea correcta
					if(!regexpPng.matcher(fichero.getName()).matches() && !regexpJpg.matcher(fichero.getName()).matches() && !regexpJpeg.matcher(fichero.getName()).matches()) {
						crearMessageDialog("¡El fichero debe tener una extensión válida!", "Rellene correctamente los campos", 0);
					} else if(resp == JFileChooser.APPROVE_OPTION) { //En caso de ser valida, introducimos la imagen temporalmente
						seleccionarFoto(fichero.getAbsolutePath());
					}
				}
			}
		});
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
			Manejadores.addManejadorBotonColor(btnContinuar);
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
					Foto foto = Controlador.getInstancia().añadirFoto(txtTitulo.getText(), txtDescripcion.getText(), fotoActual);
					//Añadimos la nueva foto al perfil
					padre.subirPublicacion(foto);
					
					//Mostramos el panelPerfil
					padre.setPanelPerfil();
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

}
