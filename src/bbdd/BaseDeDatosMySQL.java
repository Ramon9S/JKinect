package bbdd;

public class BaseDeDatosMySQL extends BaseDeDatos {

//	String driverSQL 	= "jdbc:mysql";
//	String servidorSQL	= "localhost";
//	String puertoSQL	= "3306";
//	String userSQL		= "myuser";
//	String passwordSQL	= "ramon_sm";
//	String baseDatosSQL	= "terapiaKinectInterfaceDB";
	
	
	public BaseDeDatosMySQL()
	{
		//super(driverSQL, servidorSQL, puertoSQL, userSQL, passwordSQL, baseDatosSQL);
		super("jdbc:mysql", "localhost", "3306", "myuser", "ramon_sm", "terapiaKinectInterfaceDB");
		
	}//end constructor

}//end class BaseDeDatosMySQL
