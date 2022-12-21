package modelo;

public class DescuentoPopularidad implements Descuento{
	//Descuento aplicado por edad
	private final double DESCUENTO = 0.2;
	
	@Override
	public float aplicarDescuento(Usuario usuario) {
		return (float) (Variables.precioPremium - (Variables.precioPremium * DESCUENTO));
	}
}
