package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;

import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
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

public class PanelSubir extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanelBackground panelBackground;
	private JButton btnContinuar;
	private boolean subido;

	/**
	 * Create the panel.
	 */
	public PanelSubir() {
		subido = false;
		this.setSize(450, 600);
		crearPanel();
		
	}
	private void crearPanel() {
		crearPanelSubir();
	}
	
	private void crearPanelSubir() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 300, 15, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 15, 20, 15, 256, 15, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		GridBagConstraints gbc_editorInfo = new GridBagConstraints();
		gbc_editorInfo.insets = new Insets(0, 0, 5, 5);
		gbc_editorInfo.fill = GridBagConstraints.BOTH;
		gbc_editorInfo.gridx = 1;
		gbc_editorInfo.gridy = 3;
		add(editorInfo, gbc_editorInfo);
		
		editorInfo.setContentType("text/html");
		editorInfo.setText("<center>Anímate a compartir una foto con tus amigos.<br> Puedes arrastrar la foto sobre la imagen o hacerle click.</center>");
		editorInfo.setEditable(false);
		
		
		panelBackground = new JPanelBackground();
		panelBackground.setBackground(PanelSubir.class.getResource("/imagenes/foto_defecto.png"));
		GridBagConstraints gbc_panelBackground = new GridBagConstraints();
		gbc_panelBackground.insets = new Insets(0, 0, 5, 5);
		gbc_panelBackground.fill = GridBagConstraints.BOTH;
		gbc_panelBackground.gridx = 1;
		gbc_panelBackground.gridy = 5;
		add(panelBackground, gbc_panelBackground);
		GridBagLayout gbl_panelBackground = new GridBagLayout();
		gbl_panelBackground.columnWidths = new int[]{0, 0};
		gbl_panelBackground.rowHeights = new int[]{100, 0, 0};
		gbl_panelBackground.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelBackground.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panelBackground.setLayout(gbl_panelBackground);
		
		JEditorPane subir = new JEditorPane();
		subir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		subir.setBackground(new Color(0, 0, 0, 0));
		subir.setEditable(false);
		GridBagConstraints gbc_subir = new GridBagConstraints();
		gbc_subir.insets = new Insets(0, 0, 5, 0);
		gbc_subir.fill = GridBagConstraints.BOTH;
		gbc_subir.gridx = 0;
		gbc_subir.gridy = 0;
		panelBackground.add(subir, gbc_subir);
		subir.setContentType("text/html");
		panelBackground.repaint();
		
		addManejadorBotonInsertarImagen(subir);
		addManejadorDragAndDrop(subir);
	}
	
	public void reiniciarPanel() {
		panelBackground.setBackground(PanelSubir.class.getResource("/imagenes/foto_defecto.png"));
		if(subido == true)
			remove(btnContinuar);
		subido = false;
	}
	
	private void addManejadorDragAndDrop(JEditorPane editor) {
		editor.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					Pattern regexpPng = Pattern.compile(".+\\.png");
					Pattern regexpJpg = Pattern.compile(".+\\.jpg");
					Pattern regexpJpeg = Pattern.compile(".+\\.jpeg");
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
	 */
	private void addManejadorBotonInsertarImagen(JEditorPane editor) {
		editor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern regexpPng = Pattern.compile(".+\\.png");
				Pattern regexpJpg = Pattern.compile(".+\\.jpg");
				Pattern regexpJpeg = Pattern.compile(".+\\.jpeg");
				
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
			panelBackground.setBackground(new URL("file:/" + ruta));
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
			gbc_btnContinuar.gridy = 7;
			add(btnContinuar, gbc_btnContinuar);
			
			addManejadorBotonColor(btnContinuar);
			this.updateUI();
		} catch (MalformedURLException e) {
			System.err.println("La ruta esta mal formada");
			e.printStackTrace();
		}
	}
	
	/**
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
