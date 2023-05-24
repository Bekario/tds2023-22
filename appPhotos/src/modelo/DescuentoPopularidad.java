package modelo;

public class DescuentoPopularidad implements IDescuento{
	//Descuento aplicado por edad
	private final double DESCUENTO = 0.2;
	
	@Override
	public float aplicarDescuento(float precio) {
		return (float) (precio - (precio * DESCUENTO));
	}

	@Override
	public String getNombre() {
		return "Descuento de Popularidad";
	}
	
	public String getCondiciones() {
		return "Para aplicar este descuento debes tener más de 20 me gustas entre todas tus publicaciones.";
	}
}
