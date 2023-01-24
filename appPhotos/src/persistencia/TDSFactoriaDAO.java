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
	public IAdaptadorFotoDAO getFotoDAO() {
		return AdaptadorFotoTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}

}
