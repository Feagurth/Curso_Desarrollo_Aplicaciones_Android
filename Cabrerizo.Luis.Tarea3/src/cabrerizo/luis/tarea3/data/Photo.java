package cabrerizo.luis.tarea3.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String url;
	private String descripcion;
	private ArrayList<Comment> listaComentarios;
	private int numeroFavoritos;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public ArrayList<Comment> getListaComentarios() {
		return listaComentarios;
	}
	public void setListaComentarios(ArrayList<Comment> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}
	public int getNumeroFavoritos() {
		return numeroFavoritos;
	}
	public void setNumeroFavoritos(int numeroFavoritos) {
		this.numeroFavoritos = numeroFavoritos;
	}	
}
