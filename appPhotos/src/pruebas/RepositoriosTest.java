package pruebas;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized.AfterParam;

import modelo.Foto;
import modelo.Publicacion;
import modelo.RepoPublicaciones;
import modelo.RepoUsuarios;
import modelo.Usuario;
import persistencia.FactoriaDAO;

public class RepositoriosTest {
	private static Usuario u1;
	private static Usuario u2;
	private static Usuario u3;
	private static Publicacion p1;
	private static Publicacion p2;
	private static Publicacion p3;
	private static RepoUsuarios repositorio = RepoUsuarios.getUnicaInstancia();
	private static RepoPublicaciones repositorioP = RepoPublicaciones.getUnicaInstancia();
	private static ArrayList<Usuario> listaUsuarios;
	private static ArrayList<String> hashtags;
	private static ArrayList<Publicacion> listaPublicaciones;


	
	@BeforeClass
	public static void prepararTests() {
		u1 = new Usuario("pep", "1234", "pepepepe@gmail.com", "Pepe Gonzalez", LocalDate.now(), "foto.jpg", "Soy Pepe.");
		u1.setCodigo(0);
		u2 = new Usuario("adrian", "123456", "adriango@gmail.com", "Adrian Pardo", LocalDate.now(), "fotito.jpg", "Soy Adrian.");
		u2.setCodigo(1);
		u3 = new Usuario("antoni22", "12345678", "antoniiii@gmail.com", "Antonio Martínez", LocalDate.now(), "perfil.png", "Soy Antonio.");
		u3.setCodigo(2);
		
		
		hashtags = new ArrayList<String>();
		hashtags.add("Familia");
		hashtags.add("Buenrollo");	
		p1 = new Foto("A1", "D1", LocalDate.now(), hashtags, u1, "path");
		p1.setCodigo(3);
		p2 = new Foto("A2", "D2", LocalDate.now(), hashtags, u2, "path");
		p2.setCodigo(4);
		p3 = new Foto("A3", "D3", LocalDate.now(), hashtags, u3, "path");
		p3.setCodigo(5);
		
		listaUsuarios = new ArrayList<Usuario>();
		listaUsuarios.add(u1);
		listaUsuarios.add(u2);
		listaUsuarios.add(u3);
		
		listaPublicaciones= new ArrayList<Publicacion>();
		listaPublicaciones.add(p1);
		listaPublicaciones.add(p2);
		listaPublicaciones.add(p3);
		
		//Añadimos dos usuarios
		repositorio.addUsuario(u1);
		repositorio.addUsuario(u2);
		
		repositorioP.addPublicacion(p1);
		repositorioP.addPublicacion(p2);
		
		System.out.println("Tests preparados.");
		System.out.println(u1.getClass().getName());
	}
	
	@Test
	public void testRepoUsuariosRecuperaCodigo() {
		//Compruebo si se recupera correctamente el usuario con codigo
		Usuario aux = repositorio.getUsuario(u1.getCodigo());
		assertEquals("El usuario no se recupera correctamente con el codigo.", u1, aux);
	}
	@Test
	public void testRepoUsuariosRecuperaNombre() {
		//Compruebo si se recupera correctamente el usuario con su nombre de usuario
		Usuario aux = repositorio.getUsuario(u2.getUsuario());
		assertEquals("El usuario no se recupera correctamente con el nombre de usuario.", u2, aux);
	}
	@Test
	public void testRepoUsuariosRecuperaUsuarioInexistente() {
		//Compruebo que cuando se intenta obtener un usuario que no estaba, sea null
		Usuario aux = repositorio.getUsuario(u3.getCodigo());
		assertEquals("El usuario no se recupera correctamente con el codigo cuando no existe.", null, aux);
	}
	@Test
	public void testRepoUsuariosRecuperaNombreInexistente() {
		//Compruebo que cuando se intenta obtener un usuario que no estaba con su nombre de usuario, sea null
		Usuario aux = repositorio.getUsuario(u3.getUsuario());
		assertEquals("El usuario no se recupera correctamente con el codigo cuando no existe.", null, aux);
	}
	@Test
	public void testRepoUsuariosRecuperaTodosUsuarios() {
		//Meto el tercer usuario
		repositorio.addUsuario(u3);
		
		//Compruebo que esten todos los usuarios al obtener la lista
		ArrayList<Usuario> listaAux = (ArrayList<Usuario>) repositorio.getUsuarios();
		
		//Ordeno la lista para que funcione correctamente assertArrayEquals
		listaAux.sort(Comparator.comparing(u -> u.getCodigo()));

		//Quitamos al tercer usuario para no afectar los otros tests
		repositorio.removeUsuario(u3);
		
		assertArrayEquals("No se recuperan correctamente todos los usuarios.", listaUsuarios.toArray(), listaAux.toArray());
	}
	@Test
	public void testRepoUsuariosBorrarUsuario() {
		//Compruebo que cuando se elimina un usuario, no esta
		repositorio.removeUsuario(u2);
		Usuario aux = repositorio.getUsuario(u2.getUsuario());
		
		//Volvemos a añadir al usuario 2 para que no haya problemas con otros tests
		repositorio.addUsuario(u2);
		assertEquals("El usuario no se recupera correctamente con el codigo cuando no existe.", null, aux);
		
	}

	@Test
	public void testRepoPublicacionRecuperaCodigo() {
		//Compruebo si se recupera correctamente el usuario con codigo
		Publicacion aux = repositorioP.getPublicacion(p1.getCodigo());
		assertEquals("La publicacion no se recupera correctamente con el codigo.", p1, aux);
	}

	@Test
	public void testRepoPublicacionRecuperaPublicacionInexistente() {
		//Compruebo que cuando se intenta obtener un usuario que no estaba, sea null
		Publicacion aux = repositorioP.getPublicacion(p3.getCodigo());
		assertEquals("El usuario no se recupera correctamente con el codigo cuando no existe.", null, aux);
	}

	@Test
	public void testRepoPublicacionRecuperaTodasPublicaciones() {
		//Meto el tercer usuario
		repositorioP.addPublicacion(p3);
		
		//Compruebo que esten todos los usuarios al obtener la lista
		ArrayList<Publicacion> listaAux = (ArrayList<Publicacion>) repositorioP.getPublicacion();
		
		//Ordeno la lista para que funcione correctamente assertArrayEquals
		listaAux.sort(Comparator.comparing(u -> u.getCodigo()));

		//Quitamos al tercer usuario para no afectar los otros tests
		repositorioP.removePublicacion(p3);
		
		assertArrayEquals("No se recuperan correctamente todos los usuarios.", listaPublicaciones.toArray(), listaAux.toArray());
	}
	@Test
	public void testRepoPublicacionBorrarPublicacion() {
		//Compruebo que cuando se elimina un usuario, no esta
		repositorioP.removePublicacion(p2);
		Publicacion aux = repositorioP.getPublicacion(p2.getCodigo());
		
		//Volvemos a añadir al usuario 2 para que no haya problemas con otros tests
		repositorioP.addPublicacion(p2);
		assertEquals("El usuario no se recupera correctamente con el codigo cuando no existe.", null, aux);
		
	}


}
