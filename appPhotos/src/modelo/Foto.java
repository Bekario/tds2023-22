package modelo;

import java.util.List;

public class Foto extends Publicacion{
	private String path;
	
	//Constructor
	public Foto(String titulo, String descipcion, List<String> hastags, Usuario usuario, String path) {
		super(titulo, descipcion, hastags, usuario);
		this.path = path;
	}
	
	//Metodos get / set
	public String getPath() {
		return path;
	}
}
