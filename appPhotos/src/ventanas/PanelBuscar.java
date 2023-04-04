package ventanas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;

import controlador.Controlador;
import modelo.Usuario;

import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelBuscar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtBarraBusqueda;
	private PanelListaUsuarios panelListaUsuarios;
	private JButton btnBuscar;


	/**
	 * Create the panel.
	 */
	public PanelBuscar() {
		this.setSize(450, 600);
		crearPanel();
		
	}
	private void crearPanel() {
		crearPanelBuscar();
		crearPanelLista();
	}
	
	private void crearPanelBuscar() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{5, 0, 40, 5, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 40, 10, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		JLabel lblTexto = new JLabel("Búsqueda");
		lblTexto.setFont(new Font("Segoe UI", Font.BOLD, 19));
		GridBagConstraints gbc_lblTexto = new GridBagConstraints();
		gbc_lblTexto.gridwidth = 2;
		gbc_lblTexto.insets = new Insets(0, 0, 5, 5);
		gbc_lblTexto.anchor = GridBagConstraints.SOUTH;
		gbc_lblTexto.gridx = 1;
		gbc_lblTexto.gridy = 1;
		add(lblTexto, gbc_lblTexto);
		
		txtBarraBusqueda = new JTextField();
		txtBarraBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					panelListaUsuarios.quitarUsuarios();
					List<Usuario> lista = Controlador.getInstancia().obtenerUsuariosBuscados(txtBarraBusqueda.getText());
					panelListaUsuarios.addListaUsuario(lista);
					panelListaUsuarios.comprobarSeguidos(Controlador.getInstancia().getUsuarioActual().getUsuario(), Controlador.getInstancia().getUsuarioActual().getUsuariosSeguidosNombre());
					panelListaUsuarios.updateUI();
				}
			}
		});
		GridBagConstraints gbc_txtBarraBusqueda = new GridBagConstraints();
		gbc_txtBarraBusqueda.insets = new Insets(0, 0, 5, 5);
		gbc_txtBarraBusqueda.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBarraBusqueda.gridx = 1;
		gbc_txtBarraBusqueda.gridy = 2;
		add(txtBarraBusqueda, gbc_txtBarraBusqueda);
		txtBarraBusqueda.setColumns(10);
		
		btnBuscar = new JButton("");
		btnBuscar.setFocusable(false);

		btnBuscar.setBackground(new Color(45, 42, 46));
		btnBuscar.setIcon(new ImageIcon(PanelBuscar.class.getResource("/imagenes/buscar.png")));
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscar.gridx = 2;
		gbc_btnBuscar.gridy = 2;
		add(btnBuscar, gbc_btnBuscar);
		btnBuscar.setBorder(null);
		
		//Funcionalidad opcional
		//addManejadorBuscar2(txtBarraBusqueda);
		
	}
	
	private void crearPanelLista() {
		panelListaUsuarios = new PanelListaUsuarios();
		GridBagConstraints gbc_panelListaUsuarios = new GridBagConstraints();
		gbc_panelListaUsuarios.gridwidth = 2;
		gbc_panelListaUsuarios.insets = new Insets(0, 0, 0, 5);
		gbc_panelListaUsuarios.fill = GridBagConstraints.BOTH;
		gbc_panelListaUsuarios.gridx = 1;
		gbc_panelListaUsuarios.gridy = 4;
		add(panelListaUsuarios, gbc_panelListaUsuarios);

		addManejadorBuscar(btnBuscar);
		this.repaint();
		panelListaUsuarios.repaint();
	}
	
	public void limpiarPanel() {
		txtBarraBusqueda.setText("");
		panelListaUsuarios.quitarUsuarios();

	}
	
	private void addManejadorBuscar(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelListaUsuarios.quitarUsuarios();
				List<Usuario> lista = Controlador.getInstancia().obtenerUsuariosBuscados(txtBarraBusqueda.getText());
				panelListaUsuarios.addListaUsuario(lista);
				panelListaUsuarios.comprobarSeguidos(Controlador.getInstancia().getUsuarioActual().getUsuario(), Controlador.getInstancia().getUsuarioActual().getUsuariosSeguidosNombre());
				panelListaUsuarios.updateUI();
			}
		});
	}
	
	/*Este funciona cuando escribes en campo de texto
	 * private void addManejadorBuscar2(JTextField txt) {
		txt.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				panelListaUsuarios.quitarUsuarios();
				List<Usuario> lista = Controlador.getInstancia().obtenerUsuariosBuscados(txtBarraBusqueda.getText());
				panelListaUsuarios.addListaUsuario(lista);
				panelListaUsuarios.updateUI();
			}
		});

	}*/
	

}
