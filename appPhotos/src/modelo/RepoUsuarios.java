package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class RepoUsuarios {
	private Map<String, Usuario> usuarios; 
	private static RepoUsuarios unicaInstancia = new RepoUsuarios();
	
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private RepoUsuarios() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			usuarios = new HashMap<String,Usuario>();
  			this.cargarRepositorio();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static RepoUsuarios getUnicaInstancia(){
		return unicaInstancia;
	}
	
	/*devuelve todos los usuarios*/
	public List<Usuario> getUsuarios(){
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario u: usuarios.values()) 
			lista.add(u);
		return lista;
	}
	
	public Usuario getUsuario(int codigo) {
		for (Usuario u: usuarios.values()) {
			if (u.getCodigo() == codigo) return u;
		}
		return null;
	}
	public Usuario getUsuario(String usuario) {
		return usuarios.get(usuario); 
	}
	public Usuario getEmail(String email) {
		for (Usuario u: usuarios.values()) {
			if (u.getEmail().equals(email)) return u;
		}
		return null;
	}
	public void addUsuario(Usuario usuario) {
		usuarios.put(usuario.getUsuario(), usuario);
	}
	public void removeUsuario (Usuario usuario) {
		usuarios.remove(usuario.getUsuario());
	}
	
	/*Recupera todos los usuarios para trabajar con ellos en memoria*/
	private void cargarRepositorio() throws DAOException {
		 List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario u: usuariosBD) 
			     usuarios.put(u.getUsuario(), u);
	}
	
	public boolean comprobarUsuario(String usuario) {
		for (Usuario u: usuarios.values()) {
			if (u.getUsuario().equals(usuario)) {
				return true;
			}
		}
		return false;
	}
	
}
