package persistencia;

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

public class AdaptadorNotificacionTDS implements IAdaptadorNotificacionDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorNotificacionTDS unicaInstancia = null;

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
			eNotificacion = servPersistencia.recuperarEntidad(comentario.getCodigo());
		} catch (NullPointerException e) {}
		if (eNotificacion != null) return;
		
		// crear entidad producto
		eNotificacion = new Entidad();
		eNotificacion.setNombre("notificacion");
		eNotificacion.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("texto", comentario.getTexto()))));
		
		// registrar entidad producto
		eNotificacion = servPersistencia.registrarEntidad(eNotificacion);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		comentario.setCodigo(eNotificacion.getId());  
	}

	public void borrarNotificacion(Notificacion notificacion) {
		// No se comprueba integridad con lineas de venta
		Entidad eNotificacion = servPersistencia.recuperarEntidad(comentario.getCodigo());
		servPersistencia.borrarEntidad(eNotificacion);
	}

	public void modificarNotificacion(Notificacion notificacion) {
		Entidad eComentario = servPersistencia.recuperarEntidad(comentario.getCodigo());

		for (Propiedad prop : eComentario.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(comentario.getCodigo()));
			} else if (prop.getNombre().equals("texto")) {
				prop.setValor(String.valueOf(comentario.getTexto()));
			}  
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Notificacion recuperarNotificacion(int codigo) {
		Entidad eComentario;
		String texto;

		eComentario = servPersistencia.recuperarEntidad(codigo);
		texto = servPersistencia.recuperarPropiedadEntidad(eComentario, "texto");

		Comentario comentario = new Comentario(texto);
		comentario.setCodigo(codigo);
		return comentario;
	}

	public List<Notificacion> recuperarTodosNotificacion() {
		List<Notificacion> comentarios = new ArrayList<Notificacion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("comentario");

		for (Entidad eComentario : entidades) {
			comentarios.add(recuperarNotificacion(eComentario.getId()));
		}
		return comentarios;
	}

}
