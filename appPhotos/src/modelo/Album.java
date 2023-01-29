package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Album extends Publicacion {
	private List<Foto> fotos;
	
	//Constructor
	public Album(String titulo, String descipcion, LocalDate fecha, List<String> hastags, Usuario usuario) {
		super(titulo, descipcion, fecha, hastags, usuario);
		this.fotos = new ArrayList<Foto>();
	}
	
	public void addFoto(Foto foto) {
		fotos.add(foto);
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
	
}
