package mx.edu.uaeh.alequiroz.gasolinera.controlador;

import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.edu.uaeh.alequiroz.gasolinera.Main;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Articulo;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.DBHelper;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Venta;

public class ConsultaVentaGasolineraControlador {
	
	@FXML
	private Accordion acordeonVentas;
	
	
	@FXML
	private void initialize() {
		
		ObservableList<TitledPane> elementos = acordeonVentas.getPanes();
		
		List<Venta> ventas = DBHelper.obtenerVentasPorUsuario(Main.usuarioAutenticado.getId());

		for (Venta venta : ventas) {
			List<Articulo> articulosPorVenta = DBHelper.obtenerListadoArticulos(venta.getIdVenta());
			String titulo = "ID: " + venta.getIdVenta() + " - Fecha de la venta: " + new Date(venta.getFechaVenta());
			elementos.add(construirElementoAcordeon(titulo, articulosPorVenta));
		}
		
		
	}
	
	private TitledPane construirElementoAcordeon(String titulo, List<Articulo> listaArticulos) {
		TitledPane panel = new TitledPane();
		panel.setText(titulo);
		panel.setContent(construirTablaArticulos(listaArticulos));
		return panel;
	}
	
	private TableView<Articulo> construirTablaArticulos(List<Articulo> articulos) {
		TableView<Articulo> tablaVenta = new TableView<Articulo>();
		
		TableColumn<Articulo, Double> columnaCantidad = new TableColumn<Articulo, Double>("Cantidad");
        TableColumn<Articulo, String> columnaDescripcion = new TableColumn<Articulo, String>("Descripción");
        TableColumn<Articulo, Double> columnaImporte = new TableColumn<Articulo, Double>("Importe");
        
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<Articulo, Double>("cantidad"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<Articulo, String>("descripcion"));
        columnaImporte.setCellValueFactory(new PropertyValueFactory<Articulo, Double>("importe"));
        
        tablaVenta.getColumns().addAll(columnaCantidad, columnaDescripcion, columnaImporte);
		
		ObservableList<Articulo> listadoArticulos = FXCollections.observableArrayList();
		listadoArticulos.addAll(articulos);
		
		tablaVenta.setItems(listadoArticulos);
		
		return tablaVenta;
	}

}
