package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import persistencia.DAOException;
import persistencia.FactoriaDAO;

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
	public Publicacion(String titulo, String descripcion, LocalDate fecha, List<String> hastags, Usuario usuario) {
		this.titulo=titulo;
		this.descripcion=descripcion;
		this.hashtags=hastags;
		this.usuario=usuario;
		this.fecha=fecha;
		megusta=0;
		comentarios = new ArrayList<Comentario>();
		codigo = 0;
	}
	
	public void darMeGusta() {
		megusta++;
		// Ahora hay que actualizar los DAO y repos
		RepoPublicaciones.getUnicaInstancia().removePublicacion(this);
		RepoPublicaciones.getUnicaInstancia().addPublicacion(this);
		try {
			FactoriaDAO.getInstancia().getPublicacionDAO().modificarPublicacion(this);
		} catch (DAOException e) {e.printStackTrace();}		
	}
	
	public void quitarMeGusta() {
		megusta--;
		// Ahora hay que actualizar los DAO y repos
		RepoPublicaciones.getUnicaInstancia().removePublicacion(this);
		RepoPublicaciones.getUnicaInstancia().addPublicacion(this);
		try {
			FactoriaDAO.getInstancia().getPublicacionDAO().modificarPublicacion(this);
		} catch (DAOException e) {e.printStackTrace();}		
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

	public void setMegusta(int megusta) {
		this.megusta = megusta;
	}
	
	public List<String> getHashtags() {
		return hashtags;
	}
	
	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
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
	
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, comentarios, descripcion, fecha, hashtags, megusta, titulo, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publicacion other = (Publicacion) obj;
		return codigo == other.codigo && Objects.equals(comentarios, other.comentarios)
				&& Objects.equals(descripcion, other.descripcion) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(hashtags, other.hashtags) && megusta == other.megusta
				&& Objects.equals(titulo, other.titulo) && Objects.equals(usuario, other.usuario);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
