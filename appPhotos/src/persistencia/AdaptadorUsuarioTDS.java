package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.Usuario;

//Usa un pool para evitar problemas doble referencia con ventas
public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;
	
	private final String USUARIO= "usuario";
	private final String CONTRASEÑA= "contraseña";
	private final String EMAIL= "email";
	private final String NOMBRE_COMPLETO= "nombrecompleto";
	private final String FECHA= "fecha";
	private final String PREMIUM= "premium";
	private final String PERFIL= "perfil";
	private final String DESCRIPCION= "descripcion";
	private final String SEGUIDORES= "seguidores";
	private final String NOTIFICACIONES= "notificaciones";
	private final String FOTOS= "fotos";

	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un cliente se le asigna un identificador �nico */
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario = null;

		// Si la entidad esta registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {}
		if (eUsuario != null) return;

		// registrar primero los atributos notificacion
		AdaptadorNotificacionTDS adaptadorNotificacion = AdaptadorNotificacionTDS.getUnicaInstancia();
		for (Notificacion v : usuario.getNotificaciones())
			adaptadorNotificacion.registrarNotificacion(v);
		
		// registrar primero los atributos publicacion
		AdaptadorPublicacionTDS adaptadorPublicacion = AdaptadorPublicacionTDS.getUnicaInstancia();
		for (Publicacion v : usuario.getFotos())
			adaptadorPublicacion.registrarPublicacion(v);

		// crear entidad Usuario
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(USUARIO, usuario.getUsuario()),
						new Propiedad(CONTRASEÑA, usuario.getContraseña()),
						new Propiedad(EMAIL, usuario.getEmail()),
						new Propiedad(NOMBRE_COMPLETO, usuario.getNombreCompleto()),
						//new Propiedad(FECHA,),
						new Propiedad(PREMIUM, String.valueOf(usuario.getIsPremium())),
						new Propiedad(DESCRIPCION, usuario.getDescripcion()),
						new Propiedad(PERFIL, usuario.getPerfil()),
						new Propiedad(DESCRIPCION, usuario.getDescripcion()),
						new Propiedad(NOTIFICACIONES, ),
						new Propiedad(SEGUIDORES, ),
						new Propiedad(FOTOS, )
						)));

		// registrar entidad cliente
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId());
	}

	public void borrarUsuario(Usuario usuario) {
		// No se comprueban restricciones de integridad con Venta
		Entidad eCliente = servPersistencia.recuperarEntidad(usuario.getCodigo());

		servPersistencia.borrarEntidad(eCliente);
	}

	public void modificarUsuario(Usuario usuario) {

		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(usuario.getCodigo()));
			} else if (prop.getNombre().equals(USUARIO)) {
				prop.setValor(usuario.getUsuario());
			} else if (prop.getNombre().equals(CONTRASEÑA)) {
				prop.setValor(usuario.getContraseña());
			} else if (prop.getNombre().equals(EMAIL)) {
				prop.setValor(usuario.getEmail());
			} else if (prop.getNombre().equals(NOMBRE_COMPLETO)) {
				prop.setValor(usuario.getNombreCompleto());
			} else if (prop.getNombre().equals(FECHA)) {
				prop.setValor(usuario.getFechaNacimiento().toString());
			} else if (prop.getNombre().equals(PREMIUM)) {
				prop.setValor(String.valueOf(usuario.getIsPremium()));
			} else if (prop.getNombre().equals(PERFIL)) {
				prop.setValor(usuario.getPerfil());
			} else if (prop.getNombre().equals(DESCRIPCION)) {
				prop.setValor(usuario.getDescripcion());
			} else if (prop.getNombre().equals(SEGUIDORES)) {
				//prop.setValor(usuario.getEmail());
			} else if (prop.getNombre().equals(NOTIFICACIONES)) {
				//prop.setValor(usuario.getEmail());
			} else if (prop.getNombre().equals(FOTOS)) {
				//prop.setValor(usuario.getEmail());
			}
			servPersistencia.modificarPropiedad(prop);
		}

	}

	public Usuario recuperarUsuario(int codigo) {

		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Cliente) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		Entidad eCliente;
		List<Venta> ventas = new LinkedList<Venta>();
		String dni;
		String nombre;

		// recuperar entidad
		eCliente = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		dni = servPersistencia.recuperarPropiedadEntidad(eCliente, "dni");
		nombre = servPersistencia.recuperarPropiedadEntidad(eCliente, "nombre");

		Cliente cliente = new Cliente(dni, nombre);
		cliente.setCodigo(codigo);

		// IMPORTANTE:a�adir el cliente al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, cliente);

		// recuperar propiedades que son objetos llamando a adaptadores
		// ventas
		ventas = obtenerVentasDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eCliente, "ventas"));

		for (Venta v : ventas)
			cliente.addVenta(v);

		return cliente;
	}

	public List<Usuario> recuperarTodosUsuarios() {

		List<Entidad> eClientes = servPersistencia.recuperarEntidades("cliente");
		List<Cliente> clientes = new LinkedList<Cliente>();

		for (Entidad eCliente : eClientes) {
			clientes.add(recuperarCliente(eCliente.getId()));
		}
		return clientes;
	}

	// -------------------Funciones auxiliares-----------------------------
	private String obtenerCodigosVentas(List<Venta> listaVentas) {
		String aux = "";
		for (Venta v : listaVentas) {
			aux += v.getCodigo() + " ";
		}
		return aux.trim();
	}

	private List<Venta> obtenerVentasDesdeCodigos(String ventas) {

		List<Venta> listaVentas = new LinkedList<Venta>();
		StringTokenizer strTok = new StringTokenizer(ventas, " ");
		AdaptadorVentaTDS adaptadorV = AdaptadorVentaTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaVentas.add(adaptadorV.recuperarVenta(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaVentas;
	}
}
