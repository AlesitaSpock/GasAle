package mx.edu.uaeh.alequiroz.gasolinera.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		String insertarUsuarios = "INSERT OR REPLACE INTO usuario (id_usuario, usuario, password, rol) VALUES (1, 'ale.quiroz', 'ale123', 1);";
		statement.executeUpdate(insertarUsuarios);
		String insertarProductos = "INSERT OR REPLACE INTO inventario (id_inventario, nombre, precio) VALUES (1, 'Magna', 10), (2, 'Premium', 14), (3, 'Anticongelante', 40), (4, 'Aceite', 80);";
		statement.executeUpdate(insertarProductos);
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
	
	public static List<Inventario> obtenerInventario() throws Exception {
		List<Inventario> listaInventario = new ArrayList<>();
		abrirBD();
		
		String query = "SELECT id_inventario, nombre, precio FROM Inventario";
		PreparedStatement statement = c.prepareStatement(query);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) {
			Inventario item = new Inventario();
			item.setIdInventario(resultado.getLong("id_inventario"));
			item.setNombre(resultado.getString("nombre"));
			item.setPrecio(resultado.getDouble("precio"));
			listaInventario.add(item);
		}
		resultado.close();
		cerrarBD();
		
		return listaInventario;
	}

	public static int obtenerNuevoIdParaNumVenta() throws Exception {
		abrirBD();
		String query = "SELECT max(id_venta) as maximo FROM Venta";
		PreparedStatement statement = c.prepareStatement(query);
		ResultSet resultado = statement.executeQuery();
		int numVenta = 0;
		while (resultado.next()) {
			numVenta = resultado.getInt("maximo");
		}
		resultado.close();
		cerrarBD();
		return  numVenta + 1;
	}
	
}
