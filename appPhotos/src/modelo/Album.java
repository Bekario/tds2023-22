package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Album extends Publicacion {
	private List<Foto> fotos;
	private Foto portada;
	
	//Constructor
	public Album(String titulo, String descipcion, LocalDateTime fecha, List<String> hastags, Usuario usuario, Foto portada) {
		super(titulo, descipcion, fecha, hastags, usuario);
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
		super.darMeGusta();
	}
	
	@Override
	public void quitarMeGusta() {
		// Le damos me gusta a todas las fotos
		for (Foto f : fotos ) {
			f.quitarMeGusta();
		}
		super.quitarMeGusta();
	}
	
	//Metodos get / set
	public List<Foto> getFotos() {
		return fotos;
	}


	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj) == false)
			return false;
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		return Objects.equals(fotos, other.fotos);
	}

	public Foto getPortada() {
		return portada;
	}
	
	public void setPortada(Foto portada) {
		this.portada = portada;
	}
	
}
