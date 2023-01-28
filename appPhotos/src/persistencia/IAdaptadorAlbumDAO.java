package persistencia;

import java.util.List;
import modelo.Album;

public interface IAdaptadorAlbumDAO {
	
	public void registrarAlbum(Album album);
	public void borrarAlbum(Album album);
	public void modificarAlbum(Album album);
	public Album recuperarAlbum(int codigo);
	public List<Album> recuperarTodosAlbum();
}
