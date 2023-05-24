package modelo;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
	private List<Usuario> usuariosSeguidores;
	private List<Usuario> usuariosSeguidos;
	private List<Notificacion> notificaciones;
	private List<Foto> fotos;
	private List<Album> albums;
	
	//Numero de MGs necesarios para el descuento
	private final int ME_GUSTAS = 20;
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
		usuariosSeguidores = new ArrayList<Usuario>();
		usuariosSeguidos = new ArrayList<Usuario>();
		notificaciones = new ArrayList<Notificacion>();
		albums = new ArrayList<Album>();
		fotos = new ArrayList<Foto>();
	}
	
	public boolean comprobarDescuento(IDescuento descuento) {
		//Comprobamos si se puede aplicar el de edad
		int edad = Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
		if(edad >= EDAD_MIN && edad <= EDAD_MAX && descuento.getClass().getName().equals("modelo.DescuentoEdad")) {
			return true;
		} 
		//Comprobamos si se puede aplicar el de popularidad
		int numMG = getFotos().stream()
				.map(mg -> mg.getMegusta())
				.reduce(0, (accum, mg) -> accum + mg);
		
		if(numMG >= ME_GUSTAS  && descuento.getClass().getName().equals("modelo.DescuentoPopularidad")) {
			return true;
		} 
		
		//El precio sin descuento siempre se puede aplicar
		if (descuento.getClass().getName().equals("modelo.DescuentoNull")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Elimina una notificacion
	 * @param p publicacion que esta asociada a la notificacion
	 */
	public boolean removeNotificacion(Publicacion p) {
		Notificacion notificacion = notificaciones.stream().parallel()
							   .filter(n -> n.getPublicacion().equals(p))
							   .findAny()
							   .orElse(null);
		if (notificacion == null) {
			return false;
		} else {
			removeNotificacion(notificacion);
			return true;
		}
	}
	
	/**
	 * Comprueba si este usuario sigue a otro usuario
	 * @param usuario que se va a comprobar
	 * @return booleano indicando si esta seguido o no
	 */
	public boolean comprobarSeguido(Usuario usuario) {
		 return usuariosSeguidos.stream()
						.anyMatch(u -> u.equals(usuario));
	}
	
	/**
	 * Este usuario sigue a usuario
	 * @param usuario al que se va a seguir
	 */
	public void seguirA(Usuario usuario) {
		//Añadimos a la lista de usuarios seguidos
		usuariosSeguidos.add(usuario);
		
		//Añadimos en la la lista de usuarios seguidores a este usuario
		usuario.addSeguidores(this);
	}
	
	/**
	 * Este usuario deja de seguir a usuario
	 * @param usuario al que se va a dejar de seguir
	 */
	public void dejarDeSeguirA(Usuario usuario) {
		//Quitamos de la lista de usuarios seguidos
		usuariosSeguidos.remove(usuario);
		
		//Quitamos de la la lista de usuarios seguidores a este usuario
		usuario.removeSeguidores(this);
	}
	
	private void addSeguidores(Usuario usuario) {
		usuariosSeguidores.add(usuario);
	}
	
	private void removeSeguidores(Usuario usuario) {
		usuariosSeguidores.remove(usuario);
	}
	
	public void addFoto(Foto p) {
		fotos.add(p);
	}
	
	public void removeFoto(Foto p) {
		fotos.remove(p);
	}
	
	public void addAlbum(Album a) {
		albums.add(a);
	}
	
	public void removeAlbum(Album a) {
		albums.remove(a);
	}
	
	public void addNotificacion(Notificacion n) {
		notificaciones.add(n);
	}
	
	public void removeNotificacion(Notificacion n) {
		notificaciones.remove(n);
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
	public List<Publicacion> getPublicaciones(){
		List<Publicacion> l = new ArrayList<Publicacion>(fotos);
		l.addAll(albums);
		return l;
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
	
	public List<Usuario> getUsuariosSeguidores() {
		return usuariosSeguidores;
	}
	
	public List<Usuario> getUsuariosSeguidos() {
		return usuariosSeguidos;
	}
	
	/*
	 * Obtiene los usuarios que este usuario sigue
	 */
	public List<Usuario> getUsuariosSeguidosNombre() {
		return usuariosSeguidos.stream()
				.map(u -> RepoUsuarios.getUnicaInstancia().getUsuario(u.getCodigo()))
				.collect(Collectors.toList());
				/*
				new ArrayList<String>();
		//MALENIA STREAM
		for(String u: usuariosSeguidos) {
			nombresUsuarios.add(RepoUsuarios.getUnicaInstancia().getUsuario(Integer.valueOf(u)).getUsuario());
		}
		return nombresUsuarios;
				 */
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
	
	public void setUsuariosSeguidores(List<Usuario> usuariosSeguidores) {
		this.usuariosSeguidores = usuariosSeguidores;
	}

	public void setUsuariosSeguidos(List<Usuario> usuariosSeguidos) {
		this.usuariosSeguidos = usuariosSeguidos;
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
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento) && Objects.equals(fotos, other.fotos)
				&& isPremium == other.isPremium
				&& Objects.equals(nombreCompleto, other.nombreCompleto)
				&& Objects.equals(notificaciones, other.notificaciones) && Objects.equals(perfil, other.perfil)
				&& Objects.equals(usuario, other.usuario)
				&& Objects.equals(usuariosSeguidores, other.usuariosSeguidores)
				&& Objects.equals(usuariosSeguidos, other.usuariosSeguidos);
	}
	
	
	
}
