package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Album extends Publicacion {
	private List<Foto> fotos;
	private Foto portada;
	
	//Constructor
	public Album(String titulo, String descipcion, LocalDateTime fecha, Usuario usuario, Foto portada) {
		super(titulo, descipcion, fecha, usuario);
		this.fotos = new ArrayList<Foto>();
		this.portada = portada;
	}
	
	public void addFoto(Foto foto) {
		fotos.add(foto);
	}
	
	@Override
	public void darMeGusta() {
		// Le damos me gusta a todas las fotos
		for (Foto f : fotos ) {
			f.darMeGusta();
		}
		portada.darMeGusta();
		super.darMeGusta();
	}
	
	@Override
	public void quitarMeGusta() {
		// Le damos me gusta a todas las fotos
		for (Foto f : fotos ) {
			f.quitarMeGusta();
		}
		portada.quitarMeGusta();
		super.quitarMeGusta();
	}
	
	/**
	 * Comprueba si una foto es la portada del album o parte del mismo
	 * @param foto
	 */
	public boolean comprobarFoto(Foto foto) {
		if(fotos.contains(foto) || foto.equals(portada)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Obtiene la portada de una publicacion sin importar si es un album o foto
	 * @param codigo de la publicacion
	 * @return
	 */
	@Override
	public String obtenerPortadaPublicacion() {
		//Si es un album, devolvemos el path de la portada
		return portada.getPath();
	}
	
	//Metodos get / set
	public List<Foto> getFotos() {
		return fotos;
	}
	
	public void setFotos(List<Foto> seleccionados) {
		this.fotos = seleccionados;
	}

	public Foto getPortada() {
		return portada;
	}
	
	public void setPortada(Foto portada) {
		this.portada = portada;
	}
	
	public boolean eliminarFoto(Foto foto) {
		return fotos.remove(foto);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getCodigo(), getComentarios(), getDescripcion(), getFecha(), getHashtags(), getMegusta(), getTitulo(), portada, fotos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		return Objects.equals(fotos, other.fotos) && Objects.equals(portada, other.portada);
	}
	
}
