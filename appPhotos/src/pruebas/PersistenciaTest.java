package pruebas;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import modelo.Album;
import modelo.Comentario;
import modelo.Foto;
import modelo.Notificacion;
import modelo.Usuario;
import persistencia.AdaptadorComentarioTDS;
import persistencia.AdaptadorNotificacionTDS;
import persistencia.AdaptadorPublicacionTDS;
import persistencia.AdaptadorUsuarioTDS;
import persistencia.DAOException;
import persistencia.TDSFactoriaDAO;

public class PersistenciaTest {
	private static TDSFactoriaDAO factoria;
	private static Usuario usuario;
	private static Comentario comentario;
	private static Foto foto;
	private static ArrayList<String> hashtags;
	private static Album album;
	private static Notificacion notificacion;
	private static Notificacion notificacion2;
	
	@BeforeClass
	public static void prepararTests() {
		try {
			factoria = (TDSFactoriaDAO) TDSFactoriaDAO.getInstancia();
		} catch (DAOException e) {}
		
		//Iniciamos objetos comunes para los tests
		usuario = new Usuario("pepe", "1234", "pepepepe@gmail.com", "Pepe Pepito Pepe", LocalDate.now(), "foto", "Hola soy pepe");
		comentario = new Comentario("Muy buena foto crack. Saludos desde chile!!");
		hashtags = new ArrayList<String>();
		hashtags.add("#Familia");
		hashtags.add("#Buenrollo");	
		foto = new Foto("Mi tio","Foto con mi tio",LocalDateTime.of(2023, 1, 1, 12, 14, 33), hashtags, usuario, "foto"); 
		album = new Album("Paris", "Viaje familiar a paris", LocalDateTime.of(2023, 1, 1, 12, 14, 33), hashtags, usuario, foto);
		notificacion = new Notificacion(LocalDate.of(2023, 1, 1), foto);
		notificacion2 = new Notificacion(LocalDate.of(2023, 1, 1), album);

		System.out.println("Tests preparados.");
	}
	
	@Test
	public void testUsuarioDAO() {
		AdaptadorUsuarioTDS u = (AdaptadorUsuarioTDS) factoria.getUsuarioDAO();
		
		//Registramos un usuario
		u.registrarUsuario(usuario);
		
		//Recuperamos el usuario
		Usuario recuperado = u.recuperarUsuario(usuario.getCodigo());
		
		//Comprobamos que todos los campos coincidan
		assertEquals("El usuario no coincide", usuario.getUsuario(), recuperado.getUsuario());
		assertEquals("La contraseña no coincide", usuario.getContraseña(), recuperado.getContraseña());
		assertEquals("El email no coincide", usuario.getEmail(), recuperado.getEmail());
		assertEquals("El nombre completo no coincide", usuario.getNombreCompleto(), recuperado.getNombreCompleto());
		assertEquals("La fecha de nacimiento no coincide", usuario.getFechaNacimiento(), recuperado.getFechaNacimiento());
		assertEquals("La imagen de perfil no coincide", usuario.getPerfil(), recuperado.getPerfil());
		assertEquals("La descripcion no coincide", usuario.getDescripcion(), recuperado.getDescripcion());
		
		System.out.println("Test basico UsuarioDAO superado!");
	}
	
	@Test
	public void testComentarioDAO() {
		AdaptadorComentarioTDS c = (AdaptadorComentarioTDS) factoria.getComentarioDAO();
		
		
		//Registramos comentario
		c.registrarComentario(comentario);
		
		//Recuperamos comentario
		Comentario recuperado = c.recuperarComentario(comentario.getCodigo());
		
		assertEquals("El comentario no coincide",comentario.getTexto(), recuperado.getTexto());
		
		System.out.println("Test basico ComentarioDAO superado!");
	}
	
	@Test
	public void testPublicacionFotoDAO() {
		AdaptadorPublicacionTDS p = (AdaptadorPublicacionTDS) factoria.getPublicacionDAO();
		
		//Registramos foto
		p.registrarPublicacion(foto);
		
		//Recuperamos foto
		Foto recuperado = (Foto) p.recuperarPublicacion(foto.getCodigo());
		assertEquals("El titulo no coincide", foto.getTitulo(), recuperado.getTitulo());
		assertEquals("La descripcion no coincide",foto.getDescripcion(), recuperado.getDescripcion());
		assertEquals("La fecha no coincide",foto.getFecha(), recuperado.getFecha());
		assertEquals("Los hashtags no coinciden",foto.getHashtags(), recuperado.getHashtags());
		assertEquals("El usuario no coincide",foto.getUsuario(), recuperado.getUsuario());
		assertEquals("La ruta no coincide",foto.getPath(), recuperado.getPath());
		
		System.out.println("Test basico PublicacionDAO con foto superado!");

	}
	
	
	@Test
	public void testPublicacionAlbumDAO() {
		AdaptadorPublicacionTDS p = (AdaptadorPublicacionTDS)factoria.getPublicacionDAO();
		
		//Registramos album
		p.registrarPublicacion(album);
		
		//Recuperamos album
		Album recuperado = (Album) p.recuperarPublicacion(album.getCodigo());
		
		assertEquals("El titulo no coincide",album.getTitulo(), recuperado.getTitulo());
		assertEquals("La descripcion no coincide",album.getDescripcion(), recuperado.getDescripcion());
		assertEquals("La fecha no coincide",album.getFecha(), recuperado.getFecha());
		assertEquals("Los hashtags no coinciden",album.getHashtags(), recuperado.getHashtags());
		assertEquals("El usuario no coincide",album.getUsuario(), recuperado.getUsuario());
		assertEquals("La portada no coincide",album.getPortada(), recuperado.getPortada());
		
		
		System.out.println("Test basico PublicacionDAO con albumes superado!");
		
	}
	
	@Test
	public void testNotificacionFotoDAO() {
		AdaptadorNotificacionTDS n = (AdaptadorNotificacionTDS) factoria.getNotificacionDAO();
		
		//Registramos la notificacion
		n.registrarNotificacion(notificacion);
		System.out.println(notificacion.getPublicacion().getMegusta());
		//Recuperamos la notificacion
		Notificacion recuperado = n.recuperarNotificacion(notificacion.getCodigo());
		//Comprobamos que todos los campos coincidan
		assertEquals("La fecha no coincide", notificacion.getFecha(), recuperado.getFecha());
		assertEquals("La publicacion no coincide", notificacion.getPublicacion(), recuperado.getPublicacion());
		
		System.out.println("Test basico NotificacionDAO superado!");
	}
	@Test
	public void testNotificacionAlbumDAO() {
		AdaptadorNotificacionTDS n = (AdaptadorNotificacionTDS) factoria.getNotificacionDAO();
		
		//Registramos la notificacion
		n.registrarNotificacion(notificacion2);
		
		//Recuperamos la notificacion
		Notificacion recuperado = n.recuperarNotificacion(notificacion2.getCodigo());
		//Comprobamos que todos los campos coincidan
		assertEquals("La fecha no coincide", notificacion2.getFecha(), recuperado.getFecha());
		assertEquals("La publicacion no coincide", notificacion2.getPublicacion(), recuperado.getPublicacion());
		
		System.out.println("Test basico NotificacionDAO superado!");
	}
}
