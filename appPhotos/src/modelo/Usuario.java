package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import controlador.Controlador;

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
	
	//Descuentos
	private float precio;
	private IDescuento reglaDescuento;
	
	//Panel Seleccionar
	private List<Foto> seleccionados;
	private Publicacion portadaSeleccionada;
	
	//Numero de MGs necesarios para el descuento
	private final int ME_GUSTAS = 20;
	//Edades entre las que se aplica el descuento
	private final int EDAD_MIN_JOVEN = 18;
	private final int EDAD_MAX_JOVEN = 25;
	private final int EDAD_MIN_MAYOR = 65;
	
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
		
		reglaDescuento = new DescuentoNull();
		precio = Variables.precioPremium;
		
		portadaSeleccionada = null;
		seleccionados = new ArrayList<Foto>();
	}
	
	public boolean comprobarDescuento(IDescuento descuento) {
		//Comprobamos si se puede aplicar el de edad
		int edad = Period.between(getFechaNacimiento(), LocalDate.now()).getYears();

		if(((edad >= EDAD_MIN_JOVEN && edad <= EDAD_MAX_JOVEN) || (edad >= EDAD_MIN_MAYOR)) && descuento.getClass().getName().equals("modelo.DescuentoEdad")) {
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
	
	public float obtenerPrecio() {
		return reglaDescuento.aplicarDescuento(precio);
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
	
	public Foto subirFoto(String titulo, String descripcion, String path) {
		Foto publi = new Foto(titulo, descripcion, LocalDateTime.now(), this, path);
		addFoto(publi);
		
		notificarSeguidores(publi);
		
		return publi;
	}
	
	public Album subirAlbum(String titulo, String descripcion) {
		//Creamos el album
		Album publi = new Album(titulo, descripcion, LocalDateTime.now(), this, (Foto)portadaSeleccionada);
		
		//Introducimos las fotos en el album
		seleccionados.stream()
			 .forEachOrdered(f -> publi.addFoto((Foto) f));
		
		//Añadimos el album al usuario
		addAlbum(publi);
		
		//Limpiamos seleccionados para el proximo album
		borrarSeleccionados();
		
		notificarSeguidores(publi);
		
		return publi;
	}
	
	public Album modificarAlbum(Album album, String titulo, String descripcion) {
		album.setTitulo(titulo);
		album.setDescripcion(descripcion);
		album.setPortada((Foto) portadaSeleccionada);
		album.setFotos(seleccionados);
		
		return album;
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
	
	//Elimina las publicaciones seleccionadas
	public void borrarSeleccionados() {
		portadaSeleccionada = null;
		seleccionados.clear();
	}
	
	/**
	 * Añade a la lista de publicaciones seleccionadas para crear el album
	 * @param p
	 * @return true si es posible añadirla
	 */
	public boolean addSeleccionado(Publicacion p) {
		//Comprobamos que la lista de seleccionados solo tenga 15
		if (seleccionados.size() < 15) {
			seleccionados.add((Foto) p);
			return true;
		}
		return false;
	}
	
	/**
	 * Elimina una publicacion de la lista seleccionados
	 * @param p
	 */
	public void removeSeleccionado(Publicacion p) {
		seleccionados.remove(p);
	}
	
	public List<Publicacion> getPublicacionesSubidasSeguidores(){
		List<Publicacion> pub= new ArrayList<Publicacion>(getPublicaciones());			
		
		usuariosSeguidos.stream().parallel()
						.forEach(u -> pub.addAll(u.getPublicaciones()));
		
		//Ordenamos la lista de todas las publicaciones del ususario y sus seguidos por fecha
		Collections.sort(pub, (p1, p2) -> p2.getFecha().compareTo(p1.getFecha()));
		
		//Retornamos una lista de 20 o menos
		if(pub.size() > 20) {
			return pub.subList(0, 20);	 
		} 
		 
		return pub;
	}
	
	/**
	 * Obtiene las 10 publicacion con mas me gustas
	 * @return
	 */
	public List<Publicacion> getPublicacionesTop() {
		List<Publicacion> pub= new ArrayList<Publicacion>(fotos);			
		
		//Ordenamos por numero de me gustas
		Collections.sort(pub, (p1, p2) -> (Integer.compare(p2.getMegusta(), p1.getMegusta())));
		
		//Obtenemos una lista de 10 o menos
		if(pub.size()>10) {
			pub= pub.subList(0, 10);	 
		}
		return pub;
	}
	
	/**
	 * Notifica a todos los seguidores del usuario sobre la publicacion subida
	 * @param publicacion sobre la que se va a notificar
	 */
	public void notificarSeguidores(Publicacion publicacion) {
		Notificacion n = new Notificacion(LocalDate.now(), publicacion);
		
		Controlador.getInstancia().persistirNotificacion(n);
		
		//Añadimos la notificacion a cada usuario y guardamos los cambios en el DAO y repositorio
		getUsuariosSeguidores().stream().parallel()
							   			.forEach(u -> {u.addNotificacion(n); Controlador.getInstancia().actualizarUsuario(u);});

	}
	
	//Metodos Get / Set
	public Publicacion getPortadaSeleccionada() {
		return portadaSeleccionada;
	}
	
	public void setPortadaSeleccionada(Publicacion portadaSeleccionada) {
		this.portadaSeleccionada = portadaSeleccionada;
	}
	
	/**
	 * Retorna la lista de publicaciones seleccionadas para crear un album
	 * @return
	 */
	public List<Foto> getSeleccionados() {
		return seleccionados;
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
	
	public void setReglaDescuento(IDescuento reglaDescuento) {
		this.reglaDescuento = reglaDescuento;
	}
	
	public IDescuento getReglaDescuento() {
		return reglaDescuento;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(albums, codigo, contraseña, descripcion, email,
				fechaNacimiento, fotos, isPremium, nombreCompleto, notificaciones, perfil, usuario);
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
		return EDAD_MAX_JOVEN == other.EDAD_MAX_JOVEN && EDAD_MIN_JOVEN == other.EDAD_MIN_JOVEN && ME_GUSTAS == other.ME_GUSTAS
				&& Objects.equals(albums, other.albums) && codigo == other.codigo
				&& Objects.equals(contraseña, other.contraseña) && Objects.equals(descripcion, other.descripcion)
				&& Objects.equals(email, other.email) && Objects.equals(fechaNacimiento, other.fechaNacimiento)
				&& Objects.equals(fotos, other.fotos) && isPremium == other.isPremium
				&& Objects.equals(nombreCompleto, other.nombreCompleto)
				&& Objects.equals(notificaciones, other.notificaciones) && Objects.equals(perfil, other.perfil)
				&& Objects.equals(usuario, other.usuario)
				&& Objects.equals(usuariosSeguidores, other.usuariosSeguidores)
				&& Objects.equals(usuariosSeguidos, other.usuariosSeguidos);
	}
}
