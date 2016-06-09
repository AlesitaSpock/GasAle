package mx.edu.uaeh.alequiroz.gasolinera.controlador;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.DBHelper;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Inventario;

public class CatalogoInventarioControlador {

	@FXML
	private TableView<Inventario> tablaInventario;
	
	@FXML
	private TableColumn<Inventario, Integer> columnaID;
	@FXML
	private TableColumn<Inventario, String> columnaNombre;
	@FXML
	private TableColumn<Inventario, Double> columnaPrecio;
	
	@FXML
	private TableColumn columnaEliminar;
	
	private List<Inventario> listaInventario;
	
	@FXML
	public void initialize() {
		try {
			renderizarTabla();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void renderizarTabla() throws Exception {
		listaInventario = DBHelper.obtenerInventario();
		columnaID.setCellValueFactory(new PropertyValueFactory<Inventario, Integer>("idInventario"));
		columnaNombre.setCellValueFactory(new PropertyValueFactory<Inventario, String>("nombre"));
		columnaPrecio.setCellValueFactory(new PropertyValueFactory<Inventario, Double>("precio"));
		
		tablaInventario.getItems().addAll(listaInventario);
	}
}
