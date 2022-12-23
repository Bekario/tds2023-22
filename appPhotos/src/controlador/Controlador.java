package controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Period;

import modelo.DescuentoEdad;
import modelo.DescuentoPopularidad;
import modelo.Publicacion;
import modelo.RepoPublicaciones;
import modelo.RepoUsuarios;
import modelo.Usuario;
import modelo.Variables;
import ventanas.Register2;

public class Controlador {
	private static Controlador unicaInstancia = null;
	private RepoUsuarios repoUsuarios;
	private RepoPublicaciones repoPublicaciones;
	
	//Numero de MGs necesarios para el descuento
	private final int ME_GUSTAS = 1000;
	//Edades entre las que se aplica el descuento
	private final int EDAD_MIN = 18;
	private final int EDAD_MAX = 25;
	//Ruta imagenes
	private final String RUTA_IMAGENES = "/fotosSubidas/";
	
	//TODO FALTA GENERADOR PDF Y GENERADOR EXCEL
	
	private Controlador() {
		//repoUsuarios = new RepoUsuarios();
		//repoPublicaciones = new RepoPublicaciones();
	}
	
	public static Controlador getInstancia() {
		if (unicaInstancia == null) { 
			unicaInstancia = new Controlador();
		}
		return unicaInstancia;
	}
	
	public void registrarUsuario(Usuario usuario) {
		//TODO
	}
	
	public void añadirPublicacion(Publicacion publicacion) {
		//TODO
	}
	
	/**
	 * Inserta foto en la carpeta interna fotosSubidas
	 * @param path Ruta de la foto
	 * @param nombre Nombre con el que se copia la foto
	 */
	public void insertarFotoSubida(String path, String nombre) {
		try {
			Files.copy(FileSystems.getDefault().getPath(path), FileSystems.getDefault().getPath(System.getProperty("user.dir")+"/fotosSubidas/"+nombre), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarFotoSubida(String nombre) {
		try {
			Files.delete(FileSystems.getDefault().getPath(RUTA_IMAGENES+nombre));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void comprobarDescuento(Usuario usuario) {
		int edad = Period.between(usuario.getFechaNacimiento(), LocalDate.now()).getYears();
		int numMG = usuario.getFotos().stream()
				.map(mg -> mg.getMegusta())
				.reduce(0, (accum, mg) -> accum + mg);
		
		if(edad >= EDAD_MIN && edad <= EDAD_MAX) {
			usuario.setDescuento(new DescuentoEdad());
		} else if(numMG >= ME_GUSTAS) {
			usuario.setDescuento(new DescuentoPopularidad());
		} else {
			usuario.setDescuento(null); //MALENIA
		}
	}
}
