package mx.edu.uaeh.alequiroz.gasolinera.controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mx.edu.uaeh.alequiroz.gasolinera.Main;

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
				System.out.println("usuario :" + textFieldUsuario.getText());
				System.out.println("password :" + passwordField.getText());
				//TODO: falta verificar al usuario
				main.iniciarPantallaVenta();
			}
		});
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
}
