package persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	public TDSFactoriaDAO () {
	}
	
	@Override
	public IAdaptadorComentarioDAO getComentarioDAO() {
		return AdaptadorComentarioTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorNotificacionDAO getNotificacionDAO() {
		return AdaptadorNotificacionTDS.getUnicaInstancia();
	}
	
	@Override
	public IAdaptadorPublicacionDAO getPublicacionDAO() {
		return AdaptadorPublicacionTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}

}
