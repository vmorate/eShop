package pruebas;

import java.util.ArrayList;
import SGBD.SGBD;

public final class PruebaInicial {
	public static void listado (ArrayList<String> bar) {
		int numT = bar.size();
		int j = 1;
		boolean nuevaLinea = true;
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
		boolean insercionProductos = false;
		boolean insercionClientes = false;
		boolean insercionPedido = false;
		boolean insercionProductosPedido = false;
		boolean eliminacionProductos = true;
		boolean eliminacionClientes = false;
		boolean eliminacionPedido = false;
		boolean eliminacionProductosPedido = false;
		boolean listadoProductos = false;
		boolean listadoClientes = false;
		boolean listadoPedidos = false;
		boolean listadoProductosPedido = false;
		try{ 
			ArrayList<String> bar = null;
			int value;
			if (insercionProductos) {
				System.out.print("Iniciamos la insercion de productos...\n");
				String pelicula1 = new String("1#F#10#0#0#25#Genero1#Titulo1#/dir/primero#123456#Sinopsis1#DVD#Director1#Actor1, Actor2, Actor3");
				value = SGBD.anyadir("Pelicula","Administrador",pelicula1);
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String pelicula2 = new String("2#F#5#0#0#35#Genero2#Titulo2#/dir/segundo#654321#Sinopsis2#BR#Director2#Actor4, Actor5, Actor6");
				value = SGBD.anyadir("Pelicula","Administrador",pelicula2);
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String videojuego1 = new String("3#F#15#0#0#20#Genero3#Titulo3#/dir/tercero#321654#Sinopsis3#PC#SEGA");
				value = SGBD.anyadir("Videojuego","Administrador",videojuego1);
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String videojuego2 = new String("4#F#7#0#0#60#Genero4#Titulo4#/dir/cuarto#456123#Sinopsis4#PS3#KONAMI");
				value = SGBD.anyadir("Videojuego","Administrador",videojuego2);
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
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
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String cliente2 = new String("2#4321#F#Apellido2#Nombre2#987654321#Direccion2#Telefono2#Email2#0");
				value = SGBD.anyadir("Cliente","Administrador",cliente2);
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
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
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String pedido2 = new String("2#JIHGFEDCBA0987654321#654321#654321#Direccion2#Nombre2#Estado2#987654321#2");
				value = SGBD.anyadir("Pedido","Administrador",pedido2);
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
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
				String productosPedido1 = new String("1#4321#3");
				value = SGBD.anyadir("ProductosPedido","Administrador",productosPedido1);
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
				String productosPedido2 = new String("2#1234#5");
				value = SGBD.anyadir("ProductosPedido","Administrador",productosPedido2);
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
			}
			if (listadoProductosPedido) {
				System.out.print("Consultamos todos los productos asociados a pedidos\n");
				bar = SGBD.consultar("ProductosPedido","Administrador","");
				listado(bar);
			}
			if (eliminacionProductos) {
				System.out.print("Iniciamos la eliminacion de productos...\n");
				value = SGBD.eliminar("Producto","Administrador","1234");
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
			}
			if (eliminacionClientes) {
				System.out.print("Iniciamos la eliminacion de clientes...\n");
				value = SGBD.eliminar("Cliente","Administrador","1");
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
			}
			if (eliminacionPedido) {
				System.out.print("Iniciamos la eliminacion de pedidos...\n");
				value = SGBD.eliminar("Pedido","Administrador","1");
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
			}
			if (eliminacionProductosPedido) {
				System.out.print("Iniciamos la eliminacion de productos asociados a pedidos...\n");
				value = SGBD.eliminar("ProductosPedido","Administrador","1234");
				if (value == -1) {
					System.out.print("Fallo de insercion\n");
				}
				else {
					System.out.print("Insercion exitosa\n");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
	} // main 
}
