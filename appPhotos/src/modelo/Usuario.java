package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String nombre;
	private String email;
	private String nombreCompleto;
	private LocalDate fechaNacimiento;
	private boolean isPremium;
	private List<Usuario> usuariosSeguidores;
	private List<Notificacion> notificaciones;
	private List<Publicacion> fotos;
	
	//Constructor
	public Usuario(String nombre, String email, String nombreCompleto, LocalDate fechaNacimiento) {
		this.nombre = nombre;
		this.email = email;
		this.nombreCompleto = nombreCompleto;
		this.fechaNacimiento = fechaNacimiento;
		this.isPremium = false; //Inicialmente un usuario no es Premium
		this.usuariosSeguidores = new ArrayList<Usuario>();
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
	
	
}
