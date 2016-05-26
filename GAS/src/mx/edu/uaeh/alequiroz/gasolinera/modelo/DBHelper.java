package mx.edu.uaeh.alequiroz.gasolinera.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
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
		cerrarBD();
	}
}
