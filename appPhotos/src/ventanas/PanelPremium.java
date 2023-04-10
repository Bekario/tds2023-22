package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import modelo.Usuario;
import modelo.Variables;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.UIManager;

import controlador.Controlador;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class PanelPremium extends JPanel {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Home home;
	private float precio = Variables.precioPremium;
	

	/**
	 * Create the panel.
	 */
	public PanelPremium(Home home, Usuario usuario) {
		this.usuario=usuario;
		this.home = home;
		crearPanel();
		
	}
	public Home getHome() {
		return home;
	}
	
	private void crearPanel() {
		this.setSize(450, 600);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 0, 15, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 15, 15, 15, 0, 15, 15, 50, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblPremium = new JLabel("PREMIUM");
		lblPremium.setFont(new Font("Segoe UI", Font.BOLD, 19));
		GridBagConstraints gbc_lblPremium = new GridBagConstraints();
		gbc_lblPremium.insets = new Insets(0, 0, 5, 5);
		gbc_lblPremium.gridx = 1;
		gbc_lblPremium.gridy = 1;
		add(lblPremium, gbc_lblPremium);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(PanelPremium.class.getResource("/imagenes/premium.png")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 3;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JEditorPane dtrpnquieresConocerTus = new JEditorPane();
		dtrpnquieresConocerTus.setEditable(false);
		dtrpnquieresConocerTus.setContentType("text/html");
		dtrpnquieresConocerTus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		dtrpnquieresConocerTus.setText("<center>¿Quieres conocer tus mejores publicaciones?<br>¿Te interesa conocer al detalle todos tus seguidores?<br>Consigue Premium y podras disfrutar de todas estas ventajas:<br></center><ul>\r\n  <li>Exporta en formato PDF tu lista de seguidores.</li>\r\n  <li>Exporta en formato Excel tu lista de seguidores.</li>\r\n  <li>Visualiza tus 10 fotos que más me gustas han recibido.</li>\r\n</ul>");
		
		GridBagConstraints gbc_dtrpnquieresConocerTus = new GridBagConstraints();
		gbc_dtrpnquieresConocerTus.insets = new Insets(0, 0, 5, 5);
		gbc_dtrpnquieresConocerTus.fill = GridBagConstraints.BOTH;
		gbc_dtrpnquieresConocerTus.gridx = 1;
		gbc_dtrpnquieresConocerTus.gridy = 5;
		add(dtrpnquieresConocerTus, gbc_dtrpnquieresConocerTus);
		
		JLabel lblSoloPor = new JLabel("¡SOLO POR "+String.valueOf(precio)+"€!");
		lblSoloPor.setForeground(new Color(218, 200, 41));
		lblSoloPor.setFont(new Font("Segoe UI", Font.BOLD, 19));
		GridBagConstraints gbc_lblSoloPor = new GridBagConstraints();
		gbc_lblSoloPor.insets = new Insets(0, 0, 5, 5);
		gbc_lblSoloPor.gridx = 1;
		gbc_lblSoloPor.gridy = 6;
		add(lblSoloPor, gbc_lblSoloPor);
		
		JButton btnPagar = new JButton("PAGAR");
		btnPagar.setForeground(new Color(218, 200, 41));
		btnPagar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnPagar.setBorderPainted(false);
		btnPagar.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnPagar = new GridBagConstraints();
		gbc_btnPagar.fill = GridBagConstraints.VERTICAL;
		gbc_btnPagar.insets = new Insets(0, 0, 5, 5);
		gbc_btnPagar.gridx = 1;
		gbc_btnPagar.gridy = 8;
		add(btnPagar, gbc_btnPagar);
		
		addManejadorBotonColor(btnPagar);
		if(!Controlador.getInstancia().getUsuarioActual().getIsPremium()) {
			addManejadorBotonPagar(btnPagar);
			
		}else btnPagar.setText("PAGADO");
	}
	
	private void addManejadorBotonPagar(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				home.setPanel(new PanelTarjeta(home, precio));
			}
		});
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
