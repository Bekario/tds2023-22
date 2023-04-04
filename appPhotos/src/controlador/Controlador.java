package controlador;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import modelo.DescuentoEdad;
import modelo.DescuentoPopularidad;
import modelo.Publicacion;
import modelo.RepoPublicaciones;
import modelo.RepoUsuarios;
import modelo.Usuario;
import persistencia.AdaptadorUsuarioTDS;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorPublicacionDAO;
import persistencia.IAdaptadorUsuarioDAO;
import ventanas.PanelRegister2;

public class Controlador {
	private static Controlador unicaInstancia = null;
	private Usuario usuarioActual;
//	private FactoriaDAO factoria;
	
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
	public boolean registroUsuario(String usuario, String contraseña, String email, String nombreCompleto, LocalDate fechaNacimiento, String perfil, String descripcion) {
		//Comprobamos si el nombre de usuario esta ya registrado
		if (esUsuarioRegistrado(usuario)) {
			return false;
		}
		//Creamos un usuario provisional sin descripcion ni imagen de perfil
		usuarioActual = new Usuario(usuario, contraseña, email, nombreCompleto, fechaNacimiento, perfil, descripcion);
		
		//Obtenemos el adaptador de usuario
		AdaptadorUsuarioTDS usuarioDAO=null;
		try {
			usuarioDAO = (AdaptadorUsuarioTDS) FactoriaDAO.getInstancia().getUsuarioDAO();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Registramos al usuario en la base de datos
		usuarioDAO.registrarUsuario(usuarioActual);
		
		//Lo añadimos en el repositorio de usuarios
		RepoUsuarios.getUnicaInstancia().addUsuario(usuarioActual);
		
		return true;
	}
	
	/**
	 * Este metodo añade la foto de perfil y la descripcion al usuario parcial anterior.
	 * Tambien lo introduce en la base de datos
	 * @param perfil
	 * @param descripcion
	 * @return
	 */
	
	public void modificarUsuario(String usuario, String contraseña, String email, String nombreCompleto, String descripcion) {
		RepoUsuarios.getUnicaInstancia().removeUsuario(usuarioActual);
		usuarioActual.setUsuario(usuario);
		usuarioActual.setContraseña(contraseña);
		usuarioActual.setEmail(email);
		usuarioActual.setNombreCompleto(nombreCompleto);
		usuarioActual.setDescripcion(descripcion);
		RepoUsuarios.getUnicaInstancia().addUsuario(usuarioActual);
		try {
			FactoriaDAO.getInstancia().getUsuarioDAO().modificarUsuario(usuarioActual);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getUsuario()))
			return false;

		IAdaptadorUsuarioDAO usuarioDAO=null;
		try {
			usuarioDAO = FactoriaDAO.getInstancia().getUsuarioDAO();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		usuarioDAO.borrarUsuario(usuario);

		RepoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;
	}
	
	public boolean añadirPublicacion(Publicacion publicacion) {
		//Comprobamos si la publicacion esta registrada
		if (esPublicacionRegistrada(publicacion.getCodigo()))
			return false;

		IAdaptadorPublicacionDAO publicacionDAO=null;
		try {
			publicacionDAO = FactoriaDAO.getInstancia().getPublicacionDAO();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		publicacionDAO.registrarPublicacion(publicacion);

		RepoPublicaciones.getUnicaInstancia().addPublicacion(publicacion);
		return true;	
		}
	
	public boolean borrarPublicacion(Publicacion publicacion) {
		//Comprobamos si la publicacion esta registrada
		if (!esPublicacionRegistrada(publicacion.getCodigo()))
			return false;
		
		IAdaptadorPublicacionDAO publicacionDAO=null;
		try {
			publicacionDAO = FactoriaDAO.getInstancia().getPublicacionDAO();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		publicacionDAO.borrarPublicacion(publicacion);

		RepoPublicaciones.getUnicaInstancia().removePublicacion(publicacion);
		return true;
	}
	
	public boolean esPublicacionRegistrada(int codigo) {
		return RepoPublicaciones.getUnicaInstancia().getPublicacion(codigo) != null;
	}
	
	/**
	 * El usuario actual comienza a seguir a usuario
	 * @param usuario al que se va a seguir
	 * @return
	 */
	public boolean seguirUsuario(Usuario usuario) {
		// Comprobamos que el usuario no sea seguido ya
		if(!usuarioActual.comprobarSeguido(usuario)) {
			usuarioActual.seguirA(usuario);
			
			// A continuacion, guardamos los cambios en el DAO
			try {
				FactoriaDAO.getInstancia().getUsuarioDAO().modificarUsuario(usuario);
				FactoriaDAO.getInstancia().getUsuarioDAO().modificarUsuario(usuarioActual);
			} catch (DAOException e) {
				e.printStackTrace();
			}
			
			return true;
		}
		return false;
	}
	
	/**
	 * Inserta foto en la carpeta interna fotosSubidas con un nombre único
	 * @param path Ruta de la foto
	 */
	public String subirFotoPerfil(String path) {
		String ruta = "";
		String extension;
		
		//Obtenemos la extension del archivo
		int index = path.lastIndexOf('.');
        if (index == -1) {
        	extension = "";
        } else {
        	extension = path.substring(index, path.length());
        }
		
		try {
			Files.copy(FileSystems.getDefault().getPath(path), FileSystems.getDefault().getPath(RUTA_IMAGENES+"perfil_"+usuarioActual.getUsuario()+extension), StandardCopyOption.REPLACE_EXISTING);
			ruta = FileSystems.getDefault().getPath(RUTA_IMAGENES+"perfil_"+usuarioActual.getUsuario()+"."+extension).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ruta;
	}
	public String subirFotoPerfilDefault() {
		String ruta = "/imagenes/face-detection.png";
		try {
			Files.copy(Path.of(Controlador.class.getResource("/imagenes/face-detection.png").toString().substring(6)), FileSystems.getDefault().getPath(RUTA_IMAGENES+"perfil_"+usuarioActual.getUsuario()+".png"), StandardCopyOption.REPLACE_EXISTING);
			ruta = FileSystems.getDefault().getPath(RUTA_IMAGENES+"perfil_"+usuarioActual.getUsuario()+".png").toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Cambiamos la ruta
		usuarioActual.setPerfil(ruta);
		//Lo persistimos
		try {
			FactoriaDAO.getInstancia().getUsuarioDAO().modificarUsuario(usuarioActual);
		} catch (DAOException e) {e.printStackTrace();}
		return ruta;
	}
	
	
	public boolean eliminarFotoSubida(String ruta) {
		try {
			Files.delete(FileSystems.getDefault().getPath(ruta));
			return true;
		} catch (IOException e) {
			return false;
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
		//Lo convertimos a minuscula para no distinguir
		nombre = nombre.toLowerCase();
		List<Usuario> listaBuscada =  new ArrayList<Usuario>();
		List<Usuario> listaTotal = RepoUsuarios.getUnicaInstancia().getUsuarios();
		// MALENIA STREAM
		for (Usuario usuario : listaTotal) {
			// Comprobamos que el usuario coincida en sus primeras letras y que no sea el mismo
			if(usuario.getUsuario().toLowerCase().startsWith(nombre) && !usuario.getUsuario().equals(usuarioActual.getUsuario())) {
				listaBuscada.add(usuario);
			}
		}
		
		return listaBuscada;
	}
	
	public boolean esUsuarioRegistrado(String usuario) {
		return RepoUsuarios.getUnicaInstancia().comprobarUsuario(usuario);
	}
	
	public List<Publicacion> getPublicacionesSubidas(){
		return RepoPublicaciones.getUnicaInstancia().getPublicaciones();
	}
 	
}
