package persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
				//new Propiedad(HASHTAGS, ),
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
				prop.setValor(String.valueOf(foto.getTitulo()));
			} else if (prop.getNombre().equals(FECHA)) {
				prop.setValor(String.valueOf(foto.getFecha()));
			} else if (prop.getNombre().equals(DESCRIPCION)) {
				prop.setValor(String.valueOf(foto.getDescripcion()));
			} else if (prop.getNombre().equals(MEGUSTA)) {
				prop.setValor(String.valueOf(foto.getMegusta()));
			} else if (prop.getNombre().equals(HASHTAGS)) {
				prop.setValor(String.valueOf(foto.getHashtags()));
			} else if (prop.getNombre().equals(COMENTARIOS)) {
				prop.setValor(String.valueOf(foto.getComentarios()));
			} else if (prop.getNombre().equals(USUARIO)) {
				prop.setValor(String.valueOf(foto.getUsuario()));
			} else if (prop.getNombre().equals(PATH)) {
				prop.setValor(String.valueOf(foto.getPath()));
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
		Usuario usuario;
		String path;
		List<String> hastags = new ArrayList<String>(); 
		LocalDate fecha;
		List<Comentario> comentarios= new ArrayList<Comentario>();
		

		// recuperar entidad
		eFoto = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		titulo = servPersistencia.recuperarPropiedadEntidad(eFoto, TITULO);
		descipcion = servPersistencia.recuperarPropiedadEntidad(eFoto, DESCRIPCION);
		path = servPersistencia.recuperarPropiedadEntidad(eFoto, PATH);
		megusta = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eFoto, MEGUSTA));

		Foto foto = new Foto(titulo, descipcion, hastags, usuario, path);
		foto.setCodigo(codigo);

		// IMPORTANTE:a�adir el cliente al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, foto);

		// recuperar propiedades que son objetos llamando a adaptadores
		// ventas
		ventas = obtenerVentasDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eCliente, "ventas"));

		for (Venta v : ventas)
			cliente.addVenta(v);

		return cliente;
	}

	public List<Foto> recuperarTodosFoto() {
		List<Publicacion> publicaciones = new LinkedList<Foto>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("publicacion");

		for (Entidad ePublicacion : entidades) {
			publicaciones.add(recuperarPublicacion(ePublicacion.getId()));
		}
		return publicaciones;
	}

}
