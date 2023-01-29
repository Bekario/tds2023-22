package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
	private int codigo;
	private String usuario;
	private String contraseña;
	private String email;
	private String nombreCompleto;
	private LocalDate fechaNacimiento;
	private boolean isPremium;
	private String perfil;
	private String descripcion;
	private List<String> usuariosSeguidores; //Los seguidores estan representados con su codigo
	private List<Notificacion> notificaciones;
	private List<Foto> fotos;
	private List<Album> albums;
	private Descuento descuento; //Optional malenia
	
	//Indice para numerar las fotos subidas por el usuario
	private int indicePubliacion;
	
	//Constructor
	public Usuario(String usuario, String contraseña, String email, String nombreCompleto, LocalDate fechaNacimiento, String perfil, String descripcion) {
		this.usuario = usuario;
		this.email = email;
		this.nombreCompleto = nombreCompleto;
		this.fechaNacimiento = fechaNacimiento;
		this.contraseña = contraseña;
		this.perfil = perfil;
		this.descripcion = descripcion;
		isPremium = false; //Inicialmente un usuario no es Premium
		codigo = 0;
		indicePubliacion = 0;
		usuariosSeguidores = new ArrayList<String>();
		notificaciones = new ArrayList<Notificacion>();
		albums = new ArrayList<Album>();
		fotos = new ArrayList<Foto>();
	}
	
	//Metodos
	public float calcularPrecioPremium(){
		if (descuento == null) {
			return Variables.precioPremium;
		}
		return descuento.aplicarDescuento(this);
	}
	
	public void aumentarIndicePublicacion() {
		indicePubliacion++;
	}
	
	public void addFoto(Foto p) {
		fotos.add(p);
	}
	
	public void addAlbum(Album a) {
		albums.add(a);
	}
	
	public void addNotificacion(Notificacion n) {
		notificaciones.add(n);
	}
	
	//Metodos Get / Set
	public int getIndicePubliacion() {
		return indicePubliacion;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public String getEmail() {
		return email;
	}
	
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
	public boolean getIsPremium() {
		return isPremium;
	}
	
	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public List<Foto> getFotos() {
		return fotos;
	}
	
	public List<Album> getAlbums() {
		return albums;
	}
	
	public String getContraseña() {
		return contraseña;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}
	
	public List<String> getUsuariosSeguidores() {
		return usuariosSeguidores;
	}
	
	public void setUsuariosSeguidores(List<String> usuariosSeguidores) {
		this.usuariosSeguidores = usuariosSeguidores;
	}
	
	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(albums, other.albums) && codigo == other.codigo
				&& Objects.equals(contraseña, other.contraseña) && Objects.equals(descripcion, other.descripcion)
				&& Objects.equals(descuento, other.descuento) && Objects.equals(email, other.email)
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento) && Objects.equals(fotos, other.fotos)
				&& indicePubliacion == other.indicePubliacion && isPremium == other.isPremium
				&& Objects.equals(nombreCompleto, other.nombreCompleto)
				&& Objects.equals(notificaciones, other.notificaciones) && Objects.equals(perfil, other.perfil)
				&& Objects.equals(usuario, other.usuario)
				&& Objects.equals(usuariosSeguidores, other.usuariosSeguidores);
	}
	
	
	
}
