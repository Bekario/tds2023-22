package persistencia;

import java.util.List;
import modelo.Foto;
import modelo.Publicacion;

public interface IAdaptadorPublicacionDAO {
	
	public void registrarPublicacion(Publicacion publicacion);
	public void borrarPublicacion(Publicacion publicacion);
	public void modificarPublicacion(Publicacion publicacion);
	public Foto recuperarPublicacion(Publicacion publicacion);
	public List<Publicacion> recuperarTodosPublicacion();
}
