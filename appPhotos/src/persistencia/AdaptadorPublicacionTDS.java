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

import modelo.Comentario;
import modelo.Foto;
import modelo.Publicacion;
import modelo.Usuario;
import modelo.Album;

public class AdaptadorPublicacionTDS implements IAdaptadorPublicacionDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorPublicacionTDS unicaInstancia = null;
	private final String PUBLICACION="publicacion";
	private final String FOTO="foto";
	private final String ALBUM="album";
	private final String TITULO="titulo";
	private final String FECHA="fecha";
	private final String DESCRIPCION="descripcion";
	private final String MEGUSTA="megusta";
	private final String HASHTAGS="hashtags";
	private final String COMENTARIOS="comentarios";
	private final String USUARIO="usuario";	
	private final String PATH="path";
	private final String FOTOS="fotos";
	
	//Para comprobar si es foto album, el campo fotos o path tendra este valor
	private final String VACIO="vacio";
	

	public static AdaptadorPublicacionTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorPublicacionTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorPublicacionTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un producto se le asigna un identificador unico */
	public void registrarPublicacion(Publicacion publicacion) {
		Entidad ePublicacion = null;
		try {
			ePublicacion = servPersistencia.recuperarEntidad(publicacion.getCodigo());
		} catch (NullPointerException e) {}
		if (ePublicacion != null) return;
		

		// registrar primero el atributo usuario
		AdaptadorUsuarioTDS adaptadorUsuario= AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(publicacion.getUsuario());
		
		// registrar primero los atributos que son objetos MALENIA
		AdaptadorComentarioTDS adaptadorComentario = AdaptadorComentarioTDS.getUnicaInstancia();
		for (Comentario c : publicacion.getComentarios())
			adaptadorComentario.registrarComentario(c);

		
		// crear entidad producto
		ePublicacion = new Entidad();
		ePublicacion.setNombre(PUBLICACION);
		
		ArrayList<Propiedad> propiedades = new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(TITULO, publicacion.getTitulo()),
				new Propiedad(FECHA, publicacion.getFecha().toString()),
				new Propiedad(DESCRIPCION, publicacion.getDescripcion()),
				new Propiedad(MEGUSTA, String.valueOf(publicacion.getMegusta())),
				new Propiedad(USUARIO, String.valueOf(publicacion.getUsuario().getCodigo())),
				new Propiedad(HASHTAGS, obtenerStringDeHashtags(publicacion.getHashtags())),
				new Propiedad(COMENTARIOS, obtenerStringDeComentarios(publicacion.getComentarios()))));
		
		
		//Ahora comprobamos si es una foto o publicacion
		if (publicacion.getClass().getName().equals(FOTO)) {
			propiedades.add(new Propiedad(PATH, ((Foto) publicacion).getPath()));
			propiedades.add(new Propiedad(FOTOS, VACIO));
		} else {
			propiedades.add(new Propiedad(FOTOS, obtenerStringDeFotos(((Album) publicacion).getFotos())));
			propiedades.add(new Propiedad(ALBUM, VACIO));
		}
		
		ePublicacion.setPropiedades(propiedades);
		
		// registrar entidad producto
		ePublicacion = servPersistencia.registrarEntidad(ePublicacion);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		publicacion.setCodigo(ePublicacion.getId());  
	}

	public void borrarPublicacion(Publicacion publicacion) {
		Entidad ePublicacion= servPersistencia.recuperarEntidad(publicacion.getCodigo());
		servPersistencia.borrarEntidad(ePublicacion);
	}

	public void modificarPublicacion(Publicacion publicacion) {
		Entidad ePublicacion = servPersistencia.recuperarEntidad(publicacion.getCodigo());

		for (Propiedad prop : ePublicacion.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(publicacion.getCodigo()));
			} else if (prop.getNombre().equals(TITULO)) {
				prop.setValor(publicacion.getTitulo());
			} else if (prop.getNombre().equals(FECHA)) {
				prop.setValor(publicacion.getFecha().toString());
			} else if (prop.getNombre().equals(DESCRIPCION)) {
				prop.setValor(publicacion.getDescripcion());
			} else if (prop.getNombre().equals(MEGUSTA)) {
				prop.setValor(String.valueOf(publicacion.getMegusta()));
			} else if (prop.getNombre().equals(HASHTAGS)) {
				prop.setValor(obtenerStringDeHashtags(publicacion.getHashtags()));
			} else if (prop.getNombre().equals(COMENTARIOS)) {
				prop.setValor(obtenerStringDeComentarios(publicacion.getComentarios()));
			} else if (prop.getNombre().equals(USUARIO)) {
				prop.setValor(String.valueOf(publicacion.getUsuario().getCodigo()));
			}
			
			//Dependiendo si es un album o foto comprobamos sus atributos especificos
			if (publicacion.getClass().getName().equals(FOTO)) {
				if (prop.getNombre().equals(PATH)) {
					prop.setValor(((Foto) publicacion).getPath());
				}    
			} else {
				if (prop.getNombre().equals(FOTOS)) {
					prop.setValor(obtenerStringDeFotos(((Album) publicacion).getFotos()));
				}   
			}
			
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Publicacion recuperarPublicacion(int codigo) {

		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Publicacion) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		Entidad ePublicacion;
		String titulo; 
		String descipcion;
		int megusta;
		Usuario usuario;
		List<String> hashtags = new ArrayList<String>(); 
		LocalDate fecha;
		List<Comentario> comentarios= new ArrayList<Comentario>();
		
		String path;
		List<Foto> fotos;

		// recuperar entidad
		ePublicacion = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		titulo = servPersistencia.recuperarPropiedadEntidad(ePublicacion, TITULO);
		descipcion = servPersistencia.recuperarPropiedadEntidad(ePublicacion, DESCRIPCION);
		megusta = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(ePublicacion, MEGUSTA));
		fecha = obtenerFechaDesdeString(servPersistencia.recuperarPropiedadEntidad(ePublicacion, FECHA));
		hashtags = obtenerHashtagsDesdeString(servPersistencia.recuperarPropiedadEntidad(ePublicacion, HASHTAGS));

		// recuperar propiedades que son objetos llamando a adaptadores
		usuario = obtenerUsuarioDesdeCodigo(servPersistencia.recuperarPropiedadEntidad(ePublicacion, USUARIO));
		comentarios= obtenerComentariosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(ePublicacion, COMENTARIOS));
		
		//Recuperamos path
		path = servPersistencia.recuperarPropiedadEntidad(ePublicacion, PATH);
		
		//Comprobamos si se trata de foto o album viendo si path vale VACIO
		if (path.equals(VACIO)) {
			Foto foto = new Foto(titulo, descipcion, fecha, hashtags, usuario, path);			
			foto.setCodigo(codigo);
			foto.setUsuario(usuario);
			foto.setHashtags(hashtags);
			foto.setComentarios(comentarios);
			foto.setMegusta(megusta);
			
			return foto;
		} else {
			Album album = new Album(titulo, descipcion, fecha, hashtags, usuario);
			
			album.setCodigo(codigo);
			album.setUsuario(usuario);
			album.setHashtags(hashtags);
			album.setComentarios(comentarios);
			album.setMegusta(megusta);
			
			//Añadimos todas las fotos al album
			fotos = obtenerFotosDesdeString(servPersistencia.recuperarPropiedadEntidad(ePublicacion, FOTOS));
			
			for (Foto f : fotos)
				album.addFoto(f);
			
			return album;
		}
	}
	
	public List<Publicacion> recuperarTodosPublicacion() {
		List<Publicacion> publicaciones = new LinkedList<Publicacion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(PUBLICACION);
		
		for (Entidad ePublicacion : entidades) {
			publicaciones.add(recuperarPublicacion(ePublicacion.getId()));
		}
		return publicaciones;
	}

	//-----------------------------------------
	
	/**
	 * Obtiene un string con los hashtags separados con espacios
	 * @param listaHashtags
	 * @return
	 */
	private String obtenerStringDeHashtags(List<String> listaHashtags) {
		String aux = "";
		for (String s : listaHashtags) {
			aux+=s+" ";
		}
		return aux.trim();
	}
	
	/**
	 * Retorna una lista con los hashtags
	 * @param comentarios
	 * @return
	 */
	 private List<String> obtenerHashtagsDesdeString(String hashtags){
		 ArrayList<String> listaHashtags = new ArrayList<String>();
		 StringTokenizer strTok = new StringTokenizer(hashtags, " ");
		 
		 while (strTok.hasMoreTokens()) {
			 listaHashtags.add((String) strTok.nextElement());
		 }
		 
		 return listaHashtags;
	 }
	
	 /**
		 * Obtiene un string con los codigos de los comentarios
		 * @param listaHashtags
		 * @return
		 */
		private String obtenerStringDeComentarios(List<Comentario> listaComentarios) {
			String aux = "";
			for (Comentario s : listaComentarios) {
				aux+=s.getCodigo()+" ";
			}
			return aux.trim();
		}
	 
	/**
	 * Retorna una lista con los comentarios
	 * @param comentarios
	 * @return
	 */
	 private List<Comentario> obtenerComentariosDesdeCodigos(String comentarios){
		 AdaptadorComentarioTDS adaptadorC = AdaptadorComentarioTDS.getUnicaInstancia();
		 ArrayList<Comentario> listaComentarios = new ArrayList<Comentario>();
		 StringTokenizer strTok = new StringTokenizer(comentarios, " ");
		 
		 while (strTok.hasMoreTokens()) {
			listaComentarios.add(adaptadorC.recuperarComentario(Integer.valueOf((String) strTok.nextElement())));
		}
		 
		 return listaComentarios;
	 }
	 
	/**
	 * Retorna un usuario a partir de su codigo
	 * @param usuario
	 * @return
	 */
	 private Usuario obtenerUsuarioDesdeCodigo(String usuario){
		 AdaptadorUsuarioTDS adaptadorU = AdaptadorUsuarioTDS.getUnicaInstancia();
		 return adaptadorU.recuperarUsuario(Integer.valueOf((String) usuario));
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
	
	/**
	 * Obtiene un string con todos los codigos de las fotos
	 * @param fotos
	 * @return
	 */
	private String obtenerStringDeFotos(List<Foto> fotos) {
		String aux = "";
		for (Foto f : fotos) {
			aux+=f.getCodigo()+" ";
		}
		return aux.trim();
	}
	
	/**
	 * Retorna una lista con las fotos
	 * @param fotos
	 * @return
	 */
	 private List<Foto> obtenerFotosDesdeString(String fotos){
		 AdaptadorFotoTDS adaptadorF = AdaptadorFotoTDS.getUnicaInstancia();
		 ArrayList<Foto> listaFotos = new ArrayList<Foto>();
		 StringTokenizer strTok = new StringTokenizer(fotos, " ");
		 
		 while (strTok.hasMoreTokens()) {
			listaFotos.add(adaptadorF.recuperarFoto(Integer.valueOf((String) strTok.nextElement())));
		}
		 
		 return listaFotos;
	 }

}
