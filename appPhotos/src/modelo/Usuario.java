package modelo;

import java.time.LocalDate;
import java.time.Period;
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
	
	//Numero de MGs necesarios para el descuento
	private final int ME_GUSTAS = 1000;
	//Edades entre las que se aplica el descuento
	private final int EDAD_MIN = 18;
	private final int EDAD_MAX = 25;
	
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
		usuariosSeguidores = new ArrayList<String>();
		usuariosSeguidos = new ArrayList<String>();
		notificaciones = new ArrayList<Notificacion>();
		albums = new ArrayList<Album>();
		fotos = new ArrayList<Foto>();
	}
	
	//Metodos
	public float calcularPrecioPremium(float precio){
		comprobarDescuento(); //MALENIA
		if (descuento == null) {
			return precio;
		}
		return descuento.aplicarDescuento(precio);
	}
	
	public void comprobarDescuento() {
		int edad = Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
		int numMG = getFotos().stream()
				.map(mg -> mg.getMegusta())
				.reduce(0, (accum, mg) -> accum + mg);
		
		if(edad >= EDAD_MIN && edad <= EDAD_MAX) {
			setDescuento(new DescuentoEdad());
		} else if(numMG >= ME_GUSTAS) {
			setDescuento(new DescuentoPopularidad());
		} else {
			setDescuento(null); //MALENIA
		}
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
	public void seguirA(Usuario usuario) {
		String codigo = String.valueOf(usuario.getCodigo());
		//Añadimos a la lista de usuarios seguidos
		usuariosSeguidos.add(codigo);
		
		//Añadimos en la la lista de usuarios seguidores a este usuario
		usuario.addSeguidores(this.codigo);
	}
	
	/**
	 * Este usuario deja de seguir a usuario
	 * @param usuario al que se va a dejar de seguir
	 */
	public void dejarDeSeguirA(Usuario usuario) {
		String codigo = String.valueOf(usuario.getCodigo());
		//Quitamos de la lista de usuarios seguidos
		usuariosSeguidos.remove(codigo);
		
		//Quitamos de la la lista de usuarios seguidores a este usuario
		usuario.removeSeguidores(this.codigo);
	}
	
	private void addSeguidores(int codigo) {
		usuariosSeguidores.add(String.valueOf(codigo));
	}
	
	private void removeSeguidores(int codigo) {
		usuariosSeguidores.remove(String.valueOf(codigo));
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
	
	public List<Usuario> getUsuariosSeguidoresOb() {
		List<Usuario> Usuarios = new ArrayList<Usuario>();
		//MALENIA STREAM
		for(String u: usuariosSeguidores) {
			Usuarios.add(RepoUsuarios.getUnicaInstancia().getUsuario(Integer.valueOf(u)));
		}
		return Usuarios;
	}
	
	public List<String> getUsuariosSeguidos() {
		return usuariosSeguidos;
	}
	
	/*
	 * Obtiene los usuarios que este usuario sigue
	 */
	public List<String> getUsuariosSeguidosNombre() {
		List<String> nombresUsuarios = new ArrayList<String>();
		//MALENIA STREAM
		for(String u: usuariosSeguidos) {
			nombresUsuarios.add(RepoUsuarios.getUnicaInstancia().getUsuario(Integer.valueOf(u)).getUsuario());
		}
		return nombresUsuarios;
	}
	public List<Usuario> getUsuariosSeguidosOb() {
		List<Usuario> Usuarios = new ArrayList<Usuario>();
		//MALENIA STREAM
		for(String u: usuariosSeguidos) {
			Usuarios.add(RepoUsuarios.getUnicaInstancia().getUsuario(Integer.valueOf(u)));
		}
		return Usuarios;
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
	
	public void setUsuariosSeguidores(List<String> usuariosSeguidores) {
		this.usuariosSeguidores = usuariosSeguidores;
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
				&& isPremium == other.isPremium
				&& Objects.equals(nombreCompleto, other.nombreCompleto)
				&& Objects.equals(notificaciones, other.notificaciones) && Objects.equals(perfil, other.perfil)
				&& Objects.equals(usuario, other.usuario)
				&& Objects.equals(usuariosSeguidores, other.usuariosSeguidores)
				&& Objects.equals(usuariosSeguidos, other.usuariosSeguidos);
	}
	
	
	
}
