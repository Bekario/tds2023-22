package persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.Notificacion;
import modelo.Publicacion;

public class AdaptadorNotificacionTDS implements IAdaptadorNotificacionDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorNotificacionTDS unicaInstancia = null;
	
	private final String FECHA= "fecha";
	private final String PUBLICACION= "publicacion";
	
	public static AdaptadorNotificacionTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorNotificacionTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorNotificacionTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un producto se le asigna un identificador unico */
	public void registrarNotificacion(Notificacion notificacion) {
		Entidad eNotificacion = null;
		try {
			eNotificacion = servPersistencia.recuperarEntidad(notificacion.getCodigo());
		} catch (NullPointerException e) {}
		if (eNotificacion != null) return;
		
		//Registramos la publicacion
		AdaptadorPublicacionTDS adaptadorPublicacion = AdaptadorPublicacionTDS.getUnicaInstancia();
		adaptadorPublicacion.registrarPublicacion(notificacion.getPublicacion());
		
		// crear entidad producto
		eNotificacion = new Entidad();
		eNotificacion.setNombre("notificacion");
		eNotificacion.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(FECHA, notificacion.getFecha().toString()),
																			new Propiedad(PUBLICACION, obtenerCodigoPublicacion(notificacion.getPublicacion()))
		)));
		
		// registrar entidad producto
		eNotificacion = servPersistencia.registrarEntidad(eNotificacion);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		notificacion.setCodigo(eNotificacion.getId());  
	}

	public void borrarNotificacion(Notificacion notificacion) {
		// No se comprueba integridad con lineas de venta
		Entidad eNotificacion = servPersistencia.recuperarEntidad(notificacion.getCodigo());
		servPersistencia.borrarEntidad(eNotificacion);
	}

	public void modificarNotificacion(Notificacion notificacion) {
		Entidad eNotificacion = servPersistencia.recuperarEntidad(notificacion.getCodigo());

		for (Propiedad prop : eNotificacion.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(notificacion.getCodigo()));
			} else if (prop.getNombre().equals(FECHA)) {
				prop.setValor(String.valueOf(notificacion.getFecha()));
			} else if (prop.getNombre().equals(PUBLICACION)) {
				String publicacion = obtenerCodigoPublicacion(notificacion.getPublicacion());
				prop.setValor(String.valueOf(publicacion));
			}  
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Notificacion recuperarNotificacion(int codigo) {
		Entidad eNotificacion;
		LocalDate fecha;
		Publicacion publicacion;

		eNotificacion = servPersistencia.recuperarEntidad(codigo);
		fecha = obtenerFechaDesdeString(servPersistencia.recuperarPropiedadEntidad(eNotificacion, FECHA));
		publicacion = obtenerPublicacionDesdeCodigo(servPersistencia.recuperarPropiedadEntidad(eNotificacion, PUBLICACION));
		
		Notificacion notificacion = new Notificacion(fecha, publicacion);
		notificacion.setCodigo(codigo);
		return notificacion;
	}

	public List<Notificacion> recuperarTodosNotificacion() {
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("notificacion");

		for (Entidad eNotificacion : entidades) {
			notificaciones.add(recuperarNotificacion(eNotificacion.getId()));
		}
		return notificaciones;
	}
	
	// -------------------Funciones auxiliares-----------------------------
	
		/**
		 * Obtiene un String con todos los seguidores
		 * @param listaSeguidores
		 * @return
		 */
		private String obtenerCodigoPublicacion(Publicacion publicacion) {
			return String.valueOf(publicacion.getCodigo());
		}
		
		/**
		 * Retorna la publicacion asociada a un codigo 
		 * @param seguidores
		 * @return
		 */
		private Publicacion obtenerPublicacionDesdeCodigo(String publicacionCodigo) {
			AdaptadorPublicacionTDS adaptadorP = AdaptadorPublicacionTDS.getUnicaInstancia();
			return adaptadorP.recuperarPublicacion(Integer.valueOf((String) publicacionCodigo));
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
