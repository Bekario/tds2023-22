package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Foto extends Publicacion{
	private String path;
	
	//Constructor
	public Foto(String titulo, String descipcion, LocalDate fecha, List<String> hastags, Usuario usuario, String path) {
		super(titulo, descipcion, fecha, hastags, usuario);
		this.path = path;
	}
	
	//Metodos get / set
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj) == false)
			return false;
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Foto other = (Foto) obj;
		return Objects.equals(path, other.path);
	}
}
