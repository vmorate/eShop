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
	
	private static void closeRS_STMT_CON(ResultSet rs, Statement stmt,
            Connection con) {
		if (rs != null) {
            try {
            	rs.close();
            }
            catch (SQLException e) {
    			e.printStackTrace();
            }
		}
		if (stmt != null) {
            try {
            	stmt.close();
            }
            catch (SQLException e) {
    			e.printStackTrace();
            }
		}
		if (con != null) {
            try {
            	con.close();
            }
            catch (SQLException e) {
    			e.printStackTrace();
            }
		}
	}
	
	private static void closeSTMT_CON(Statement stmt,
            Connection con) {
		if (stmt != null) {
            try {
            	stmt.close();
            }
            catch (SQLException e) {
    			e.printStackTrace();
            }
		}
		if (con != null) {
            try {
            	con.close();
            }
            catch (SQLException e) {
    			e.printStackTrace();
            }
		}
	}

	private static ArrayList<String> consultarProducto (String parametros) {
		ArrayList<String> listado = new ArrayList<String>();
		Statement stmt = null;
		ResultSet results = null;
		String tipo = new String("");
		try {
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (argumentos[0].compareTo("Pelicula") == 0) {
				tipo = tipo.concat("Pelicula");
				if (argumentos[1].compareTo("DVD") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.PELICULA WHERE ((ESHOP.PRODUCTO.IDPRODUCTO" +
												"=ESHOP.PELICULA.IDPRODUCTO) AND (ESHOP.PELICULA." +
												"SOPORTE='DVD'))");
				}
				else if (argumentos[1].compareTo("BR") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.PELICULA WHERE ((ESHOP.PRODUCTO.IDPRODUCTO" +
				                                "=ESHOP.PELICULA.IDPRODUCTO) AND (ESHOP.PELICULA" +
				                                ".SOPORTE='BR'))");
				}
				else {// Queremos consultar el listado de TODAS las peliculas
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.PELICULA WHERE (ESHOP.PRODUCTO.IDPRODUCTO" +
				                                "=ESHOP.PELICULA.IDPRODUCTO)");
				}
			}
			else if (argumentos[0].compareTo("Videojuego") == 0) {
				tipo = tipo.concat("Videojuego");
			    if (argumentos[1].compareTo("PC") == 0) {
			    	results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE ((ESHOP.PRODUCTO.IDPRODUCTO" +
				                                "=ESHOP.VIDEOJUEGO.IDPRODUCTO) AND (ESHOP.VIDEOJUEGO" +
				                                ".PLATAFORMA='PC'))");
			    }
				else if (argumentos[1].compareTo("PS3") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE ((ESHOP.PRODUCTO.IDPRODUCTO" +
				                                "=ESHOP.VIDEOJUEGO.IDPRODUCTO) AND (ESHOP.VIDEOJUEGO" +
				                                ".PLATAFORMA='PS3'))");
				}
				else if (argumentos[1].compareTo("PSP") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +  
				                                "ESHOP.VIDEOJUEGO WHERE ((ESHOP.PRODUCTO.IDPRODUCTO" +
				                                "=ESHOP.VIDEOJUEGO.IDPRODUCTO) AND (ESHOP.VIDEOJUEGO" +
				                                ".PLATAFORMA='PSP'))");
				}
				else if (argumentos[1].compareTo("Wii") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE ((ESHOP.PRODUCTO.IDPRODUCTO" +
				                                "=ESHOP.VIDEOJUEGO.IDPRODUCTO) AND (ESHOP.VIDEOJUEGO" +
				                                ".PLATAFORMA='Wii'))");
				}
				else if (argumentos[1].compareTo("NDS") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE ((ESHOP.PRODUCTO.IDPRODUCTO" +
				                                "=ESHOP.VIDEOJUEGO.IDPRODUCTO) AND (ESHOP.VIDEOJUEGO" +
				                                ".PLATAFORMA='NDS'))");
				}
				else if (argumentos[1].compareTo("Xbox") == 0) {
				    results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE ((ESHOP.PRODUCTO.IDPRODUCTO" +
				                                "=ESHOP.VIDEOJUEGO.IDPRODUCTO) AND (ESHOP.VIDEOJUEGO" +
				                                ".PLATAFORMA='Xbox'))");
				}
				else { // Queremos consultar el listado de TODOS los videojuegos
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO," +
				                                "ESHOP.VIDEOJUEGO WHERE (ESHOP.PRODUCTO.IDPRODUCTO" +
				                                "=ESHOP.VIDEOJUEGO.IDPRODUCTO)");
				}
			}
			else { // Queremos consultar el resultado de TODOS los productos
				tipo = tipo.concat("Todo");
				results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO, ESHOP.PELICULA," +
				                            "ESHOP.VIDEOJUEGO WHERE ((ESHOP.PRODUCTO.IDPRODUCTO" +
				                            "=ESHOP.VIDEOJUEGO.IDPRODUCTO) OR (ESHOP.PRODUCTO.IDPRODUCTO" +
				                            "=ESHOP.PELICULA.IDPRODUCTO))");
			}
			ResultSetMetaData rsmd = results.getMetaData();
			
			while (results.next()) {
				String idProducto = results.getString(1);
			    listado.add(idProducto);
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
				if ((tipo.compareTo("Pelicula") == 0) ||
						(tipo.compareTo("Todo") == 0)) {
					String soporte = results.getString(13);
					System.out.print("Nombre columna: " + rsmd.getColumnName(13) + "\n");
					if (!(soporte.isEmpty())) {
						listado.add(soporte);
						String director = results.getString(14);
						listado.add(director);
						String actores = results.getString(15);
						listado.add(actores);
					}
				}
				if ((tipo.compareTo("Videojuego") == 0) ||
							(tipo.compareTo("Todo") == 0)) {
					String plataforma = results.getString(13);
					System.out.print("Nombre columna: " + rsmd.getColumnName(13) + "\n");
					if (!(plataforma.isEmpty())) {
						listado.add(plataforma);
						String companya = results.getString(14);
						listado.add(companya);
					}
				}
				
				listado.add("#$#");
			}
			return listado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeRS_STMT_CON(results,stmt,conn);
		}
		return listado;
	}

	private static ArrayList<String> consultarCliente (String usuario, String parametros) {
		ArrayList<String> listado = new ArrayList<String>();
		Statement stmt = null;
		ResultSet results = null;
		try {
			getConnection();
			stmt = conn.createStatement();

			if (usuario.compareTo("Cliente") == 0) {
				String argumentos[] = CajonSastre.CortarString(parametros);
				results = stmt.executeQuery("SELECT * FROM ESHOP.USUARIO " +
				                                "WHERE (ESHOP.USUARIO.IDUSUARIO='" +
				                                argumentos[2] + "')");
			}
			else {// Somos Admins, queremos consultar el listado de TODOS los clientes y admin
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
		finally {
			closeRS_STMT_CON(results,stmt,conn);
		}
		return listado;
	}
	
	private static ArrayList<String> consultarPedido (String usuario, String parametros) {
		ArrayList<String> listado = new ArrayList<String>();
		Statement stmt = null;
		ResultSet results = null;
		try {
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (usuario.compareTo("Cliente") == 0) {
				results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO " +
				                                "WHERE (ESHOP.PEDIDO.IDUSUARIO='" +
				                                argumentos[2] + "')");
			}
			else {// Somos Admins, queremos consultar el listado de TODOS los pedidos
				if (argumentos[3].compareTo("Fecha") == 0) {
					if (!(argumentos[4].isEmpty())) { // Hay limite inferior
						if (!(argumentos[5].isEmpty())) { // Hay limite superior
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO " +
									"WHERE ((ESHOP.PEDIDO.FECHAPEDIDO>" + argumentos[4] +
									") AND (ESHOP.PEDIDO.FECHAPEDIDO<" + argumentos[5] + "))");	
						}
						else { // Hay limite inferior, no hay superior
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO " +
									"WHERE (ESHOP.PEDIDO.FECHAPEDIDO>" + argumentos[4] + ")");
						}
					}
					else { // No hay limite inferior
						if (!(argumentos[5].isEmpty())) { // Hay limite superior
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO " +
									"WHERE (ESHOP.PEDIDO.FECHAPEDIDO<" + argumentos[5] + ")");							
						}
						else { // No hay superior
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO");
						}
					}
				}
				else if (argumentos[3].compareTo("Importe") == 0) {
					if (!(argumentos[4].isEmpty())) { // Hay limite inferior
						if (!(argumentos[5].isEmpty())) { // Hay limite superior
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO " +
									"WHERE ((ESHOP.PEDIDO.IMPORTE>" + argumentos[4] +
									") AND (ESHOP.PEDIDO.IMPORTE<" + argumentos[5] + "))");							
						}
						else { // Hay limite inferior, no hay superior
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO " +
									"WHERE (ESHOP.PEDIDO.IMPORTE>" + argumentos[4] + ")");
						}
					}
					else { // No hay limite inferior
						if (!(argumentos[5].isEmpty())) { // Hay limite superior
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO " +
									"WHERE (ESHOP.PEDIDO.IMPORTE<" + argumentos[5] + ")");							
						}
						else { // No hay limite superior
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO");
						}
					}
				}
				else if (argumentos[3].compareTo("Estado") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO " +
												"WHERE (ESHOP.PEDIDO.ESTADO=" +
												argumentos[4] + ")");
				}
				else if (argumentos[3].compareTo("Cliente") == 0) {
					results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO " +
												"WHERE (ESHOP.PEDIDO.IDUSUARIO='" +
												argumentos[4] + "')");
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
		finally {
			closeRS_STMT_CON(results,stmt,conn);
		}
		return listado;
	}
	
	private static ArrayList<String> consultarProductosPedido (String usuario, String parametros) {
		ArrayList<String> listado = new ArrayList<String>();
		Statement stmt = null;
		ResultSet results = null;
		try {
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (usuario.compareTo("Cliente") == 0) {
				results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTOSPEDIDO, ESHOP.PEDIDOS," +
											"ESHOP.CLIENTE WHERE ((ESHOP.PRODUCTOSPEDIDO.IDPEDIDO" +
											"=ESHOP.PEDIDO.IDPEDIDO) AND (ESHOP.PEDIDO.IDUSUARIO='" +
											argumentos[0] + "'))");
			}
			else if (!(argumentos[1].isEmpty())) { // Somos Admins, queremos consultar el listado de productos asociados a un pedido concreto
				results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTOSPEDIDO, ESHOP.PEDIDOS " +
											"WHERE (ESHOP.PRODUCTOSPEDIDO.IDPEDIDO=" + argumentos[1] + ")");
			}
			else { // Somos Admins, queremos el listado completo.
				results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTOSPEDIDO, ESHOP.PEDIDOS " +
						"WHERE (ESHOP.PRODUCTOSPEDIDO.IDPEDIDO=ESHOP.PEDIDO.IDPEDIDO)");
			}
			
			while (results.next()) {
				String idPedido = results.getString(1);
			    listado.add(idPedido);
			    String idProducto = results.getString(2);
			    listado.add(idProducto);
			    int cantidad = results.getInt(3);
			    listado.add(Integer.toString(cantidad));
			    listado.add("#$#");
			}
			return listado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeRS_STMT_CON(results,stmt,conn);
		}
		return listado;
	}
	
	private static boolean consultarIDPedido (String idPedido) {
		Statement stmt = null;
		ResultSet results = null;
		try {
			getConnection();
			stmt = conn.createStatement();
			results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO " +
				                                "WHERE (ESHOP.PEDIDO.IDPEDIDO" +
												"=" + idPedido + ")");
			if (results.next()) {
				return false;
			}
			else {
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeRS_STMT_CON(results,stmt,conn);
		}
		return true;
	}
	
	private static int anyadirProducto (String tipo, String usuario, String parametros) {
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				int value;
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				value = stmt.executeUpdate("INSERT INTO ESHOP.PRODUCTO" +
										   "(IDPRODUCTO, AGOTADO, EJEMPLARES, PUNTUACION," +
										   " NUMVOTOS, PRECIOUD, GENERO, TITULO, FICHERO," +
										   " FECHA, SINOPSIS) VALUES ('" + argumentos[0] +
										   "', '" + argumentos[1] + "', " + argumentos[2] +
										   ", " + argumentos[3] + ", " + argumentos[4] +
										   ", " + argumentos[5] + ", '" + argumentos[6] +
										   "', '" + argumentos[7] + "', '" + argumentos[8] +
										   "', " + argumentos[9] + ", '" + argumentos[10] + "')");
				if (value == -1) {
					return -1;
				}
				
				if (tipo.compareTo("Pelicula") == 0) {
					int index= 0;
					for (int i= 0; i < 11; i++) {
						index= parametros.indexOf("#", index);
						index++;
					}
					String parametrosPE = new String("");
					parametrosPE = parametrosPE.concat(argumentos[0]);
					parametrosPE = parametrosPE.concat("#");
					parametrosPE = parametrosPE.concat(parametros.substring(index));
					value = anyadirProducto_PE(parametrosPE);
				}
				else { // tipo = "Videojuego"
					int index= 0;
					for (int i= 0; i < 11; i++) {
						index= parametros.indexOf("#", index);
						index++;
					}
					String parametrosVI = new String("");
					parametrosVI = parametrosVI.concat(argumentos[0]);
					parametrosVI = parametrosVI.concat("#");
					parametrosVI = parametrosVI.concat(parametros.substring(index));
					value = anyadirProducto_VI(parametrosVI);
				}
				
				if (value == 0) {
					return 0;
				}
				else {
					return -1;
				}
			}
			else {
				return -1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}

	private static int anyadirProducto_PE (String parametros) {
		Statement stmt = null;
		try {
			int value;
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			value = stmt.executeUpdate("INSERT INTO ESHOP.PELICULA" +
									   "(IDPRODUCTO, SOPORTE, DIRECTOR, ACTORES)" +
									   "VALUES ('" + argumentos[0] + "', '" + argumentos[1] +
									   "', '" + argumentos[2] + "', '" + argumentos[3] + "')");
			
			if (value == 0) {
				return -1;
			}
			else {
				return 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;	
	}
	
	private static int anyadirProducto_VI (String parametros) {
		Statement stmt = null;
		try {
			int value;
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			value = stmt.executeUpdate("INSERT INTO ESHOP.VIDEOJUEGO" +
									   "(IDPRODUCTO, PLATAFORMA, COMPANYA)" +
									   "VALUES ('" + argumentos[0] + "', '" +
									   argumentos[1] + "', '" + argumentos[2] + "')");
			
			if (value == 0) {
				return -1;
			}
			else {
				return 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		System.out.print("FAIL!");
		return -1;	
	}	
	
	private static int anyadirCliente (String usuario, String parametros) {
		Statement stmt = null;
		try {
			String argumentos[] = CajonSastre.CortarString(parametros);
			if (usuario.compareTo("Administrador") == 0) {
				int value;
				getConnection();
				stmt = conn.createStatement();
				value = stmt.executeUpdate("INSERT INTO ESHOP.USUARIO" +
						   "(IDUSUARIO, PASSWORD, ADMINISTRADOR, APELLIDOS," +
						   " NOMBRE, NIF, DIRECCION, TELEFONO, EMAIL, BONIFICADO)" +
						   " VALUES ('" + argumentos[0] + "', '" + argumentos[1] +
						   "', '" + argumentos[2] + "', '" + argumentos[3] +
						   "', '" + argumentos[4] + "', '" + argumentos[5] +
						   "', '" + argumentos[6] + "', '" + argumentos[7] +
						   "', '" + argumentos[8] + "', " + argumentos[9] + ")");
				
				if (value == 0) {
					return -1;
				}
				else {
					return 0;
				}
			}
			else if (argumentos[2].compareTo("F") == 0) {
				int value;
				getConnection();
				stmt = conn.createStatement();
				value = stmt.executeUpdate("INSERT INTO ESHOP.USUARIO" +
						   "(IDUSUARIO, PASSWORD, ADMINISTRADOR, APELLIDOS," +
						   " NOMBRE, NIF, DIRECCION, TELEFONO, EMAIL, BONIFICADO)" +
						   " VALUES ('" + argumentos[0] + "', '" + argumentos[1] +
						   "', '" + argumentos[2] + "', '" + argumentos[3] +
						   "', '" + argumentos[4] + "', '" + argumentos[5] +
						   "', '" + argumentos[6] + "', '" + argumentos[7] +
						   "', '" + argumentos[8] + "', " + argumentos[9] + ")");
				
				if (value == 0) {
					return -1;
				}
				else {
					return 0;
				}
			}
			else {
				return -1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int anyadirPedido (String usuario, String parametros) {
		Statement stmt = null;
		int value;
		try {
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			value = stmt.executeUpdate("INSERT INTO ESHOP.PEDIDO" +
									   "(IDPEDIDO, NUMCUENTA, FECHAPEDIDO, FCADUCIDAD," +
									   " DIRECCION, NOMBRE_CL, ESTADO, CIF_NIF, IDUSUARIO)" +
									   " VALUES ('" + argumentos[0] + "', '" + argumentos[1] +
									   "', " + argumentos[2] + ", " + argumentos[3] +
									   ", '" + argumentos[4] + "', '" + argumentos[5] +
									   "', '" + argumentos[6] + "', '" + argumentos[7] +
									   "', '" + argumentos[8] + "')");
			
			if (value == 0) {
				return -1;
			}
			else {
				return 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int anyadirProductoPedido (String usuario, String parametros) {
		Statement stmt = null;
		int value;
		try {
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			value = stmt.executeUpdate("INSERT INTO ESHOP.PRODUCTOSPEDIDO" +
									   "(IDPEDIDO, IDPRODUCTO, CANTIDAD)" +
									   " VALUES ('" + argumentos[0] + "', '" +
									   argumentos[1] + "', '" + argumentos[2] + ")");
			
			if (value == 0) {
				return -1;
			}
			else {
				return 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int eliminarProducto (String usuario, String parametros) {
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt.executeUpdate("DELETE FROM ESHOP.PRODUCTO " +
								   "WHERE (ESHOP.PRODUCTO.IDPRODUCTO='" +
								   argumentos[0] + "')");
				stmt.executeUpdate("DELETE FROM ESHOP.PELICULA " +
								   "WHERE (ESHOP.PELICULA.IDPRODUCTO='" +
								   argumentos[0] + "')");
				stmt.executeUpdate("DELETE FROM ESHOP.VIDEOJUEGO " +
				   				   "WHERE (ESHOP.VIDEOJUEGO.IDPRODUCTO='" +
				   				   argumentos[0] + "')");
				return 0;
			}
			else {
				return -1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int eliminarCliente (String usuario, String parametros) {
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt.executeUpdate("DELETE FROM ESHOP.USUARIO " +
								   "WHERE (ESHOP.USUARIO.IDUSUARIO='" +
								   argumentos[0] + "')");
				return 0;
			}
			else {
				return -1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int eliminarPedido (String usuario, String parametros) {
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt.executeUpdate("DELETE FROM ESHOP.PEDIDO " +
								   "WHERE (ESHOP.PEDIDO.IDPEDIDO='" +
								   argumentos[0] + "')");
				
				if (eliminarProductosPedido_PE(parametros) == 0) {
					return 0;
				}
				else {
					return -1;
				}
			}
			else {
				return -1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}

	private static int eliminarProductosPedido_PE (String parametros) {
		Statement stmt = null;
		try {
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			stmt.executeUpdate("DELETE FROM ESHOP.PEDIDOSPRODUCTO " +
					   		   "WHERE (ESHOP.PEDIDOSPRODUCTO.IDPEDIDO='" +
					   		   argumentos[0] +"')");
			return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int eliminarProductosPedido_PR (String usuario, String parametros) {
		Statement stmt = null;
		try {
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			stmt.executeUpdate("DELETE FROM ESHOP.PRODUCTOSPEDIDO" +
					   		   "WHERE ((ESHOP.PRODUCTOSPEDIDO.IDPEDIDO='" + argumentos[0] +
					   		   "') AND (ESHOP.PEDIDOSPRODUCTO.IDPRODUCTO='" + argumentos[1] + "'))");
			return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int modificarProducto (String usuario, String parametros) {
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt.executeUpdate("UPDATE FROM ESHOP.PRODUCTO"+
						   		   "SET AGOTADO='" + argumentos[1] + "', EJEMPLARES=" +
						   		   argumentos[2] + ", PUNTUACION=" + argumentos[3] +
						   		   ", NUMVOTOS=" + argumentos[4] + ", PRECIOUD=" +
						   		   argumentos[5] + ", GENERO='" + argumentos[6] + "', TITULO='" +
						   		   argumentos[7] + "', FICHERO='" + argumentos[8] + "', FECHA=" +
						   		   argumentos[9] + ", SINOPSIS='" + argumentos[10] +
						   		   "' WHERE (IDPRODUCTO='" + argumentos[0] + "')");			
				return 0;
			}
			else {
				return -1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int modificarCliente (String usuario, String parametros) {
		Statement stmt = null;
		try {
			String argumentos[] = CajonSastre.CortarString(parametros);
			if ((usuario.compareTo("Administrador") == 0) || (argumentos[0].compareTo(argumentos[1]) == 0)) {
				getConnection();
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE FROM ESHOP.CLIENTE"+
				  				   "SET PASSWORD='" + argumentos[2] + "', ADMINISTRADOR='" +
				   				   argumentos[3] + "', APELLIDOS='" + argumentos[4] +
				   				   "', NOMBRE='" + argumentos[5] + "', NIF='" +
				   				   argumentos[6] + "', DIRECCION='" + argumentos[7] +
				   				   "', TELEFONO='" + argumentos[8] + "', EMAIL='" + argumentos[9] +
				   				   "', BONIFICADO=" + argumentos[10] + " WHERE (IDUSUARIO='" +
				   				   argumentos[1] + "')");
				return 0;
			}
			else {
				return -1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int modificarPedido (String usuario, String parametros) {
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt.executeUpdate("UPDATE FROM ESHOP.CLIENTE"+
						   		   "SET NUMCUENTA='" + argumentos[1] + "', FECHAPEDIDO='" +
						   		   argumentos[2] + "', FCADUCIDAD='" + argumentos[3] +
						   		   "', DIRECCION='" + argumentos[4] + "', NOMBRE_CL='" +
						   		   argumentos[5] + "', ESTADO='" + argumentos[6] +
						   		   "', CIF_NIF='" + argumentos[7] + "' WHERE (IDPEDIDO='" +
						   		   argumentos[0] + "')");
				return 0;
			}
			else {
				return -1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}

	private static int modificarProductosPedido (String usuario, String parametros) {
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt.executeUpdate("UPDATE FROM ESHOP.PRODUCTOSPEDIDO"+
								   "SET CANTIDAD=" + argumentos[2] + " WHERE ((IDPEDIDO='" +
								   argumentos[0] + "') AND (IDPRODUCTO='" + argumentos[1] +"'))");
				
				if (Integer.parseInt(argumentos[2]) == 0) {
					eliminarProductosPedido_PR(usuario, parametros);
				}
				if (consultarIDPedido(argumentos[0])) {
					eliminarPedido(usuario, argumentos[0]);
				}
				return 0;
			}
			else {
				return -1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	public static ArrayList<String> consultar (String objeto, String usuario, String parametros) {
		ArrayList<String> lista = new ArrayList<String>();
		
		if (objeto.compareTo("Producto") == 0) {
			lista = consultarProducto(parametros);			
		}
		else if (objeto.compareTo("Cliente") == 0) {
			lista = consultarCliente(usuario, parametros);
		}
		else if (objeto.compareTo("Pedido") == 0) {
			lista = consultarPedido(usuario, parametros);
		}
		else {
			lista = consultarProductosPedido(usuario, parametros);
		}
		
		return lista;
	}

	public static int anyadir (String objeto, String usuario, String parametros) {
		int value;
		
		if ((objeto.compareTo("Pelicula") == 0) || (objeto.compareTo("Videojuego") == 0)) {
			value = anyadirProducto(objeto, usuario, parametros);
		}
		else if (objeto.compareTo("Cliente") == 0) {
			value = anyadirCliente(usuario, parametros);
		}
		else if (objeto.compareTo("Pedido") == 0) {
			value = anyadirPedido(usuario, parametros);
		}
		else { // Anyadimos a ProductosPedido
			value = anyadirProductoPedido(usuario, parametros);
		}
		
		return value;
	}

	public static int eliminar (String objeto, String usuario, String parametros) {
		int value;
		
		if (objeto.compareTo("Producto") == 0) {
			value = eliminarProducto(usuario, parametros);
		}
		else if (objeto.compareTo("Cliente") == 0) {
			value = eliminarCliente(usuario, parametros);
		}
		else { // Eliminamos Pedidos
			value = eliminarPedido(usuario, parametros);
		}
		
		return value;
	}

	public static int modificar (String objeto, String usuario, String parametros) {
		int value;
		
		if (objeto.compareTo("Producto") == 0) {
			value = modificarProducto(usuario, parametros);
		}
		else if (objeto.compareTo("Cliente") == 0) {
			value = modificarCliente(usuario, parametros);
		}
		else if (objeto.compareTo("Pedido") == 0) {
			value = modificarPedido(usuario, parametros);
		}
		else { // ProductosAPedidos.
			value = modificarProductosPedido(usuario, parametros);
		}
		
		return value;
	}
}
