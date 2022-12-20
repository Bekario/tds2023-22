package modelo;

import java.time.LocalDate;
import java.util.List;

public class Publicacion {
	private String titulo;
	private LocalDate fecha;
	private String descripcion;
	private int megusta;
	private List<String> hashtags;
	
	public Publicacion(String titulo, String descipcion, List<String> hastags) {
		this.titulo=titulo;
		this.fecha=LocalDate.now();
		this.descripcion=descipcion;
		this.megusta=0;
		this.hashtags=hastags;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getMegusta() {
		return megusta;
	}

	public List<String> getHashtags() {
		return hashtags;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
