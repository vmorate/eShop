package SGBD;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.Exception;
import cajonSastre.CajonSastre;

public final class SGBD {
	private final static String DRIVER_CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
	private static String DRIVER_URL = "jdbc:derby:bd; create=true; user=admin; password=1234";
	private final static String USER = "admin";
	private final static String PASSWORD = "1234";

	private static Connection conn;

	static {
		try {
			/** Cargamos el driver correspondiente para el JDBC.*/
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) { 
			e.printStackTrace(System.err);
		}
	}

	private static void getConnection() throws SQLException {
		/** Establecemos la conexión con el JDBC.*/
		conn = DriverManager.getConnection(DRIVER_URL, USER, PASSWORD);
	}
	
	private static void loadSQLScript(String file) throws SQLException {
		/** Ejecutamos un script con sentencias SQL almacenado en el fichero 'file'.*/ 
    	String str = new String();
        StringBuffer sb = new StringBuffer();  
   
        try {
        	FileReader fr = new FileReader(new File(file));
            BufferedReader br = new BufferedReader(fr);
            /** Leemos el contenido del fichero 'file'.*/
            while((str = br.readLine()) != null) {  
            	sb.append(str);
            }
            br.close();
            /** Almacenamos por separado cada sentencia SQL.*/
            String[] inst = sb.toString().split(";");
            getConnection();
            Statement stmt = conn.createStatement();  
            for(int i = 0; i<inst.length; i++) {
            	/** Nos aseguramos de que no se van a ejecutar sentencias vacias.*/
            	if(!inst[i].trim().equals("")) {
            		stmt.executeUpdate(inst[i]);
                    System.out.println(">>"+inst[i]);
            	}
            }
        }
        catch(FileNotFoundException e1) {
        	e1.printStackTrace();
        }
        catch(IOException e2) {
        	e2.printStackTrace();
        }
	}
	
