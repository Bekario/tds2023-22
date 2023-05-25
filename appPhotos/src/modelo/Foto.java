package modelo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Foto extends Publicacion{
	private String path;
	
	//Constructor
	public Foto(String titulo, String descipcion, LocalDateTime fecha, List<String> hastags, Usuario usuario, String path) {
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(path);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Foto other = (Foto) obj;
		return Objects.equals(path, other.path);
	}
}
