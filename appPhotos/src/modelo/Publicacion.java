package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Publicacion {
	private Usuario usuario;
	private int codigo;
	private String titulo;
	private LocalDateTime fecha;
	private String descripcion;
	private int megusta;
	private List<Comentario> comentarios;
	
	// Constructor
	public Publicacion(String titulo, String descripcion, LocalDateTime fecha, Usuario usuario) {
		this.titulo=titulo;
		this.descripcion=descripcion;
		this.usuario=usuario;
		this.fecha=fecha;
		megusta=0;
		comentarios = new ArrayList<Comentario>();
		codigo = 0;
	}
	
	public void darMeGusta() {
		megusta++;	
	}
	
	public void quitarMeGusta() {
		megusta--;	
	}
	
	public Comentario publicarComentario(Usuario usuario, String text) {
		//Creamos un objeto comentario
		Comentario c= new Comentario(usuario.getUsuario()+": "+text);
		
		//Añadimos a la publicacion el comentario
		addComentario(c);
		return c;
	}
	
	
	public void addComentario(Comentario c) {
		comentarios.add(c);
	}
	
	/**
	 * Dada una cadena de texto, obtiene todos los hashtags
	 * @param texto
	 * @return array con los hashtags
	 */
	public List<String> procesarHashtags(String texto) {
		Pattern MY_PATTERN = Pattern.compile("#(\\S{1,15})\\b");
		Matcher mat = MY_PATTERN.matcher(texto);
		List<String> hash = new ArrayList<String>();
		while (mat.find()) {
		  hash.add(mat.group(1));
		}
		return hash;
	}
	
	/**
	 * Obtiene la portada de una publicacion sin importar si es un album o foto
	 * @param codigo de la publicacion
	 * @return
	 */
	abstract public String obtenerPortadaPublicacion();
	
	// Metodos get / set
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getMegusta() {
		return megusta;
	}

	public void setMegusta(int megusta) {
		this.megusta = megusta;
	}
	
	public List<String> getHashtags() {
		return procesarHashtags(descripcion);
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
	
	public int getNumComentarios() {
		return comentarios.size();
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, comentarios, descripcion, fecha, megusta, titulo);
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
				&& megusta == other.megusta
				&& Objects.equals(titulo, other.titulo) && Objects.equals(usuario, other.usuario);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
