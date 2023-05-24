package persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
	private final String SEGUIDOS= "seguidos";
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

	/* cuando se registra un cliente se le asigna un identificador unico */
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
		AdaptadorPublicacionTDS adaptadorPublicacion = AdaptadorPublicacionTDS.getUnicaInstancia();
		for (Foto f : usuario.getFotos())
			adaptadorPublicacion.registrarPublicacion(f);
		
		// registrar los atributos album
		for (Album a : usuario.getAlbums())
			adaptadorPublicacion.registrarPublicacion(a);

		// crear entidad Usuario
		eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(USUARIO, usuario.getUsuario()),
						new Propiedad(CONTRASEÑA, usuario.getContraseña()),
						new Propiedad(EMAIL, usuario.getEmail()),
						new Propiedad(NOMBRE_COMPLETO, usuario.getNombreCompleto()),
						new Propiedad(FECHA, usuario.getFechaNacimiento().toString()),
						new Propiedad(PREMIUM, String.valueOf(usuario.getIsPremium())),
						new Propiedad(DESCRIPCION, usuario.getDescripcion()),
						new Propiedad(PERFIL, usuario.getPerfil()),
						new Propiedad(NOTIFICACIONES, obtenerCodigosNotificaciones(usuario.getNotificaciones())),
						new Propiedad(SEGUIDORES, obtenerStringSeguidores(usuario.getUsuariosSeguidores())),
						new Propiedad(SEGUIDOS, obtenerStringSeguidores(usuario.getUsuariosSeguidos())),
						//Para fotos y albums utilizamos la funcion para obtener codigos de publicaciones
						new Propiedad(FOTOS, obtenerCodigosPublicaciones(usuario.getFotos().stream()
																					    .map(e -> (Publicacion) e)
																					    .collect(Collectors.toList()))),
						new Propiedad(ALBUMS, obtenerCodigosPublicaciones(usuario.getAlbums().stream()
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
			} else if (prop.getNombre().equals(SEGUIDOS)) {
				String seguidos = obtenerStringSeguidores(usuario.getUsuariosSeguidos());
				prop.setValor(seguidos);
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
		List<Usuario> seguidores = new ArrayList<Usuario>();
		List<Usuario> seguidos = new ArrayList<Usuario>();
		
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

		// Añadimos el usario a la pool
		PoolDAO.getUnicaInstancia().addObjeto(codigo, user);

		// recuperar propiedades que son objetos llamando a adaptadores
		// fotos
		fotos = obtenerFotosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, FOTOS));
		
		for (Foto f : fotos)
			user.addFoto(f);
		
		// albums
		albums = obtenerAlbumsDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, ALBUMS));
	
		for (Album a : albums)
			user.addAlbum(a);
		
		// recuperar propiedades que son objetos llamando a adaptadores
		// seguidores
		seguidores = obtenerSeguidores(servPersistencia.recuperarPropiedadEntidad(eUsuario, SEGUIDORES));
		seguidos = obtenerSeguidores(servPersistencia.recuperarPropiedadEntidad(eUsuario, SEGUIDOS));
		
		user.setUsuariosSeguidores(seguidores);
		user.setUsuariosSeguidos(seguidos);
		
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
	private String obtenerStringSeguidores(List<Usuario> listaSeguidores) {
		String aux = "";
		for (Usuario u : listaSeguidores) {
			aux+=u.getCodigo()+" ";
		}
		return aux.trim();
	}
	
	/**
	 * Retorna una lista con los codigos de los seguidores
	 * @param seguidores
	 * @return
	 */
	private List<Usuario> obtenerSeguidores(String seguidores) {
		List<Usuario> listaSeguidores = new ArrayList<Usuario>();
		StringTokenizer strTok = new StringTokenizer(seguidores, " ");
		IAdaptadorUsuarioDAO adaptadorU = (IAdaptadorUsuarioDAO) AdaptadorPublicacionTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaSeguidores.add(adaptadorU.recuperarUsuario(Integer.valueOf((String) strTok.nextElement())));
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
	 * Obtiene la lista de fotos a partir de un string de codigos
	 * @param fotos
	 * @return
	 */
	private List<Foto> obtenerFotosDesdeCodigos(String fotos) {

		List<Foto> listaFotos = new ArrayList<Foto>();
		StringTokenizer strTok = new StringTokenizer(fotos, " ");
		AdaptadorPublicacionTDS adaptadorP = AdaptadorPublicacionTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaFotos.add((Foto) adaptadorP.recuperarPublicacion(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaFotos;
	}
	
	/**
	 * Obtiene la lista de albums a partir de un string de codigos
	 * @param albums
	 * @return
	 */
	private List<Album> obtenerAlbumsDesdeCodigos(String albums) {

		List<Album> listaAlbums = new ArrayList<Album>();
		StringTokenizer strTok = new StringTokenizer(albums, " ");
		AdaptadorPublicacionTDS adaptadorP = AdaptadorPublicacionTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaAlbums.add((Album) adaptadorP.recuperarPublicacion(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaAlbums;
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

		StringTokenizer strTok = new StringTokenizer(fechaS, "-");

		int year = Integer.valueOf((String) strTok.nextElement());
		int mes = Integer.valueOf((String) strTok.nextElement());
		int dia = Integer.valueOf((String) strTok.nextElement());
		return LocalDate.of(year, mes, dia);
	}
}
