package labProg.eShop;

import java.sql.*;

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
		catch (CLassNotFoundException e) {
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
		
	}
	
	private ArrayList<String> consultarProducto (tpParametros parametros) {
		try {
			stmt = conn.createStatement();
			// if (tipo_de_producto.compareTo("Pelicula") == 0) {
			//    if (soporte.compareTo("DVD") == 0)
			//       ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR,
			//                                   ESHOP.PELICULA PE WHERE (PR.idProducto == PE.idProducto
			//                                   AND PE.Soporte == 'DVD'));
			//    else if (soporte.compareTo("BR") == 0)
			//       ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR,
			//                                   ESHOP.PELICULA PE WHERE (PR.idProducto == PE.idProducto
			//                                   AND PE.Soporte == 'DVD'));
			//    else // Queremos consultar el listado de TODAS las peliculas
			//       ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR,
			//                                   ESHOP.PELICULA VJ WHERE (PR.idProducto == PE.idProducto));
			// }
			// else if (tipo_de_producto.compareTo("Videojuego") == 0) {
			//    if (plataforma.compareTo("PC") == 0)
			//       ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR,
			//                                   ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto
			//                                   AND VJ.Plataforma == 'PC'));
			//    else if (plataforma.compareTo("PS3") == 0)
			//       ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR,
			//                                   ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto
			//                                   AND VJ.Plataforma == 'PS3'));
			//    else if (plataforma.compareTo("PSP") == 0)
			//       ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR,
			//                                   ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto
			//                                   AND VJ.Plataforma == 'PSP'));
			//    else if (plataforma.compareTo("Wii") == 0)
			//       ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR,
			//                                   ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto
			//                                   AND VJ.Plataforma == 'Wii'));
			//    else if (plataforma.compareTo("NDS") == 0)
			//       ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR,
			//                                   ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto
			//                                   AND VJ.Plataforma == 'NDS'));
			//    else if (plataforma.compareTo("Xbox") == 0)
			//       ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR,
			//                                   ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto
			//                                   AND VJ.Plataforma == 'Xbox'));
			//    else // Queremos consultar el listado de TODOS los videojuegos
			//       ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR,
			//                                   ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto));
			// }
			// else // Queremos consultar el resultado de TODOS los productos
			//    ResultSet results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO PR, ESHOP PELICULA PE,
			//                                   ESHOP.VIDEOJUEGO VJ WHERE (PR.idProducto == VJ.idProducto
			//                                   OR PR.idProducto == PE.idProducto));
			// ResultSetMetaData rsmd = results.getMetaData();
			// int numCols = rsmd.getColumnCount();
			
			// ArrayList<String> listado = new ArrayList<String>;
			// if (numCols == 14) { // Trabajamos con Peliculas
			
			//    while (results.next()) {
			//       int idProducto = results.getInt(1);
			//       listado.add(idProducto.toString());
			//       boolean Agotado = results.getBoolean(2);
			//       listado.add(Agotado.toString());
			//       int Ejemplares = results.getInt(3);
			//       listado.add(Ejemplares.toString());
			//       int Puntuacion = results.getInt(4);
			//       listado.add(Puntuacion.toString());
			//       int NumVotos  = results.getInt(5);
			//       listado.add(NumVotos.toString());
			//       int PrecioUd = results.getInt(6);
			//       listado.add(PrecioUd.toString());
			//       String Genero = results.getString(7);
			//       listado.add(Genero);
			//       String Titulo = results.getString(8);
			//       listado.add(Titulo);
			//       String Fichero = results.getString(9);
			//       listado.add(Fichero);
			//       int Fecha = results.getInt(10);
			//       listado.add(Fecha.toString());
			//       String Sinopsis = results.getString(11);
			//       listado.add(Sinopsis);
			//    }
			// }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public iniSGBD () {
		createConnection();
	}
	
	public Consultar (tpObjeto objeto, tpUsuario usuario, tpParametros parametros) {
		
	}
		
	public int Anyadir (tpObjeto objeto, tpUsuario usuario, tpParametro parametros) {
		
	}
		
	public int Eliminar (tpObjeto objeto, tpUsuario usuario, tpParametro parametros) {
			
	}
		
	public int Modificar (tpObjetos objeto, tpUsuario usuario, tpParametro parametros) {
			
	}
}
