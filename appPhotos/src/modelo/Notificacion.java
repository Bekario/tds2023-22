package modelo;

import java.time.LocalDate;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(codigo, fecha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notificacion other = (Notificacion) obj;
		return codigo == other.codigo && Objects.equals(fecha, other.fecha)
				&& Objects.equals(publicacion, other.publicacion);
	}
	
	
}
