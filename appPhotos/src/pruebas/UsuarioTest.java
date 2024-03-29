package pruebas;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import modelo.Album;
import modelo.DescuentoEdad;
import modelo.DescuentoNull;
import modelo.DescuentoPopularidad;
import modelo.Foto;
import modelo.Notificacion;
import modelo.Usuario;

public class UsuarioTest {

    private static Usuario usuario, usuario2;
	private static Foto foto, foto2;
	private static Album album;
	private static Notificacion notificacion;
    
    @Before
    public void setUp() {
    	usuario = new Usuario("pepe", "1234", "pepepepe@gmail.com", "Pepe Pepito Pepe", LocalDate.of(1900, 5, 22), "foto", "Hola soy pepe");
    	usuario2 = new Usuario("usuario2", "contraseña", "email@example.com", "Nombre Completo", LocalDate.of(2002, 5, 22), "perfil", "descripcion");
		foto = new Foto("Mi tio","Foto con mi tio",LocalDateTime.of(2023, 1, 1, 12, 14, 33), usuario, "foto");
		foto2 = new Foto("Mi tio","Foto con mi tio",LocalDateTime.of(2023, 1, 1, 12, 14, 33), usuario, "foto");
		album = new Album("titulo", "descripcion", LocalDateTime.now(), usuario, foto);
    }

    @Test
    public void testComprobarDescuentoEdadMayor() {
        DescuentoEdad descuentoEdad = new DescuentoEdad();

        assertTrue(usuario.comprobarDescuento(descuentoEdad));
        System.out.println("Test basico ComprobarDescuentoEdadMayor superado!");
    }
    
    @Test
    public void testComprobarDescuentoEdadJoven() {
        DescuentoEdad descuentoEdad = new DescuentoEdad();

        assertTrue(usuario2.comprobarDescuento(descuentoEdad));
        System.out.println("Test basico ComprobarDescuentoEdadJoven superado!");
    }

    @Test
    public void testComprobarDescuentoPopularidad() {
        DescuentoPopularidad descuentoPopularidad = new DescuentoPopularidad();

        // Agregar fotos con suficientes "me gusta" para el descuento
        foto.setMegusta(10);
        foto2.setMegusta(15);

        usuario.addFoto(foto);
        usuario.addFoto(foto2);

        assertTrue(usuario.comprobarDescuento(descuentoPopularidad));
        
        System.out.println("Test basico ComprobarDescuentoPopularidad superado!");
    }

    @Test
    public void testComprobarDescuentoNull() {
        DescuentoNull descuentoNull = new DescuentoNull();

        assertTrue(usuario.comprobarDescuento(descuentoNull));
        
        System.out.println("Test basico ComprobarDescuentoNull superado!");
    }

    @Test
    public void testCompruebaNotificacion() {
        // Crear una publicación y agregar una notificación asociada a ella
        Notificacion notificacion = new Notificacion(LocalDate.now(), foto);

        usuario.addNotificacion(notificacion);

        assertTrue(usuario.getNotificaciones().contains(notificacion));
        
        System.out.println("Test basico CompruebaNotificacion superado!");
    }

    @Test
    public void testSeguirYDejarDeSeguir() {
        usuario.seguirA(usuario2);
        
        assertTrue(usuario.getUsuariosSeguidos().contains(usuario2) && usuario2.getUsuariosSeguidores().contains(usuario));
        
        usuario.dejarDeSeguirA(usuario2);

        assertFalse(usuario2.getUsuariosSeguidores().contains(usuario) && usuario.getUsuariosSeguidos().contains(usuario2));
        
        System.out.println("Test basico SeguirYDejarDeSeguir superado!");
    }

    @Test
    public void testAddYRemoveFoto() {
        usuario.addFoto(foto);
        
        assertTrue(usuario.getFotos().contains(foto));
        
        usuario.removeFoto(foto);

        assertFalse(usuario.getFotos().contains(foto));
        
        System.out.println("Test basico AddYRemoveFoto superado!");
    }

    @Test
    public void testAddYRemoveAlbum() {
        usuario.addAlbum(album);
        
        assertTrue(usuario.getAlbums().contains(album));
        
        usuario.removeAlbum(album);

        assertFalse(usuario.getAlbums().contains(album));
        
        System.out.println("Test basico AddYRemoveAlbum superado!");
    }

    @Test
    public void testAddYRemoveNotificacion() {
        usuario.addNotificacion(notificacion);

        assertTrue(usuario.getNotificaciones().contains(notificacion));

        usuario.removeNotificacion(notificacion);

        assertFalse(usuario.getNotificaciones().contains(notificacion));
        
        System.out.println("Test basico AddYRemoveNotificacion superado!");
    }

}
