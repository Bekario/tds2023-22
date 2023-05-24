package modelo;

public interface IDescuento {	
	public float aplicarDescuento(float precio);
	public String getNombre();
	public String getCondiciones();
}
