package mx.edu.uaeh.alequiroz.gasolinera.controlador;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.edu.uaeh.alequiroz.gasolinera.Main;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Articulo;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.DBHelper;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Inventario;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Venta;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.VentaArticulo;

public class VentaGasolineraControlador {

	@FXML
	private TextField numVenta;
	
	@FXML
	private ChoiceBox<Inventario> buscaProducto;
	
	@FXML
	private TextField cantidad;
	
	@FXML
	private Button botonAgregar;
	
	@FXML
	private Button botonLimpiar;
	
	@FXML
	private Button botonCobrar;
	
	@FXML
	private TableView<Articulo> tablaVenta;
	
	@FXML
	private TableColumn<Articulo, Double> columnaCantidad;
	
	@FXML
	private TableColumn<Articulo, String> columnaDescripcion;
	
	@FXML
	private TableColumn<Articulo, Double> columnaImporte;
	
	@FXML
	private Label textoTotal;
	
	private Venta venta;
	
	private List<Inventario> inventario = new ArrayList<>();
	
	private List<VentaArticulo> articulosEnTransaccion = new ArrayList<>();
	
	private ObservableList<Articulo> listadoArticulos = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		venta = new Venta();
		venta.setFkUsuario(Main.usuarioAutenticado.getId());
		poblarListadoInventario();
		calcularNuevoIdParaNumVenta();
		botonAgregar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				agregarEnLista();
			}
		});
		botonLimpiar.setOnAction((ActionEvent event) -> limpiarFormulario());
		botonCobrar.setOnAction((ActionEvent e) -> cobrar());
		
		tablaVenta.setItems(listadoArticulos);
		
		columnaCantidad.setCellValueFactory(new PropertyValueFactory<Articulo, Double>("cantidad"));
		columnaDescripcion.setCellValueFactory(new PropertyValueFactory<Articulo, String>("descripcion"));
		columnaImporte.setCellValueFactory(new PropertyValueFactory<Articulo, Double>("importe"));
		
	}

	private void poblarListadoInventario() {
		try {
			inventario = DBHelper.obtenerInventario();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Inventario elemento : inventario) {
			buscaProducto.getItems().add(elemento);
		}
		
	}
	private void calcularNuevoIdParaNumVenta() {
		try {
			int nuevoIdParaVenta = DBHelper.obtenerNuevoIdParaNumVenta();
			numVenta.setText(String.valueOf(nuevoIdParaVenta));
			venta.setIdVenta(nuevoIdParaVenta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void agregarEnLista() {
		VentaArticulo ventaArticulo = new VentaArticulo();
		ventaArticulo.setFkVenta(venta.getIdVenta());
		ventaArticulo.setFkInventario(buscaProducto.getValue().getIdInventario());
		ventaArticulo.setCantidad(Double.parseDouble(cantidad.getText()));
		double subtotal = ventaArticulo.getCantidad() * buscaProducto.getValue().getPrecio();
		ventaArticulo.setSubtotal(subtotal);
		articulosEnTransaccion.add(ventaArticulo);
		
		Articulo articulo = new Articulo();
		articulo.setCantidad(Double.parseDouble(cantidad.getText()));
		articulo.setDescripcion(buscaProducto.getValue().getNombre());
		articulo.setImporte(subtotal);
		listadoArticulos.add(articulo);
		
		calcularTotal();
	}
	
	private void calcularTotal() {
		double nuevoTotal = 0;
		for (VentaArticulo elemento : articulosEnTransaccion) {
			nuevoTotal += elemento.getSubtotal();
		}
		venta.setImporte(nuevoTotal);
		textoTotal.setText(String.valueOf(nuevoTotal));
	}
	
	private void limpiarFormulario() {
		numVenta.clear();
		cantidad.clear();
		textoTotal.setText("0.00");
		listadoArticulos.clear();
	}
	
	private void cobrar() {
		venta.setFechaVenta(new Date().getTime());
		try {
			DBHelper.agregarVenta(venta);
			for (VentaArticulo articulo : articulosEnTransaccion) {
				DBHelper.agregarVentaArticulo(articulo);
			}
			new Alert(
					AlertType.INFORMATION,
					"El registro se ha agregado correctamente",
					ButtonType.OK).showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
		limpiarFormulario();
	}
	
	
	
	
	
}
