package cabrerizo.luis.tarea3.data;

import java.io.Serializable;

public class Comment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String comentario;

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
		
}
