package controlador;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adaptadores.AdaptadorEXCEL;
import adaptadores.AdaptadorPDF;
import modelo.Album;
import modelo.Comentario;
import modelo.Foto;
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.RepoPublicaciones;
import modelo.RepoUsuarios;
import modelo.Usuario;
import modelo.Variables;
import persistencia.AdaptadorUsuarioTDS;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorComentarioDAO;
import persistencia.IAdaptadorNotificacionDAO;
import persistencia.IAdaptadorPublicacionDAO;
import persistencia.IAdaptadorUsuarioDAO;
import umu.tds.fotos.ComponenteCargadorFotos;
import umu.tds.fotos.FotosEvent;
import umu.tds.fotos.IFotosListener;


public class Controlador implements IFotosListener {
	private static Controlador unicaInstancia = null;
	private Usuario usuarioActual;
	
	//Panel Seleccionar
	private List<Publicacion> seleccionados;
	private Publicacion portadaSeleccionada;
	
	//Ruta imagenes
	private final String RUTA_IMAGENES = System.getProperty("user.dir")+"/fotosSubidas/";
	
	//Cargador de fotos
	ComponenteCargadorFotos c;
	String rutaXml;
	
	private Controlador() {
		usuarioActual = null;
		portadaSeleccionada = null;
		rutaXml = null;
		seleccionados = new ArrayList<Publicacion>();
		//Añadimos el controlador como listener
		c = new ComponenteCargadorFotos();
		c.addListener(this);
	}
	
	public static Controlador getInstancia() {
		if (unicaInstancia == null) { 
			unicaInstancia = new Controlador();
		}
		return unicaInstancia;
	}
	
	@Override
	public void notificaNuevasFotos(EventObject e) {
		List<umu.tds.fotos.Foto> fotos = ((FotosEvent) e).getFotos().getFoto();
		fotos.stream()
			 .forEach(f -> {f.getHashTags().stream()
						.flatMap(h -> h.getHashTag().stream())
						.forEachOrdered(h -> f.setDescripcion(f.getDescripcion()+" #"+h));
				 añadirFoto(f.getTitulo(), f.getDescripcion(), FileSystems.getDefault().getPath(rutaXml+f.getPath()).toString());
			 });
	}
	
