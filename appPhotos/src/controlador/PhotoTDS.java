package controlador;

import java.time.LocalDate;
import java.time.Period;

import modelo.DescuentoEdad;
import modelo.DescuentoPopularidad;
import modelo.Publicacion;
import modelo.RepoPublicaciones;
import modelo.RepoUsuarios;
import modelo.Usuario;
import modelo.Variables;

public class PhotoTDS {
	private RepoUsuarios repoUsuarios;
	private RepoPublicaciones repoPublicaciones;
	
	//Numero de MGs necesarios para el descuento
	private final int ME_GUSTAS = 1000;
	//Edades entre las que se aplica el descuento
	private final int EDAD_MIN = 18;
	private final int EDAD_MAX = 25;
	
	
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
