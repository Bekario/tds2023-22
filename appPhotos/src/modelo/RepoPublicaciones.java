package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorPublicacionDAO;


	/* El catálogo mantiene los objetos en memoria, en una tabla hash
	 * para mejorar el rendimiento. Esto no se podría hacer en una base de
	 * datos con un número grande de objetos. En ese caso se consultaria
	 * directamente la base de datos
	 */
	public class RepoPublicaciones {
		private Map<Integer, Publicacion> publicaciones; 
		private static RepoPublicaciones unicaInstancia = new RepoPublicaciones();
		
		private FactoriaDAO dao;
		private IAdaptadorPublicacionDAO adaptadorPublicacion;
		
		private RepoPublicaciones() {
			try {
	  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
	  			publicaciones = new HashMap<Integer, Publicacion>();
	  			adaptadorPublicacion = dao.getPublicacionDAO();
	  			this.cargarRepositorio();
	  		} catch (DAOException eDAO) {
	  			eDAO.printStackTrace();
	  		}
		}
		
		public static RepoPublicaciones getUnicaInstancia(){
			return unicaInstancia;
		}
		
		/*devuelve todos las publicaciones*/
		public List<Publicacion> getPublicaciones(){
			ArrayList<Publicacion> lista = new ArrayList<Publicacion>();
			for (Publicacion p: publicaciones.values()) 
				lista.add(p);
			return lista;
		}
		
		public Publicacion getPublicacion(int codigo) {
			return publicaciones.get(codigo);
		}
		
		public void addPublicacion(Publicacion publicacion) {
			publicaciones.put(publicacion.getCodigo(), publicacion);
		}
		
		public void removePublicacion (Publicacion publicacion) {
			publicaciones.remove(publicacion.getCodigo());
		}
		
		/*Recupera todos los clientes para trabajar con ellos en memoria*/
		private void cargarRepositorio() throws DAOException {
			 List<Publicacion> publicacionesBD = adaptadorPublicacion.recuperarTodosPublicacion();
			 for (Publicacion p: publicacionesBD) 
				     publicaciones.put(p.getCodigo(), p);

		}
}
