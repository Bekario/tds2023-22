package modelo;

public class DescuentoEdad implements Descuento{
	//Descuento aplicado por edad
	private final double DESCUENTO = 0.4;
	
	@Override
	public float aplicarDescuento(Usuario usuario, float precio) {
		return (float) (precio - (precio * DESCUENTO));
	}
	
}
