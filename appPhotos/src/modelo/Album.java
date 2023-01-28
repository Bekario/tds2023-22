package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
}
