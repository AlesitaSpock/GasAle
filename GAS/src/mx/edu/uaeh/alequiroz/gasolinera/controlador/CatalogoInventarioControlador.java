package mx.edu.uaeh.alequiroz.gasolinera.controlador;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
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
	private Button botonEliminar;
	
	@FXML
	private Button botonGuardar;
	
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
		tablaInventario.setEditable(true);
		tablaInventario.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listaInventario = DBHelper.obtenerInventario();
		columnaID.setCellValueFactory(new PropertyValueFactory<Inventario, Integer>("idInventario"));
		columnaNombre.setCellValueFactory(new PropertyValueFactory<Inventario, String>("nombre"));
		columnaPrecio.setCellValueFactory(new PropertyValueFactory<Inventario, Double>("precio"));
		columnaNombre.setCellFactory(TextFieldTableCell.forTableColumn());
		columnaPrecio.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		columnaNombre.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Inventario,String>>() {
			@Override
			public void handle(CellEditEvent<Inventario, String> event) {
				Inventario item = event.getRowValue();
				item.setNombre(event.getNewValue());
			}
		});
		columnaPrecio.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Inventario,Double>>() {
			@Override
			public void handle(CellEditEvent<Inventario, Double> event) {
				Inventario item = event.getRowValue();
				item.setPrecio(event.getNewValue());
			}
		});
		
		tablaInventario.getItems().addAll(listaInventario);
		
		botonGuardar.setOnAction((ActionEvent event)-> actualizar());
		botonEliminar.setOnAction((ActionEvent event)-> eliminar());
	}

	private void actualizar() {
		try {
			for (Inventario inventario : listaInventario) {
				DBHelper.actualizarInventario(inventario);
			}
			listaInventario = tablaInventario.getItems();
			new Alert(AlertType.INFORMATION, "El inventario se ha actualizado correctamente", ButtonType.OK).showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			new Alert(
					AlertType.ERROR,
					"Hubo un error al actulizar los artículos, favor de intentarlo nuevamente.",
					ButtonType.OK).showAndWait();
		}
	}
	
	private void eliminar() {
		ObservableList<Inventario> elementosParaEliminar = tablaInventario.getSelectionModel().getSelectedItems();
		try {
			for (Inventario item : elementosParaEliminar) {
				DBHelper.eliminarInventario(item.getIdInventario());
				tablaInventario.getItems().remove(item);
			}
			listaInventario = tablaInventario.getItems();
			new Alert(AlertType.INFORMATION, "Los elementos fueron eliminados correctamente", ButtonType.OK).showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			new Alert(
					AlertType.ERROR,
					"Hubo un error al actulizar los artículos, favor de intentarlo nuevamente.",
					ButtonType.OK).showAndWait();
		}
		
	}
}
