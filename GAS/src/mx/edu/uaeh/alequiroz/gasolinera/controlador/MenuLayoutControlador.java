package mx.edu.uaeh.alequiroz.gasolinera.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import mx.edu.uaeh.alequiroz.gasolinera.Main;

public class MenuLayoutControlador {
	
	private Main main;

	@FXML
	private MenuItem cerrarSesion;
	
	@FXML
	private MenuItem salir;
	
	@FXML
	private MenuItem consultarVenta;
	
	@FXML
	private MenuItem nuevaVenta;
	
	@FXML
	private MenuItem nuevoUsuario;
	
	@FXML
	private MenuItem agregarInventario;
	
	@FXML
	private MenuItem catalogoArticulos;
	
	
	@FXML
	private void initialize() {
		
		cerrarSesion.setOnAction((ActionEvent e) -> {
			Main.usuarioAutenticado = null;
			recalcularOpcionesMenu();
			main.iniciarPantallaLogin();
		});
		
		salir.setOnAction((ActionEvent e) -> System.exit(0));
		
		consultarVenta.setOnAction((ActionEvent e) -> main.iniciarPantallaConsultaVenta());
		
		nuevaVenta.setOnAction((ActionEvent e) -> main.iniciarPantallaVenta());
		
		nuevoUsuario.setOnAction((ActionEvent e) -> main.iniciarPantallaRegistroUsuario());
		
		agregarInventario.setOnAction((ActionEvent e) -> main.iniciarPantallaRegistroInventario());
		
		catalogoArticulos.setOnAction((ActionEvent e) -> main.iniciarPantallaListadoInventario());
		
		recalcularOpcionesMenu();
	}
	
	public void recalcularOpcionesMenu() {
		consultarVenta.setDisable(Main.usuarioAutenticado == null);
		nuevaVenta.setDisable(Main.usuarioAutenticado == null);
		nuevoUsuario.setDisable(Main.usuarioAutenticado == null);
		agregarInventario.setDisable(Main.usuarioAutenticado == null);
		catalogoArticulos.setDisable(Main.usuarioAutenticado == null);
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}
