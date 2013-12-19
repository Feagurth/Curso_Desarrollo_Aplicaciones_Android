package cabrerizo.luis.tarea4.data.models;

import java.io.Serializable;

public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	private int IdComentario;
	private String comentario;
	private String fecha;

	public int getIdComentario() {
		return IdComentario;
	}

	public void setIdComentario(int idComentario) {
		IdComentario = idComentario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
