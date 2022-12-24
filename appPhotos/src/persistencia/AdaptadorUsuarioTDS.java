package persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.Album;
import modelo.Foto;
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
	private final String ALBUMS= "albums";

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
		
		// registrar los atributos foto
		AdaptadorFotoTDS adaptadorFoto = AdaptadorFotoTDS.getUnicaInstancia();
		for (Foto f : usuario.getFotos())
			adaptadorFoto.registrarFoto(f);
		
		// registrar primero los atributos album
		AdaptadorAlbumTDS adaptadorAlbum = AdaptadorAlbumTDS.getUnicaInstancia();
		for (Album a : usuario.getAlbums())
			adaptadorAlbum.registrarAlbum(a);

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
						new Propiedad(NOTIFICACIONES, obtenerCodigosNotificaciones(usuario.getNotificaciones())),
						new Propiedad(SEGUIDORES, obtenerStringSeguidores(usuario.getUsuariosSeguidores())),
						new Propiedad(FOTOS, obtenerCodigosPublicaciones(usuario.getFotos().stream()
																					    .map(e -> (Publicacion) e)
																					    .collect(Collectors.toList()))),
						new Propiedad(FOTOS, obtenerCodigosPublicaciones(usuario.getAlbums().stream()
																					    .map(e -> (Publicacion) e)
																					    .collect(Collectors.toList())))
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
				String seguidores = obtenerStringSeguidores(usuario.getUsuariosSeguidores());
				prop.setValor(seguidores);
			} else if (prop.getNombre().equals(NOTIFICACIONES)) {
				String notificaciones = obtenerCodigosNotificaciones(usuario.getNotificaciones());
				prop.setValor(notificaciones);
			} else if (prop.getNombre().equals(FOTOS)) {
				String fotos = obtenerCodigosPublicaciones(usuario.getFotos().stream()
					    .map(e -> (Publicacion) e)
					    .collect(Collectors.toList()));
				prop.setValor(fotos);
			} else if (prop.getNombre().equals(ALBUMS)) {
				String albums = obtenerCodigosPublicaciones(usuario.getAlbums().stream()
					    .map(e -> (Publicacion) e)
					    .collect(Collectors.toList()));
				prop.setValor(albums);
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Usuario recuperarUsuario(int codigo) {

		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		Entidad eUsuario;
		
		String usuario;
		String contraseña;
		String email;
		String nombreCompleto;
		LocalDate fechaNacimiento;
		boolean premium;
		String perfil;
		String descripcion;
		List<Foto> fotos = new ArrayList<Foto>();
		List<Album> albums = new ArrayList<Album>();
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		List<String> seguidores = new ArrayList<String>();
		
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
		
		//Obtenemos la fecha de nacimiento
		fechaNacimiento = obtenerFechaDesdeString(servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA));
		
		Usuario user = new Usuario(usuario, contraseña, email, nombreCompleto, fechaNacimiento, perfil, descripcion);
		user.setCodigo(codigo);
		user.setPremium(premium);

		// IMPORTANTE:añadir el cliente al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, user);

		// recuperar propiedades que son objetos llamando a adaptadores
		// fotos
		fotos = //malenia
		
		for (Foto f : fotos)
			user.addFoto(f);
		
		// albums
		albums = //malenia
	
		for (Album a : albums)
			user.addAlbum(a);
		
		// recuperar propiedades que son objetos llamando a adaptadores
		// seguidores
		seguidores = obtenerSeguidores(servPersistencia.recuperarPropiedadEntidad(eUsuario, SEGUIDORES));
		user.setUsuariosSeguidores(seguidores);
		
		// recuperar propiedades que son objetos llamando a adaptadores
		// notificaciones
		notificaciones = obtenerNotificacionesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, NOTIFICACIONES));

		for (Notificacion n : notificaciones)
			user.addNotificacion(n);

		return user;
	}

	public List<Usuario> recuperarTodosUsuarios() {

		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new ArrayList<Usuario>();

		for (Entidad eCliente : eUsuarios) {
			usuarios.add(recuperarUsuario(eCliente.getId()));
		}
		return usuarios;
	}

	// -------------------Funciones auxiliares-----------------------------
	
	/**
	 * Obtiene un String con todos los seguidores
	 * @param listaSeguidores
	 * @return
	 */
	private String obtenerStringSeguidores(List<String> listaSeguidores) {
		String aux = "";
		for (String s : listaSeguidores) {
			aux+=s+" ";
		}
		return aux.trim();
	}
	
	/**
	 * Retorna una lista con los codigos de los seguidores
	 * @param seguidores
	 * @return
	 */
	private List<String> obtenerSeguidores(String seguidores) {
		List<String> listaSeguidores = new ArrayList<String>();
		StringTokenizer strTok = new StringTokenizer(seguidores, " ");
		while (strTok.hasMoreTokens()) {
			listaSeguidores.add((String) strTok.nextElement());
		}
		return listaSeguidores;
	}
	
	/**
	 * Obtiene un string con los codigos de las publicaciones
	 * @param listaVentas
	 * @return
	 */
	private String obtenerCodigosPublicaciones(List<Publicacion> listaPublicaciones) {
		String aux = "";
		for (Publicacion p : listaPublicaciones) {
			aux += p.getCodigo() + " ";
		}
		return aux.trim();
	}
	
	/**
	 * Obtiene la lista de publicaciones a partir de un string de codigos
	 * @param publicaciones
	 * @return
	 */
	private List<Publicacion> obtenerPublicacionesDesdeCodigos(String publicaciones) {

		List<Publicacion> listaPublicaciones = new ArrayList<Publicacion>();
		StringTokenizer strTok = new StringTokenizer(publicaciones, " ");
		AdaptadorPublicacionTDS adaptadorP = AdaptadorPublicacionTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaPublicaciones.add(adaptadorP.recuperarPublicacion(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaPublicaciones;
	}
	
	/**
	 * Obtiene un string con los codigos de las publicaciones
	 * @param listaVentas
	 * @return
	 */
	private String obtenerCodigosNotificaciones(List<Notificacion> listaNotificaciones) {
		String aux = "";
		for (Notificacion n : listaNotificaciones) {
			aux += n.getCodigo() + " ";
		}
		return aux.trim();
	}
	
	/**
	 * Obtiene la lista de publicaciones a partir de un string de codigos
	 * @param publicaciones
	 * @return
	 */
	private List<Notificacion> obtenerNotificacionesDesdeCodigos(String notificaciones) {

		List<Notificacion> listaNotificaciones = new ArrayList<Notificacion>();
		StringTokenizer strTok = new StringTokenizer(notificaciones, " ");
		AdaptadorNotificacionTDS adaptadorN = AdaptadorNotificacionTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaNotificaciones.add(adaptadorN.recuperarNotificacion(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaNotificaciones;
	}
	
	/**
	 * Obtiene la lista de publicaciones a partir de un string de codigos
	 * @param publicaciones
	 * @return
	 */
	private LocalDate obtenerFechaDesdeString(String fechaS) {

		StringTokenizer strTok = new StringTokenizer(fechaS, "/");

		int año = (int) strTok.nextElement();
		int mes = (int) strTok.nextElement();
		int dia = (int) strTok.nextElement();
		return LocalDate.of(año, mes, dia);
	}
}
