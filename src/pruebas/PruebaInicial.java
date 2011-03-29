package pruebas;

import java.util.ArrayList;
import SGBD.SGBD;

public final class PruebaInicial {
	public static void listado (ArrayList<String> bar) {
		int numT = bar.size();
		int j = 1;
		boolean nuevaLinea = true;
		if (bar.get(0).compareTo("Error SQL") == 0) {
			System.out.println("Error SQL");
			return;
		}
		for (int i=0; i < numT; i++) {
			String aux = bar.get(i);
			if (aux.compareTo("#$#") == 0) {
				j++;
				nuevaLinea = true;
				System.out.print("\n");
			}
			else {
				if (nuevaLinea) {
					System.out.printf("Fila " + j + ": ");
					nuevaLinea = false;
				}
				System.out.printf(aux + " \t");
				
			}
		}
	}
	
	public static void main (String[] args) {
		boolean resetBD = true;
		boolean insercionProductos = true;
		boolean insercionClientes = true;
		boolean insercionPedido = true;
		boolean insercionProductosPedido = true;
		boolean modificacionProductos = true;
		boolean modificacionClientes = true;
		boolean modificacionPedido = true;
		boolean modificacionProductosPedido = true;
		boolean eliminacionProductos = true;
		boolean eliminacionClientes = false;
		boolean eliminacionPedido = true;
		boolean eliminacionProductosPedido = true;
		boolean listadoProductos = true;
		boolean listadoClientes = true;
		boolean listadoPedidos = true;
		boolean listadoProductosPedido = true;
		try{ 
			ArrayList<String> bar = null;
			int value;
			if (resetBD) {
				SGBD.resetDatabase();
			}
			if (insercionProductos) {
				System.out.print("Iniciamos la insercion de productos...\n");
				String pelicula1 = new String("1#F#10#0#0#25#Genero1#Titulo1#/dir/primero#123456#Sinopsis1#DVD#Director1#Actor1, Actor2, Actor3");
				value = SGBD.anyadir("Pelicula","Administrador",pelicula1);
				if (value != 0) {
					System.out.print("No se inserto nada.\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String pelicula2 = new String("2#F#5#0#0#35#Genero2#Titulo2#/dir/segundo#654321#Sinopsis2#BR#Director2#Actor4, Actor5, Actor6");
				value = SGBD.anyadir("Pelicula","Administrador",pelicula2);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String videojuego1 = new String("3#F#15#0#0#20#Genero3#Titulo3#/dir/tercero#321654#Sinopsis3#PC#SEGA");
				value = SGBD.anyadir("Videojuego","Administrador",videojuego1);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String videojuego2 = new String("4#F#7#0#0#60#Genero4#Titulo4#/dir/cuarto#456123#Sinopsis4#PS3#KONAMI");
				value = SGBD.anyadir("Videojuego","Administrador",videojuego2);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
			}
			if (listadoProductos) {
				System.out.print("Consultamos todas las peliculas\n");
				bar = SGBD.consultar("Producto","Cliente","Pelicula#Todo");
				listado(bar);
				System.out.print("Consultamos todas las peliculas en DVD\n");
				bar = SGBD.consultar("Producto","Cliente","Pelicula#DVD");
				listado(bar);
				System.out.print("Consultamos todas las peliculas en Blu-Ray\n");
				bar = SGBD.consultar("Producto","Cliente","Pelicula#BR");
				listado(bar);
				System.out.print("Consultamos todos los videojuegos\n");
				bar = SGBD.consultar("Producto","Cliente","Videojuego#Todo");
				listado(bar);
				System.out.print("Consultamos todos los videojuegos de PC\n");
				bar = SGBD.consultar("Producto","Cliente","Videojuego#PC");
				listado(bar);
				System.out.print("Consultamos todos los videojuegos de PS3\n");
				bar = SGBD.consultar("Producto","Cliente","Videojuego#PS3");
				listado(bar);
			}
			if (insercionClientes) {
				System.out.print("Iniciamos la insercion de clientes...\n");
				String cliente1 = new String("1#1234#F#Apellido1#Nombre1#123456789#Direccion1#Telefono1#Email1#0");
				value = SGBD.anyadir("Cliente","Administrador",cliente1);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String cliente2 = new String("2#4321#F#Apellido2#Nombre2#987654321#Direccion2#Telefono2#Email2#0");
				value = SGBD.anyadir("Cliente","Administrador",cliente2);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
			}
			if (listadoClientes) {
				System.out.print("Consultamos todos los clientes\n");
				bar = SGBD.consultar("Cliente","Administrador","");
				listado(bar);
			}
			if (insercionPedido) {
				System.out.print("Iniciamos la insercion de pedidos...\n");
				String pedido1 = new String("1#1234567890ABCDEFGHIJ#123456#123456#Direccion1#Nombre1#Estado1#123456789#1");
				value = SGBD.anyadir("Pedido","Administrador",pedido1);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String pedido2 = new String("2#JIHGFEDCBA0987654321#654321#654321#Direccion2#Nombre2#Estado2#987654321#2");
				value = SGBD.anyadir("Pedido","Administrador",pedido2);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String pedido3 = new String("3#1234ABCD5678EFGH90IJ#123654#312654#Direccion3#Nombre3#Estado3#123459876#2");
				value = SGBD.anyadir("Pedido","Administrador",pedido3);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
			}
			if (listadoPedidos) {
				System.out.print("Consultamos todos los pedidos\n");
				bar = SGBD.consultar("Pedido","Administrador","Todo#Todo#Todo#Todo");
				listado(bar);
				System.out.print("Consultamos los pedidos por estado\n");
				bar = SGBD.consultar("Pedido","Administrador","Todo#Todo#Todo#Estado#Estado1");
				listado(bar);
				System.out.print("Consultamos los pedidos por cliente\n");
				bar = SGBD.consultar("Pedido","Administrador","Todo#Todo#Todo#Cliente#2");
				listado(bar);
				System.out.print("Consultamos los pedidos por fecha\n");
				System.out.print("Limite inferior\n");
				bar = SGBD.consultar("Pedido","Administrador","Todo#Todo#Todo#Fecha#120000##");
				listado(bar);
				System.out.print("Limite superior\n");
				bar = SGBD.consultar("Pedido","Administrador","Todo#Todo#Todo#Fecha##650000");
				listado(bar);
			}
			if (insercionProductosPedido) {
				System.out.print("Iniciamos la insercion de productos asociados a pedidos...\n");
				String productosPedido1 = new String("1#4#3");
				value = SGBD.anyadir("ProductosPedido","Administrador",productosPedido1);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Inclusion exitosa\n");
				}
				String productosPedido2 = new String("2#2#5");
				value = SGBD.anyadir("ProductosPedido","Administrador",productosPedido2);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Inclusion exitosa\n");
				}
				String productosPedido3 = new String("2#4#7");
				value = SGBD.anyadir("ProductosPedido","Administrador",productosPedido3);
				if (value != 0) {
					System.out.print("No se inserto nada\n");
				}
				else {
					System.out.print("Inclusion exitosa\n");
				}
			}
			if (listadoProductosPedido) {
				System.out.print("Consultamos todos los productos asociados a pedidos\n");
				bar = SGBD.consultar("ProductosPedido","Administrador","");
				listado(bar);
			}
			if (modificacionProductos) {
				System.out.print("Iniciamos la modificacion de productos...\n");
				String pelicula1 = new String("1#F#20#0#0#45#Genero1#Titulo1#/dir/primero#123456#Sinopsis1#BR#Director1#Actor1, Actor2, Actor5");
				value = SGBD.modificar("Pelicula","Administrador",pelicula1);
				if (value != 0) {
					System.out.print("No se modifico nada\n");
				}
				else {
					System.out.print("Modificacion exitosa\n");
				}
			}
			if (modificacionClientes) {
				System.out.print("Iniciamos la modificacion de clientes...\n");
				String cliente1 = new String("2#1#Password1#V#Apellido1#Nombre1#123456789#Direccion1#Telefono1#Email1#10");
				value = SGBD.modificar("Cliente","Administrador",cliente1);
				if (value != 0) {
					System.out.print("No se modifico nada\n");
				}
				else {
					System.out.print("Modificacion exitosa\n");
				}
			}
			if (modificacionPedido) {
				System.out.print("Iniciamos la modificacion de pedidos...\n");
				String pedido1 = new String("2#1234567890987654321A#123654#123456#Direccion1#Nombre1#Estado1#12345678V#1");
				value = SGBD.modificar("Pedido","Administrador",pedido1);
				if (value != 0) {
					System.out.print("No se modifico nada\n");
				}
				else {
					System.out.print("Modificacion exitosa\n");
				}
			}
			if (modificacionProductosPedido) {
				System.out.print("Iniciamos la modificacion de productos asociados a pedidos...\n");
				String productosPedido1 = new String("1#4#9");
				value = SGBD.modificar("ProductosPedido","Administrador",productosPedido1);
				if (value != 0) {
					System.out.print("No se modifico nada\n");
				}
				else {
					System.out.print("Modificacion exitosa\n");
				}
			}
			if (eliminacionProductos) {
				System.out.print("Iniciamos la eliminacion de productos...\n");
				value = SGBD.eliminar("Producto","Administrador","3");
				if (value != 0) {
					System.out.print("No se elimino nada\n");
				}
				else {
					System.out.print("Eliminacion exitosa\n");
				}
			}
			if (eliminacionClientes) {
				System.out.print("Iniciamos la eliminacion de clientes...\n");
				value = SGBD.eliminar("Cliente","Administrador","1");
				if (value != 0) {
					System.out.print("No se elimino nada\n");
				}
				else {
					System.out.print("Eliminacion exitosa\n");
				}
			}
			if (eliminacionPedido) {
				System.out.print("Iniciamos la eliminacion de pedidos...\n");
				value = SGBD.eliminar("Pedido","Administrador","3");
				if (value != 0) {
					System.out.print("No se elimino nada\n");
				}
				else {
					System.out.print("Eliminacion exitosa\n");
				}
			}
			if (eliminacionProductosPedido) {
				System.out.print("Iniciamos la eliminacion de productos asociados a pedidos...\n");
				value = SGBD.eliminar("ProductosPedido","Administrador","2#4");
				if (value != 0) {
					System.out.print("No se elimino nada\n");
				}
				else {
					System.out.print("Eliminacion exitosa\n");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
	} // main 
}
