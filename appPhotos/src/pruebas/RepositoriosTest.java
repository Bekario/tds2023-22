package pruebas;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import modelo.RepoUsuarios;
import modelo.Usuario;

public class RepositoriosTest {
	private static Usuario u1;
	private static Usuario u2;
	private static Usuario u3;
	private static RepoUsuarios repositorio;
	private static ArrayList<Usuario> listaUsuarios;
	
	@BeforeClass
	public static void prepararTests() {
		u1 = new Usuario("pep", "1234", "pepepepe@gmail.com", "Pepe Gonzalez", LocalDate.now(), "foto.jpg", "Soy Pepe.");
		u1.setCodigo(0);
		u2 = new Usuario("adrian", "123456", "adriango@gmail.com", "Adrian Pardo", LocalDate.now(), "fotito.jpg", "Soy Adrian.");
		u2.setCodigo(1);
		u3 = new Usuario("antoni22", "12345678", "antoniiii@gmail.com", "Antonio Martínez", LocalDate.now(), "perfil.png", "Soy Antonio.");
		u3.setCodigo(2);
		
		listaUsuarios = new ArrayList<Usuario>();
		listaUsuarios.add(u1);
		listaUsuarios.add(u2);
		listaUsuarios.add(u3);
		
		System.out.println("Tests preparados.");
	
		//Obtenemos el repositorio
		RepoUsuarios repositorio = RepoUsuarios.getUnicaInstancia();
		
		//Añadimos dos usuarios
		repositorio.addUsuario(u1);
		repositorio.addUsuario(u2);
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
		assertArrayEquals("No se recuperan correctamente todos los usuarios.", listaUsuarios.toArray(), listaAux.toArray());
	}
	@Test
	public void testRepoUsuariosBorrarUsuario() {
		//Compruebo que cuando se elimina un usuario, no esta
		repositorio.removeUsuario(u2);
		Usuario aux = repositorio.getUsuario(u2.getUsuario());
		assertEquals("El usuario no se recupera correctamente con el codigo cuando no existe.", null, aux);
		
	}

}
