package cabrerizo.luis.tarea4.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idfoto;
	private String url;
	private String descripcion;
	private ArrayList<Comment> listaComentarios;
	private int numeroFavoritos;
	private int esFavorito;

	public String getUrl() {
		return url;
	}

	public int getIdfoto() {
		return idfoto;
	}

	public void setIdfoto(int idfoto) {
		this.idfoto = idfoto;
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

	public int getEsFavorito() {
		return esFavorito;
	}

	public void setEsFavorito(int esFavorito) {
		this.esFavorito = esFavorito;
	}
}
