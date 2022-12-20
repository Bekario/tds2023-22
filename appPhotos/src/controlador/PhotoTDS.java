package controlador;

import modelo.Publicacion;
import modelo.RepoPublicaciones;
import modelo.RepoUsuarios;
import modelo.Usuario;

public class PhotoTDS {
	private RepoUsuarios repoUsuarios;
	private RepoPublicaciones repoPublicaciones;
	//TODO FALTA GENERADOR PDF Y GENERADOR EXCEL
	
	public PhotoTDS() {
		repoUsuarios = new RepoUsuarios();
		repoPublicaciones = new RepoPublicaciones();
	}
	
	public void registrarUsuario(Usuario usuario) {
		//TODO
	}
	
	public void añadirPublicacion(Publicacion publicacion) {
		//TODO
	}
}
