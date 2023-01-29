package pruebas;

import static org.junit.Assert.*;

import java.awt.List;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.Album;
import modelo.Comentario;
import modelo.Foto;
import modelo.Usuario;
import persistencia.AdaptadorAlbumTDS;
import persistencia.AdaptadorComentarioTDS;
import persistencia.AdaptadorFotoTDS;
import persistencia.AdaptadorNotificacionTDS;
import persistencia.AdaptadorUsuarioTDS;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.TDSFactoriaDAO;

public class PersistenciaTest {
	private static TDSFactoriaDAO factoria;
	private static Usuario usuario;
	private static Comentario comentario;
	private static Foto foto;
	private static ArrayList<String> hashtags;
	private static Album album;
	
	@BeforeClass
	public static void prepararTests() {
		try {
			factoria = (TDSFactoriaDAO) TDSFactoriaDAO.getInstancia();
		} catch (DAOException e) {}
		
		//Iniciamos objetos comunes para los tests
		usuario = new Usuario("pepe", "1234", "pepepepe@gmail.com", "Pepe Pepito Pepe", LocalDate.now(), "foto", "Hola soy pepe");
		comentario = new Comentario("Muy buena foto crack. Saludos desde chile!!");
		hashtags = new ArrayList<String>();
		hashtags.add("Familia");
		hashtags.add("Buenrollo");	
		foto = new Foto("Mi tio","Foto con mi tio",LocalDate.of(2023, 1, 1), hashtags, usuario, "foto"); 
		album = new Album("Paris", "Viaje familiar a paris", LocalDate.of(2023, 1, 1), hashtags, usuario);
		
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
	public void testFotoDAO() {
		AdaptadorFotoTDS f = (AdaptadorFotoTDS) factoria.getFotoDAO();
		
		//Registramos foto
		f.registrarFoto(foto);
		
		//Recuperamos foto
		Foto recuperado = f.recuperarFoto(foto.getCodigo());
		
		assertEquals("El titulo no coincide",foto.getTitulo(), recuperado.getTitulo());
		assertEquals("La descripcion no coincide",foto.getDescripcion(), recuperado.getDescripcion());
		assertEquals("La fecha no coincide",foto.getFecha(), recuperado.getFecha());
		assertEquals("Los hashtags no coinciden",foto.getHashtags(), recuperado.getHashtags());
		//assertEquals("El usuario no coincide",foto.getUsuario(), recuperado.getUsuario());
		assertEquals("La ruta no coincide",foto.getPath(), recuperado.getPath());
		
		System.out.println("Test basico FotoDAO superado!");

	}
	
	
	@Test
	public void testAlbumDAO() {
		AdaptadorAlbumTDS a = (AdaptadorAlbumTDS)factoria.getAlbumDAO();
		
		//Registramos album
		a.registrarAlbum(album);
		
		//Recuperamos album
		Album recuperado = a.recuperarAlbum(album.getCodigo());
		
		assertEquals("El titulo no coincide",album.getTitulo(), recuperado.getTitulo());
		assertEquals("La descripcion no coincide",album.getDescripcion(), recuperado.getDescripcion());
		assertEquals("La fecha no coincide",album.getFecha(), recuperado.getFecha());
		assertEquals("Los hashtags no coinciden",album.getHashtags(), recuperado.getHashtags());
		//assertEquals("El usuario no coincide",album.getUsuario(), recuperado.getUsuario());

		
		System.out.println("Test basico AlbumDAO superado!");
		
	}
	
	@Test
	public void testNotificacionDAO() {
		AdaptadorNotificacionTDS n = (AdaptadorNotificacionTDS) factoria.getNotificacionDAO();
		
		//Registramos un usuario
		n.registrarNotificacion(null);
		
		//Recuperamos el usuario
		Usuario recuperado = n.recuperarUsuario(usuario.getCodigo());
		
		//Comprobamos que todos los campos coincidan
		assertEquals("El usuario no coincide", usuario.getUsuario(), recuperado.getUsuario());
		assertEquals("La contraseña no coincide", usuario.getContraseña(), recuperado.getContraseña());
		assertEquals("El email no coincide", usuario.getEmail(), recuperado.getEmail());
		assertEquals("El nombre completo no coincide", usuario.getNombreCompleto(), recuperado.getNombreCompleto());
		assertEquals("La fecha de nacimiento no coincide", usuario.getFechaNacimiento(), recuperado.getFechaNacimiento());
		assertEquals("La imagen de perfil no coincide", usuario.getPerfil(), recuperado.getPerfil());
		assertEquals("La descripcion no coincide", usuario.getDescripcion(), recuperado.getDescripcion());
		
		System.out.println("Test basico NotificacionDAO superado!");
	}
}
