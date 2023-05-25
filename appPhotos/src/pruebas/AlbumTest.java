package pruebas;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import modelo.Album;
import modelo.Foto;
import modelo.Usuario;

public class AlbumTest {
	private static Album album;
	private static Foto foto1;
	private static Foto foto2;
	private static Foto portada;
	private static Usuario usuario;
	
	@Before
	public void setUp() {
		// Creamos las fotos y la portada
		usuario = new Usuario("pepe", "1234", "pepepepe@gmail.com", "Pepe Pepito Pepe", LocalDate.now(), "foto", "Hola soy pepe");
		foto1 = new Foto("Mi tio","Foto con mi tio",LocalDateTime.of(2023, 1, 1, 12, 14, 33), new ArrayList<>(), usuario, "foto"); 
		foto2 = new Foto("Prueba 2","P2",LocalDateTime.of(2023, 1, 1, 12, 14, 33), new ArrayList<>(), usuario, "foto");
		portada = new Foto("Prueba 3","P3",LocalDateTime.of(2023, 1, 1, 12, 14, 33), new ArrayList<>(), usuario, "foto");
		
		// Creamos el álbum
		album = new Album("Mi álbum", "Descripción del álbum", LocalDateTime.now(), new ArrayList<>(), usuario, portada);
	}
	
	@Test
	public void testAddFoto() {
		// Añadimos una foto al álbum
		album.addFoto(foto1);
		
		// Verificamos que la foto se haya agregado correctamente
		assertTrue(album.getFotos().contains(foto1));
	}
	
	@Test
	public void testDarMeGusta() {
		// Añadimos fotos al álbum
		int mg1 = foto1.getMegusta();
		int mg2 = foto1.getMegusta();
		int mgA = album.getMegusta();
		int mgP = portada.getMegusta();
		album.addFoto(foto1);
		album.addFoto(foto2);
		
		// Damos me gusta al álbum
		album.darMeGusta();
		
		// Verificamos que se haya dado me gusta a todas las fotos y a la portada
		assertEquals(foto1.getMegusta(), mg1 + 1);
		assertEquals(foto2.getMegusta(), mg2 + 1);
		assertEquals(portada.getMegusta(), mgP + 1);
		assertEquals(album.getMegusta(), mgA + 1);
	}
	
	@Test
	public void testQuitarMeGusta() {
		// Añadimos fotos al álbum
		int mg1 = foto1.getMegusta();
		int mg2 = foto1.getMegusta();
		int mgA = album.getMegusta();
		int mgP = portada.getMegusta();
		album.addFoto(foto1);
		album.addFoto(foto2);
		
		// Quitamos me gusta al álbum
		album.quitarMeGusta();
		
		// Verificamos que se haya quitado el me gusta a todas las fotos y a la portada
		assertEquals(foto1.getMegusta(), mg1 - 1);
		assertEquals(foto2.getMegusta(), mg2 - 1);
		assertEquals(portada.getMegusta(), mgP - 1);
		assertEquals(album.getMegusta(), mgA - 1);
	}
	
	@Test
	public void testEliminarFoto() {
		// Añadimos una foto al álbum
		album.addFoto(foto1);
		
		// Eliminamos la foto del álbum
		boolean eliminada = album.eliminarFoto(foto1);
		
		// Verificamos que la foto se haya eliminado correctamente
		assertTrue(eliminada);
		assertFalse(album.getFotos().contains(foto1));
	}
	
	@Test
	public void testComprobarFoto() {
		// Añadimos fotos al álbum
		album.addFoto(foto1);
		
		// Verificamos que la foto 1 y la portada estén en el álbum
		assertTrue(album.getFotos().contains(foto1));
		assertTrue(album.getPortada().equals(portada));
		
		// Verificamos que la foto 2 no esté en el álbum
		assertFalse(album.getFotos().contains(foto2));
	}
}