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
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.Usuario;

public class AdaptadorFotoTDS implements IAdaptadorFotoDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorFotoTDS unicaInstancia = null;
	private final String TITULO="titulo";
	private final String FECHA="fecha";
	private final String DESCRIPCION="descripcion";
	private final String MEGUSTA="megusta";
	private final String HASHTAGS="hashtags";
	private final String COMENTARIOS="comentarios";
	private final String USUARIO="usuario";	
	private final String PATH="path";
	

	public static AdaptadorFotoTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorFotoTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorFotoTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un producto se le asigna un identificador unico */
	public void registrarFoto(Foto foto) {
		Entidad eFoto = null;
		try {
			eFoto = servPersistencia.recuperarEntidad(foto.getCodigo());
		} catch (NullPointerException e) {}
		if (eFoto != null) return;
		

		// registrar primero el atributo usuario
		AdaptadorUsuarioTDS adaptadorUsuario= AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(foto.getUsuario());
		
		// registrar primero los atributos que son objetos MALENIA
		AdaptadorComentarioTDS adaptadorComentario = AdaptadorComentarioTDS.getUnicaInstancia();
		for (Comentario c : foto.getComentarios())
			adaptadorComentario.registrarComentario(c);

		
		// crear entidad producto
		eFoto = new Entidad();
		eFoto.setNombre("foto");
		eFoto.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(TITULO, foto.getTitulo()),
				new Propiedad(FECHA, foto.getFecha().toString()),
				new Propiedad(DESCRIPCION, foto.getDescripcion()),
				new Propiedad(MEGUSTA, String.valueOf(foto.getMegusta())),
				new Propiedad(PATH, foto.getPath())
		//		new Propiedad(USUARIO, publicacion.getUsuario()),
				new Propiedad(HASHTAGS, obtenerStringDeHashtags(foto.getHashtags())),
				//new Propiedad(COMENTARIOS, ),
				
				)));
		
		// registrar entidad producto
		eFoto = servPersistencia.registrarEntidad(eFoto);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		foto.setCodigo(eFoto.getId());  
	}

	public void borrarFoto(Foto foto) {
		// No se comprueba integridad con lineas de venta
		Entidad eFoto= servPersistencia.recuperarEntidad(foto.getCodigo());
		servPersistencia.borrarEntidad(eFoto);
	}

	public void modificarFoto(Foto foto) {
		Entidad eFoto = servPersistencia.recuperarEntidad(foto.getCodigo());

		for (Propiedad prop : eFoto.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(foto.getCodigo()));
			} else if (prop.getNombre().equals(TITULO)) {
				prop.setValor(foto.getTitulo());
			} else if (prop.getNombre().equals(FECHA)) {
				prop.setValor(foto.getFecha().toString());
			} else if (prop.getNombre().equals(DESCRIPCION)) {
				prop.setValor(foto.getDescripcion());
			} else if (prop.getNombre().equals(MEGUSTA)) {
				prop.setValor(String.valueOf(foto.getMegusta()));
			} else if (prop.getNombre().equals(HASHTAGS)) {
				prop.setValor(obtenerStringDeHashtags(foto.getHashtags()));
			} else if (prop.getNombre().equals(COMENTARIOS)) {
				prop.setValor(obt(foto.getComentarios()));
			} else if (prop.getNombre().equals(USUARIO)) {
				prop.setValor(String.valueOf(foto.getUsuario().getCodigo()));
			} else if (prop.getNombre().equals(PATH)) {
				prop.setValor(foto.getPath());
			}       
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Foto recuperarFoto(int codigo) {

		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Foto) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		Entidad eFoto;
		String titulo; 
		String descipcion;
		int megusta;
		String path;
		Usuario usuario;
		List<String> hashtags = new ArrayList<String>(); 
		LocalDate fecha;
		List<Comentario> comentarios= new ArrayList<Comentario>();
		

		// recuperar entidad
		eFoto = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		titulo = servPersistencia.recuperarPropiedadEntidad(eFoto, TITULO);
		descipcion = servPersistencia.recuperarPropiedadEntidad(eFoto, DESCRIPCION);
		path = servPersistencia.recuperarPropiedadEntidad(eFoto, PATH);
		megusta = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eFoto, MEGUSTA));
		fecha = obtenerFechaDesdeString(servPersistencia.recuperarPropiedadEntidad(eFoto, FECHA));
		hashtags = obtenerHashtagsDesdeString(servPersistencia.recuperarPropiedadEntidad(eFoto, HASHTAGS));


		// IMPORTANTE: meter el cliente al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, foto); //malenia

		// recuperar propiedades que son objetos llamando a adaptadores
		// ventas
		usuario = obtenerUsuarioDesdeCodigo(servPersistencia.recuperarPropiedadEntidad(eFoto, USUARIO));
		comentarios= obtenerComentariosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eFoto, COMENTARIOS));
		
		Foto foto = new Foto(titulo, descipcion, fecha, hashtags, usuario, path);
		
		foto.setCodigo(codigo);
		foto.setUsuario(usuario);
		foto.setHashtags(hashtags);
		foto.setComentarios(comentarios);

		return foto;
	}
	
	public List<Foto> recuperarTodosFoto() {
		List<Foto> fotos = new LinkedList<Foto>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("foto");
		
		for (Entidad ePublicacion : entidades) {
			fotos.add(recuperarFoto(ePublicacion.getId()));
		}
		return fotos;
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

		StringTokenizer strTok = new StringTokenizer(fechaS, "/");

		int año = (int) strTok.nextElement();
		int mes = (int) strTok.nextElement();
		int dia = (int) strTok.nextElement();
		return LocalDate.of(año, mes, dia);
	}
	

}
