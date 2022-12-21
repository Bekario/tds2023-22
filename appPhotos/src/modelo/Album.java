package modelo;

import java.util.ArrayList;
import java.util.List;

public class Album extends Publicacion {
	private List<Foto> fotos;
	
	//Constructor
	public Album(String titulo, String descipcion, List<String> hastags) {
		super(titulo, descipcion, hastags);
		this.fotos = new ArrayList<Foto>();
	}
	
	//Metodos get / set
	public List<Foto> getFotos() {
		return fotos;
	}
}
