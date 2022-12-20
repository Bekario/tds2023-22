package modelo;

	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;

	import persistencia.DAOException;
	import persistencia.FactoriaDAO;
	import persistencia.IAdaptadorClienteDAO;


	/* El catálogo mantiene los objetos en memoria, en una tabla hash
	 * para mejorar el rendimiento. Esto no se podría hacer en una base de
	 * datos con un número grande de objetos. En ese caso se consultaria
	 * directamente la base de datos
	 */
	public class RepoPublicaciones {
		private Map<String,Publicacion> publicaciones; 
		private static RepoPublicaciones unicaInstancia = new RepoPublicaciones();
		
		private FactoriaDAO dao;
		private IAdaptadorPublicacionDAO adaptadorPublicacion;
		
		private RepoPublicaciones() {
			try {
	  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
	  			adaptadorPublicacion = dao.getPublicacionDAO();
	  			clientes = new HashMap<String,Publicacion>();
	  			this.cargarRepo();
	  		} catch (DAOException eDAO) {
	  			eDAO.printStackTrace();
	  		}
		}
		
		public static CatalogoClientes getUnicaInstancia(){
			return unicaInstancia;
		}
		
		/*devuelve todos los clientes*/
		public List<Cliente> getClientes(){
			ArrayList<Cliente> lista = new ArrayList<Cliente>();
			for (Cliente c:clientes.values()) 
				lista.add(c);
			return lista;
		}
		
		public Cliente getCliente(int codigo) {
			for (Cliente c:clientes.values()) {
				if (c.getCodigo()==codigo) return c;
			}
			return null;
		}
		public Cliente getCliente(String dni) {
			return clientes.get(dni); 
		}
		
		public void addCliente(Cliente cli) {
			publicaciones.put(cli.getDni(),cli);
		}
		public void removeCliente (Cliente cli) {
			publicaciones.remove(cli.getDni());
		}
		
		/*Recupera todos los clientes para trabajar con ellos en memoria*/
		private void cargarRepo() throws DAOException {
			 List<Publicacion> publicacionesBD = adaptadorPublicacion.recuperarTodasPublicaciones();
			 for (Publicacion publ: publicacionesBD) 
				     publicaciones.put(publ.getTitulo(),publ);
		}
		
	}

}
