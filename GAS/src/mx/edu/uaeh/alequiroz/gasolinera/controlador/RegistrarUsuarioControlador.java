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
import mx.edu.uaeh.alequiroz.gasolinera.modelo.DBHelper;
import mx.edu.uaeh.alequiroz.gasolinera.modelo.Usuario;

public class RegistrarUsuarioControlador {

	
	@FXML
	private TextField textFieldUsuario;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private PasswordField confirmarPassword;
	
	@FXML
	private Button botonAgregar;

	
	@FXML
	private void initialize(){
		botonAgregar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				registrarUsuario();
			}
		});
	}
	
	private void registrarUsuario() {
		String cadenaUsuario = textFieldUsuario.getText();
		String cadenaPassword = password.getText();
		String cadenaConfirmar = confirmarPassword.getText();
		if (!cadenaPassword.equals(cadenaConfirmar)) {
			new Alert(
					AlertType.ERROR,
					"El password no coincide. Favor de verificarlo",
					ButtonType.OK).showAndWait();
			return;
		}
		Usuario usuarioRegistrar = new Usuario();
		usuarioRegistrar.setUsuario(cadenaUsuario);
		usuarioRegistrar.setPassword(cadenaPassword);
		usuarioRegistrar.setRol(Usuario.ROL_ADMIN);
		
		try {
			DBHelper.agregarUsuario(usuarioRegistrar);
			new Alert(
					AlertType.INFORMATION,
					"El usuario se ha registrado correctamente.",
					ButtonType.OK).showAndWait();
					limpiarFormulario();
		} catch (Exception e) {
			new Alert(
					AlertType.ERROR,
					"Hubo un error al registrar un usuario, favor de intentarlo nuevamente.",
					ButtonType.OK).showAndWait();
		}	
	}
	
	private void limpiarFormulario() {
		textFieldUsuario.clear();
		password.clear();
		confirmarPassword.clear();
	}
	
	
}
