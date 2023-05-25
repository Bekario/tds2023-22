package pruebas;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AlbumTest.class, PersistenciaTest.class, UsuarioTest.class })
public class AllTests {

}
