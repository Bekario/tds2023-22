package modelo;

import java.time.LocalDate;

public class Notificacion {
	private int codigo;
	private LocalDate fecha;
	private Publicacion publicacion;
	
	//Constructor
	public Notificacion(LocalDate fecha, Publicacion publicacion) {
		this.fecha = fecha;
		this.publicacion = publicacion;
		codigo = 0;
	}
	
	//Metodos get / set
	public LocalDate getFecha() {
		return fecha;
	}
	
	public Publicacion getPublicacion() {
		return publicacion;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
