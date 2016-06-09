package mx.edu.uaeh.alequiroz.gasolinera.modelo;

public class Usuario {

	private Integer idUsuario;
	private String usuario;
	private String password;
	private Integer rol;
	
	public static final int ROL_ADMIN = 1;
	
	public Usuario() {
		this(null, null, null, null);
	}
	
	public Usuario(Integer idUsuario, String usuario, String password, Integer rol) {
		this.idUsuario = idUsuario;
		this.usuario = usuario;
		this.password = password;
		this.rol = rol;
	}
	
	public Integer getId() {
		return this.idUsuario;
	}
	
	public void setId(Integer id) {
		this.idUsuario = id;
	}
	
	public String getUsuario() {
		return this.usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getRol() {
		return this.rol;
	}
	
	public void setRol(Integer rol) {
		this.rol = rol;
	}
	
}
