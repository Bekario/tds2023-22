package ventanas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JPanel;

import modelo.Foto;
import modelo.Publicacion;
import modelo.Usuario;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PanelListaUsuarios extends JPanel {
	private Home ventana;
	private int y;
	
	/**
	 * Create the panel.
	 */
	public PanelListaUsuarios(Home ventana) {
		this.ventana = ventana;
		this.setSize(450, 490);
		y=0;
		
		crearPanel();
		addUsuario(new Usuario("adrian_cabron", "", "", "", LocalDate.now(), "C:\\Users\\anton\\Desktop\\ParticipantImageServlet2.jpeg", " "));
		addUsuario(new Usuario("adrian", "", "", "", LocalDate.now(), "C:\\Users\\anton\\Desktop\\a.jpg", " "));
	}
	
	private void crearPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		/*PanelUsuario panelUsuario = new PanelUsuario(new Usuario("adrian", "", "", "", LocalDate.now(), "C:\\Users\\anton\\Desktop\\a.jpg", " "));
		GridBagConstraints gbc_panelUsuario = new GridBagConstraints();
		gbc_panelUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_panelUsuario.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario.gridx = 0;
		gbc_panelUsuario.gridy = 0;
		add(panelUsuario, gbc_panelUsuario);
		
		PanelUsuario panelUsuario_1 = new PanelUsuario(new Usuario("adrian", "", "", "", LocalDate.now(), "C:\\Users\\anton\\Desktop\\a.jpg", " "));
		GridBagConstraints gbc_panelUsuario_1 = new GridBagConstraints();
		gbc_panelUsuario_1.insets = new Insets(0, 0, 5, 0);
		gbc_panelUsuario_1.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario_1.gridx = 0;
		gbc_panelUsuario_1.gridy = 1;
		add(panelUsuario_1, gbc_panelUsuario_1);

		PanelUsuario panelUsuario_2 = new PanelUsuario(new Usuario("adrian", "", "", "", LocalDate.now(), "C:\\Users\\anton\\Desktop\\a.jpg", " "));
		GridBagConstraints gbc_panelUsuario_2 = new GridBagConstraints();
		gbc_panelUsuario_1.insets = new Insets(0, 0, 5, 0);
		gbc_panelUsuario_2.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario_2.gridx = 0;
		gbc_panelUsuario_2.gridy = 2;
		add(panelUsuario_2, gbc_panelUsuario_2);*/
		
		ImageIcon imagen = new ImageIcon(Register2.class.getResource("/imagenes/noPublicaciones.png"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH));
	}
	
	public void addUsuario(Usuario usuario) {
		PanelUsuario panelUsuario = new PanelUsuario(usuario);
		GridBagConstraints gbc_panelUsuario = new GridBagConstraints();
		gbc_panelUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_panelUsuario.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario.gridx = 0;
		gbc_panelUsuario.gridy = y;
		y+=1;
		add(panelUsuario, gbc_panelUsuario);
		
	}
	
	
	

}
