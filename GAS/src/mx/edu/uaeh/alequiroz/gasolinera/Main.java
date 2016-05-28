package mx.edu.uaeh.alequiroz.gasolinera;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mx.edu.uaeh.alequiroz.gasolinera.controlador.LoginGasolineraControlador;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.DBHelper;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Usuario;

public class Main extends Application {
	
	private static final String NOMBRE_APLICACION = "GASOLINERA APAXCO"; 
	
	public ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
	
	private Stage stagePrincipal;
	private BorderPane layoutMenu;
	
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
			
			LoginGasolineraControlador controller = fxmLoader.getController();
	        controller.setMain(this);
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
			
			LoginGasolineraControlador controller = fxmLoader.getController();
	        controller.setMain(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
		
		//TODO: Ale pondrá código aqui!
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public ObservableList<Usuario> getUsuarios() {
        return usuarios;
    }

}
