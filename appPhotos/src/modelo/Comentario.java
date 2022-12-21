package modelo;

public class Comentario {
	private int codigo;
	private String texto;
	
	// Comentario
	public Comentario(String texto) {
		this.texto = texto;
		this.codigo = 0;
	}
	
	// Metodos get / set
	public String getTexto() {
		return texto;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
