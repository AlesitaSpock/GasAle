package mx.edu.uaeh.alequiroz.gasolinera;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mx.edu.uaeh.alequiroz.gasolinera.controlador.LoginGasolineraControlador;
import mx.edu.uaeh.alequiroz.gasolinera.controlador.MenuLayoutControlador;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.DBHelper;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Usuario;

public class Main extends Application {
	
	private static final String NOMBRE_APLICACION = "GASOLINERA APAXCO"; 
	
	public static Usuario usuarioAutenticado;
	
	private Stage stagePrincipal;
	private BorderPane layoutMenu;
	
	private MenuLayoutControlador controladorMenuLayout;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stagePrincipal = primaryStage;
		this.stagePrincipal.setTitle(NOMBRE_APLICACION);
		DBHelper.iniciarBaseDatos();
		iniciarLayout();
		
		iniciarPantallaLogin();
	}

	private void iniciarLayout() {
		try {
			FXMLLoader fxmLoader = new FXMLLoader();
			fxmLoader.setLocation(Main.class.getResource("interfaz/MenuLayout.fxml"));
            layoutMenu = (BorderPane) fxmLoader.load();
			controladorMenuLayout = fxmLoader.getController();
			controladorMenuLayout.setMain(this);

            Scene scene = new Scene(layoutMenu);
            stagePrincipal.setScene(scene);
            stagePrincipal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void iniciarPantallaLogin() {
		try {
			FXMLLoader fxmLoader = new FXMLLoader();
			fxmLoader.setLocation(Main.class.getResource("interfaz/Login.Gasolinera.fxml"));
			AnchorPane loginPane = (AnchorPane)fxmLoader.load();
			
			layoutMenu.setCenter(loginPane);
			
			LoginGasolineraControlador controlador = fxmLoader.getController();
			controlador.setMain(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void iniciarPantallaVenta() {
		try {
			FXMLLoader fxmLoader = new FXMLLoader();
			fxmLoader.setLocation(Main.class.getResource("interfaz/Venta.Gasolinera.fxml"));
			AnchorPane loginPane = (AnchorPane)fxmLoader.load();
			layoutMenu.setCenter(loginPane);
			controladorMenuLayout.recalcularOpcionesMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void iniciarPantallaConsultaVenta() {
		try {
			FXMLLoader fxmLoader = new FXMLLoader();
			fxmLoader.setLocation(Main.class.getResource("interfaz/Ventastot.Gasolinera.fxml"));
			AnchorPane loginPane = (AnchorPane)fxmLoader.load();
			layoutMenu.setCenter(loginPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void iniciarPantallaRegistroUsuario() {
		cargarFxml("interfaz/RegistrarUsuario.Gasolinera.fxml");
	}
	
	public void iniciarPantallaRegistroInventario() {
		cargarFxml("interfaz/RegistroInventario.Gasolinera.fxml");
	}
	
	private void cargarFxml(String nombreArchivo) {
		try {
			FXMLLoader fxmLoader = new FXMLLoader();
			fxmLoader.setLocation(Main.class.getResource(nombreArchivo));
			AnchorPane loginPane = (AnchorPane)fxmLoader.load();
			layoutMenu.setCenter(loginPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
