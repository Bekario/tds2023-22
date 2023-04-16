package persistencia;

import java.util.List;
import modelo.Publicacion;

public interface IAdaptadorPublicacionDAO {
	
	public void registrarPublicacion(Publicacion publicacion);
	public void borrarPublicacion(Publicacion publicacion);
	public void modificarPublicacion(Publicacion publicacion);
	public Publicacion recuperarPublicacion(int codigo);
	public List<Publicacion> recuperarTodosPublicacion();
}
