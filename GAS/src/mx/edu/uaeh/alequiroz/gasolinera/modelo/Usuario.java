package mx.edu.uaeh.alequiroz.gasolinera.modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usuario {

	private final IntegerProperty idUsuario;
	private final StringProperty usuario;
	private final StringProperty password;
	private final IntegerProperty rol;
	
	public Usuario() {
		this(null, null, null, null);
	}
	
	public Usuario(Integer idUsuario, String usuario, String password, Integer rol) {
		this.idUsuario = new SimpleIntegerProperty(idUsuario);
		this.usuario = new SimpleStringProperty(usuario);
		this.password = new SimpleStringProperty(password);
		this.rol = new SimpleIntegerProperty(rol);
	}
	
	public Integer getId() {
		return this.idUsuario.get();
	}
	
	public void setId(Integer id) {
		this.idUsuario.set(id);
	}
	
	public String getUsuario() {
		return this.usuario.get();
	}
	
	public void setUsuario(String usuario) {
		this.usuario.set(usuario);
	}
	
	public String getPassword() {
		return this.password.get();
	}
	
	public void setPassword(String password) {
		this.password.set(password);
	}
	
	public Integer getRol() {
		return this.rol.get();
	}
	
	public void setRol(Integer rol) {
		this.rol.set(rol);
	}
	
}
