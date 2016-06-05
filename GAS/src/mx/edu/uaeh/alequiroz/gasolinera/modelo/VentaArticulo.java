package mx.edu.uaeh.alequiroz.gasolinera.modelo;

public class VentaArticulo {

	private Integer idVentaArticulo;
	private Integer fkVenta;
	private Integer fkInventario;
	private Double cantidad;
	private Double subtotal;
	
	public Integer getIdVentaArticulo() {
		return idVentaArticulo;
	}
	public void setIdVentaArticulo(Integer idVentaArticulo) {
		this.idVentaArticulo = idVentaArticulo;
	}
	public Integer getFkVenta() {
		return fkVenta;
	}
	public void setFkVenta(Integer fkVenta) {
		this.fkVenta = fkVenta;
	}
	public Integer getFkInventario() {
		return fkInventario;
	}
	public void setFkInventario(Integer fkInventario) {
		this.fkInventario = fkInventario;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	
}