	public void cargarFotos(String rutaXml) {
		//Quitamos /nombre.xml de la ruta para poder leer las imagenes a continuacion
		this.rutaXml = rutaXml.substring(0, rutaXml.lastIndexOf('\\'));
		c.setArchivoFotos(rutaXml);
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
	
	public Foto añadirFoto(String titulo, String descripcion, String path) {
		List<String> hashtags = procesarHashtags(descripcion);
		
		Foto publi = new Foto(titulo, descripcion, LocalDateTime.now(), hashtags, usuarioActual, path);
		
		
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
		
		
		return publi;	
	}
	
	public Album añadirAlbum(String titulo, String descripcion) {
		List<String> hashtags = procesarHashtags(descripcion);
		
		
		//Creamos el album
		Album publi = new Album(titulo, descripcion, LocalDateTime.now(), hashtags, usuarioActual, (Foto)portadaSeleccionada);
		
		//Introducimos las fotos en el album
		seleccionados.stream()
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
		
		return publi;	
	}
	
	public boolean borrarPublicacion(Publicacion publicacion) {
		//Comprobamos si la publicacion esta registrada
		if (!esPublicacionRegistrada(publicacion.getCodigo()))
			return false;
		
		IAdaptadorPublicacionDAO publicacionDAO;
		RepoPublicaciones repoPublicaciones = RepoPublicaciones.getUnicaInstancia();
		try {
			publicacionDAO = FactoriaDAO.getInstancia().getPublicacionDAO();
			//Ahora comprobamos si la publicacion está contenida en un album y lo borramos
			List<Album> albums = new ArrayList<Album>(usuarioActual.getAlbums());
			
			albums.stream()
				  .filter(a -> a.comprobarFoto((Foto) publicacion))
				  .forEach(a -> {publicacionDAO.borrarPublicacion(a); 
				 				repoPublicaciones.removePublicacion(a);
				 				usuarioActual.removeAlbum((Album) a);});
			//Borramos la publicacion
			publicacionDAO.borrarPublicacion(publicacion);
			usuarioActual.removeFoto((Foto) publicacion);
			
			//Actualizamos el usuario
			IAdaptadorUsuarioDAO u = FactoriaDAO.getInstancia().getUsuarioDAO();
			u.modificarUsuario(usuarioActual);
			
			//Eliminamos las notificacion que han podido recibir otros usuario
			usuarioActual.getUsuariosSeguidoresOb().stream().parallel()
															.filter(us -> us.removeNotificacion(publicacion))
															.forEach(us -> u.modificarUsuario(us));;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		repoPublicaciones.removePublicacion(publicacion);
		
		
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
		Collections.sort(list, (x, y) -> x.getUsuario().compareToIgnoreCase(y.getUsuario()));

		
		return list;
	}
	
	public void darMeGusta(Publicacion publicacion) {
		publicacion.darMeGusta();
	}	
	
	public void quitarMeGusta(Publicacion publicacion) {
		publicacion.quitarMeGusta();
	}
	
	public boolean esUsuarioRegistrado(String usuario) {
		return RepoUsuarios.getUnicaInstancia().comprobarUsuario(usuario);
	}
	
	public List<Publicacion> getPublicacionesSubidas(){
		return RepoPublicaciones.getUnicaInstancia().getPublicaciones();
	}
	
	public List<Publicacion> getPublicacionesSubidasSeguidores(){
		List<Publicacion> pub= new ArrayList<Publicacion>(usuarioActual.getFotos());			
		
		//MALENIA STREAM
		for (Usuario u : usuarioActual.getUsuariosSeguidosOb()) {
			pub.addAll(u.getFotos());
		}
		 Collections.sort(pub, (p1, p2) -> p2.getFecha().compareTo(p1.getFecha()));
		 return pub;
	}
	
	public void convertirUsuarioPremium() {
		usuarioActual.setPremium(true);
		actualizarUsuario(usuarioActual);

	}

	public List<Publicacion> getPublicacionesTop() {
		List<Publicacion> pub= new ArrayList<Publicacion>(usuarioActual.getFotos());			
		 Collections.sort(pub, (p1, p2) -> (Integer.compare(p2.getMegusta(), p1.getMegusta())));
		 if(pub.size()>10) {
			 pub= pub.subList(0, 9);
			 
		 }
		 return pub;
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

	public void addComentario(Publicacion publicacion, String text) {
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
		publicacion.addComentario(c);
		actualizarPublicacion(publicacion);
	}
	
	/**
	 * Obtiene la portada de una publicacion sin importar si es un album o foto
	 * @param codigo de la publicacion
	 * @return
	 */
	public String obtenerPortadaPublicacion(Publicacion publicacion) {
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
	
	public List<Album> obtenerAlbums(String usuario) {
		//Devolvemos la lista de albumes 
		return RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getAlbums();
	}
	
	public List<Foto> obtenerFotos(String usuario) {
		//Devolvemos la lista de fotos
		return RepoUsuarios.getUnicaInstancia().getUsuario(usuario).getFotos();
	}
		
	public boolean comprobarPremium() {
		return usuarioActual.getIsPremium();
	}
	
	
	public List<Publicacion> getSeleccionados() {
		return seleccionados;
	}
	
	public void addSeleccionado(Publicacion p) {
		seleccionados.add(p);
	}
	
	public void removeSeleccionado(Publicacion p) {
		seleccionados.remove(p);
	}
	
	public Publicacion getPortadaSeleccionada() {
		return portadaSeleccionada;
	}
	
	public void setPortadaSeleccionada(Publicacion portadaSeleccionada) {
		this.portadaSeleccionada = portadaSeleccionada;
	}

	public List<String> obtenerHashTagsBuscados(String nombre) {
		//Lo convertimos a minuscula para no distinguir
		nombre = nombre.toLowerCase();
		List<String> listaBuscada =  new ArrayList<String>();
		List<String> listaTotal = getHashTagsTotales();
		// MALENIA STREAM
		for (String hashtag : listaTotal) {
			// Comprobamos que el usuario coincida en sus primeras letras y que no sea el mismo
			if(hashtag.toLowerCase().startsWith(nombre)) {
				listaBuscada.add(hashtag);
			}
		}
		Collections.sort(listaBuscada, (x, y) -> x.compareToIgnoreCase(y));
		return listaBuscada;
	}

	private List<String> getHashTagsTotales() {
		HashSet<String> a = new HashSet<String>();
		RepoPublicaciones.getUnicaInstancia().getPublicaciones().stream()
							.forEach(h -> a.addAll(h.getHashtags()));
		List<String> lista= new ArrayList<String>(a);
		return lista;
	}	
	public List<Publicacion> getPublicacionesHashTags(String s) {
		List<Publicacion> l = new ArrayList<Publicacion>(); 
		List<Publicacion> list = RepoPublicaciones.getUnicaInstancia().getPublicaciones();
		for (Publicacion publicacion : list) {
			if(publicacion.getHashtags().contains(s)) {
				l.add(publicacion);
			}
		}
		return l;
	}
	
	/**
	 * Notifica a todos los seguidores del usuario sobre la publicacion subida
	 * @param publicacion sobre la que se va a notificar
	 */
	public void notificarSeguidores(Publicacion publicacion) {
		Notificacion n = new Notificacion(LocalDate.now(), publicacion);
		IAdaptadorNotificacionDAO adaptadorNotificacion= null;
		
		//Preparamos el DAO
		try {
			adaptadorNotificacion = FactoriaDAO.getInstancia().getNotificacionDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		//Registramos la notificacion
		adaptadorNotificacion.registrarNotificacion(n);
		
		//Añadimos la notificacion a cada usuario y guardamos los cambios en el DAO y repositorio
		usuarioActual.getUsuariosSeguidoresOb().stream().parallel()
											   			.forEach(u -> {u.addNotificacion(n);
											   			try {
															FactoriaDAO.getInstancia().getUsuarioDAO().modificarUsuario(u);
														} catch (DAOException e) {
															e.printStackTrace();
														}
											   			RepoUsuarios.getUnicaInstancia().removeUsuario(u);
											   			RepoUsuarios.getUnicaInstancia().addUsuario(u);
											   			});
	}
	
	/**
	 * Elimina la notificacion de la lista del usuarioActual
	 * @param n notificacion que se va a borrar
	 */
	public void eliminarNotificacion(Notificacion n) {
		//Eliminamos la notificacion del usuario
		usuarioActual.removeNotificacion(n);
		
		//Persistimos la notificacion y modificamos el usuario
		IAdaptadorNotificacionDAO notificacionDAO = null;
		IAdaptadorUsuarioDAO usuarioDAO = null;
		try {
			notificacionDAO = FactoriaDAO.getInstancia().getNotificacionDAO();
			usuarioDAO = FactoriaDAO.getInstancia().getUsuarioDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		notificacionDAO.borrarNotificacion(n);
		usuarioDAO.modificarUsuario(usuarioActual);
	}
	
	public float obtenerPrecioPremium() {
		return usuarioActual.comprobarDescuento(Variables.precioPremium);

	}
}
