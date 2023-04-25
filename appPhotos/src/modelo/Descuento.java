package modelo;

public interface Descuento {	
	public float aplicarDescuento(float precio);
	public String getNombre();
	public String getCondiciones();
}
