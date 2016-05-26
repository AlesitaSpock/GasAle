package mx.edu.uaeh.alequiroz.gasolinera;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.DBHelper;

public class Main extends Application {
	
	private static final String NOMBRE_APLICACION = "GASOLINERA APAXCO"; 
	
	private Stage stagePrincipal;
	private BorderPane layoutMenu;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stagePrincipal = primaryStage;
		this.stagePrincipal.setTitle(NOMBRE_APLICACION);
		DBHelper.iniciarBaseDatos();
		iniciarLayout();
		
		
	}

	private void iniciarLayout() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("interfaz/MenuLayout.fxml"));
            layoutMenu = (BorderPane) loader.load();

            Scene scene = new Scene(layoutMenu);
            stagePrincipal.setScene(scene);
            stagePrincipal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
