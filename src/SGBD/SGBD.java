package SGBD;

import java.sql.*;
import java.util.*;
import java.lang.Exception;
import cajonSastre.CajonSastre;

public final class SGBD {
	private final static String DRIVER_CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
	private final static String DRIVER_URL = "jdbc:derby:bd; create=true; user=admin; password=1234";
	private final static String USER = "admin";
	private final static String PASSWORD = "1234";

	private static Connection conn;
	private static Statement stmt;

	static {
		try { 
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) { 
			e.printStackTrace(System.err);
		}
	}

	private SGBD () {

	}

	private static final void getConnection() {
		try {
			conn = DriverManager.getConnection(DRIVER_URL, USER, PASSWORD);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<String> consultarProducto (String parametros) {
		ArrayList<String> listado = new ArrayList<String>();
		ResultSet results;
		try {
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (argumentos[0].compareTo("Pelicula") == 0) {
				if (argumentos[1].compareTo("DVD") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.PELICULA WHERE (ESHOP.PRODUCTO.idProducto" +
												"= ESHOP.PELICULA.idProducto AND" +
												"ESHOP.PELICULA.Soporte = 'DVD')");
				}
				else if (argumentos[1].compareTo("BR") == 0) {
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
			else if (argumentos[0].compareTo("Videojuego") == 0) {
			    if (argumentos[1].compareTo("PC") == 0) {
			    	results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'PC')");
			    }
				else if (argumentos[1].compareTo("PS3") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'PS3')");
				}
				else if (argumentos[1].compareTo("PSP") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +  
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'PSP')");
				}
				else if (argumentos[1].compareTo("Wii") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'Wii')");
				}
				else if (argumentos[1].compareTo("NDS") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.idProducto" +
				                                "= ESHOP.VIDEOJUEGO.idProducto AND ESHOP.VIDEOJUEGO" +
				                                ".Plataforma = 'NDS')");
				}
				else if (argumentos[1].compareTo("Xbox") == 0) {
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
				    boolean agotado = results.getBoolean(2);
				    listado.add(Boolean.toString(agotado));
				    int ejemplares = results.getInt(3);
				    listado.add(Integer.toString(ejemplares));
				    int puntuacion = results.getInt(4);
				    listado.add(Integer.toString(puntuacion));
				    int numVotos  = results.getInt(5);
				    listado.add(Integer.toString(numVotos));
				    int precioUd = results.getInt(6);
					listado.add(Integer.toString(precioUd));
					String genero = results.getString(7);
					listado.add(genero);
					String titulo = results.getString(8);
					listado.add(titulo);
					String fichero = results.getString(9);
					listado.add(fichero);
					int fecha = results.getInt(10);
					listado.add(Integer.toString(fecha));
					String sinopsis = results.getString(11);
					listado.add(sinopsis);
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

	private static ArrayList<String> consultarCliente (String usuario, String parametros) {
		ArrayList<String> listado = new ArrayList<String>();
		ResultSet results;
		try {
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (usuario.compareTo("Cliente") == 0) {
				results = stmt.executeQuery("SELECT * FROM ESHOP.USUARIO" +
				                                "WHERE (ESHOP.USUARIO.idUsuario" +
												"=" + argumentos[2] + ")");
			}
			else {// Queremos consultar el listado de TODOS los clientes y admin
					results = stmt.executeQuery("SELECT * FROM ESHOP.USUARIO");
			}
			
			while (results.next()) {
				String idUsuario = results.getString(1);
				listado.add(idUsuario);
				String passwd = results.getString(2);
				listado.add(passwd);
				String admin = results.getString(3);
				listado.add(admin);
				String apellidos = results.getString(4);
			    listado.add(apellidos);
			    String nombre = results.getString(5);
			    listado.add(nombre);
			    String NIF = results.getString(6);
			    listado.add(NIF);
			    String direccion = results.getString(7);
			    listado.add(direccion);
			    String telefono = results.getString(8);
			    listado.add(telefono);
			    String email = results.getString(9);
			    listado.add(email);
			    int bonificado = results.getInt(10);
			    listado.add(Integer.toString(bonificado));
				listado.add("#$#");
			}
			return listado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return listado;
	}

	private static ArrayList<String> consultarPedido (String usuario, String parametros) {
		ArrayList<String> listado = new ArrayList<String>();
		ResultSet results;
		try {
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (usuario.compareTo("Cliente") == 0) {
				results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO" +
				                                "WHERE (ESHOP.PEDIDO.idUsuario" +
												"=" + argumentos[2] + ")");
			}
			else {// Queremos consultar el listado de TODOS los pedidos
				if (argumentos[3].compareTo("Fecha") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO");
				}
				else if (argumentos[3].compareTo("Importe") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO");
				}
				else if (argumentos[3].compareTo("Estado") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO");
				}
				else if (argumentos[3].compareTo("Cliente") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO");
				}
				else { //
					results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO");
				}
			}
			
			while (results.next()) {
				String idPedido = results.getString(1);
			    listado.add(idPedido);
			    String numCuenta = results.getString(2);
			    listado.add(numCuenta);
			    int fechaPedido = results.getInt(3);
			    listado.add(Integer.toString(fechaPedido));
			    int fCaducidad = results.getInt(4);
			    listado.add(Integer.toString(fCaducidad));
			    String direccion = results.getString(5);
			    listado.add(direccion);
			    String nombreCl = results.getString(6);
			    listado.add(nombreCl);
			    String estado = results.getString(7);
			    listado.add(estado);
			    String CIF_NIF = results.getString(7);
			    listado.add(CIF_NIF);
			    String idUsuario = results.getString(7);
			    listado.add(idUsuario);
				listado.add("#$#");
			}
			return listado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return listado;
	}
	
	public static void iniSGBD () {
		getConnection();
		
	}
	
	public static int AnyadirProducto (String usuario, String parametros) {
		int value;
		try {
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			if ((Integer.parseInt(argumentos[0]) >= 0) && (argumentos[1].length() == 1) &&
					(Integer.parseInt(argumentos[2]) >= 0) && (Integer.parseInt(argumentos[3]) >= 0) &&
					(Integer.parseInt(argumentos[4]) >= 0) && (Integer.parseInt(argumentos[5]) >= 0) &&
					!(argumentos[6].isEmpty()) && (argumentos[6].length() <= 40) &&
					!(argumentos[7].isEmpty()) && (argumentos[7].length() <= 40) &&
					!(argumentos[8].isEmpty()) && (argumentos[8].length() <= 100) &&
					(Integer.parseInt(argumentos[9]) >= 0) && !(argumentos[10].isEmpty()) &&
					!(argumentos[10].length() <= 1000)) {
				value = stmt.executeUpdate("INSERT INTO ESHOP.USUARIO" +
										   "(IDPRODUCTO, AGOTADO, EJEMPLARES, PUNTUACION," +
										   " NUMVOTOS, PRECIOUD, GENERO, TITULO, FICHERO," +
										   " FECHA, SINOPSIS, VALUES (" + Integer.parseInt(argumentos[0]) +
										   ", " + Integer.parseInt(argumentos[1]) +
										   ", " + Integer.parseInt(argumentos[2]) +
										   ", " + Integer.parseInt(argumentos[3]) +
										   ", " + Integer.parseInt(argumentos[4]) +
										   ", " + Integer.parseInt(argumentos[5]) +
										   ", '" + argumentos[6] + "', '" + argumentos[7] +
										   "', '" + argumentos[8] + "', " +
										   Integer.parseInt(argumentos[9]) + ", '" + argumentos[10] + "')");

				return 0;
			}
			else {
				return -1;
			}
		}
		catch (NumberFormatException e1) {
			return -1;
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return -1;
	}
	
	public static int AnyadirCliente (String usuario, String parametros) {
		int value;
		try {
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			if (!(argumentos[0].isEmpty()) && (argumentos[0].length() <= 20) &&
					!(argumentos[1].isEmpty()) && (argumentos[1].length() <= 40) &&
					(argumentos[2].length() == 1) && !(argumentos[3].isEmpty()) &&
					(argumentos[3].length() <= 56) && !(argumentos[4].isEmpty()) &&
					(argumentos[4].length() <= 20) && (argumentos[5].length() == 9) &&
					!(argumentos[6].isEmpty()) && (argumentos[6].length() <= 200) &&
					(argumentos[7].length() == 13) && !(argumentos[8].isEmpty()) &&
					(argumentos[8].length() <= 40) && (Integer.parseInt(argumentos[9]) >= 0)) {
				value = stmt.executeUpdate("INSERT INTO ESHOP.USUARIO" +
										   "(IDUSUARIO, PASSWORD, ADMINISTRADOR, APELLIDOS," +
										   " NOMBRE, NIF, DIRECCION, TELEFONO, EMAIL, BONIFICADO" +
										   " VALUES ('" + argumentos[0] + "', '" + argumentos[1] +
										   "', '" + argumentos[2] + "', '" + argumentos[3] +
										   "', '" + argumentos[4] + "', '" + argumentos[5] +
										   "', '" + argumentos[6] + "', '" + argumentos[7] +
										   "', '" + argumentos[8] + "', " +
										   Integer.parseInt(argumentos[9]) + ")");

				return 0;
			}
			else {
				return -1;
			}
		}
		catch (NumberFormatException e1) {
			return -1;
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return -1;
	}
	
	public static int EliminarProducto(String usuario, String parametros) {
		int value;
		try {
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			if (Integer.parseInt(argumentos[0]) >= 0) {
				value = stmt.executeUpdate("DELETE FROM ESHOP.PRODUCTO " +
										   "WHERE (ESHOP.PRODUCTO.IDPRODUCTO = " +
										   Integer.parseInt(argumentos[0]) + ")");
				return 0;
			}
			else {
				return -1;
			}
		}
		catch (NumberFormatException e1) {
			return -1;
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return -1;
	}
	
	public static int EliminarCliente(String usuario, String parametros) {
		int value;
		try {
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			if (!(argumentos[0].isEmpty()) && (argumentos[0].length() <= 20)) {
				value = stmt.executeUpdate("DELETE FROM ESHOP.USUARIO" +
										   "WHERE (ESHOP.USUARIO.IDUSUARIO= " +
										   argumentos[0] + ")");
				return 0;
			}
			else {
				return -1;
			}
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return -1;
	}
	
	public static ArrayList<String> Consultar (String objeto, String usuario, String parametros) {
		ArrayList<String> lista = new ArrayList<String>();
		
		if (objeto.compareTo("Producto") == 0) {
			lista = consultarProducto(parametros);			
		}
		else if (objeto.compareTo("Cliente") == 0) {
			lista = consultarCliente(usuario, parametros);
		}
		else { // Realizamos la consulta sobre los pedidos
			lista = consultarPedido(usuario, parametros);
		}
		
		return lista;
	}

	public static int Anyadir (String objeto, String usuario, String parametros) {
		int value;
		if (objeto.compareTo("Producto") == 0) {
			value = AnyadirProducto(usuario, parametros);
		}
		else if (objeto.compareTo("Cliente") == 0) {
			value = AnyadirCliente(usuario, parametros);
		}
		else { // Anyadimos Pedidos, ProductosAPedidos
			value = 0;
		}
		
		return value;
	}

	public static int Eliminar (String objeto, String usuario, String parametros) {
		int value;
		if (objeto.compareTo("Producto") == 0) {
			value = EliminarProducto(usuario, parametros);
		}
		else if (objeto.compareTo("Cliente") == 0) {
			value = EliminarCliente(usuario, parametros);
		}
		else { // Eliminamos Pedidos, ProductosAPedidos.
			value = 0;
		}
		
		return value;
	}

	public static int Modificar (String objeto, String usuario, String parametros) {
		return 0;
	}
}
