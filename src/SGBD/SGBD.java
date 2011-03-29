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
	private final static boolean DEBUG = true;
	private static Connection conn;

	static {
		try {
			/** Cargamos el driver correspondiente para el JDBC.*/
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			if (DEBUG) {
				e.printStackTrace(System.err);
			}
		}
	}

	private static void getConnection () throws SQLException {
		/** Establecemos la conexion con el JDBC.*/
		conn = DriverManager.getConnection(DRIVER_URL, USER, PASSWORD);
	} /* getConnection */
	
	private static int loadSQLScript (String file) throws SQLException {
		/** Ejecutamos un script con sentencias SQL almacenado en el fichero 'file'.
			Dev:  0 si exito.
				 -1 si fichero no encontrado.
				 -2 si fallo E/S.
		*/
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

            for(int i = 0; i<inst.length; i++) {
            	/** Nos aseguramos de que no se van a ejecutar sentencias vacias.*/
            	if(!inst[i].trim().equals("")) {
            		PreparedStatement stmt = conn.prepareStatement(inst[i]);
            		stmt.executeUpdate();
            		if (DEBUG) {
            			System.out.println(">>"+inst[i]);
            		}
            	}
            }
            
            return 0;
        }
        catch(FileNotFoundException e1) {
        	if (DEBUG) {
        		e1.printStackTrace();
        	}
        	
        	return -1;
        }
        catch(IOException e2) {
        	if (DEBUG) {
        		e2.printStackTrace();
        	}
        	        	
        	return -2;
        }
	} /* loadSQLScript */
	
    public static int resetDatabase () {
    	/** Ejecutamos el script SQL que reinicializa la BD.
    		Dev:  0 si exito.
    			 -1 si fichero no encontrado.
    			 -2 si error E/S.
    			 -3 si error SQL.
    	*/
    	try {
        	if (DEBUG) {
        		System.out.println("Lanzamos el script para resetear la BD");
        	}
        	int value;
    		value = loadSQLScript("scripts/ResetBD.sql");
    		
    		if ((value == -1) || (value == -2)) {
    			return value;
    		}
    	}
    	catch (SQLException e) {
    		if (e.toString().indexOf("Schema 'ESHOP' does not exist") <= 0) {
    			if (DEBUG) {
    				e.printStackTrace();
    			}
    			return -3;
    		}
    		else {
    			if (DEBUG) {
    				System.out.println("La BD no estaba creada con anterioridad");
    			}
    		}
    	}
		try {
			int value;
			value = loadSQLScript("scripts/CreateBD.sql");
			return value;
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
			return -3;
		}
    } /* resetDataBase */
     
	public static int setDriverURL (String directorio) {
		/** Establecemos la URL del driver en funcion del valor de 'directorio'.
			Dev:  0 si exito.
				 -1 si error SQL.
		*/
		DRIVER_URL = "jdbc:derby:" + directorio + "; create=true; user=admin; password=1234";
		try {
			getConnection();
		}
		catch (SQLException e) {
			return -1;
		}
		
		return 0;
	} /* setDriverURL */
	
	private static void closeRS_STMT_CON (ResultSet rs, Statement stmt, Connection con) {
		/** Liberamos los recursos del JDBC previamente reservados.*/ 
		if (rs != null) {
            try {
            	rs.close();
            }
            catch (SQLException e) {
            	if (DEBUG) {
            		e.printStackTrace();
            	}
            }
		}
		if (stmt != null) {
            try {
            	stmt.close();
            }
            catch (SQLException e) {
            	if (DEBUG) {
            		e.printStackTrace();
            	}
            }
		}
		if (con != null) {
            try {
            	con.close();
            }
            catch (SQLException e) {
            	if (DEBUG) {
            		e.printStackTrace();
            	}
            }
		}
	} /* closeRS_STMT_CON */
	
	private static void closeSTMT_CON (Statement stmt, Connection con) {
		/** Liberamos los recursos del JDBC previamente reservados.*/
		if (stmt != null) {
            try {
            	stmt.close();
            }
            catch (SQLException e) {
            	if (DEBUG) {
            		e.printStackTrace();
            	}
            }
		}
		if (con != null) {
            try {
            	con.close();
            }
            catch (SQLException e) {
            	if (DEBUG) {
            		e.printStackTrace();
            	}
            }
		}
	} /* closeSTMT_CON */

	private static ArrayList<String> consultarProducto (String parametros) {
		/** Ejecutamos la consulta de productos correspondiente sobre la BD.
			Dev: Listado de los productos consultados si exito.
				 Listado con un elemento caracteristico si error SQL.
		*/
		ArrayList<String> listado = new ArrayList<String>();
		PreparedStatement stmt = null;
		ResultSet results = null;
		String tipo = new String("");
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (argumentos[0].compareTo("Pelicula") == 0) {
				/** Queremos consultar las peliculas disponibles.*/
				tipo = tipo.concat("Pelicula");
				if (argumentos[1].compareTo("DVD") == 0) {
					/** Queremos consultar las peliculas en DVD.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR," +
				                                "ESHOP.PELICULA AS PE WHERE ((PR.IDPRODUCTO" +
												"=PE.IDPRODUCTO) AND (PE.SOPORTE='DVD'))");
					results = stmt.executeQuery();
				}
				else if (argumentos[1].compareTo("BR") == 0) {
					/** Queremos consultar las peliculas en BluRay.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR," +
                            					 "ESHOP.PELICULA AS PE WHERE ((PR.IDPRODUCTO" +
                            					 "=PE.IDPRODUCTO) AND (PE.SOPORTE='BR'))");
					results = stmt.executeQuery();
				}
				else {/** Queremos consultar el listado de TODAS las peliculas.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR," +
                            					 "ESHOP.PELICULA AS PE WHERE (PR.IDPRODUCTO" +
                            					 "=PE.IDPRODUCTO)");
					results = stmt.executeQuery();
				}
			} /* if [ argumentos[0] == "Pelicula" ] */
			
			else if (argumentos[0].compareTo("Videojuego") == 0) {
				/** Queremos consultar los videojuegos disponibles.*/
				tipo = tipo.concat("Videojuego");
			    if (argumentos[1].compareTo("PC") == 0) {
			    	/** Queremos consultar los videojuegos para PC.*/
			    	stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR," +
                            					 "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
                            					 "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='PC'))");
			    	results = stmt.executeQuery();
			    }
				else if (argumentos[1].compareTo("PS3") == 0) {
					/** Queremos consultar los videojuegos para PlayStation 3.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR," +
                            					 "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
                            					 "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='PS3'))");
					results = stmt.executeQuery();
				}
				else if (argumentos[1].compareTo("PSP") == 0) {
					/** Queremos consultar los videojuegos para PlayStation Portable.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR," +  
                            					 "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
                            					 "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='PSP'))");
					results = stmt.executeQuery();
				}
				else if (argumentos[1].compareTo("Wii") == 0) {
					/** Queremos consultar los videojuegos para Nintendo Wii.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR," +
                            					 "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
                            					 "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='Wii'))");
					results = stmt.executeQuery();
				}
				else if (argumentos[1].compareTo("NDS") == 0) {
					/** Queremos consultar los videojuegos para Nintendo DS.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR," +
                            					 "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
                            					 "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='NDS'))");
					results = stmt.executeQuery();
				}
				else if (argumentos[1].compareTo("Xbox") == 0) {
					/** Queremos consultar los videojuegos para Xbox 360.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR," +
                            					 "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
                            					 "=VI.IDPRODUCTO) AND (VI.PLATAFORMA='Xbox'))");
				    results = stmt.executeQuery();
				}
				else { /** Queremos consultar el listado de TODOS los videojuegos.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR," +
                            					 "ESHOP.VIDEOJUEGO AS VI WHERE (PR.IDPRODUCTO" +
                            					 "=VI.IDPRODUCTO)");
					results = stmt.executeQuery();
				}
			} /* else if [ argumentos[0] == "Videojuego" ] */
			
			else { /** Queremos consultar el resultado de TODOS los productos.*/
				tipo = tipo.concat("Todo");
				stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTO AS PR, ESHOP.PELICULA AS PE," +
                        					 "ESHOP.VIDEOJUEGO AS VI WHERE ((PR.IDPRODUCTO" +
                        					 "=VI.IDPRODUCTO) OR (PR.IDPRODUCTO=PE.IDPRODUCTO))");
				results = stmt.executeQuery();
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
			} /* while (results.next()) */
			
			return listado;
		} /* try */
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
			/** Vamos a tener una lista con un elemento caracteristico */
			listado.clear();
			listado.add("Error SQL");
			
			return listado;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeRS_STMT_CON(results,stmt,conn);
		}
	} /* consultarProducto */

	private static ArrayList<String> consultarCliente (String usuario, String parametros) {
		/** Ejecutamos la consulta de clientes correspondiente sobre la BD.
			Dev: Listado de los clientes consultados si exito.
				 Listado con un elemento caracteristico si error SQL.
		*/
		ArrayList<String> listado = new ArrayList<String>();
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();

			if (usuario.compareTo("Cliente") == 0) { /**Somos clientes.*/
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt = conn.prepareStatement("SELECT * FROM ESHOP.USUARIO AS US " +
                        					 "WHERE (US.IDUSUARIO=?)");
				stmt.setInt(1,Integer.parseInt(argumentos[2]));
				results = stmt.executeQuery();
			}
			else {/** Somos Administradores, queremos consultar
			          el listado de TODOS los clientes.*/
				stmt = conn.prepareStatement("SELECT * FROM ESHOP.USUARIO");
				results = stmt.executeQuery();
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
			if (DEBUG) {
				e.printStackTrace();
			}
			/** Vamos a tener una lista con un elemento caracteristico */
			listado.clear();
			listado.add("Error SQL");
			
			return listado;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeRS_STMT_CON(results,stmt,conn);
		}
	} /* consultarCliente */
	
	private static ArrayList<String> consultarPedido (String usuario, String parametros) {
		/** Ejecutamos la consulta de pedidos correspondiente sobre la BD.
			Dev: Listado de los pedidos consultados si exito.
				 Listado con un elemento caracteristico si error SQL.
		*/
		ArrayList<String> listado = new ArrayList<String>();
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (usuario.compareTo("Cliente") == 0) { /**Somos clientes.*/
				stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO AS PE " +
                        					  "WHERE (PE.IDUSUARIO=?)");
				stmt.setString(1,argumentos[2]);
				results = stmt.executeQuery();
			}
			else {/** Somos Administradores, queremos consultar
		          el listado de TODOS los clientes.*/
				if (argumentos[3].compareTo("Fecha") == 0) { /** Buscamos solo en un periodo
				                                                 temporal concreto.*/
					if (!(argumentos[4].isEmpty())) { /** Hay limite inferior.*/
						if (argumentos.length == 6) { /** Hay limite superior.*/
							stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO AS PE " +
														 "WHERE ((PE.FECHAPEDIDO>?) AND " +
														 "(PE.FECHAPEDIDO<?))");
							stmt.setInt(1,Integer.parseInt(argumentos[4]));
							stmt.setInt(2,Integer.parseInt(argumentos[5]));
							results = stmt.executeQuery();	
						}
						else { /** Hay limite inferior, no hay superior.*/
							stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO AS PE " +
														 "WHERE (PE.FECHAPEDIDO>?)");
							stmt.setInt(1,Integer.parseInt(argumentos[4]));
							results = stmt.executeQuery();
						}
					} /* if [ Hay limite inferior ] */
					
					else { /** No hay limite inferior.*/
						if (!(argumentos[5].isEmpty())) { /** Hay limite superior.*/
							stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO AS PE " +
														 "WHERE (PE.FECHAPEDIDO<?)");
							stmt.setInt(1,Integer.parseInt(argumentos[5]));
							results = stmt.executeQuery();							
						}
						else { /** No hay superior.*/
							stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO");
							results = stmt.executeQuery();
						}
					} /* else [ No hay limite inferior ] */
				} /* if [ argumentos[3] == "Fecha" ] */
				
				else if (argumentos[3].compareTo("Importe") == 0) { /** Buscamos solo en un intervalo
                    													de precios concreto.*/
					if (!(argumentos[4].isEmpty())) { /** Hay limite inferior.*/
						if (!(argumentos[5].isEmpty())) { /** Hay limite superior.*/
							stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO AS PE " +
														 "WHERE ((.PE.IMPORTE>?) AND " +
														 "(PE.IMPORTE<?))");
							stmt.setInt(1,Integer.parseInt(argumentos[4]));
							stmt.setInt(2,Integer.parseInt(argumentos[5]));
							results = stmt.executeQuery();							
						}
						else { /** Hay limite inferior, no hay superior.*/
							stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO AS PE " +
														 "WHERE (PE.IMPORTE>?)");
							stmt.setInt(1,Integer.parseInt(argumentos[4]));
							results = stmt.executeQuery();
						}
					} /* if [ Hay limite inferior ] */
					
					else { /** No hay limite inferior.*/
						if (!(argumentos[5].isEmpty())) { /** Hay limite superior.*/
							stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO AS PE " +
														 "WHERE (PE.IMPORTE<?)");
							stmt.setInt(1,Integer.parseInt(argumentos[5]));
							results = stmt.executeQuery();
						}
						else { /** No hay limite superior.*/
							stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO");
							results = stmt.executeQuery();
						}
					} /* else [ No hay limite inferior ] */
				} /* else if [ argumentos[3] == "Importe" ] */
				
				else if (argumentos[3].compareTo("Estado") == 0) { /** Buscamos solo en un estado
																	   concreto.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO AS PE " +
												 "WHERE (PE.ESTADO='?')");
					stmt.setString(1,argumentos[4]);
					results = stmt.executeQuery();
				}
				else if (argumentos[3].compareTo("Cliente") == 0) { /** Buscamos solo en un cliente
																		concreto.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO AS PE " +
												 "WHERE (PE.IDUSUARIO=?)");
					stmt.setString(1,argumentos[4]);
					results = stmt.executeQuery();
				}
				else { /** Buscamos todos los pedidos realizados.*/
					stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO");
					results = stmt.executeQuery();
				}
			} /* else [Somos administradores] */

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
		} /* try */
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
			/** Vamos a tener una lista con un elemento caracteristico */
			listado.clear();
			listado.add("Error SQL");
			
			return listado;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeRS_STMT_CON(results,stmt,conn);
		}
	} /* consultarPedido */
	
	private static ArrayList<String> consultarProductosPedido (String usuario, String parametros) {
		/** Ejecutamos la consulta de productos pedidos correspondiente sobre la BD.
			Dev: Listado de los productos asociados a un pedido concreto si exito
				 Listado con un elemento caracteristico si error SQL.
		*/
		ArrayList<String> listado = new ArrayList<String>();
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			String argumentos[] = CajonSastre.CortarString(parametros);

			if (usuario.compareTo("Cliente") == 0) { /**Somos clientes.*/
				stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTOSPEDIDO AS PP, ESHOP.PEDIDO AS PE," +
											 "ESHOP.CLIENTE AS CL WHERE ((PP.IDPEDIDO" +
											 "=PE.IDPEDIDO) AND (PE.IDUSUARIO=?))");
				stmt.setString(1,argumentos[0]);
				results = stmt.executeQuery();
			}
			/** Somos Administradores, queremos consultar el listado de productos asociados
			    a un pedido concreto.*/
			else if (argumentos.length == 2) {
				stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTOSPEDIDO AS PP " +
											 "WHERE (PP.IDPEDIDO=?)");
				stmt.setString(1,argumentos[1]);
				results = stmt.executeQuery();
			}
			else { /** Somos Administradores, queremos el listado completo.*/
				stmt = conn.prepareStatement("SELECT * FROM ESHOP.PRODUCTOSPEDIDO AS PP, " + 
	                    					 "ESHOP.PEDIDO AS PE WHERE (PP.IDPEDIDO=PE.IDPEDIDO)");
				results = stmt.executeQuery();
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
		} /* try */
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
			/** Vamos a tener una lista con un elemento caracteristico */
			listado.clear();
			listado.add("Error SQL");
			
			return listado;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeRS_STMT_CON(results,stmt,conn);
		}
	} /* consultarProductosPedido */
	
	private static int consultarIDPedido (String idPedido) {
		/** Ejecutamos la consulta de un pedido concreto sobre la BD.
			Dev:  1 si existe dicho pedido.
				  0 si no existe dicho pedido.
				 -1 si error SQL.
		*/
		PreparedStatement stmt = null;
		ResultSet results = null;

		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			stmt = conn.prepareStatement("SELECT * FROM ESHOP.PEDIDO AS PE " +
                    					 "WHERE (PE.IDPEDIDO=?)");
			stmt.setString(1,idPedido);
			results = stmt.executeQuery();
			if (results.next()) {
				return 1;
			}
			else {
				return 0;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
			return -1;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeRS_STMT_CON(results,stmt,conn);
		}
	} /* consultarIDPedido */

	private static int anyadirProducto (String tipo, String usuario, String parametros) {
		/** Anyadimos un nuevo producto a la BD.
			Dev:  0 si exito.
				 -1 si no se ha insertado nada.
				 -2 si error de permisos.
				 -3 si error SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			if (usuario.compareTo("Administrador") == 0) {
				int value;
				
				/** Establecemos la conexion con la BD.*/
				getConnection();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt = conn.prepareStatement("INSERT INTO ESHOP.PRODUCTO VALUES " +
						                     "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1,argumentos[0]);
				stmt.setString(2,argumentos[1]);
				stmt.setInt(3,Integer.parseInt(argumentos[2]));
				stmt.setInt(4,Integer.parseInt(argumentos[3]));
				stmt.setInt(5,Integer.parseInt(argumentos[4]));
				stmt.setInt(6,Integer.parseInt(argumentos[5]));
				stmt.setString(7,argumentos[6]);
				stmt.setString(8,argumentos[7]);
				stmt.setString(9,argumentos[8]);
				stmt.setInt(10,Integer.parseInt(argumentos[9]));
				stmt.setString(11,argumentos[10]);
				
				value = stmt.executeUpdate();
				if (value == 0) {
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
				
				if ((value == 0) || (value == -1)) {
					return value;
				}
				else {
					return -3;
				}
			} /* if [ usuario == "Administrador" ] */
			else { /** No somos administradores */
				return -2;
			}
		} /* try */
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* anyadirProducto */

	private static int anyadirProducto_PE (String parametros) {
		/** Anyadimos una nueva pelicula a la BD.
			Dev:  0 si exito.
				 -1 si no se ha insertado nada.
				 -2 si error SQL.
		*/
		PreparedStatement stmt = null;
		try {
			int value;
			String argumentos[] = CajonSastre.CortarString(parametros);
			stmt = conn.prepareStatement("INSERT INTO ESHOP.PELICULA " +
					   					 "VALUES (?, ?, ?, ?)");
			stmt.setString(1,argumentos[0]);
			stmt.setString(2,argumentos[1]);
			stmt.setString(3,argumentos[2]);
			stmt.setString(4,argumentos[3]);
			
			value = stmt.executeUpdate();
			
			if (value == 0) {
				return -1;
			}
			else {
				return 0;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -2;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* anyadirProducto_PE */
	
	private static int anyadirProducto_VI (String parametros) {
		/** Anyadimos un nuevo videojuego a la BD.
			Dev:  0 si exito.
				 -1 si no se ha insertado ningun elemento.
				 -2 si error SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			int value;
			String argumentos[] = CajonSastre.CortarString(parametros);
			stmt = conn.prepareStatement("INSERT INTO ESHOP.VIDEOJUEGO " +
					   "VALUES (?, ?, ?)");
			stmt.setString(1,argumentos[0]);
			stmt.setString(2,argumentos[1]);
			stmt.setString(3,argumentos[2]);
			
			value = stmt.executeUpdate();
			
			if (value == 0) {
				return -1;
			}
			else {
				return 0;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -2;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}	
	} /* anyadirProducto_VI */
	
	private static int anyadirCliente (String usuario, String parametros) {
		/** Anyadimos un nuevo cliente a la BD.
			Dev:  0 si exito.
				 -1 si no se ha insertado nada.
				 -2 si error de permisos.
				 -3 si error SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			String argumentos[] = CajonSastre.CortarString(parametros);
			/** Comprobamos los permisos. */
			if ((usuario.compareTo("Administrador") == 0) ||
					((usuario.compareTo("Cliente") == 0) &&
							(argumentos[2].compareTo("F") == 0))) {
				int value;
				/** Establecemos la conexion con la BD.*/
				getConnection();
				stmt = conn.prepareStatement("INSERT INTO ESHOP.USUARIO " +
						   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1,argumentos[0]);
				stmt.setString(2,argumentos[1]);
				stmt.setString(3,argumentos[2]);
				stmt.setString(4,argumentos[3]);
				stmt.setString(5,argumentos[4]);
				stmt.setString(6,argumentos[5]);
				stmt.setString(7,argumentos[6]);
				stmt.setString(8,argumentos[7]);
				stmt.setString(9,argumentos[8]);
				stmt.setInt(10,Integer.parseInt(argumentos[9]));
				
				value = stmt.executeUpdate();
				
				if (value == 0) {
					return -1;
				}
				else {
					return 0;
				}
			} /* if [ usuario == "Administrador" ] */
			
			else { /** No somos administradores. */
				return -2;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* anyadirCliente */
	
	private static int anyadirPedido (String usuario, String parametros) {
		/** Anyadimos un nuevo pedido a la BD.
			Dev:  0 si exito.
				 -1 si no se ha insertado nada.
				 -2 si error de permisos.
				 -3 si error SQL.
		*/
		PreparedStatement stmt = null;
		int value;
		
		try {
			 /** Comprobamos permisos. */
			if (usuario.compareTo("Administrador") == 0) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt = conn.prepareStatement("INSERT INTO ESHOP.PEDIDO " +
						   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1,argumentos[0]);
				stmt.setString(2,argumentos[1]);
				stmt.setInt(3,Integer.parseInt(argumentos[2]));
				stmt.setInt(4,Integer.parseInt(argumentos[3]));
				stmt.setString(5,argumentos[4]);
				stmt.setString(6,argumentos[5]);
				stmt.setString(7,argumentos[6]);
				stmt.setString(8,argumentos[7]);
				stmt.setString(9,argumentos[8]);
				
				value = stmt.executeUpdate();
				
				if (value == 0) {
					return -1;
				}
				else {
					return 0;
				}
			}
			else {
				return -2;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* anyadirPedido */
	
	private static int anyadirProductoPedido (String usuario, String parametros) {
		/** Anyadimos un nuevo producto a un pedido de la BD.
			Dev:  0 si exito.
				 -1 si no se ha insertado nada.
				 -2 si error de permisos.
				 -3 si error SQL.
		*/
		PreparedStatement stmt = null;
		int value;
		
		try {
			if (usuario.compareTo("Administrador") == 0) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt = conn.prepareStatement("INSERT INTO ESHOP.PRODUCTOSPEDIDO " +
						   "VALUES (?, ?, ?)");
				stmt.setString(1,argumentos[0]);
				stmt.setString(2,argumentos[1]);
				stmt.setInt(3, Integer.parseInt(argumentos[2]));
				
				value = stmt.executeUpdate();
				
				if (value == 0) {
					return -1;
				}
				else {
					return 0;
				}
			}
			else {
				return -2;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* anyadirProductoPedido */

	private static int modificarProducto (String tipo, String usuario, String parametros) {
		/** Modificamos los atributos de un producto de la BD.
			Dev:  0 si exito.
				 -1 si no se ha modificado nada.
				 -2 si error de permisos.
				 -3 si error SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			if (usuario.compareTo("Administrador") == 0) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt = conn.prepareStatement("UPDATE ESHOP.PRODUCTO " +
				   		   					 "SET AGOTADO=?, EJEMPLARES=?, PUNTUACION=?, NUMVOTOS=?, " +
				   		   					 "PRECIOUD=?, GENERO=?, TITULO=?, FICHERO=?, FECHA=?, " +
				   		   					 "SINOPSIS=? WHERE (IDPRODUCTO=?)");
				stmt.setString(1, argumentos[1]);
				stmt.setInt(2,Integer.parseInt(argumentos[2]));
				stmt.setInt(3,Integer.parseInt(argumentos[3]));
				stmt.setInt(4,Integer.parseInt(argumentos[4]));
				stmt.setInt(5,Integer.parseInt(argumentos[5]));
				stmt.setString(6,argumentos[6]);
				stmt.setString(7,argumentos[7]);
				stmt.setString(8,argumentos[8]);
				stmt.setInt(9,Integer.parseInt(argumentos[9]));
				stmt.setString(10,argumentos[10]);
				stmt.setString(11,argumentos[0]);
				
				int valuePR = stmt.executeUpdate();
				int valuePE_VI;
				
				if (tipo.compareTo("Pelicula") == 0) { /** Modificamos los atributos caracteristicos
				                                           de la pelicula.*/
					stmt = conn.prepareStatement("UPDATE ESHOP.PELICULA " +
							   			  		 "SET SOPORTE=?, DIRECTOR=?, ACTORES=? "+
							   			  		 "WHERE (IDPRODUCTO=?)");
					stmt.setString(1,argumentos[11]);
					stmt.setString(2,argumentos[12]);
					stmt.setString(3,argumentos[13]);
					stmt.setString(4,argumentos[0]);
					
					valuePE_VI = stmt.executeUpdate();
				}
				else { /** Modificamos los atributos caracteristicos del videojuego.*/
					stmt = conn.prepareStatement("UPDATE ESHOP.VIDEOJUEGO " +
									   			"SET PLATAFORMA=?, COMPANYA=? " +
									   			"WHERE (IDPRODUCTO=?)");
					stmt.setString(1,argumentos[11]);
					stmt.setString(2,argumentos[12]);
					stmt.setString(3,argumentos[0]);
					
					valuePE_VI = stmt.executeUpdate();
				}
				if ((valuePR == 0) && (valuePE_VI == 0)) {
					return -1;
				}
				else {
					return 0;
				}
			} /* if [ usuario == "Administrador" ] */
			else { /** No somos administradores. */
				return -2;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* modificarProducto */
	
	private static int modificarCliente (String usuario, String parametros) {
		/** Modificamos los atributos de un cliente de la BD.
			Dev:  0 si exito.
				 -1 si no se ha modificado ningun elemento.
				 -2 si error de permisos.
				 -3 si error SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			String argumentos[] = CajonSastre.CortarString(parametros);
			if ((usuario.compareTo("Administrador") == 0) ||
					(argumentos[0].compareTo(argumentos[1]) == 0)) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				stmt = conn.prepareStatement("UPDATE ESHOP.USUARIO " +
		  				   					 "SET PASSWORD=?, ADMINISTRADOR=?, APELLIDOS=?, " +
		  				   					 "NOMBRE=?, NIF=?, DIRECCION=?, TELEFONO=?, " +
		  				   					 "EMAIL=?, BONIFICADO=? WHERE (IDUSUARIO=?)");
				stmt.setString(1,argumentos[2]);
				stmt.setString(2,argumentos[3]);
				stmt.setString(3,argumentos[4]);
				stmt.setString(4,argumentos[5]);
				stmt.setString(5,argumentos[6]);
				stmt.setString(6,argumentos[7]);
				stmt.setString(7,argumentos[8]);
				stmt.setString(8,argumentos[9]);
				stmt.setInt(9,Integer.parseInt(argumentos[10]));
				stmt.setString(10,argumentos[1]);
				
				int value = stmt.executeUpdate();
				
				if (value == 0) {
					return -1;
				}
				else {
					return 0;
				}
			} /* if [ usuario == "Administrador" ] */
			else { /** No somos administradores. */
				return -2;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* modificarCliente */
	
	private static int modificarPedido (String usuario, String parametros) {
		/** Modificamos los atributos de un pedido de la BD.
			Dev:  0 si exito.
				 -1 si no se ha modificado ningun elemento.
				 -2 si error de permisos.
				 -3 si error de SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			if (usuario.compareTo("Administrador") == 0) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt = conn.prepareStatement("UPDATE ESHOP.PEDIDO " +
				   		   					 "SET NUMCUENTA=?, FECHAPEDIDO=?, " +
				   		   					 "FCADUCIDAD=?, DIRECCION=?, NOMBRE_CL=?, " +
				   		   					 "ESTADO=?, CIF_NIF=? WHERE (IDPEDIDO=?)");
				stmt.setString(1,argumentos[1]);
				stmt.setInt(2,Integer.parseInt(argumentos[2]));
				stmt.setInt(3,Integer.parseInt(argumentos[3]));
				stmt.setString(4,argumentos[4]);
				stmt.setString(5,argumentos[5]);
				stmt.setString(6,argumentos[6]);
				stmt.setString(7,argumentos[7]);
				stmt.setString(8,argumentos[0]);

				int value = stmt.executeUpdate();

				if (value == 0) {
					return 1;
				}
				else {
					return 0;
				}
			}
			else { /** No somos administradores. */
				return -2;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* modificarPedido */

	private static int modificarProductosPedido (String usuario, String parametros) {
		/** Modificamos los atributos de un producto asociado a un pedido de la BD.
		 	Dev:  0 si exito.
		 		 -1 si no se ha modificado ningun elemento.
		 		 -2 si error de permisos.
		 		 -3 si error SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			if (usuario.compareTo("Administrador") == 0) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt = conn.prepareStatement("UPDATE ESHOP.PRODUCTOSPEDIDO " +
						   					 "SET CANTIDAD=? WHERE ((IDPEDIDO=?) " +
						   					 "AND (IDPRODUCTO=?))");
				stmt.setInt(1,Integer.parseInt(argumentos[2]));
				stmt.setString(2,argumentos[0]);
				stmt.setString(3,argumentos[1]);

				int value = stmt.executeUpdate();

				if (Integer.parseInt(argumentos[2]) == 0) {
					/** Estamos eliminado el producto del pedido.*/
					eliminarProductosPedido_PR(usuario, parametros);
				}
				if ((consultarIDPedido(argumentos[0])) == 0) {
					/** El pedido esta vacio, borramos las entradas de la tabla ProductosPedido.*/
					eliminarPedido(usuario, argumentos[0]);
				}

				if (value == 0) {
					return -1;
				}
				else {
					return 0;
				}
			}
			else { /** No somos administradores. */
				return -2;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* modificarProductosPedido */
	
	private static int eliminarProducto (String usuario, String parametros) {
		/** Eliminamos un producto de la BD.
			Dev:  0 si exito.
				 -1 si no se ha eliminado ningun elemento.
				 -2 si error de permisos.
				 -3 si error SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			if (usuario.compareTo("Administrador") == 0) { /** Somos administradores.*/
				/** Establecemos la conexion con la BD.*/
				getConnection();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt = conn.prepareStatement("DELETE FROM ESHOP.PELICULA AS PE " +
				   		   					 "WHERE (PE.IDPRODUCTO=?)");
				stmt.setString(1,argumentos[0]);

				int valuePE = stmt.executeUpdate();
				
				stmt = conn.prepareStatement("DELETE FROM ESHOP.VIDEOJUEGO AS VI " +
				    	   					 "WHERE (VI.IDPRODUCTO=?)");
				stmt.setString(1,argumentos[0]);

				int valueVI = stmt.executeUpdate();

				stmt = conn.prepareStatement("DELETE FROM ESHOP.PRODUCTO AS PR " +
						   					 "WHERE (PR.IDPRODUCTO=?)");
				stmt.setString(1,argumentos[0]);

				int valuePR = stmt.executeUpdate();
				
				if ((valuePE == 0) && (valueVI == 0) && (valuePR == 0)) {
					return -1;
				}
				else {
					return 0;
				}
			}
			else { /** No somos administradores. */
				return -2;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* eliminarProducto */
	
	private static int eliminarCliente (String usuario, String parametros) {
		/** Eliminamos un cliente de la BD.
			Dev:  0 si exito.
				 -1 si no se ha eliminado ningun elemento.
				 -2 si error de permisos.
				 -3 si error SQL.
		*/
		PreparedStatement stmt = null;
		ResultSet results = null;
		ArrayList<String> bar = null;
		
		try {
			if (usuario.compareTo("Administrador") == 0) { /** Somos administradores.*/
				bar = new ArrayList<String>();
				/** Establecemos la conexion con la BD.*/
				getConnection();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt = conn.prepareStatement("SELECT PE.IDPEDIDO FROM ESHOP.PEDIDO AS PE," +
											 "ESHOP.PRODUCTOSPEDIDO AS PP " +
											 "WHERE ((PE.IDUSUARIO=?) AND " +
											 "(PP.IDPEDIDO=PE.IDPEDIDO))");
				stmt.setString(1,argumentos[0]);
				results = stmt.executeQuery();
				
				int i = 1;
				
				while (results.next()) {
					String idPedido = results.getString(i);
					bar.add(idPedido);
				}
				int value = 0;
				/** Eliminamos las entradas correspondientes en la tabla ProductosPedido.*/
				for (i = 0; i < bar.size(); i++) {
					stmt = conn.prepareStatement("DELETE FROM ESHOP.PRODUCTOSPEDIDO AS PP " +
							   					 "WHERE (ESHOP.PP.IDPEDIDO=?)");
					stmt.setString(1,bar.get(i));
					value += stmt.executeUpdate();
				}
				
				/** Eliminamos todos los pedidos asociados a dicho cliente.*/
				for (i = 0; i < bar.size(); i++) {
					stmt = conn.prepareStatement("DELETE FROM ESHOP.PEDIDO AS PE " +
							   					 "WHERE (PE.IDPEDIDO=?)");
					stmt.setString(1,bar.get(i));
					value += stmt.executeUpdate();
				}
				
				stmt = conn.prepareStatement("DELETE FROM ESHOP.PEDIDO AS PE " +
						   					 "WHERE (PE.IDUSUARIO =?)");
				stmt.setString(1,argumentos[0]);
				value += stmt.executeUpdate();
				
				stmt = conn.prepareStatement("DELETE FROM ESHOP.USUARIO AS US " +
						   					 "WHERE (US.IDUSUARIO=?)");
				stmt.setString(1,argumentos[0]);
				value += stmt.executeUpdate();

				if (value == 0) {
					return -1;
				}
				else {
					return 0;
				}
			}
			else { /** No somos administradores. */
				return -2;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* eliminarCliente */
	
	private static int eliminarPedido (String usuario, String parametros) {
		/** Eliminamos un pedido de la BD.
			Dev:  0 si exito.
				 -1 si no se ha eliminado ningun elemento.
				 -2 si error de permisos.
				 -3 si error SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			if (usuario.compareTo("Administrador") == 0) {
				/** Establecemos la conexion con la BD.*/
				getConnection();
				String argumentos[] = CajonSastre.CortarString(parametros);
				stmt = conn.prepareStatement("DELETE FROM ESHOP.PEDIDO AS PE " +
						   					 "WHERE (PE.IDPEDIDO=?)");
				stmt.setString(1,argumentos[0]);
				/** Eliminamos los productos asociados a dicho pedido.*/
				if (eliminarProductosPedido_PE(parametros) == 0) {
					int value = stmt.executeUpdate();
					if (value == 0) {
						return -1;
					}
					else {
						return 0;
					}
				}
				else {
					return -3;
				}

			}
			else { /** No somos administradores. */
				return -2;
			}
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -3;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* eliminarPedido */

	private static int eliminarProductosPedido_PE (String parametros) {
		/** Eliminamos los productos asociados al producto que estamos eliminando de la BD.
			Dev:  0 si exito.
				 -1 si error SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			String argumentos[] = CajonSastre.CortarString(parametros);
			stmt = conn.prepareStatement("DELETE FROM ESHOP.PRODUCTOSPEDIDO AS PP " +
			   		   					 "WHERE (PP.IDPEDIDO=?)");
			stmt.setString(1,argumentos[0]);
			stmt.executeUpdate();
			
			return 0;
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -1;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* eliminarProductosPedido_PE */
	
	private static int eliminarProductosPedido_PR (String usuario, String parametros) {
		/** Eliminamos un producto determinado asociado al producto que estamos
		    eliminando de la BD.
		    Dev:  0 si exito.
		    	 -1 si error SQL.
		*/
		PreparedStatement stmt = null;
		
		try {
			/** Establecemos la conexion con la BD.*/
			getConnection();
			String argumentos[] = CajonSastre.CortarString(parametros);
			stmt = conn.prepareStatement("DELETE FROM ESHOP.PRODUCTOSPEDIDO AS PP " +
			   		   					 "WHERE ((PP.IDPEDIDO=?) AND (PP.IDPRODUCTO=?))");
			stmt.setString(1,argumentos[0]);
			stmt.setString(2,argumentos[1]);
			stmt.executeUpdate();
			
			return 0;
		}
		catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}

			return -1;
		}
		finally {
			/** Cerramos la conexion con la BD y liberamos recursos.*/
			closeSTMT_CON(stmt,conn);
		}
	} /* eliminarProductosPedido_PR */
	
	public static ArrayList<String> consultar (String objeto, String usuario, String parametros) {
		/** Realizamos una consulta sobre la BD.
			Dev: Listado con los elementos consultados si exito.
				 Listado con un solo elemento caracteristico si error.
		*/
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
			Dev:  0 si exito.
				 -1 si no se ha insertado nada.
				 -2 si error de permisos.
				 -3 si error SQL.
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
			Dev:  0 si exito.
				 -1 si no se ha modificado nada.
				 -2 si error de permisos.
				 -3 si error SQL.
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
			Dev:  0 si exito.
				 -1 si no se ha eliminado ningun elemento.
				 -2 si error de permisos.
				 -3 si error SQL.
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

