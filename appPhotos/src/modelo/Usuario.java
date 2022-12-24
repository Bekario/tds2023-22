package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	private List<Publicacion> fotos;
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
		usuariosSeguidores = new ArrayList<Usuario>();
		codigo = 0;
		indicePubliacion = 0;
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
	
	public void addPublicacion(Publicacion p) {
		fotos.add(p);

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
	
	public List<Publicacion> getFotos() {
		return fotos;
	}
	
	public String getContraseña() {
		return contraseña;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public String getDescripcion() {
		return descripcion;
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
	
}
