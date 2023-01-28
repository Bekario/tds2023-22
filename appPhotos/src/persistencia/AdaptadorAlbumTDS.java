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
import modelo.Album;
import modelo.Comentario;
import modelo.Foto;
import modelo.Usuario;

public class AdaptadorAlbumTDS implements IAdaptadorAlbumDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorAlbumTDS unicaInstancia = null;
	private final String ALBUM="album";
	private final String FOTOS="fotos";
	private final String TITULO="titulo";
	private final String FECHA="fecha";
	private final String DESCRIPCION="descripcion";
	private final String MEGUSTA="megusta";
	private final String HASHTAGS="hashtags";
	private final String COMENTARIOS="comentarios";
	private final String USUARIO="usuario";	
	

	public static AdaptadorAlbumTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorAlbumTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorAlbumTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un producto se le asigna un identificador unico */
	public void registrarAlbum(Album album) {
		Entidad eAlbum = null;
		try {
			eAlbum = servPersistencia.recuperarEntidad(album.getCodigo());
		} catch (NullPointerException e) {}
		if (eAlbum != null) return;
		

		// registrar primero el atributo usuario
		AdaptadorUsuarioTDS adaptadorUsuario= AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(album.getUsuario());
		
		// registrar primero los atributos que son objetos MALENIA
		AdaptadorComentarioTDS adaptadorComentario = AdaptadorComentarioTDS.getUnicaInstancia();
		for (Comentario c : album.getComentarios())
			adaptadorComentario.registrarComentario(c);

		
		// crear entidad producto
		eAlbum = new Entidad();
		eAlbum.setNombre(ALBUM);
		eAlbum.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(TITULO, album.getTitulo()),
				new Propiedad(FECHA, album.getFecha().toString()),
				new Propiedad(DESCRIPCION, album.getDescripcion()),
				new Propiedad(MEGUSTA, String.valueOf(album.getMegusta())),
				new Propiedad(USUARIO, String.valueOf(album.getUsuario().getCodigo())),
				new Propiedad(HASHTAGS, obtenerStringDeHashtags(album.getHashtags())),
				new Propiedad(COMENTARIOS, obtenerStringDeComentarios(album.getComentarios())),
				new Propiedad(FOTOS, obtenerStringDeFotos(album.getFotos()))
				)));
		
		// registrar entidad producto
		eAlbum = servPersistencia.registrarEntidad(eAlbum);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		album.setCodigo(eAlbum.getId());  
	}

	public void borrarAlbum(Album album) {
		Entidad eAlbum= servPersistencia.recuperarEntidad(album.getCodigo());
		servPersistencia.borrarEntidad(eAlbum);
	}

	public void modificarAlbum(Album album) {
		Entidad eAlbum = servPersistencia.recuperarEntidad(album.getCodigo());

		for (Propiedad prop : eAlbum.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(album.getCodigo()));
			} else if (prop.getNombre().equals(TITULO)) {
				prop.setValor(album.getTitulo());
			} else if (prop.getNombre().equals(FECHA)) {
				prop.setValor(album.getFecha().toString());
			} else if (prop.getNombre().equals(DESCRIPCION)) {
				prop.setValor(album.getDescripcion());
			} else if (prop.getNombre().equals(MEGUSTA)) {
				prop.setValor(String.valueOf(album.getMegusta()));
			} else if (prop.getNombre().equals(HASHTAGS)) {
				prop.setValor(obtenerStringDeHashtags(album.getHashtags()));
			} else if (prop.getNombre().equals(COMENTARIOS)) {
				prop.setValor(obtenerStringDeComentarios(album.getComentarios()));
			} else if (prop.getNombre().equals(USUARIO)) {
				prop.setValor(String.valueOf(album.getUsuario().getCodigo()));
			} else if (prop.getNombre().equals(FOTOS)) {
				prop.setValor(obtenerStringDeFotos(album.getFotos()));
			}       
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Album recuperarAlbum(int codigo) {

		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Album) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		Entidad eAlbum;
		String titulo; 
		String descipcion;
		int megusta;
		Usuario usuario;
		List<String> hashtags = new ArrayList<String>(); 
		LocalDate fecha;
		List<Comentario> comentarios= new ArrayList<Comentario>();
		List<Foto> fotos= new ArrayList<Foto>();
		

		// recuperar entidad
		eAlbum = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		titulo = servPersistencia.recuperarPropiedadEntidad(eAlbum, TITULO);
		descipcion = servPersistencia.recuperarPropiedadEntidad(eAlbum, DESCRIPCION);
		megusta = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eAlbum, MEGUSTA));
		fecha = obtenerFechaDesdeString(servPersistencia.recuperarPropiedadEntidad(eAlbum, FECHA));
		hashtags = obtenerHashtagsDesdeString(servPersistencia.recuperarPropiedadEntidad(eAlbum, HASHTAGS));

		// recuperar propiedades que son objetos llamando a adaptadores
		// ventas
		usuario = obtenerUsuarioDesdeCodigo(servPersistencia.recuperarPropiedadEntidad(eAlbum, USUARIO));
		comentarios= obtenerComentariosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eAlbum, COMENTARIOS));
		
		Album album = new Album(titulo, descipcion, fecha, hashtags, usuario);
		
		//Añadimos todas las fotos al album
		fotos = obtenerFotosDesdeString(servPersistencia.recuperarPropiedadEntidad(eAlbum, FOTOS));
		
		for (Foto f : fotos)
			album.addFoto(f);
		
		album.setCodigo(codigo);
		album.setUsuario(usuario);
		album.setHashtags(hashtags);
		album.setComentarios(comentarios);
		album.setMegusta(megusta);

		return album;
	}
	
	public List<Album> recuperarTodosAlbum() {
		List<Album> album = new LinkedList<Album>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("foto");
		
		for (Entidad eAlbum : entidades) {
			album.add(recuperarAlbum(eAlbum.getId()));
		}
		return album;
	}

	//-----------------------------------------
	
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

		StringTokenizer strTok = new StringTokenizer(fechaS, "/");

		int año = (int) strTok.nextElement();
		int mes = (int) strTok.nextElement();
		int dia = (int) strTok.nextElement();
		return LocalDate.of(año, mes, dia);
	}
	

}
