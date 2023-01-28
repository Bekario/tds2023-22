package pruebas;

import static org.junit.Assert.*;

import java.time.LocalDate;

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
	
	@BeforeClass
	public static void prepararTests() {
		try {
			factoria = (TDSFactoriaDAO) TDSFactoriaDAO.getInstancia();
		} catch (DAOException e) {}
		System.out.println("Tests preparados.");
	}
	
	@Test
	public void testUsuarioDAO() {
		AdaptadorUsuarioTDS u = (AdaptadorUsuarioTDS) factoria.getUsuarioDAO();
		Usuario user = new Usuario("pepe", "1234", "pepepepe@gmail.com", "Pepe Pepito Pepe", LocalDate.now(), "foto", "Hola soy pepe");
		
		//Registramos un usuario
		u.registrarUsuario(user);
		
		//Recuperamos el usuario
		Usuario recuperado = u.recuperarUsuario(user.getCodigo());
		

	}
	@Test
	public void testComentarioDAO() {
		AdaptadorComentarioTDS c = (AdaptadorComentarioTDS) factoria.getUsuarioDAO();
		Comentario comentario = new Comentario("Muy buena foto crack. Saludos desde chile!!");
		
		//Registramos comentario
		c.registrarComentario(comentario);
		
		//Recuperamos comentario
		Comentario recuperado = c.recuperarComentario(comentario.getCodigo());
		
		assertEquals("El comentario no coincide","Muy buena foto crack. Saludos desde chile!!", recuperado.getTexto());
	}
	
	@Test
	public void testFotoDAO() {
		AdaptadorFotoTDS f = (AdaptadorFotoTDS) factoria.getFotoDAO();
		Foto = new Foto("Mi tio","Foto con mi tio",LocalDate.of(2023, 1, 1), hash, 
		
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
