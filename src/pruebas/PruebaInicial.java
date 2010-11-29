package pruebas;

import java.util.ArrayList;
import SGBD.SGBD;

public final class PruebaInicial {
	public static void main (String[] args) {
	try{ 
			ArrayList<String> bar = null;
			int value;
			System.out.print("Iniciamos la insercion de productos...\n");
			String pelicula1 = new String("1234#F#10#0#0#25#Genero1#Titulo1#/dir/primero#123456#Sinopsis1#DVD#Director1#Actor1, Actor2, Actor3");
			value = SGBD.anyadir("Pelicula","Administrador",pelicula1);
			if (value == -1) {
				System.out.print("Fallo de insercion\n");
			}
			else {
				System.out.print("Insercion exitosa\n");
			}
			String pelicula2 = new String("4321#F#5#0#0#35#Genero2#Titulo2#/dir/segundo#654321#Sinopsis2#BR#Director2#Actor4, Actor5, Actor6");
			value = SGBD.anyadir("Pelicula","Administrador",pelicula2);
			if (value == -1) {
				System.out.print("Fallo de insercion\n");
			}
			else {
				System.out.print("Insercion exitosa\n");
			}
			String videojuego1 = new String("2143#F#15#0#0#20#Genero3#Titulo3#/dir/tercero#321654#Sinopsis3#PC#SEGA");
			value = SGBD.anyadir("Videojuego","Administrador",videojuego1);
			if (value == -1) {
				System.out.print("Fallo de insercion\n");
			}
			else {
				System.out.print("Insercion exitosa\n");
			}
			String videojuego2 = new String("3412#F#7#0#0#60#Genero4#Titulo4#/dir/cuarto#456123#Sinopsis4#PS3#KONAMI");
			value = SGBD.anyadir("Videojuego","Administrador",videojuego2);
			if (value == -1) {
				System.out.print("Fallo de insercion\n");
			}
			else {
				System.out.print("Insercion exitosa\n");
			}
			bar = SGBD.consultar("Producto","Cliente","Todo");
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
	catch (Exception e) {
		e.printStackTrace(System.err);
	}
  } // main 
}
