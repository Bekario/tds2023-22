package modelo;

public class DescuentoEdad implements Descuento{
	//Descuento aplicado por edad
	private final double DESCUENTO = 0.4;
	
	@Override
	public float aplicarDescuento(Usuario usuario) {
		return (float) (Variables.precioPremium - (Variables.precioPremium * DESCUENTO));
	}
	
}
