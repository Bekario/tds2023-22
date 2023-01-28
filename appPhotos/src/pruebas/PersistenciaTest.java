package pruebas;

import static org.junit.Assert.*;

import java.awt.List;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.Comentario;
import modelo.Foto;
import modelo.Usuario;
import persistencia.AdaptadorAlbumTDS;
import persistencia.AdaptadorComentarioTDS;
import persistencia.AdaptadorFotoTDS;
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
		
		
	}
	
	@Test
	public void testFotoDAO() {
		AdaptadorFotoTDS f = (AdaptadorFotoTDS) factoria.getFotoDAO();
		
		//Registramos comentario
		c.registrarComentario(comentario);
		
		//Recuperamos comentario
		Comentario recuperado = c.recuperarComentario(comentario.getCodigo());
		
		assertEquals("El comentario no coincide","Muy buena foto crack. Saludos desde chile!!", recuperado.getTexto());
	}
	
	/*	
	@Test
	public void testAlbumDAO() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testNotificacionDAO() {
		fail("Not yet implemented");
	}*/
}
