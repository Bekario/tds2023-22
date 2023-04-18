package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorComentarioDAO;


	/* El catálogo mantiene los objetos en memoria, en una tabla hash
	 * para mejorar el rendimiento. Esto no se podría hacer en una base de
	 * datos con un número grande de objetos. En ese caso se consultaria
	 * directamente la base de datos
	 */
	public class RepoComentarios {
		private Map<Integer, Comentario> comentarios; 
		private static RepoComentarios unicaInstancia = new RepoComentarios();
		
		private FactoriaDAO dao;
		private IAdaptadorComentarioDAO adaptadorComentario;
		
		private RepoComentarios() {
			try {
	  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
	  			comentarios = new HashMap<Integer, Comentario>();
	  			adaptadorComentario= dao.getComentarioDAO();
	  			this.cargarRepositorio();
	  		} catch (DAOException eDAO) {
	  			eDAO.printStackTrace();
	  		}
		}
		
		public static RepoComentarios getUnicaInstancia(){
			return unicaInstancia;
		}
		
		/*devuelve todos las publicaciones*/
		public List<Comentario> getComentarios(){
			ArrayList<Comentario> lista = new ArrayList<Comentario>();
			for (Comentario c: comentarios.values()) 
				lista.add(c);
			return lista;
		}
		
		public Comentario getComentario(int codigo) {
			return comentarios.get(codigo);
		}
		
		public void addComentario(Comentario comentario) {
			comentarios.put(comentario.getCodigo(), comentario);
		}
		
		public void removeComentario(Comentario comentario) {
			comentarios.remove(comentario.getCodigo());
		}
		
		/*Recupera todos los clientes para trabajar con ellos en memoria*/
		private void cargarRepositorio() throws DAOException {
			 List<Comentario> comentariosBD = adaptadorComentario.recuperarTodosComentarios();
			 for (Comentario c: comentariosBD) 
				 comentarios.put(c.getCodigo(), c);

		}
}
