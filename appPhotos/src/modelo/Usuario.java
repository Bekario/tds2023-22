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
	private List<String> usuariosSeguidos; //Los seguidos estan representados con su codigo
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
		usuariosSeguidos = new ArrayList<String>();
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
	
	/**
	 * Comprueba si este usuario sigue a otro usuario
	 * @param usuario que se va a comprobar
	 * @return booleano indicando si esta seguido o no
	 */
	public boolean comprobarSeguido(Usuario usuario) {
		String codigo = String.valueOf(usuario.getCodigo());
		 return usuariosSeguidos.stream()
						.anyMatch(u -> u.equals(codigo));
	}
	
	/**
	 * Este usuario sigue a usuario
	 * @param usuario al que se va a seguir
	 */
	public void seguir(Usuario usuario) {
		String codigo = String.valueOf(usuario.getCodigo());
		usuariosSeguidos.add(codigo);
	}
	
	/**
	 * Este usuario es seguido por otro usuario
	 * @param usuario que sigue a este usuario
	 */
	public void seguido(Usuario usuario) {
		String codigo = String.valueOf(usuario.getCodigo());
		usuariosSeguidores.add(codigo);
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
	
	public List<String> getUsuariosSeguidos() {
		return usuariosSeguidos;
	}
	
	public List<String> getUsuariosSeguidosNombre() {
		List<String> nombresUsuarios = new ArrayList<String>();
		//MALENIA STREAM
		for(String u: usuariosSeguidos) {
			nombresUsuarios.add(RepoUsuarios.getUnicaInstancia().getUsuario(Integer.valueOf(u)).getUsuario());
		}
		return nombresUsuarios;
	}
	
	public void setUsuariosSeguidores(List<String> usuariosSeguidores) {
		this.usuariosSeguidores = usuariosSeguidores;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public void setUsuariosSeguidos(List<String> usuariosSeguidos) {
		this.usuariosSeguidos = usuariosSeguidos;
	}
	
	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
	}
	public int getNumeroSeguidores() {
		return this.usuariosSeguidores.size();
	}
	public int getNumeroSeguidos() {
		return this.usuariosSeguidos.size();
	}
	public int getNumeroPublicaciones() {
		return this.albums.size()+this.fotos.size();
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
				&& Objects.equals(usuariosSeguidores, other.usuariosSeguidores)
				&& Objects.equals(usuariosSeguidos, other.usuariosSeguidos);
	}
	
	
	
}
