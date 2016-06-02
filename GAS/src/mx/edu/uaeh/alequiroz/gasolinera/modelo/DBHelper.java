package mx.edu.uaeh.alequiroz.gasolinera.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBHelper {

	private static Connection c = null;
	
	public static Connection abrirBD() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:gas.db");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public static void cerrarBD() {
		try {
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void iniciarBaseDatos() throws Exception {
		abrirBD();
		Statement statement = c.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS Inventario (id_inventario	INTEGER PRIMARY KEY AUTOINCREMENT, nombre	TEXT, precio	NUMERIC);";
		sql += "CREATE TABLE IF NOT EXISTS Usuario (id_usuario	INTEGER PRIMARY KEY AUTOINCREMENT, usuario	TEXT NOT NULL, password	TEXT NOT NULL, rol	INTEGER);";
		sql += "CREATE TABLE IF NOT EXISTS Venta (id_venta	INTEGER PRIMARY KEY AUTOINCREMENT, fk_usuario	INTEGER NOT NULL, importe	NUMERIC, fecha_venta	INTEGER, FOREIGN KEY(fk_usuario) REFERENCES Usuario(id_usuario));";
		sql += "CREATE TABLE IF NOT EXISTS Venta_articulo (id_venta_articulo	INTEGER PRIMARY KEY AUTOINCREMENT, fk_venta	INTEGER, fk_inventario	INTEGER, cantidad	NUMERIC, subtotal	NUMERIC, FOREIGN KEY(fk_venta) REFERENCES Venta(id_venta), FOREIGN KEY(fk_inventario) REFERENCES Inventario(id_inventario));";
		statement.executeUpdate(sql);
		String insertUsuarios = "INSERT OR REPLACE INTO usuario (usuario, password, rol) VALUES ('ale.quiroz', 'ale123', 1);";
		statement.executeUpdate(insertUsuarios);
		cerrarBD();
	}
	
	/**
	 * Obtiene el objeto Usuario cuyo select haga match con usuario y password
	 * @param usuario
	 * @param password
	 * @return objeto Usuario, o null si no existe
	 */
	public static Usuario obtenerUsuario(String usuario, String password) throws Exception {
		Usuario objetoUsuario = null;
		abrirBD();
		String query = "SELECT id_usuario, usuario, password, rol FROM Usuario WHERE usuario = ? AND password = ?";
		PreparedStatement statement = c.prepareStatement(query);
		statement.setString(1, usuario);
		statement.setString(2, password);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) {
			objetoUsuario = new Usuario();
			objetoUsuario.setId(resultado.getInt("id_usuario"));
			objetoUsuario.setUsuario(resultado.getString("usuario"));
			objetoUsuario.setPassword(resultado.getString("password"));
			objetoUsuario.setRol(resultado.getInt("rol"));
			break;
		}
		resultado.close();
		cerrarBD();
		return objetoUsuario;
	}
}
