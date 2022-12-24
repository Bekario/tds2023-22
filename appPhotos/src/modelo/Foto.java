package modelo;

import java.time.LocalDate;
import java.util.List;

public class Foto extends Publicacion{
	private String path;
	
	//Constructor
	public Foto(String titulo, String descipcion, LocalDate fecha, List<String> hastags, Usuario usuario, String path) {
		super(titulo, descipcion, fecha, hastags, usuario);
		this.path = path;
	}
	
	//Metodos get / set
	public String getPath() {
		return path;
	}
}
