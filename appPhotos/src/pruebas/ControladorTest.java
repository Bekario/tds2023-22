package pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import controlador.Controlador;
import modelo.Album;
import modelo.Comentario;
import modelo.Foto;
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.Usuario;
import persistencia.AdaptadorFotoTDS;
import persistencia.DAOException;
import persistencia.TDSFactoriaDAO;

public class ControladorTest {
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
		hashtags.add("Familia");
		hashtags.add("Buenrollo");	
		foto = new Foto("Mi tio","Foto con mi tio",LocalDate.of(2023, 1, 1), hashtags, usuario, "foto"); 
		album = new Album("Paris", "Viaje familiar a paris", LocalDate.of(2023, 1, 1), hashtags, usuario);
		notificacion = new Notificacion(LocalDate.of(2023, 1, 1), foto);
		notificacion2 = new Notificacion(LocalDate.of(2023, 1, 1), album);

		
		System.out.println("Tests preparados.");
	}
	
	@Test
	public void testFotoEliminar() {
		AdaptadorFotoTDS f = (AdaptadorFotoTDS) factoria.getFotoDAO();
		
		//Registramos foto
		Controlador.getInstancia().añadirPublicacion(foto);
		Foto recuperado = f.recuperarFoto(foto.getCodigo());
		assertEquals("La foto no se recupera correctamente con el codigo cuando no existe.", null, recuperado);
		Controlador.getInstancia().borrarPublicacion(foto);
		recuperado = f.recuperarFoto(foto.getCodigo());
		assertNotEquals("La foto se recupera y debia estar borrada con el codigo cuando no existe.", null, recuperado);
	}
}