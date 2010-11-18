package SGBD;

import java.sql.*;
import java.util.*;
import java.lang.Exception;
import cajonSastre.CajonSastre;

public final class SGBD {
	private final static String DRIVER_CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
	private final static String DRIVER_URL = "jdbc:derby:C:\\Users\\Familia Merino\\Documents\\Universidad\\Carrera\\LabProg\\eShop\\bd; create=true; user=admin; password=1234";
	private final static String USER = "admin";
	private final static String PASSWORD = "1234";

	private Connection conn;
	private Statement stmt;


	public SGBD () {

	}

	private static void createConnection () {
		try {
			Class.forName(DRIVER_CLASS_NAME);
		}
		catch (ClassNotFoundException e1) {
			e1.printStackTrace(System.err);
		}
	}

	private final void getConnection() {
		try {
			this.conn = DriverManager.getConnection(DRIVER_URL, USER, PASSWORD);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<String> consultarProducto (String parametros) {
		ArrayList<String> listado = new ArrayList<String>();
		ResultSet results;
		try {
			this.stmt = this.conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (argumentos[3].compareTo("Pelicula") == 0) {
				if (argumentos[4].compareTo("DVD") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.PELICULA WHERE (ESHOP.PRODUCTO.idProducto" +
												"= ESHOP.PELICULA.idProducto AND" +
												"ESHOP.PELICULA.Soporte = 'DVD')");
				}
				else if (argumentos[4].compareTo("BR") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.PELICULA WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.PELICULA.idProducto AND ESHOP.PELICULA" +
				                                ".Soporte = 'DVD')");
				}
				else {// Queremos consultar el listado de TODAS las peliculas
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.PELICULA WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.PELICULA.idProducto)");
				}
			}
			else if (argumentos[3].compareTo("Videojuego") == 0) {
			    if (argumentos[4].compareTo("PC") == 0) {
			    	results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'PC')");
			    }
				else if (argumentos[4].compareTo("PS3") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'PS3')");
				}
				else if (argumentos[4].compareTo("PSP") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +  
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'PSP')");
				}
				else if (argumentos[4].compareTo("Wii") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'Wii')");
				}
				else if (argumentos[4].compareTo("NDS") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'NDS')");
				}
				else if (argumentos[4].compareTo("Xbox") == 0) {
				    results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'Xbox')");
				}
				else { // Queremos consultar el listado de TODOS los videojuegos
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto)");
				}
			}
			else { // Queremos consultar el resultado de TODOS los productos
				results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO, ESHOP.PELICULA," +
				                            "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                            "= ESHOP.VIDEOJUEGO.idProducto OR ESHOP.PRODUCTO.idProducto" +
				                            "= ESHOP.PELICULA.idProducto)");
			}
			ResultSetMetaData rsmd = results.getMetaData();
			int numCols = rsmd.getColumnCount();

			if (numCols == 18) { // Trabajamos con Peliculas

				while (results.next()) {
					int idProducto = results.getInt(1);
				    listado.add(Integer.toString(idProducto));
				    boolean Agotado = results.getBoolean(2);
				    listado.add(Boolean.toString(Agotado));
				    int Ejemplares = results.getInt(3);
				    listado.add(Integer.toString(Ejemplares));
				    int Puntuacion = results.getInt(4);
				    listado.add(Integer.toString(Puntuacion));
				    int NumVotos  = results.getInt(5);
				    listado.add(Integer.toString(NumVotos));
				    int PrecioUd = results.getInt(6);
					listado.add(Integer.toString(PrecioUd));
					String Genero = results.getString(7);
					listado.add(Genero);
					String Titulo = results.getString(8);
					listado.add(Titulo);
					String Fichero = results.getString(9);
					listado.add(Fichero);
					int Fecha = results.getInt(10);
					listado.add(Integer.toString(Fecha));
					String Sinopsis = results.getString(11);
					listado.add(Sinopsis);
					listado.add("#$#");
				}
			}
			return listado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return listado;
	}

	public void iniSGBD () {
		createConnection();
		getConnection();
		
	}

	public ArrayList<String> Consultar (String objeto, String usuario, String parametros) {
		ArrayList<String> foo = new ArrayList<String>();
		foo = consultarProducto(parametros);
		return foo;
	}

	public int Anyadir (String objeto, String usuario, String parametros) {
		return 0;
	}

	public int Eliminar (String objeto, String usuario, String parametros) {
		return 0;
	}

	public int Modificar (String objeto, String usuario, String parametros) {
		return 0;
	}
}
