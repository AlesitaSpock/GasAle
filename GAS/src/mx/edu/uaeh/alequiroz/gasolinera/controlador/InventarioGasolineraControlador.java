package mx.edu.uaeh.alequiroz.gasolinera.controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.DBHelper;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Inventario;


public class InventarioGasolineraControlador {
	@FXML
	private TextField nombreProd;
	
	@FXML
	private TextField costoUnitario;
	
	
	@FXML
	private Button botonAgregarRegistro;
	
		
	@FXML	
    private void initialize() {
		
		botonAgregarRegistro.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				agregarRegistro();
			}
		});
	}
	
	private void agregarRegistro() {
		String cadenaNombreProducto = nombreProd.getText();
		double costo = 0;
		try {
			costo = Double.parseDouble(costoUnitario.getText());
		} catch (Exception e) {
			new Alert(AlertType.ERROR, "El campo Costo Unitario no es un numero válido",
					ButtonType.OK).showAndWait();
			return;
		}
		Inventario inventario = new Inventario();
		inventario.setNombre(cadenaNombreProducto);
		inventario.setPrecio(costo);
		try {
			DBHelper.agregarInventario(inventario);
			new Alert(AlertType.INFORMATION, "El registro del nuevo producto se ha realizado con éxito.",
					ButtonType.OK).showAndWait();
			limpiarCampos();
		} catch (Exception e) {
			new Alert(AlertType.ERROR, "No se pudo insertar en la base de datos. Invente nuevamente",
					ButtonType.OK).showAndWait();
			e.printStackTrace();
			return;
		}
	}
	
	private void limpiarCampos() {
		nombreProd.clear();
		costoUnitario.clear();
	}
	
}
