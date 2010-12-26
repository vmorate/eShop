package Usuario;

import java.util.ArrayList;

import SGBD.SGBD;



public final class Usuario {
	private String idUsuario;
	private String password;
	private Boolean administrador;
	private String apellidos;
	private String nombre;
	private String NIF;
	private String dirPostal;
	private String telefono;
	private String dirElectronica;
	private int bonificacion;
	
	public Usuario () {
		
	}
	
	public Usuario(String idUsuario, String password, Boolean administrador,
				   String apellidos, String nombre, String NIF, String dirPostal,
				   String telefono, String dirElectronica, int bonificacion) {
		
		this.idUsuario = idUsuario;
		this.password = password;
		this.administrador = administrador;
		this.apellidos = apellidos;
		this.nombre = nombre;
		this.NIF = NIF;
		this.dirPostal = dirPostal;
		this.telefono = telefono;
		this.dirElectronica = dirElectronica;
		this.bonificacion = bonificacion;
	}
	
	public String getIdUsuario() {
		return idUsuario;
	}
	
	private void setIdUsuario (String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getPassword () {
		return password;
	}
	
	public void setPassword (String password) {
		this.password = password;
	}

	public Boolean getAdministrador () {
		return administrador;
	}

	public void setAdministrador (Boolean administrador) {
		this.administrador = administrador;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos (String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getNombre () {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNIF () {
		return NIF;
	}
	
	public void setNIF (String NIF) {
		this.NIF = NIF;
	}
	
	public String getDirPostal () {
		return dirPostal;
	}
	
	public void setDirPostal (String dirPostal) {
		this.dirPostal = dirPostal;
	}
	
	public String getTelefono () {
		return telefono;
	}
	
	public void setTelefono (String telefono) {
		this.telefono = telefono;
	}
	
	public String getDirElectronica () {
		return dirElectronica;
	}
	
	public void setDirElectronica (String dirElectronica) {
		this.dirElectronica = dirElectronica;
	}
	
	public int getBonificacion () {
		return bonificacion;
	}
	
	public void setBonificacion (int bonificacion) {
		this.bonificacion = bonificacion;
	}
	
	//Métodos propios de la clase Usuario
	
	public int ModificarUsuario(){
		
		//String objeto, String usuario, String parametros
		String s="#";
		String usuario="Cliente";
		String booleano="F";
		if (this.administrador) {
			usuario="Administrador"; 
			booleano="T";
		}		
		// String cliente1 = new String("1#1234#F#Apellido1#Nombre1#123456789#Direccion1#Telefono1#Email1#0");
		String parametros = idUsuario+s+password+s+booleano+s+apellidos+s+nombre+s+NIF+s+dirPostal+s+telefono+s+dirElectronica+s+bonificacion;
		String objeto = "Cliente";
		return (SGBD.modificar(objeto,usuario,parametros));
		
	}
	public int AnyadirUsuario(){		
		//String objeto, String usuario, String parametros
		String s="#";
		String usuario="Cliente";
		String booleano="F";
		if (this.administrador) {
			usuario="Administrador"; 
			booleano="T";
		}		
		// String cliente1 = new String("1#1234#F#Apellido1#Nombre1#123456789#Direccion1#Telefono1#Email1#0");
		String parametros = idUsuario+s+password+s+booleano+s+apellidos+s+nombre+s+NIF+s+dirPostal+s+telefono+s+dirElectronica+s+bonificacion;
		String objeto = "Cliente";
		return (SGBD.anyadir(objeto,usuario,parametros));	
		
	}
	public int EliminarUsuario(){
		//String objeto, String usuario, String parametros
		String s="#";
		String usuario="Cliente";
		String booleano="F";
		if (this.administrador) {
			usuario="Administrador"; 
			booleano="T";
		}		
		// String cliente1 = new String("1#1234#F#Apellido1#Nombre1#123456789#Direccion1#Telefono1#Email1#0");
		String parametros = idUsuario+s+password+s+booleano+s+apellidos+s+nombre+s+NIF+s+dirPostal+s+telefono+s+dirElectronica+s+bonificacion;
		String objeto = "Cliente";
		return (SGBD.eliminar(objeto,usuario,parametros));	
		
	}
	public ArrayList<String> ConsultarUsuario(){
		//String objeto, String usuario, String parametros
		String s="#";
		String usuario="Cliente";
		String booleano="F";
		if (this.administrador) {
			usuario="Administrador"; 
			booleano="T";
		}		
		// String cliente1 = new String("1#1234#F#Apellido1#Nombre1#123456789#Direccion1#Telefono1#Email1#0");
		String parametros = idUsuario+s+password+s+booleano+s+apellidos+s+nombre+s+NIF+s+dirPostal+s+telefono+s+dirElectronica+s+bonificacion;
		String objeto = "Cliente";
		return (SGBD.consultar(objeto,usuario,parametros));	
	}
	public void BonificarCliente(boolean admin,int Porcentaje){}
	
}
