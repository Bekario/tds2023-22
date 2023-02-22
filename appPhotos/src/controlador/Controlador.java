package controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import modelo.Album;
import modelo.DescuentoEdad;
import modelo.DescuentoPopularidad;
import modelo.Foto;
import modelo.Publicacion;
import modelo.RepoPublicaciones;
import modelo.RepoUsuarios;
import modelo.Usuario;
import modelo.Variables;
import persistencia.AdaptadorUsuarioTDS;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorAlbumDAO;
import persistencia.IAdaptadorFotoDAO;
import persistencia.IAdaptadorUsuarioDAO;
import ventanas.Register2;

public class Controlador {
	private static Controlador unicaInstancia = null;
	private RepoUsuarios repoUsuarios;
	private RepoPublicaciones repoPublicaciones;
	private Usuario usuarioActual;
	private FactoriaDAO factoria;
	
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
		repoUsuarios = RepoUsuarios.getUnicaInstancia();
		//repoPublicaciones = RepoPublicaciones.getUnicaInstancia();
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {}
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
		return repoUsuarios.getUsuario(login) != null;
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
	
	/**
	 * Este metodo añade la foto de perfil y la descripcion al usuario parcial anterior.
	 * Tambien lo introduce en la base de datos
	 * @param perfil
	 * @param descripcion
	 * @return
	 */
	public void finalizarRegistroUsuario(String perfil, String descripcion) {
		usuarioActual.setDescripcion(descripcion);
		usuarioActual.setPerfil(perfil);

		//Obtenemos el adaptador de usuario
		AdaptadorUsuarioTDS usuarioDAO = (AdaptadorUsuarioTDS) factoria.getUsuarioDAO();
		
		//Registramos al usuario en la base de datos
		usuarioDAO.registrarUsuario(usuarioActual);
		
		//Lo añadimos en el repositorio de usuarios
		RepoUsuarios.getUnicaInstancia().addUsuario(usuarioActual);
	}

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getUsuario()))
			return false;

		IAdaptadorUsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.borrarUsuario(usuario);

		RepoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;
	}
	
	public boolean añadirPublicacion(Publicacion publicacion) {
		//Comprobamos si la publicacion esta registrada
		if (esPublicacionRegistrada(publicacion.getCodigo()))
			return false;
		
		//Dependiendo si es una foto o un album utilizamos un adaptador u otro
		if(publicacion.getClass().getName() == Foto.class.getName()) {
			IAdaptadorFotoDAO fotoDAO = factoria.getFotoDAO();
			fotoDAO.registrarFoto((Foto) publicacion);
		} else {
			IAdaptadorAlbumDAO albumDAO = factoria.getAlbumDAO();
			albumDAO.registrarAlbum((Album) publicacion);
		}

		RepoPublicaciones.getUnicaInstancia().addPublicacion(publicacion);
		return true;	
		}
	
	public boolean borrarPublicacion(Publicacion publicacion) {
		//Comprobamos si la publicacion esta registrada
		if (!esPublicacionRegistrada(publicacion.getCodigo()))
			return false;
		
		//Dependiendo si es una foto o un album utilizamos un adaptador u otro
		if(publicacion.getClass().getName() == Foto.class.getName()) {
			IAdaptadorFotoDAO fotoDAO = factoria.getFotoDAO();
			fotoDAO.borrarFoto((Foto) publicacion);
		} else {
			IAdaptadorAlbumDAO albumDAO = factoria.getAlbumDAO();
			albumDAO.borrarAlbum((Album) publicacion);
		}

		RepoPublicaciones.getUnicaInstancia().removePublicacion(publicacion);
		return true;
	}
	
	public boolean esPublicacionRegistrada(int codigo) {
		return repoPublicaciones.getPublicacion(codigo) != null;
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
	
	public List<Usuario> obtenerUsuariosBuscados(String nombre){
		List<Usuario> listaBuscada =  new ArrayList<Usuario>();
		List<Usuario> listaTotal = repoUsuarios.getUsuarios();
		for (Usuario usuario : listaTotal) {
			if(usuario.getUsuario().startsWith(nombre)) {
				listaBuscada.add(usuario);
			}
		}
		
		return listaBuscada;
	}
	
}
