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
			item.setIdInventario(resultado.getInt("id_inventario"));
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
	
	public static void agregarVenta(Venta venta) throws Exception {
		abrirBD();
		String query = "INSERT INTO Venta(id_venta, fk_usuario, importe, fecha_venta) VALUES (?,?,?,?)";
		PreparedStatement statement = c.prepareStatement(query);
		statement.setInt(1, venta.getIdVenta());
		statement.setInt(2, venta.getFkUsuario());
		statement.setDouble(3, venta.getImporte());
		statement.setLong(4, venta.getFechaVenta());
		statement.executeUpdate();
		cerrarBD();
	}
	
	public static List<Venta> obtenerVentasPorUsuario(int idUsuario) {
		List<Venta> listaVentas = new ArrayList<>();
		abrirBD();
		String query = "SELECT id_venta, fk_usuario, importe, fecha_venta FROM Venta WHERE fk_usuario = ?";
		try {
			PreparedStatement statement = c.prepareStatement(query);
			statement.setInt(1, idUsuario);
			ResultSet resultado = statement.executeQuery();
			while (resultado.next()) {
				Venta item = new Venta();
				item.setIdVenta(resultado.getInt("id_venta"));
				item.setFkUsuario(resultado.getInt("fk_usuario"));
				item.setImporte(resultado.getDouble("importe"));
				item.setFechaVenta(resultado.getLong("fecha_venta"));
				listaVentas.add(item);
			}
			resultado.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		cerrarBD();

		return listaVentas;
	}
	
	public static List<Articulo> obtenerListadoArticulos(int idVenta) {
		List<Articulo> listaArticulos = new ArrayList<>();
		String query = "SELECT Venta_articulo.cantidad, Inventario.nombre, Venta_articulo.subtotal FROM Venta_articulo";
		query += " JOIN Inventario on Inventario.id_inventario = Venta_articulo.fk_inventario ";
		query += " WHERE Venta_articulo.fk_venta = ? ";
		abrirBD();
		
		try {
			PreparedStatement statement = c.prepareStatement(query);
			statement.setInt(1, idVenta);
			ResultSet resultado = statement.executeQuery();
			
			while (resultado.next()) {
				Articulo item = new Articulo();
				item.setCantidad(resultado.getDouble("cantidad"));
				item.setDescripcion(resultado.getString("nombre"));
				item.setImporte(resultado.getDouble("subtotal"));
				listaArticulos.add(item);
			}
			resultado.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		cerrarBD();
		
		return listaArticulos;
	}
	
	public static void agregarVentaArticulo(VentaArticulo ventaArticulo) throws Exception {
		abrirBD();
		String query = "INSERT INTO Venta_articulo(fk_venta, fk_inventario, cantidad, subtotal) VALUES (?,?,?,?)";
		PreparedStatement statement = c.prepareStatement(query);
		statement.setInt(1, ventaArticulo.getFkVenta());
		statement.setInt(2, ventaArticulo.getFkInventario());
		statement.setDouble(3, ventaArticulo.getCantidad());
		statement.setDouble(4, ventaArticulo.getSubtotal());
		statement.executeUpdate();
		cerrarBD();
	}
	
}