    public static void resetDatabase() throws SQLException {
    	/** Ejecutamos el script SQL que reinicializa la BD.*/
    	System.out.println("Lanzamos el script para resetear la BD");
    	try {
    		loadSQLScript("scripts/ResetBD.sql");
    	}
    	catch (SQLException e) {
    		if (e.toString().indexOf("Schema 'ESHOP' does not exist") <= 0) {
    			e.printStackTrace();
    		}
    	}
    	finally {
    		try {
    			loadSQLScript("scripts/CreateBD.sql");
    		}
    		catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }
     
	public static int setDriverURL(String directorio) {
		/** Establecemos la URL del driver en funcion del valor de 'directorio'.*/
		DRIVER_URL = "jdbc:derby:" + directorio + "; create=true; user=admin; password=1234";
		try {
			getConnection();
		}
		catch (SQLException e) {
			return -1;
		}
		return 0;
	}
	
	private static void closeRS_STMT_CON(ResultSet rs, Statement stmt, Connection con) {
		/** Liberamos los recursos del JDBC previamente reservados.*/ 
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
	
	private static void closeSTMT_CON(Statement stmt, Connection con) {
		/** Liberamos los recursos del JDBC previamente reservados.*/
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
		/** Ejecutamos la consulta de productos correspondiente sobre la BD.*/
		ArrayList<String> listado = new ArrayList<String>();
		Statement stmt = null;
		ResultSet results = null;
		String tipo = new String("");
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (argumentos[0].compareTo("Pelicula") == 0) {
				/** Queremos consultar las peliculas disponibles.*/
				tipo = tipo.concat("Pelicula");
				if (argumentos[1].compareTo("DVD") == 0) {
					/** Queremos consultar las peliculas en DVD.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR," +
				                                "ESHOP.PELICULA AS PE WHERE ((PR.IDPRODUCTO" +
												"=PE.IDPRODUCTO) AND (PE.SOPORTE='DVD'))");
				}
				else if (argumentos[1].compareTo("BR") == 0) {
					/** Queremos consultar las peliculas en BluRay.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR," +
				                                "ESHOP.PELICULA AS PE WHERE ((PR.IDPRODUCTO" +
				                                "=PE.IDPRODUCTO) AND (PE.SOPORTE='BR'))");
				}
				else {/** Queremos consultar el listado de TODAS las peliculas.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR," +
				                                "ESHOP.PELICULA AS PE WHERE (PR.IDPRODUCTO" +
				                                "=PE.IDPRODUCTO)");
				}
			}
			else if (argumentos[0].compareTo("Videojuego") == 0) {
				/** Queremos consultar los videojuegos disponibles.*/
				tipo = tipo.concat("Videojuego");
			    if (argumentos[1].compareTo("PC") == 0) {
			    	/** Queremos consultar los videojuegos para PC.*/
			    	results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR," +
				                                "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
				                                "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='PC'))");
			    }
				else if (argumentos[1].compareTo("PS3") == 0) {
					/** Queremos consultar los videojuegos para PlayStation 3.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR," +
				                                "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
				                                "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='PS3'))");
				}
				else if (argumentos[1].compareTo("PSP") == 0) {
					/** Queremos consultar los videojuegos para PlayStation Portable.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR," +  
				                                "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
				                                "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='PSP'))");
				}
				else if (argumentos[1].compareTo("Wii") == 0) {
					/** Queremos consultar los videojuegos para Nintendo Wii.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR," +
				                                "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
				                                "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='Wii'))");
				}
				else if (argumentos[1].compareTo("NDS") == 0) {
					/** Queremos consultar los videojuegos para Nintendo DS.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR," +
				                                "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
				                                "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='NDS'))");
				}
				else if (argumentos[1].compareTo("Xbox") == 0) {
					/** Queremos consultar los videojuegos para Xbox 360.*/
				    results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR," +
				                                "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
				                                "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='Xbox'))");
				}
				else { /** Queremos consultar el listado de TODOS los videojuegos.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR," +
				                                "ESHOP.VIDEOJUEGO AS VI WHERE (PR.IDPRODUCTO" +
				                                "=VI.IDPRODUCTO)");
				}
			}
			else { /** Queremos consultar el resultado de TODOS los productos.*/
				tipo = tipo.concat("Todo");
				results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTO AS PR, ESHOP.PELICULA AS PE," +
				                            "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
				                            "=VI.IDPRODUCTO) OR (PR.IDPRODUCTO=PE.IDPRODUCTO))");
			}
			/** Almacenamos la info devuelta por el JDBC.*/
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
				int i=11;
				/** En funcion de la consulta, obtenemos una info u otra.*/
				if ((tipo.compareTo("Pelicula") == 0) ||
					((tipo.compareTo("Todo") == 0) &&
						(idProducto.compareTo(results.getString(12)) == 0))) {
					String soporte = results.getString(13);
					listado.add(soporte);
					String director = results.getString(14);
					listado.add(director);
					String actores = results.getString(15);
					listado.add(actores);
				}
				if (tipo.compareTo("Todo") == 0) {
					i=15;
				}
				if ((tipo.compareTo("Videojuego") == 0) ||
					((tipo.compareTo("Todo") == 0) &&
						(idProducto.compareTo(results.getString(++i)) == 0))) {
					String plataforma = results.getString(++i);
					listado.add(plataforma);
					String companya = results.getString(++i);
					listado.add(companya);
				}
				/** Delimitador entre tuplas de info.*/
				listado.add("#$#");
				i++;
			}
			return listado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeRS_STMT_CON(results,stmt,conn);
		}
		return listado;
	}

	private static ArrayList<String> consultarCliente (String usuario, String parametros) {
		/** Ejecutamos la consulta de clientes correspondiente sobre la BD.*/
		ArrayList<String> listado = new ArrayList<String>();
		Statement stmt = null;
		ResultSet results = null;
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			stmt = conn.createStatement();

			if (usuario.compareTo("Cliente") == 0) { /**Somos clientes.*/
				String argumentos[] = CajonSastre.CortarString(parametros);
				results = stmt.executeQuery("SELECT * FROM ESHOP.USUARIO AS US " +
				                                "WHERE (US.IDUSUARIO='" + argumentos[2] + "')");
			}
			else {/** Somos Administradores, queremos consultar
			          el listado de TODOS los clientes.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.USUARIO");
			}

			/** Almacenamos la info devuelta por el JDBC.*/
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
			    /** Delimitador entre tuplas de info.*/
				listado.add("#$#");
			}
			return listado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeRS_STMT_CON(results,stmt,conn);
		}
		return listado;
	}
	
	private static ArrayList<String> consultarPedido (String usuario, String parametros) {
		/** Ejecutamos la consulta de pedidos correspondiente sobre la BD.*/
		ArrayList<String> listado = new ArrayList<String>();
		Statement stmt = null;
		ResultSet results = null;
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (usuario.compareTo("Cliente") == 0) { /**Somos clientes.*/
				results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO AS PE " +
				                                "WHERE (PE.IDUSUARIO='" + argumentos[2] + "')");
			}
			else {/** Somos Administradores, queremos consultar
		          el listado de TODOS los clientes.*/
				if (argumentos[3].compareTo("Fecha") == 0) { /** Buscamos solo en un periodo
				                                                 temporal concreto.*/
					if (!(argumentos[4].isEmpty())) { /** Hay limite inferior.*/
						if (argumentos.length == 6) { /** Hay limite superior.*/
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO AS PE " +
									"WHERE ((PE.FECHAPEDIDO>" + argumentos[4] +
									") AND (PE.FECHAPEDIDO<" + argumentos[5] + "))");	
						}
						else { /** Hay limite inferior, no hay superior.*/
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO AS PE " +
									"WHERE (PE.FECHAPEDIDO>" + argumentos[4] + ")");
						}
					}
					else { /** No hay limite inferior.*/
						if (!(argumentos[5].isEmpty())) { /** Hay limite superior.*/
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO AS PE " +
									"WHERE (PE.FECHAPEDIDO<" + argumentos[5] + ")");							
						}
						else { /** No hay superior.*/
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO");
						}
					}
				}
				else if (argumentos[3].compareTo("Importe") == 0) { /** Buscamos solo en un intervalo
                    													de precios concreto.*/
					if (!(argumentos[4].isEmpty())) { /** Hay limite inferior.*/
						if (!(argumentos[5].isEmpty())) { /** Hay limite superior.*/
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO AS PE " +
									"WHERE ((.PE.IMPORTE>" + argumentos[4] +
									") AND (PE.IMPORTE<" + argumentos[5] + "))");							
						}
						else { /** Hay limite inferior, no hay superior.*/
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO AS PE " +
									"WHERE (PE.IMPORTE>" + argumentos[4] + ")");
						}
					}
					else { /** No hay limite inferior.*/
						if (!(argumentos[5].isEmpty())) { /** Hay limite superior.*/
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO AS PE " +
									"WHERE (PE.IMPORTE<" + argumentos[5] + ")");							
						}
						else { /** No hay limite superior.*/
							results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO");
						}
					}
				}
				else if (argumentos[3].compareTo("Estado") == 0) { /** Buscamos solo en un estado
																	   concreto.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO AS PE " +
												"WHERE (PE.ESTADO='" + argumentos[4] + "')");
				}
				else if (argumentos[3].compareTo("Cliente") == 0) { /** Buscamos solo en un cliente
																		concreto.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO AS PE " +
												"WHERE (PE.IDUSUARIO='" + argumentos[4] + "')");
				}
				else { /** Buscamos todos los pedidos realizados.*/
					results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO");
				}
			}

			/** Almacenamos la info devuelta por el JDBC.*/
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
			    String CIF_NIF = results.getString(8);
			    listado.add(CIF_NIF);
			    String idUsuario = results.getString(9);
			    listado.add(idUsuario);
			    /** Delimitador entre tuplas de info.*/
				listado.add("#$#");
			}
			return listado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeRS_STMT_CON(results,stmt,conn);
		}
		return listado;
	}
	
	private static ArrayList<String> consultarProductosPedido (String usuario, String parametros) {
		/** Ejecutamos la consulta de productos pedidos correspondiente sobre la BD.*/
		ArrayList<String> listado = new ArrayList<String>();
		Statement stmt = null;
		ResultSet results = null;
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (usuario.compareTo("Cliente") == 0) { /**Somos clientes.*/
				results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTOSPEDIDO AS PP, ESHOP.PEDIDO AS PE," +
											"ESHOP.CLIENTE AS CL WHERE ((PP.IDPEDIDO" +
											"=PE.IDPEDIDO) AND (PE.IDUSUARIO='" +
											argumentos[0] + "'))");
			}
			/** Somos Administradores, queremos consultar el listado de productos asociados
			    a un pedido concreto.*/
			else if (argumentos.length == 2) {
				results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTOSPEDIDO AS PP " +
											"WHERE (PP.IDPEDIDO='" + argumentos[1] + "')");
			}
			else { /** Somos Administradores, queremos el listado completo.*/
				results = stmt.executeQuery("SELECT * FROM ESHOP.PRODUCTOSPEDIDO AS PP, " + 
						                    "ESHOP.PEDIDO AS PE WHERE (PP.IDPEDIDO=PE.IDPEDIDO)");
			}

			/** Almacenamos la info devuelta por el JDBC.*/
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeRS_STMT_CON(results,stmt,conn);
		}
		return listado;
	}
	
	private static boolean consultarIDPedido (String idPedido) {
		/** Ejecutamos la consulta de un pedido concreto sobre la BD.*/
		Statement stmt = null;
		ResultSet results = null;
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			stmt = conn.createStatement();
			results = stmt.executeQuery("SELECT * FROM ESHOP.PEDIDO AS PE " +
				                        "WHERE (PE.IDPEDIDO='" + idPedido + "')");
			if (results.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeRS_STMT_CON(results,stmt,conn);
		}
		return true;
	}
	
	private static int anyadirProducto (String tipo, String usuario, String parametros) {
		/** Anyadimos un nuevo producto a la BD.*/
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				int value;
				/** Establecemos la conexion con la BD.*/
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
				
				if (tipo.compareTo("Pelicula") == 0) { /** Se trata de una pelicula.*/
					int index= 0;
					for (int i= 0; i < 11; i++) {
						index= parametros.indexOf("#", index);
						index++;
					}
					String parametrosPE = new String("");
					parametrosPE = parametrosPE.concat(argumentos[0]);
					parametrosPE = parametrosPE.concat("#");
					parametrosPE = parametrosPE.concat(parametros.substring(index));
					/** Anyadimos los atributos caracteristicos de las peliculas.*/
					value = anyadirProducto_PE(parametrosPE);
				}
				else { /** Se trata de un videojuego.*/
					int index= 0;
					for (int i= 0; i < 11; i++) {
						index= parametros.indexOf("#", index);
						index++;
					}
					String parametrosVI = new String("");
					parametrosVI = parametrosVI.concat(argumentos[0]);
					parametrosVI = parametrosVI.concat("#");
					parametrosVI = parametrosVI.concat(parametros.substring(index));
					/** Anyadimos los atributos caracteristicos de los videojuegos.*/
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}

	private static int anyadirProducto_PE (String parametros) {
		/** Anyadimos una nueva pelicula a la BD.*/
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;	
	}
	
	private static int anyadirProducto_VI (String parametros) {
		/** Anyadimos un nuevo videojuego a la BD.*/
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		System.out.print("FAIL!");
		return -1;	
	}	
	
	private static int anyadirCliente (String usuario, String parametros) {
		/** Anyadimos un nuevo cliente a la BD.*/
		Statement stmt = null;
		try {
			String argumentos[] = CajonSastre.CortarString(parametros);
			if (usuario.compareTo("Administrador") == 0) { /** El nuevo cliente
			                                    en realidad es un administrador.*/
				int value;
				/** Establecemos la conexion con la BD.*/
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
				/** Establecemos la conexion con la BD.*/
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int anyadirPedido (String usuario, String parametros) {
		/** Anyadimos un nuevo pedido a la BD.*/
		Statement stmt = null;
		int value;
		try {
			/** Establecemos la conexion con la BD.*/
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int anyadirProductoPedido (String usuario, String parametros) {
		/** Anyadimos un nuevo producto a un pedido de la BD.*/
		Statement stmt = null;
		int value;
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			value = stmt.executeUpdate("INSERT INTO ESHOP.PRODUCTOSPEDIDO" +
									   "(IDPEDIDO, IDPRODUCTO, CANTIDAD)" +
									   " VALUES ('" + argumentos[0] + "', '" +
									   argumentos[1] + "', " + argumentos[2] + ")");
			
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}

	private static int modificarProducto (String tipo, String usuario, String parametros) {
		/** Modificamos los atributos de un producto de la BD.*/
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt.executeUpdate("UPDATE ESHOP.PRODUCTO " +
						   		   "SET AGOTADO='" + argumentos[1] + "', EJEMPLARES=" +
						   		   argumentos[2] + ", PUNTUACION=" + argumentos[3] +
						   		   ", NUMVOTOS=" + argumentos[4] + ", PRECIOUD=" +
						   		   argumentos[5] + ", GENERO='" + argumentos[6] + "', TITULO='" +
						   		   argumentos[7] + "', FICHERO='" + argumentos[8] + "', FECHA=" +
						   		   argumentos[9] + ", SINOPSIS='" + argumentos[10] +
						   		   "' WHERE (IDPRODUCTO='" + argumentos[0] + "')");
				if (tipo.compareTo("Pelicula") == 0) { /** Modificamos los atributos caracteristicos
				                                           de la pelicula.*/
					stmt.executeUpdate("UPDATE ESHOP.PELICULA " +
									   "SET SOPORTE='" + argumentos[11] + "', DIRECTOR='" +
									   argumentos[12] + "', ACTORES='" + argumentos[13] + "'" +
									   "WHERE (IDPRODUCTO='" + argumentos[0] + "')");
				}
				else { /** Modificamos los atributos caracteristicos del videojuego.*/
					stmt.executeUpdate("UPDATE ESHOP.VIDEOJUEGO " +
									   "SET PLATAFORMA='" + argumentos[11] + "', COMPANYA='" +
									   argumentos[12] + "' WHERE (IDPRODUCTO='" + argumentos[0] + "')");
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int modificarCliente (String usuario, String parametros) {
		/** Modificamos los atributos de un cliente de la BD.*/
		Statement stmt = null;
		try {
			String argumentos[] = CajonSastre.CortarString(parametros);
			if ((usuario.compareTo("Administrador") == 0) || (argumentos[0].compareTo(argumentos[1]) == 0)) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE ESHOP.USUARIO " +
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int modificarPedido (String usuario, String parametros) {
		/** Modificamos los atributos de un pedido de la BD.*/
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt.executeUpdate("UPDATE ESHOP.PEDIDO " +
						   		   "SET NUMCUENTA='" + argumentos[1] + "', FECHAPEDIDO=" +
						   		   argumentos[2] + ", FCADUCIDAD=" + argumentos[3] +
						   		   ", DIRECCION='" + argumentos[4] + "', NOMBRE_CL='" +
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}

	private static int modificarProductosPedido (String usuario, String parametros) {
		/** Modificamos los atributos de un producto asociado a un pedido de la BD.*/
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt.executeUpdate("UPDATE ESHOP.PRODUCTOSPEDIDO " +
								   "SET CANTIDAD=" + argumentos[2] + " WHERE ((IDPEDIDO='" +
								   argumentos[0] + "') AND (IDPRODUCTO='" + argumentos[1] +"'))");
				if (Integer.parseInt(argumentos[2]) == 0) {
					/** Estamos eliminado el producto del pedido.*/
					eliminarProductosPedido_PR(usuario, parametros);
				}
				if (!(consultarIDPedido(argumentos[0]))) {
					/** El pedido esta vacio, borramos las entradas de la tabla ProductosPedido.*/
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int eliminarProducto (String usuario, String parametros) {
		/** Eliminamos un producto de la BD.*/
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) { /** Somos administradores.*/
				/** Establecemos la conexion con la BD.*/
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt.executeUpdate("DELETE FROM ESHOP.PELICULA AS PE " +
						   		   "WHERE (PE.IDPRODUCTO='" + argumentos[0] + "')");
				stmt.executeUpdate("DELETE FROM ESHOP.VIDEOJUEGO AS VI " +
		   				    	   "WHERE (VI.IDPRODUCTO='" + argumentos[0] + "')");
				stmt.executeUpdate("DELETE FROM ESHOP.PRODUCTO AS PR " +
								   "WHERE (PR.IDPRODUCTO='" + argumentos[0] + "')");
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int eliminarCliente (String usuario, String parametros) {
		/** Eliminamos un cliente de la BD.*/
		ResultSet results = null;
		Statement stmt = null;
		ArrayList<String> bar = null;
		try {
			if (usuario.compareTo("Administrador") == 0) { /** Somos administradores.*/
				bar = new ArrayList<String>();
				/** Establecemos la conexion con la BD.*/
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				results = stmt.executeQuery("SELECT PE.IDPEDIDO FROM ESHOP.PEDIDO AS PE," +
											"ESHOP.PRODUCTOSPEDIDO AS PP " +
											"WHERE ((PE.IDUSUARIO='" + argumentos[0] +
							      			"') AND (PP.IDPEDIDO=PE.IDPEDIDO))");
				int i = 1;
				
				while (results.next()) {
					String idPedido = results.getString(i);
					bar.add(idPedido);
				}
				/** Eliminamos las entradas correspondientes en la tabla ProductosPedido.*/
				for (i = 0; i < bar.size(); i++) {
					stmt.executeUpdate("DELETE FROM ESHOP.PRODUCTOSPEDIDO AS PP " +
									   "WHERE (ESHOP.PP.IDPEDIDO='" + bar.get(i) + "')");
				}
				/** Eliminamos todos los pedidos asociados a dicho cliente.*/
				for (i = 0; i < bar.size(); i++) {
					stmt.executeUpdate("DELETE FROM ESHOP.PEDIDO AS PE " +
									   "WHERE (PE.IDPEDIDO='" + bar.get(i) + "')");
				}
				stmt.executeUpdate("DELETE FROM ESHOP.PEDIDO AS PE " +
								   "WHERE (PE.IDUSUARIO ='" + argumentos[0] + "')");
				stmt.executeUpdate("DELETE FROM ESHOP.USUARIO AS US " +
								   "WHERE (US.IDUSUARIO='" + argumentos[0] + "')");
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int eliminarPedido (String usuario, String parametros) {
		/** Eliminamos un pedido de la BD.*/
		Statement stmt = null;
		try {
			if (usuario.compareTo("Administrador") == 0) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				stmt = conn.createStatement();
				String argumentos[] = CajonSastre.CortarString(parametros);
				/** Eliminamos los productos asociados a dicho pedido.*/
				if (eliminarProductosPedido_PE(parametros) == 0) {
					stmt.executeUpdate("DELETE FROM ESHOP.PEDIDO AS PE " +
							   "WHERE (PE.IDPEDIDO='" + argumentos[0] + "')");
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
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}

	private static int eliminarProductosPedido_PE (String parametros) {
		/** Eliminamos los productos asociados al producto que estamos eliminando de la BD.*/
		Statement stmt = null;
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			stmt.executeUpdate("DELETE FROM ESHOP.PRODUCTOSPEDIDO AS PP " +
					   		   "WHERE (PP.IDPEDIDO='" + argumentos[0] +"')");
			return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	private static int eliminarProductosPedido_PR (String usuario, String parametros) {
		/** Eliminamos un producto determinado asociado al producto que estamos
		    eliminando de la BD.*/
		Statement stmt = null;
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			stmt = conn.createStatement();
			String argumentos[] = CajonSastre.CortarString(parametros);
			stmt.executeUpdate("DELETE FROM ESHOP.PRODUCTOSPEDIDO AS PP " +
					   		   "WHERE ((PP.IDPEDIDO='" + argumentos[0] +
					   		   "') AND (PP.IDPRODUCTO='" + argumentos[1] + "'))");
			return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
		return -1;
	}
	
	public static ArrayList<String> consultar (String objeto, String usuario, String parametros) {
		/** Realizamos una consulta sobre la BD.*/
		ArrayList<String> lista = new ArrayList<String>();

		/** La consulta es sobre productos.*/
		if (objeto.compareTo("Producto") == 0) {
			lista = consultarProducto(parametros);			
		}
		/** La consulta es sobre clientes.*/
		else if (objeto.compareTo("Cliente") == 0) {
			lista = consultarCliente(usuario, parametros);
		}
		/** La consulta es sobre pedidos.*/
		else if (objeto.compareTo("Pedido") == 0) {
			lista = consultarPedido(usuario, parametros);
		}
		/** La consulta es sobre productos asociados a pedidos.*/
		else {
			lista = consultarProductosPedido(usuario, parametros);
		}
		
		return lista;
	}

	public static int anyadir (String objeto, String usuario, String parametros) {
		/** Anyadimos un elemento a la BD.
		    Dev: -1 en caso de error.
		          0 si se ha anyadido correctamente.
		 */
		
		int value;

		/** Vamos a anyadir un producto.*/
		if ((objeto.compareTo("Pelicula") == 0) || (objeto.compareTo("Videojuego") == 0)) {
			value = anyadirProducto(objeto, usuario, parametros);
		}
		/** Vamos a anyadir un cliente.*/
		else if (objeto.compareTo("Cliente") == 0) {
			value = anyadirCliente(usuario, parametros);
		}
		/** Vamos a anyadir un pedido.*/
		else if (objeto.compareTo("Pedido") == 0) {
			value = anyadirPedido(usuario, parametros);
		}
		else { /** Vamos a anyadir un producto a un pedido determinado.*/
			value = anyadirProductoPedido(usuario, parametros);
		}
		
		return value;
	}

	public static int modificar (String objeto, String usuario, String parametros) {
		/** Modificamos los atributos de un elemento de la BD.
			Dev: -1 en caso de error.
		          0 si se ha anyadido correctamente.
		*/
		int value;

		/** Las modificaciones se realizaran sobre un producto.*/
		if ((objeto.compareTo("Pelicula") == 0) || (objeto.compareTo("Videojuego") == 0)) {
			value = modificarProducto(objeto,usuario, parametros);
		}
		/** Las modificaciones se realizaran sobre un cliente.*/
		else if (objeto.compareTo("Cliente") == 0) {
			value = modificarCliente(usuario, parametros);
		}
		/** Las modificaciones se realizaran sobre un pedido.*/
		else if (objeto.compareTo("Pedido") == 0) {
			value = modificarPedido(usuario, parametros);
		}
		else { /** Las modificaciones se realizaran sobre un producto asociado a un pedido.*/
			value = modificarProductosPedido(usuario, parametros);
		}
		
		return value;
	}
	
	public static int eliminar (String objeto, String usuario, String parametros) {
		/** Eliminamos un elemento de la BD.
			Dev: -1 en caso de error.
		          0 si se ha anyadido correctamente.
		*/
		int value;

		/** Eliminamos un producto.*/
		if (objeto.compareTo("Producto") == 0) {
			value = eliminarProducto(usuario, parametros);
		}
		/** Eliminamos un cliente.*/
		else if (objeto.compareTo("Cliente") == 0) {
			value = eliminarCliente(usuario, parametros);
		}
		/** Eliminamos un pedido.*/
		else if (objeto.compareTo("Pedido") == 0) {
			value = eliminarPedido(usuario, parametros);
		}
		else { /** Eliminamos un producto asociado a un pedido.*/
			value = eliminarProductosPedido_PR(usuario, parametros);
		}
		
		return value;
	}

}
