package pruebas;

import java.util.ArrayList;
import SGBD.SGBD;

public final class PruebaInicial {
	public static void main (String[] args) {
	try{ 
			/* Get a connection. */ 
			SGBD.iniSGBD();
			ArrayList<String> bar = null; 
			bar = SGBD.Consultar("Objeto","Usuario","Prueba1#Prueba1#TODO#TODO");
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
