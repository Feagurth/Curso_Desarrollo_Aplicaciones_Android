package cabrerizo.luis.tarea4.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String direccion;
	private String telefono;
	private String horarios;
	private String website;
	private String email;
	private ArrayList<Comment> listadoComentarios;
	private int numeroFavoritos;
	private double[] ubicacionGeografica;
	private Photo foto;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getHorarios() {
		return horarios;
	}

	public void setHorarios(String horarios) {
		this.horarios = horarios;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Comment> getListadoComentarios() {
		return listadoComentarios;
	}

	public void setListadoComentarios(ArrayList<Comment> listadoComentarios) {
		this.listadoComentarios = listadoComentarios;
	}

	public int getNumeroFavoritos() {
		return numeroFavoritos;
	}

	public void setNumeroFavoritos(int numeroFavoritos) {
		this.numeroFavoritos = numeroFavoritos;
	}

	public double[] getUbicacionGeografica() {
		return ubicacionGeografica;
	}

	public void setUbicacionGeografica(double[] ubicacionGeografica) {
		this.ubicacionGeografica = ubicacionGeografica;
	}

	public Photo getFoto() {
		return foto;
	}

	public void setFoto(Photo foto) {
		this.foto = foto;
	}
}
