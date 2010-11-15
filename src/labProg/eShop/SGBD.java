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
	
	private consultarProducto (tpParametros parametros) {
		try {
			stmt = conn.createStatement();
			// if (tipo_de_producto.compareTo("Pelicula") == 0) {
			//    if (soporte.compareTo("DVD") == 0)
			//       ResultSet results = stmt.executeQuery()
			//    else if (soporte.compareTo("BR") == 0)
			//       ResultSet results = stmt.executeQuery()
			//    else // Queremos consultar el listado de TODAS las peliculas
			//       ResultSet results = stmt.executeQuery()
			// }
			// else if (tipo_de_producto.compareTo("Videojuego") == 0) {
			//    if (plataforma.compareTo("PC") == 0)
			//       ResultSet results = stmt.executeQuery()
			//    else if (plataforma.compareTo("PS3") == 0)
			//       ResultSet results = stmt.executeQuery()
			//    else if (plataforma.compareTo("PSP") == 0)
			//       ResultSet results = stmt.executeQuery()
			//    else if (plataforma.compareTo("PC") == 0)
			//       ResultSet results = stmt.executeQuery()
			//    else if (plataforma.compareTo("Wii") == 0)
			//       ResultSet results = stmt.executeQuery()
			//    else if (plataforma.compareTo("NDS") == 0)
			//       ResultSet results = stmt.executeQuery()
			//    else if (plataforma.compareTo("Xbox") == 0)
			//       ResultSet results = stmt.executeQuery()
			//    else // Queremos consultar el listado de TODOS los videojuegos
			//       ResultSet results = stmt.executeQuery()
			// }
			// else // Queremos consultar el resultado de TODOS los productos
			//    ResultSet results = stmt.executeQuery()
			// ResultSetStatment results.getMetaData();
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
