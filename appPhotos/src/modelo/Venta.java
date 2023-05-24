package modelo;

public class Venta {
	
	private float precio;
	private IDescuento reglaDescuento;
	
	public Venta(float precio) {
		this.precio = precio;
		reglaDescuento = new DescuentoNull();
	}
	
	public void setReglaDescuento(IDescuento reglaDescuento) {
		this.reglaDescuento = reglaDescuento;
	}
	
	public IDescuento getReglaDescuento() {
		return reglaDescuento;
	}
	
	public float obtenerPrecio() {
		return reglaDescuento.aplicarDescuento(precio);
	}
	
}
