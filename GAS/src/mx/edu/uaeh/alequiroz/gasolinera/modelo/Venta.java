package mx.edu.uaeh.alequiroz.gasolinera.modelo;

public class Venta {
	
	private Integer idVenta;
	private Integer fkUsuario;
	private Double importe;
	private Long fechaVenta;
	public Integer getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}
	public Integer getFkUsuario() {
		return fkUsuario;
	}
	public void setFkUsuario(Integer fkUsuario) {
		this.fkUsuario = fkUsuario;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public Long getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(Long fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	
	

}
