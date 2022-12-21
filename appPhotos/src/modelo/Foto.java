package modelo;

import java.util.List;

public class Foto extends Publicacion{
	private String path;
	
	//Constructor
	public Foto(String titulo, String descipcion, List<String> hastags, String path) {
		super(titulo, descipcion, hastags);
		this.path = path;
	}
	
	//Metodos get / set
	public String getPath() {
		return path;
	}
}
