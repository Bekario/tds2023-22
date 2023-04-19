package controlador;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import adaptadores.AdaptadorEXCEL;
import adaptadores.AdaptadorPDF;
import modelo.Album;
import modelo.Comentario;
import modelo.Foto;
import modelo.Publicacion;
import modelo.RepoPublicaciones;
import modelo.RepoUsuarios;
import modelo.Usuario;
import persistencia.AdaptadorUsuarioTDS;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorComentarioDAO;
import persistencia.IAdaptadorPublicacionDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class Controlador {
	private static Controlador unicaInstancia = null;
	private Usuario usuarioActual;

	//Ruta imagenes
	private final String RUTA_IMAGENES = System.getProperty("user.dir")+"/fotosSubidas/";
	
	private Controlador() {
		usuarioActual = null;
	}
	
	public static Controlador getInstancia() {
		if (unicaInstancia == null) { 
			unicaInstancia = new Controlador();
		}
		return unicaInstancia;
	}
	
	public void cerrarSesion() {
		usuarioActual = null;
	}
	
	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = RepoUsuarios.getUnicaInstancia().getUsuario(nombre);
		if (usuario == null) usuario = RepoUsuarios.getUnicaInstancia().getEmail(nombre);
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
	
	public void modificarUsuario(String usuario, String contraseña, String email, String nombreCompleto, String descripcion, String perfil) {
		RepoUsuarios.getUnicaInstancia().removeUsuario(usuarioActual);
		usuarioActual.setUsuario(usuario);
		usuarioActual.setContraseña(contraseña);
		usuarioActual.setEmail(email);
		usuarioActual.setNombreCompleto(nombreCompleto);
		usuarioActual.setDescripcion(descripcion);
		usuarioActual.setPerfil(perfil);
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
	
	public int añadirFoto(String titulo, String descripcion, String path) {
		List<String> hashtags = procesarHashtags(descripcion);
				
		Foto publi = new Foto(titulo, descripcion, LocalDate.now(), hashtags, usuarioActual, path);
		
		
		IAdaptadorPublicacionDAO publicacionDAO = null;
		try {
			publicacionDAO = FactoriaDAO.getInstancia().getPublicacionDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		publicacionDAO.registrarPublicacion(publi);

		RepoPublicaciones.getUnicaInstancia().addPublicacion(publi);
		
		usuarioActual.addFoto(publi);

		//Subimos la foto a la carpeta fotos subidas
		publi.setPath(subirPublicacion(path, publi.getCodigo()));
		System.out.println();
		publicacionDAO.modificarPublicacion(publi);
		
		// A continuacion, guardamos los cambios en el usuario y la publicacion
		actualizarUsuario(usuarioActual);
		
		
		return publi.getCodigo();	
	}
	
	public int añadirAlbum(String titulo, String descripcion, List<Integer> fotos, int portada) {
		List<String> hashtags = procesarHashtags(descripcion);
		
		
		//Creamos el album
		Album publi = new Album(titulo, descripcion, LocalDate.now(), hashtags, usuarioActual, (Foto) RepoPublicaciones.getUnicaInstancia().getPublicacion(portada));
		
		//Introducimos las fotos en el album
		fotos.stream()
			 .map(f -> RepoPublicaciones.getUnicaInstancia().getPublicacion(f))
			 .forEachOrdered(f -> publi.addFoto((Foto) f));
		
		//Almacenamos el nuevo album en el DAO
		IAdaptadorPublicacionDAO publicacionDAO = null;
		try {
			publicacionDAO = FactoriaDAO.getInstancia().getPublicacionDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		publicacionDAO.registrarPublicacion(publi);
		
		//Almacenamos el album en el repositorio
		RepoPublicaciones.getUnicaInstancia().addPublicacion(publi);
		
		//Añadimos el album al usuario
		usuarioActual.addAlbum(publi);
		
		// A continuacion, guardamos los cambios
		actualizarUsuario(usuarioActual);
		
		return publi.getCodigo();	
	}
	
	public boolean borrarPublicacion(Publicacion publicacion) {
		//Comprobamos si la publicacion esta registrada
		if (!esPublicacionRegistrada(publicacion.getCodigo()))
			return false;
		
		IAdaptadorPublicacionDAO publicacionDAO = null;
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
	
	private List<String> procesarHashtags(String texto) {
		Pattern MY_PATTERN = Pattern.compile("#(\\S{1,15})\\b");
		Matcher mat = MY_PATTERN.matcher(texto);
		List<String> hash = new ArrayList<String>();
		while (mat.find()) {
		  hash.add(mat.group(1));
		}
		
		return hash;
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
			
			// A continuacion, guardamos los cambios 
			actualizarUsuario(usuarioActual);
			actualizarUsuario(usuario);
			
			return true;
		}
		return false;
	}
	
	/**
	 * El usuario actual deja de seguir a usuario
	 * @param usuario al que se va a dejar de seguir
	 * @return
	 */
	public boolean dejarDeSeguirUsuario(Usuario usuario) {
		// Comprobamos que el usuario este siendo seguido
		if(usuarioActual.comprobarSeguido(usuario)) {
			usuarioActual.dejarDeSeguirA(usuario);
			
			// A continuacion, guardamos los cambios
			actualizarUsuario(usuarioActual);
			actualizarUsuario(usuario);
			
			return true;
		}
		return false;
	}
	
	public String obtenerNumeroSeguidores(String usuario) {
		return String.valueOf(RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getNumeroSeguidores());
	}
	
	public String obtenerNumeroSeguidos(String usuario) {
		return String.valueOf(RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getNumeroSeguidos());
	}
	
	public String obtenerNumeroPublicaciones(String usuario) {
		return String.valueOf(RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getNumeroPublicaciones());
	}
	
	/**
	 * Compruba si el usuarioActual sigue al usuario
	 * @param usuario que se va a comprobar
	 */
	public boolean comprobarSeguido(Usuario usuario) {
		return usuarioActual.comprobarSeguido(usuario);
	}
	
	/**
	 * Inserta la foto de perfil en la carpeta interna fotosSubidas con un nombre único
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
	
	/**
	 * Inserta la publicacion en la carpeta interna fotosSubidas con un nombre único
	 * @param path Ruta de la foto
	 */
	public String subirPublicacion(String path, int codigo) {
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
			Files.copy(FileSystems.getDefault().getPath(path), FileSystems.getDefault().getPath(RUTA_IMAGENES+"publicacion_"+usuarioActual.getUsuario()+"_"+String.valueOf(codigo)+extension), StandardCopyOption.REPLACE_EXISTING);
			ruta = FileSystems.getDefault().getPath(RUTA_IMAGENES+"publicacion_"+usuarioActual.getUsuario()+"_"+String.valueOf(codigo)+extension).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public List<Usuario> obtenerUsuariosBuscados(String nombre){
		//Lo convertimos a minuscula para no distinguir
		nombre = nombre.toLowerCase();
		HashSet<Usuario> listaBuscada =  new HashSet<Usuario>();
		List<Usuario> listaTotal = RepoUsuarios.getUnicaInstancia().getUsuarios();
		// MALENIA STREAM
		for (Usuario usuario : listaTotal) {
			// Comprobamos que el usuario coincida en sus primeras letras y que no sea el mismo
			if(usuario.getUsuario().toLowerCase().startsWith(nombre) && !usuario.getUsuario().equals(usuarioActual.getUsuario())) {
				listaBuscada.add(usuario);
			}
			if(usuario.getNombreCompleto().toLowerCase().startsWith(nombre) && !usuario.getNombreCompleto().equals(usuarioActual.getNombreCompleto())) {
				listaBuscada.add(usuario);
			}
			if(usuario.getEmail().toLowerCase().startsWith(nombre) && !usuario.getEmail().equals(usuarioActual.getEmail())) {
				listaBuscada.add(usuario);
			}
		}
		List<Usuario> list= new ArrayList<Usuario>(listaBuscada);
		
		return list;
	}
	
	public void darMeGusta(int publicacion) {
		RepoPublicaciones.getUnicaInstancia().getPublicacion(publicacion).darMeGusta();
	}	
	
	public void quitarMeGusta(int publicacion) {
		RepoPublicaciones.getUnicaInstancia().getPublicacion(publicacion).quitarMeGusta();
	}
	
	public boolean esUsuarioRegistrado(String usuario) {
		return RepoUsuarios.getUnicaInstancia().comprobarUsuario(usuario);
	}
	
	public List<Publicacion> getPublicacionesSubidas(){
		return RepoPublicaciones.getUnicaInstancia().getPublicaciones();
	}
	
	public List<Integer> getPublicacionesSubidasSeguidores(){
		List<Publicacion> pub= new ArrayList<Publicacion>(usuarioActual.getFotos());			
		
		//MALENIA STREAM
		for (Usuario u : usuarioActual.getUsuariosSeguidosOb()) {
			pub.addAll(u.getFotos());
		}
		 Collections.sort(pub, (p1, p2) -> p2.getFecha().compareTo(p1.getFecha()));
		 return pub.stream()
				 	.map(p -> p.getCodigo())
				 	.collect(Collectors.toList());
	}
	
	public void convertirUsuarioPremium() {
		usuarioActual.setPremium(true);
		actualizarUsuario(usuarioActual);

	}

	public List<Integer> getPublicacionesTop() {
		List<Publicacion> pub= new ArrayList<Publicacion>(usuarioActual.getFotos());			
		 Collections.sort(pub, (p1, p2) -> (Integer.compare(p2.getMegusta(), p1.getMegusta())));
		 if(pub.size()>10) {
			 pub= pub.subList(0, 9);
			 
		 }
		 return pub.stream()
				   .map(p -> p.getCodigo())
				   .collect(Collectors.toList());
	}
	
	public void generarPDF() {
		AdaptadorPDF a = new AdaptadorPDF();
		a.crearArchivo(usuarioActual.getUsuariosSeguidoresOb(), usuarioActual.getUsuario()+"_seguidores");
	}
	
	public void generarEXCEL() {
		AdaptadorEXCEL a = new AdaptadorEXCEL();
		a.crearArchivo(usuarioActual.getUsuariosSeguidoresOb(), usuarioActual.getUsuario()+"_seguidores");
	}
	
	
	/**
	 * Actualiza los datos del usuario en el repositorio y en el DAO
	 * @param usuario
	 */
	private void actualizarUsuario(Usuario usuario) {
		RepoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		RepoUsuarios.getUnicaInstancia().addUsuario(usuario);
		
		// A continuacion, guardamos los cambios en el DAO
		try {
			FactoriaDAO.getInstancia().getUsuarioDAO().modificarUsuario(usuario);
		} catch (DAOException e) {
			e.printStackTrace();
		}

	}

	private void actualizarPublicacion(Publicacion p) {
		RepoPublicaciones.getUnicaInstancia().removePublicacion(p);
		RepoPublicaciones.getUnicaInstancia().addPublicacion(p);
		
		// A continuacion, guardamos los cambios en el DAO
		try {
			FactoriaDAO.getInstancia().getPublicacionDAO().modificarPublicacion(p);
		} catch (DAOException e) {
			e.printStackTrace();
		}

	}

	public void addComentario(int publicacion, String text) {
		Publicacion p = RepoPublicaciones.getUnicaInstancia().getPublicacion(publicacion);
		
		//Creamos un objeto comentario
		Comentario c= new Comentario(usuarioActual.getUsuario()+": "+text);
		
		//Persistimos el comentario
		IAdaptadorComentarioDAO comentarioDAO = null;
		try {
			comentarioDAO = FactoriaDAO.getInstancia().getComentarioDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		comentarioDAO.registrarComentario(c);
		
		//Añadimos a la publicacion el comentario
		p.addComentario(c);
		actualizarPublicacion(p);
	}
	
	/**
	 * Obtiene la portada de una publicacion sin importar si es un album o foto
	 * @param codigo de la publicacion
	 * @return
	 */
	public String obtenerPortadaPublicacion(int codigo) {
		Publicacion publicacion = RepoPublicaciones.getUnicaInstancia().getPublicacion(codigo);
		
		//Si es una foto, devolvemos el path
		if (publicacion.getClass().getName() == "modelo.Foto") {
			return ((Foto) publicacion).getPath();			
		} else { //Si es un album, devolvemos el path de la portada
			return ((Album) publicacion).getPortada().getPath();	
		}
	}
	
	public Usuario obtenerUsuarioActual() {
		return usuarioActual;
	}
	
	public List<Integer> obtenerAlbums(String usuario) {
		//Devolvemos la lista de albumes con sus codigos
		return RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getAlbums().stream()
															.map(f -> f.getCodigo())
															.collect(Collectors.toList());
	}
	
	public List<Integer> obtenerFotos(String usuario) {
		//Devolvemos la lista de fotos con sus codigos
		return RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getFotos().stream()
															.map(f -> f.getCodigo())
															.collect(Collectors.toList());
	}
	
	public String obtenerPerfil(String usuario) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getPerfil();
	}
	
	public String obtenerEmail(String usuario) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getPerfil();
	}
	
	public String obtenerDescripcion(String usuario) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getDescripcion();
	}
	
	public List<String> obtenerUsuariosSeguidos(String usuario) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getUsuariosSeguidosNombre();
	}
	
	public boolean comprobarPremium() {
		return usuarioActual.getIsPremium();
	}
	
	public String obtenerUsuario(int publicacion) {
		return RepoPublicaciones.getUnicaInstancia().getPublicacion(publicacion).getUsuario().getUsuario();
	}
	
	public String obtenerMeGustaPublicacion(int publicacion) {
		return String.valueOf(RepoPublicaciones.getUnicaInstancia().getPublicacion(publicacion).getMegusta());
	}
	
	public String obtenerTituloPublicacion(int publicacion) {
		return RepoPublicaciones.getUnicaInstancia().getPublicacion(publicacion).getTitulo();
	}
	
	public String obtenerDescripcionPublicacion(int publicacion) {
		return RepoPublicaciones.getUnicaInstancia().getPublicacion(publicacion).getDescripcion();
	}
	
	public List<Integer> obtenerComentariosPublicacion(int publicacion) {
		return RepoPublicaciones.getUnicaInstancia().getPublicacion(publicacion).getComentarios().stream()
																								 .map(c -> c.getCodigo())
																								 .collect(Collectors.toList());
	}
	
	public String obtenerTextoComentario(int publicacion, int comentario) {
		Comentario com = RepoPublicaciones.getUnicaInstancia().getPublicacion(publicacion).getComentarios().stream()
																								 .filter(c -> comentario == c.getCodigo())
																								 .findAny()
																								 .orElse(null);
		return com.getTexto();																				 
	}
	
		
}
