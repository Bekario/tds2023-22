package persistencia;

import java.util.List;
import modelo.Comentario;

public interface IAdaptadorComentarioDAO {
	
	public void registrarComentario(Comentario comentario);
	public void borrarComentario(Comentario comentario);
	public void modificarComentario(Comentario comentario);
	public Comentario recuperarComentario(int codigo);
	public List<Comentario> recuperarTodosComentarios();
	

}
