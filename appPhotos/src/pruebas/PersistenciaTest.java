package pruebas;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.Usuario;
import persistencia.AdaptadorAlbumTDS;
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
/*	
	@Test
	public void testComentarioDAO() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFotoDAO() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAlbumDAO() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testNotificacionDAO() {
		fail("Not yet implemented");
	}*/
}
