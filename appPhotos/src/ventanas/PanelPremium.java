package ventanas;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import modelo.Variables;

import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.UIManager;

import controlador.Controlador;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;


public class PanelPremium extends JPanel {

	private static final long serialVersionUID = 1L;
	private Home home;
	private float precio;
	

	/**
	 * Create the panel.
	 */
	public PanelPremium(Home home) {
		precio = Controlador.getInstancia().obtenerPrecioPremium();
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
		
		JEditorPane txtSoloPor = new JEditorPane();
		txtSoloPor.setEditable(false);
		txtSoloPor.setBackground(null);
		txtSoloPor.setContentType("text/html");
		DecimalFormat df = new DecimalFormat("#.##");
		if(precio != Variables.precioPremium) {
			txtSoloPor.setText("<p>¡SOLO POR <strike>"+df.format(Variables.precioPremium)+"</strike> "+df.format(precio)+"€!</p>");
		} else {
			txtSoloPor.setText("¡SOLO POR "+String.valueOf(precio)+"€!");
		}
		txtSoloPor.setForeground(Colores.NARANJA);
		txtSoloPor.setFont(new Font("Segoe UI", Font.BOLD, 19));
		GridBagConstraints gbc_txtSoloPor = new GridBagConstraints();
		gbc_txtSoloPor.insets = new Insets(0, 0, 5, 5);
		gbc_txtSoloPor.gridx = 1;
		gbc_txtSoloPor.gridy = 6;
		add(txtSoloPor, gbc_txtSoloPor);
		
		JButton btnPagar = new JButton("PAGAR");
		btnPagar.setForeground(Colores.NARANJA);
		btnPagar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnPagar.setBorderPainted(false);
		btnPagar.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_btnPagar = new GridBagConstraints();
		gbc_btnPagar.fill = GridBagConstraints.VERTICAL;
		gbc_btnPagar.insets = new Insets(0, 0, 5, 5);
		gbc_btnPagar.gridx = 1;
		gbc_btnPagar.gridy = 8;
		add(btnPagar, gbc_btnPagar);
		
		Manejadores.addManejadorBotonColor(btnPagar);
		
		if(!Controlador.getInstancia().comprobarPremium()) {
			addManejadorBotonPagar(btnPagar, precio);
			
		}else btnPagar.setText("PAGADO");
	}
	
	private void addManejadorBotonPagar(JButton boton, float precio) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				home.setPanel(new PanelTarjeta(home, precio));
			}
		});
	}

}
