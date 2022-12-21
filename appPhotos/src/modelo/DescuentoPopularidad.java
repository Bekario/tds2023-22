package modelo;

public class DescuentoPopularidad implements Descuento{
	//Descuento aplicado por edad
	private final double DESCUENTO = 0.2;
	
	//Numero de MGs necesarios para el descuento
	private final int ME_GUSTAS = 1000;
	
	@Override
	public float aplicarDescuento(Usuario usuario) {
		int numMG = usuario.getFotos().stream()
				.map(mg -> mg.getMegusta())
				.reduce(0, (accum, mg) -> accum + mg);
		
		if(numMG >= ME_GUSTAS) {
			return (float) (Variables.precioPremium - (Variables.precioPremium * DESCUENTO));
		}
		return Variables.precioPremium;
	}
}
