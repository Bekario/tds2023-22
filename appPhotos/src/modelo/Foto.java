package modelo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Foto extends Publicacion{
	private String path;
	
	//Constructor
	public Foto(String titulo, String descipcion, LocalDateTime fecha, Usuario usuario, String path) {
		super(titulo, descipcion, fecha, usuario);
		this.path = path;
	}
	
	/**
	 * Obtiene la portada de una publicacion sin importar si es un album o foto
	 * @param codigo de la publicacion
	 * @return
	 */
	@Override
	public String obtenerPortadaPublicacion() {
		//Si es una foto, devolvemos el path
		return path;
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
		return Objects.hash(getCodigo(), getComentarios(), getDescripcion(), getFecha(), getHashtags(), getMegusta(), getTitulo(), path);
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
