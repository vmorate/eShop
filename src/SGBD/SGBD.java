package SGBD;

import java.sql.*;
import java.util.*;
import java.lang.Exception;
import cajonSastre.CajonSastre;

public final class SGBD {
	private final static String DRIVER_CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
	private final static String DRIVER_URL = "jdbc:derby:/home/franxesk/Universidad/LabProg/eShop/bd; create=true; user=admin; password=1234";
	private final static String USER = "admin";
	private final static String PASSWORD = "1234";

	private static Connection conn = null;
	private static Statement stmt = null;


	private SGBD () {

	}

	private static void createConnection () {
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}
	}

	private final static Connection getConnection() {
		try {
			conn = DriverManager.getConnection(DRIVER_URL, USER, PASSWORD);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;

	}

	private ArrayList<String> consultarProducto (String parametros) {
		ArrayList<String> listado = new ArrayList<String>();
		ResultSet results;
		try {
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (argumentos[3].compareTo("Pelicula") == 0) {
				if (argumentos[4].compareTo("DVD") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR," +
				                                "ESHOP.PELICULA PE WHERE (PR.idProducto == PE.idProducto" +
				                                "AND PE.Soporte == 'DVD')");
				}
				else if (argumentos[4].compareTo("BR") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR," +
				                                "ESHOP.PELICULA PE WHERE (PR.idProducto == PE.idProducto" +
				                                "AND PE.Soporte == 'DVD')");
				}
				else {// Queremos consultar el listado de TODAS las peliculas
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR," +
				                                "ESHOP.PELICULA VJ WHERE (PR.idProducto == PE.idProducto)");
				}
			}
			else if (argumentos[3].compareTo("Videojuego") == 0) {
			    if (argumentos[4].compareTo("PC") == 0) {
			    	results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR," +
				                                "ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto" +
				                                "AND VJ.Plataforma == 'PC')");
			    }
				else if (argumentos[4].compareTo("PS3") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR," +
				                                "ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto" +
				                                "AND VJ.Plataforma == 'PS3')");
				}
				else if (argumentos[4].compareTo("PSP") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR," +  
				                                "ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto" +
				                                "AND VJ.Plataforma == 'PSP')");
				}
				else if (argumentos[4].compareTo("Wii") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR," +
				                                "ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto" +
				                                "AND VJ.Plataforma == 'Wii')");
				}
				else if (argumentos[4].compareTo("NDS") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR," +
				                                "ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto" +
				                                "AND VJ.Plataforma == 'NDS')");
				}
				else if (argumentos[4].compareTo("Xbox") == 0) {
				    results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR," +
				                                "ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto" +
				                                "AND VJ.Plataforma == 'Xbox')");
				}
				else { // Queremos consultar el listado de TODOS los videojuegos
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR," +
				                                "ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto)");
				}
			}
			else { // Queremos consultar el resultado de TODOS los productos
				results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR, ESHOP PELICULA PE," +
				                            "ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto" +
	                                        "OR PR.idProducto == PE.idProducto)");
			}
			ResultSetMetaData rsmd = results.getMetaData();
			int numCols = rsmd.getColumnCount();

			if (numCols == 14) { // Trabajamos con Peliculas

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
	}

	public ArrayList<String> Consultar (String objeto, String usuario, String parametros) {
		ArrayList<String> foo = new ArrayList<String>();
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
