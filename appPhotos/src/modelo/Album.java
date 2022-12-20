package modelo;

import java.util.ArrayList;
import java.util.List;

public class Album {
	private List<Foto> fotos;
	
	//Constructor
	public Album() {
		this.fotos = new ArrayList<Foto>();
	}
	
	//Metodos get / set
	public List<Foto> getFotos() {
		return fotos;
	}
}
