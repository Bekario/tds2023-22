package persistencia;

import java.time.LocalDate;
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
						new Propiedad(FECHA, usuario.getFechaNacimiento().toString()),
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
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
<<<<<<< HEAD
		Entidad eUsuario;
		
		String usuario;
		String contraseña;
		String email;
		String nombreCompleto;
		LocalDate fechaNacimiento;
		boolean premium;
		String perfil;
		String descripcion;
		List<Publicacion> publicaciones = new ArrayList<Publicacion>();
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		List<Usuario> seguidores = new ArrayList<Usuario>();
=======
		Entidad eCliente;
		List<Venta> ventas = new LinkedList<Venta>();
		String dni;
		String nombre;
		
>>>>>>> refs/remotes/origin/main

		// recuperar entidad
		eUsuario = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		usuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, USUARIO);
		contraseña = servPersistencia.recuperarPropiedadEntidad(eUsuario, CONTRASEÑA);
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, EMAIL);
		nombreCompleto = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_COMPLETO);
		premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM));
		perfil = servPersistencia.recuperarPropiedadEntidad(eUsuario, PERFIL);
		descripcion = servPersistencia.recuperarPropiedadEntidad(eUsuario, DESCRIPCION);

		Usuario user = new Usuario(usuario, contraseña, email, nombreCompleto, fechaNacimiento, perfil, descripcion);
		user.setCodigo(codigo);
		user.setPremium(premium);

		// IMPORTANTE:añadir el cliente al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, user);

		// recuperar propiedades que son objetos llamando a adaptadores
		// publicaciones
		ventas = obtenerVentasDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eCliente, "ventas"));

		for (Venta v : ventas)
			cliente.addVenta(v);
		
		// recuperar propiedades que son objetos llamando a adaptadores
		// seguidores
		seguidores = obtenerSeguidores(servPersistencia.recuperarPropiedadEntidad(eUsuario, SEGUIDORES));
		
		// recuperar propiedades que son objetos llamando a adaptadores
		// notificaciones
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

	private List<String> obtenerSeguidores(String seguidores) {
		List<String> listaSeguidores = new ArrayList<String>();
		StringTokenizer strTok = new StringTokenizer(seguidores, " ");
		while (strTok.hasMoreTokens()) {
			listaSeguidores.add((String) strTok.nextElement());
		}
		return listaSeguidores;
	}
}
