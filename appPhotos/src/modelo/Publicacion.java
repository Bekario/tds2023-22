package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Publicacion {
	private Usuario usuario;
	private int codigo;
	private String titulo;
	private LocalDate fecha;
	private String descripcion;
	private int megusta;
	private List<String> hashtags;
	private List<Comentario> comentarios;
	
	// Constructor
	public Publicacion(String titulo, String descipcion, LocalDate fecha, List<String> hastags, Usuario usuario) {
		this.titulo=titulo;
		this.descripcion=descipcion;
		this.hashtags=hastags;
		this.usuario=usuario;
		this.fecha=fecha;
		megusta=0;
		comentarios = new ArrayList<Comentario>();
		codigo = 0;
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

	public int getCodigo() {
		return codigo;
	}
	

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
