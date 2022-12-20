package modelo;

import java.time.LocalDate;

public class Notificacion {
	private LocalDate fecha;
	
	//Constructor
	public Notificacion(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	//Metodos get / set
	public LocalDate getFecha() {
		return fecha;
	}
}
