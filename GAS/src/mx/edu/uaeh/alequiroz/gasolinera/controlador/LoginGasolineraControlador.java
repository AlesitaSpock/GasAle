package mx.edu.uaeh.alequiroz.gasolinera.controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.edu.uaeh.alequiroz.gasolinera.Main;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.DBHelper;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Usuario;

public class LoginGasolineraControlador {

	private Main main;
	
	@FXML
	private TextField textFieldUsuario;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Button botonLogin;
	
	public LoginGasolineraControlador(){}
	
	@FXML
	private void initialize() {
		botonLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String cadenaUsuario = textFieldUsuario.getText();
				String cadenaPassword = passwordField.getText();
				try {
					Usuario usuarioAutenticado = DBHelper.obtenerUsuario(cadenaUsuario, cadenaPassword);
					if (usuarioAutenticado != null) {
						main.usuarioAutenticado = usuarioAutenticado;
						main.iniciarPantallaVenta();
					} else {
						new Alert(
							AlertType.ERROR,
							"El nombre de usuario o contraseña es incrrecto",
							ButtonType.OK).showAndWait();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
		});
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
}
