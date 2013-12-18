package cabrerizo.luis.tarea4.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private String direccion;
	private String telefono;
	private String horarios;
	private String website;
	private int tipoTienda;
	private String email;
	private ArrayList<Comment> listaComentarios;
	private int numeroFavoritos;
	private int esFavorito;
	private double[] ubicacionGeografica;
	private Photo foto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int getTipoTienda() {
		return tipoTienda;
	}

	public void setTipoTienda(int tipoTienda) {
		this.tipoTienda = tipoTienda;
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
