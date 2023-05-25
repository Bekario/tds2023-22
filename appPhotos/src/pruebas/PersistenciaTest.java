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
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorComentarioDAO;
import persistencia.IAdaptadorNotificacionDAO;
import persistencia.IAdaptadorPublicacionDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class PersistenciaTest {
	private static Usuario usuario;
	private static Comentario comentario;
	private static Foto foto;
	private static ArrayList<String> hashtags;
	private static Album album;
	private static Notificacion notificacion;
	
	@BeforeClass
	public static void prepararTests() {
		//Iniciamos objetos comunes para los tests
		usuario = new Usuario("pepe", "1234", "pepepepe@gmail.com", "Pepe Pepito Pepe", LocalDate.now(),  PersistenciaTest.class.getResource("/imagenes/perfil_default.png").toString().substring(6), "Hola soy pepe");
		comentario = new Comentario("Muy buena foto crack. Saludos desde chile!!");
		hashtags = new ArrayList<String>();
		hashtags.add("#Familia");
		hashtags.add("#Buenrollo");	
		foto = new Foto("Mi tio","Foto con mi tio",LocalDateTime.of(2023, 1, 1, 12, 14, 33), hashtags, usuario, PersistenciaTest.class.getResource("/imagenes/perfil_default.png").toString().substring(6));
		album = new Album("Paris", "Viaje familiar a paris", LocalDateTime.of(2023, 1, 1, 12, 14, 33), hashtags, usuario, foto);
		notificacion = new Notificacion(LocalDate.of(2023, 1, 1), foto);
	}
	
	@Test
	public void testUsuarioDAO() {
		IAdaptadorUsuarioDAO u = null;
		try {
			u = FactoriaDAO.getInstancia().getUsuarioDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		//Registramos un usuario
		u.registrarUsuario(usuario);
		
		//Recuperamos el usuario
		Usuario recuperado = u.recuperarUsuario(usuario.getCodigo());
		
		//Comprobamos que todos los campos coincidan
		assertEquals("El usuario no coincide", usuario, recuperado);
		
		System.out.println("Test basico UsuarioDAO superado!");
	}
	
	@Test
	public void testComentarioDAO() {
		IAdaptadorComentarioDAO c = null;
		try {
			c = FactoriaDAO.getInstancia().getComentarioDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		//Registramos comentario
		c.registrarComentario(comentario);
		
		//Recuperamos comentario
		Comentario recuperado = c.recuperarComentario(comentario.getCodigo());
		
		assertEquals("El comentario no coincide",comentario.getTexto(), recuperado.getTexto());
		
		c.borrarComentario(comentario);
		
		System.out.println("Test basico ComentarioDAO superado!");
	}
	
	@Test
	public void testPublicacionFotoDAO() {
		IAdaptadorPublicacionDAO p = null;
		try {
			p = FactoriaDAO.getInstancia().getPublicacionDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		//Registramos foto
		p.registrarPublicacion(foto);
		
		//Recuperamos foto
		Foto recuperado = (Foto) p.recuperarPublicacion(foto.getCodigo());
		assertEquals("La foto no coincide",foto, recuperado);
		
		System.out.println("Test basico PublicacionDAO con foto superado!");

	}
	
	@Test
	public void testPublicacionAlbumDAO() {
		IAdaptadorPublicacionDAO p = null;
		try {
			p = FactoriaDAO.getInstancia().getPublicacionDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		//Registramos album
		p.registrarPublicacion(album);
		
		//Recuperamos album
		Album recuperado = (Album) p.recuperarPublicacion(album.getCodigo());
		
		assertEquals("El album no coincide",album, recuperado);
		
		
		System.out.println("Test basico PublicacionDAO con albumes superado!");
		
	}
	
	@Test
	public void testNotificacionFotoDAO() {
		IAdaptadorNotificacionDAO n = null;
		try {
			n = FactoriaDAO.getInstancia().getNotificacionDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		//Registramos la notificacion
		n.registrarNotificacion(notificacion);

		//Recuperamos la notificacion
		Notificacion recuperado = n.recuperarNotificacion(notificacion.getCodigo());
		
		//Comprobamos que todos los campos coincidan
		assertEquals("La notificacion no coincide", notificacion, recuperado);
		
		System.out.println("Test basico NotificacionDAO superado!");
	}
}