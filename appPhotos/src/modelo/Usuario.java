package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private int codigo;
	private String nombre;
	private String email;
	private String nombreCompleto;
	private LocalDate fechaNacimiento;
	private boolean isPremium;
	private List<Usuario> usuariosSeguidores;
	private List<Notificacion> notificaciones;
	private List<Publicacion> fotos;
	private Descuento descuento;
	
	//Constructor
	public Usuario(String nombre, String email, String nombreCompleto, LocalDate fechaNacimiento) {
		this.nombre = nombre;
		this.email = email;
		this.nombreCompleto = nombreCompleto;
		this.fechaNacimiento = fechaNacimiento;
		isPremium = false; //Inicialmente un usuario no es Premium
		usuariosSeguidores = new ArrayList<Usuario>();
		codigo = 0;
	}
	
	//Metodos
	public float calcularPrecioPremium(){
		if (descuento == NULL) {
			return Variables.precioPremium;
		}
		return descuento.aplicarDescuento(this);
	}
	
	//Metodos Get / Set
	public String getNombre() {
		return nombre;
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
	
	public int getCodigo() {
		return codigo;
	}
	
	public List<Publicacion> getFotos() {
		return fotos;
	}
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}
	
	public List<Usuario> getUsuariosSeguidores() {
		return usuariosSeguidores;
	}
	
	
}
