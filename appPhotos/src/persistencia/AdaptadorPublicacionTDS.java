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
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.Usuario;

public class AdaptadorPublicacionTDS implements IAdaptadorPublicacionDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorPublicacionTDS unicaInstancia = null;
	private final String TITULO="titulo";
	private final String FECHA="fecha";
	private final String DESCRIPCION="descripcion";
	private final String MEGUSTA="megusta";
	private final String HASHTAGS="hashtags";
	private final String COMENTARIOS="comentarios";
	private final String USUARIO="usuario";	

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
		ePublicacion.setNombre("publicacion");
		ePublicacion.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(TITULO, publicacion.getTitulo()),
				new Propiedad(FECHA, publicacion.getFecha().toString()),
				new Propiedad(DESCRIPCION, publicacion.getDescripcion()),
				new Propiedad(MEGUSTA, String.valueOf(publicacion.getMegusta()))
		//		new Propiedad(USUARIO, publicacion.getUsuario()),
				//new Propiedad(HASHTAGS, ),
				//new Propiedad(COMENTARIOS, ),
				
				)));
		
		// registrar entidad producto
		ePublicacion = servPersistencia.registrarEntidad(ePublicacion);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		publicacion.setCodigo(ePublicacion.getId());  
	}

	public void borrarPublicacion(Publicacion publicacion) {
		// No se comprueba integridad con lineas de venta
		Entidad ePublicacion = servPersistencia.recuperarEntidad(publicacion.getCodigo());
		servPersistencia.borrarEntidad(ePublicacion);
	}

	public void modificarPublicacion(Publicacion publicacion) {
		Entidad ePublicacion = servPersistencia.recuperarEntidad(publicacion.getCodigo());

		for (Propiedad prop : ePublicacion.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(publicacion.getCodigo()));
			} else if (prop.getNombre().equals(TITULO)) {
				prop.setValor(String.valueOf(publicacion.getTitulo()));
			} else if (prop.getNombre().equals(FECHA)) {
				prop.setValor(String.valueOf(publicacion.getFecha()));
			} else if (prop.getNombre().equals(DESCRIPCION)) {
				prop.setValor(String.valueOf(publicacion.getDescripcion()));
			} else if (prop.getNombre().equals(MEGUSTA)) {
				prop.setValor(String.valueOf(publicacion.getMegusta()));
			} else if (prop.getNombre().equals(HASHTAGS)) {
				prop.setValor(String.valueOf(publicacion.getHashtags()));
			} else if (prop.getNombre().equals(COMENTARIOS)) {
				prop.setValor(String.valueOf(publicacion.getComentarios()));
			} else if (prop.getNombre().equals(USUARIO)) {
				prop.setValor(String.valueOf(publicacion.getUsuario()));
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
		LocalDate fecha;
		String descipcion;
		List<String> hastags = new ArrayList<String>(); 
		Usuario usuario;
		int megusta;
		List<Comentario> comentarios= new ArrayList<Comentario>();
		

		// recuperar entidad
		ePublicacion = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		titulo = servPersistencia.recuperarPropiedadEntidad(ePublicacion, TITULO);
		descipcion = servPersistencia.recuperarPropiedadEntidad(ePublicacion, DESCRIPCION);
		megusta = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(ePublicacion, MEGUSTA));

		Publicacion publicacion = new Publicacion(titulo, descipcion, hastags, usuario);
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

	public List<Publicacion> recuperarTodosPublicacion() {
		List<Publicacion> publicaciones = new LinkedList<Publicacion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("publicacion");

		for (Entidad ePublicacion : entidades) {
			publicaciones.add(recuperarPublicacion(ePublicacion.getId()));
		}
		return publicaciones;
	}

}
