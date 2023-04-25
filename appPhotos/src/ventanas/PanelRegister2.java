package ventanas;

import controlador.Controlador;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;


public class PanelRegister2 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JEditorPane descripcion;
	private JButton btnborrar;
	private JButton btnMeterImg;
	private JLabel perfil;
	private JButton btnRegistrarse;
	
	private String fotoActual;
	private final String FOTO_DEFECTO = "/imagenes/perfil_default.png";
	private JButton btnAtras;
	
	private Login padre;
	
	public PanelRegister2(Login padre) {
		this.padre = padre;
		this.setSize(450, 600);
		crearPanel();
	}
	
	public void mostrar() {
		this.updateUI();
	}
	
	private void crearPanel() {
		crearLayout();
		establecerTitulo();
		establecerBotones();
		establecerImagenSubida();
		establecerDescripcion();
	}
	
	private void crearLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 35, 0, 0, 35, 50, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 60, 0, 25, 0, 10, 30, 50, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	
	/**
	 * Establece el titulo de la aplicacion
	 */
	private void establecerTitulo() {
		JLabel nombreApp = new JLabel("appPhotos");
		nombreApp.setForeground(new Color(233, 233, 233));
		nombreApp.setIcon(null);
		nombreApp.setFont(new Font("Serif", Font.BOLD, 40));
		GridBagConstraints gbc_nombreApp = new GridBagConstraints();
		gbc_nombreApp.gridwidth = 2;
		gbc_nombreApp.insets = new Insets(0, 0, 5, 5);
		gbc_nombreApp.gridx = 2;
		gbc_nombreApp.gridy = 1;
		add(nombreApp, gbc_nombreApp);
	}
	
	/**
	 * Establece la imagen que mostrara la imagen seleccionada
	 */
	private void establecerImagenSubida() {
		perfil = new JLabel("");
		ImageIcon imagen = new ImageIcon(PanelRegister2.class.getResource(FOTO_DEFECTO));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
		
		perfil.setIcon(icono);

		GridBagConstraints gbc_perfil = new GridBagConstraints();
		gbc_perfil.insets = new Insets(0, 0, 5, 5);
		gbc_perfil.gridx = 3;
		gbc_perfil.gridy = 3;
		add(perfil, gbc_perfil);
		
		fotoActual = FOTO_DEFECTO;
	}
	
	/**
	 * Establece el campo de la descripcion
	 */
	private void establecerDescripcion() {
		scrollPane = new JScrollPane();
		scrollPane.setToolTipText("Introduce la descripción que será mostrada en tu perfil");
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 5;
		add(scrollPane, gbc_scrollPane);
		
		descripcion = new JEditorPane();
		descripcion.setText("Introduce una breve descripción sobre ti...");
		scrollPane.setViewportView(descripcion);
		
		Manejadores.addManejadorDescripcion(descripcion, "Introduce una breve descripción sobre ti...");
	}
	

	
	/**
	 * Establece el boton de insertar imagen, borrar texto y registrarse
	 */
	private void establecerBotones() {
		//Boton imagen
		btnMeterImg = new JButton("Añadir Imagen");
		btnMeterImg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		GridBagConstraints gbc_btnMeterImg = new GridBagConstraints();
		gbc_btnMeterImg.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMeterImg.insets = new Insets(0, 0, 5, 5);
		gbc_btnMeterImg.gridx = 2;
		gbc_btnMeterImg.gridy = 3;
		add(btnMeterImg, gbc_btnMeterImg);
		
		//Boton borrar texto
		btnborrar = new JButton("Borrar Texto");
		
		btnborrar.setToolTipText("Elimina todo el texto contenido en el campo de la descripción");
		GridBagConstraints gbc_btnborrar = new GridBagConstraints();
		gbc_btnborrar.anchor = GridBagConstraints.EAST;
		gbc_btnborrar.insets = new Insets(0, 0, 5, 5);
		gbc_btnborrar.gridx = 3;
		gbc_btnborrar.gridy = 6;
		add(btnborrar, gbc_btnborrar);
		
		//Boton registrarse
		btnRegistrarse = new JButton("REGISTRARSE");
		addManejadorRegistrarse(btnRegistrarse);
		btnRegistrarse.setBorderPainted(false);
		btnRegistrarse.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnRegistrarse.setBackground(new Color(78,80,82));
		btnRegistrarse.setForeground(Colores.NARANJA);
		GridBagConstraints gbc_btnRegistrarse = new GridBagConstraints();
		gbc_btnRegistrarse.fill = GridBagConstraints.VERTICAL;
		gbc_btnRegistrarse.gridwidth = 2;
		gbc_btnRegistrarse.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrarse.gridx = 2;
		gbc_btnRegistrarse.gridy = 8;
		add(btnRegistrarse, gbc_btnRegistrarse);
		
		//Boton atras
		btnAtras = new JButton("      ATRÁS      ");
		btnAtras.setForeground(Colores.NARANJA);
		btnAtras.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnAtras.setBorderPainted(false);
		btnAtras.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnAtras = new GridBagConstraints();
		gbc_btnAtras.fill = GridBagConstraints.VERTICAL;
		gbc_btnAtras.gridwidth = 2;
		gbc_btnAtras.insets = new Insets(0, 0, 5, 5);
		gbc_btnAtras.gridx = 2;
		gbc_btnAtras.gridy = 9;
		add(btnAtras, gbc_btnAtras);
		
		Manejadores.addManejadorBotonColor(btnRegistrarse);
		Manejadores.addManejadorBotonColor(btnAtras);
		
		addManejadorBotonBorrar(btnborrar);
		addManejadorBotonInsertarImagen(btnMeterImg);
		addManejadorBotonAtras(btnAtras);
	}
	
	/**
	 * Controla el registro del usuario
	 * @param boton
	 */
	private void addManejadorRegistrarse(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Si todos los campos son correctos
				if(checkFields()) {
					if (padre.registrarUsuario()) {
						padre.setPanelLogin();
						//Subimos la foto del perfil a la carpeta fotosSubidas
						if(!fotoActual.equals(FOTO_DEFECTO)) {
							Controlador.getInstancia().subirFotoPerfil(fotoActual);																							
						}else {
							Controlador.getInstancia().subirFotoPerfilDefault();
						}
					} else {
						JOptionPane.showMessageDialog(padre.getFrame(), "Este usuario ya está registrado", "Usuario ya registrado", 0);
					}
				}
			}
		});

	}
	
	/**
	 * Encargado de seleccionar la imagen del sistema con un JFileChooser 
	 * MALENIA lo usamos 3 4 veces en todo el programa, unificar?
	 */
	private void addManejadorBotonInsertarImagen(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
						JOptionPane.showMessageDialog(padre.getFrame(), "¡El fichero debe tener una extensión válida!", "Rellene correctamente los campos", 0);
					} else if(resp == JFileChooser.APPROVE_OPTION) { //En caso de ser valida, introducimos la imagen temporalmente
						fotoActual = fichero.getAbsolutePath();
						
						ImageIcon imagen = new ImageIcon(FotoPersonalizada.redondearFoto(fotoActual));
						Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
						perfil.setIcon(icono);
					}
				}
			}
		});
	}
	
	/**
	 * Encargado de borrar el texto del campo descripcion
	 * @param boton Boton que provoca la accion
	 */
	private void addManejadorBotonBorrar(JButton boton) {
		//En caso de hacer click en el boton
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Borramos el texto de la descripcion
				descripcion.setText("");
				//Lo ponemos en focus para que el usuario no tenga que hacer click para empezar a escribir
				descripcion.grabFocus();
			}
		});
	}
	
	/**
	 * Controlamos el evento de volver a la ventana anterior
	 * @param boton
	 */
	private void addManejadorBotonAtras(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Rellenamos los campos con los datos anteriores
				padre.setPanelRegisterRellenado();
			}
		});
		
	}
	
	/**
	 * Comprueba que todos los campos tengan un dato valido
	 * @return booleano indicando si estan correctos los campos
	 */
	private boolean checkFields() {
		boolean estado = true;
		
		String info = "";
		
		//Comprobamos si hay una descripcion
		if(descripcion.getText().equals("Introduce una breve descripción sobre ti...")) {
			descripcion.setText("");
		}
		if(descripcion.getText().length()>200) {
			estado = false;
			info = "¡Sabemos que eres muy interesante, pero el máximo de carácteres son 200 :( !";
		}
		
		//Si falta un campo, informamos
		if(!estado) {
			JOptionPane.showMessageDialog(padre.getFrame(), info, "Rellene correctamente los campos", 0);
		}
		
		return estado;
	}
	
	public String getDescripcion() {
		return descripcion.getText();
	}
	
	public String getPerfil() {
		return fotoActual;
	}

}
