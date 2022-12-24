package persistencia;

import java.util.List;
import modelo.Foto;

public interface IAdaptadorFotoDAO {
	
	public void registrarFoto(Foto foto);
	public void borrarFoto(Foto foto);
	public void modificarFoto(Foto foto);
	public Foto recuperarFoto(int codigo);
	public List<Foto> recuperarTodosFoto();
	

}
