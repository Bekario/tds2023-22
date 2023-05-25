package modelo;

public class DescuentoEdad implements IDescuento{
	//Descuento aplicado por edad
	private final double DESCUENTO = 0.4;
	
	@Override
	public float aplicarDescuento(float precio) {
		return (float) (precio - (precio * DESCUENTO));
	}

	@Override
	public String getNombre() {
		return "Descuento de Edad";
	}

	@Override
	public String getCondiciones() {
		return "Para aplicar este descuento debes tener entre 18 y 25 años o ser mayor de 65 años.";
	}
	
}
