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
	private Usuario usuarioActual;
	
	//Numero de MGs necesarios para el descuento
	private final int ME_GUSTAS = 1000;
	//Edades entre las que se aplica el descuento
	private final int EDAD_MIN = 18;
	private final int EDAD_MAX = 25;
	//Ruta imagenes
	private final String RUTA_IMAGENES = System.getProperty("user.dir")+"/fotosSubidas/";
	
	//TODO FALTA GENERADOR PDF Y GENERADOR EXCEL
	
	private Controlador() {
		usuarioActual = null;
		//repoUsuarios = new RepoUsuarios();
		//repoPublicaciones = new RepoPublicaciones();
	}
	
	public static Controlador getInstancia() {
		if (unicaInstancia == null) { 
			unicaInstancia = new Controlador();
		}
		return unicaInstancia;
	}
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public boolean esUsuarioRegistrado(String login) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(login) != null;
	}

	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = RepoUsuarios.getUnicaInstancia().getUsuario(nombre);
		if (usuario != null && usuario.getContraseña().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}
	
	/**
	 * Esto se ejecuta para completar la ventana de register y permitir obtener los datos al volver atras
	 * @param usuario
	 * @param contraseña
	 * @param email
	 * @param nombreCompleto
	 * @param fechaNacimiento
	 * @return
	 */
	public boolean registroUsuarioParcial(String usuario, String contraseña, String email, String nombreCompleto, LocalDate fechaNacimiento) {
		//Comprobamos si el nombre de usuario esta ya registrado
		if (esUsuarioRegistrado(usuario)) {
			return false;
		}
		//Creamos un usuario provisional sin descripcion ni imagen de perfil
		Usuario user = new Usuario(usuario, contraseña, email, nombreCompleto, fechaNacimiento, "", "");
		
		//Lo establecemos como usuario actual
		usuarioActual = user;
		return true;
	}
	
	public boolean registrarUsuario(String usuario, String contraseña, String email, String nombreCompleto, LocalDate fechaNacimiento, String perfil, String descripcion) {
		if (esUsuarioRegistrado(usuario)) {
			return false;
		}
		Usuario user = new Usuario(usuario, contraseña, email, nombreCompleto, fechaNacimiento, perfil, descripcion);

		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO(); /* Adaptador DAO para almacenar el nuevo Usuario en la BD */
		usuarioDAO.create(usuario);

		RepoUsuarios.getUnicaInstancia().addUsuario(usuario);
		return true;
	}

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getUsuario()))
			return false;

		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO(); /* Adaptador DAO para borrar el Usuario de la BD */
		usuarioDAO.delete(usuario);

		CatalogoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;
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
			Files.copy(FileSystems.getDefault().getPath(path), FileSystems.getDefault().getPath(RUTA_IMAGENES+nombre), StandardCopyOption.REPLACE_EXISTING);
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
	
	public void comprobarDescuento(Usuario usuario) { //ponerlo en usuario
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
