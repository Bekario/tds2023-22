package adaptadores;

import java.util.List;

import modelo.Usuario;

public interface IAdaptadorSeguidores {
	
	public void crearArchivo(List<Usuario> seguidores, String nombreArchivo);
}
