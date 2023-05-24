package modelo;

public class DescuentoNull implements IDescuento{
	
	@Override
	public float aplicarDescuento(float precio) {
		return precio;
	}

	@Override
	public String getNombre() {
		return "Sin descuento";
	}

	@Override
	public String getCondiciones() {
		return "Precio estándar sin descuento.";
	}
	
}
