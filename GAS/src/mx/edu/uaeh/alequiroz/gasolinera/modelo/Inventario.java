package mx.edu.uaeh.alequiroz.gasolinera.modelo;

public class Inventario {

	private Integer idInventario;
	
	private String nombre;
	
	private double precio;

	public Integer getIdInventario() {
		return idInventario;
	}

	public void setIdInventario(Integer idInventario) {
		this.idInventario = idInventario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return this.nombre + " --- " + this.precio;
	}
	
	
}
