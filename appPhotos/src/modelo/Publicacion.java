package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Publicacion {
	private String titulo;
	private LocalDate fecha;
	private String descripcion;
	private int megusta;
	private List<String> hashtags;
	private List<Comentario> comentarios;
	
	// Constructor
	public Publicacion(String titulo, String descipcion, List<String> hastags) {
		this.titulo=titulo;
		this.descripcion=descipcion;
		this.hashtags=hastags;
		fecha=LocalDate.now();
		megusta=0;
		comentarios = new ArrayList<Comentario>();
	}
	
	// Metodos get / set
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
