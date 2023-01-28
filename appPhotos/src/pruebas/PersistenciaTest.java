package pruebas;

import static org.junit.Assert.*;

import org.junit.Test;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.TDSFactoriaDAO;

public class PersistenciaTest {
	FactoriaDAO factoria;
	
	public void prepararTests() {
		try {
			factoria = TDSFactoriaDAO.getInstancia();
		} catch (DAOException e) {}
	}
	
	@Test
	public void testUsuarioDAO() {

	}
	
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
	}
}
