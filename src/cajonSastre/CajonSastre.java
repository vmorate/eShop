package cajonSastre;

public class CajonSastre {

	public static String[] CortarString(String Parametros)
	{	
		//String[longitud] VParam; /* VParam sera un vector de strings que contendra los distintos parametros introducidos por el usuario */
		String delims = "#";
		return Parametros.split(delims);
		/*for (int i = 0; i < VParam.length; i++)
		    System.out.println(VParam[i]);		*/		
	}
}