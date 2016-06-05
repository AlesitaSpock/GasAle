package mx.edu.uaeh.alequiroz.gasolinera.controlador;



import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Articulo;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.DBHelper;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Inventario;

public class VentaGasolineraControlador {

	@FXML
	private TextField numVenta;
	
	@FXML
	private ChoiceBox<Inventario> buscaProducto;
	
	@FXML
	private TextField cantidad;
	
	@FXML
	private TextField monto;
	
	@FXML
	private Button botonAgregar;
	
	@FXML
	private Button cancelar;
	
	@FXML
	private Button cobrar;
	
	@FXML
	private TableView<Articulo> tablaVenta;
	
	private List<Inventario> inventario;
	
	@FXML
	private void initialize() {
		poblarListadoInventario();
		calcularNuevoIdParaNumVenta();
		cobrar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				agregarEnLista();
			}
		});
	}

	private void poblarListadoInventario() {
		try {
			inventario = DBHelper.obtenerInventario();
			for (Inventario elemento : inventario) {
				buscaProducto.getItems().add(elemento);
			}
			buscaProducto.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					System.out.println("di click");
					System.out.println(buscaProducto.getValue().getNombre());
					System.out.println(buscaProducto.getValue().getPrecio());
					
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void calcularNuevoIdParaNumVenta() {
		try {
			int nuevoIdParaVenta = DBHelper.obtenerNuevoIdParaNumVenta();
			numVenta.setText(String.valueOf(nuevoIdParaVenta));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void agregarEnLista() {
		Articulo articulo = new Articulo();
		articulo.setCantidad(Double.parseDouble(cantidad.getText()));
		//buscaProducto.getItems().get
		//articulo.setDescripcion(descripcion);
	}
	
	
	
	
	
}
