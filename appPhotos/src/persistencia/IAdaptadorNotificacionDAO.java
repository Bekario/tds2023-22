package persistencia;

import java.util.List;
import modelo.Notificacion;

public interface IAdaptadorNotificacionDAO {
	
	public void registrarNotificacion(Notificacion notificacion);
	public void borrarNotificacion(Notificacion notificacion);
	public void modificarNotificacion(Notificacion notificacion);
	public Notificacion recuperarNotificacion(int codigo);
	public List<Notificacion> recuperarTodosNotificacion();
}
