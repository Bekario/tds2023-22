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

public class AdaptadorComentarioTDS implements IAdaptadorComentarioDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorComentarioTDS unicaInstancia = null;

	public static AdaptadorComentarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorComentarioTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorComentarioTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un producto se le asigna un identificador unico */
	public void registrarComentario(Comentario comentario) {
		Entidad eComentario = null;
		try {
			eComentario = servPersistencia.recuperarEntidad(comentario.getCodigo());
		} catch (NullPointerException e) {}
		if (eComentario != null) return;
		
		// crear entidad producto
		eComentario = new Entidad();
		eComentario.setNombre("comentario");
		eComentario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("texto", comentario.getTexto()))));
		
		// registrar entidad producto
		eComentario = servPersistencia.registrarEntidad(eComentario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		comentario.setCodigo(eComentario.getId());  
	}

	public void borrarComentario(Comentario comentario) {
		// No se comprueba integridad con lineas de venta
		Entidad eComentario = servPersistencia.recuperarEntidad(comentario.getCodigo());
		servPersistencia.borrarEntidad(eComentario);
	}

	public void modificarComentario(Comentario comentario) {
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

	public Comentario recuperarComentario(int codigo) {
		Entidad eComentario;
		String texto;

		eComentario = servPersistencia.recuperarEntidad(codigo);
		texto = servPersistencia.recuperarPropiedadEntidad(eComentario, "texto");

		Comentario comentario = new Comentario(texto);
		comentario.setCodigo(codigo);
		return comentario;
	}

	public List<Comentario> recuperarTodosComentarios() {
		List<Comentario> comentarios = new LinkedList<Comentario>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("comentario");

		for (Entidad eComentario : entidades) {
			comentarios.add(recuperarComentario(eComentario.getId()));
		}
		return comentarios;
	}

}
