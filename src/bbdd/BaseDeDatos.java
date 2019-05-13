package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDeDatos {

	String driver;
	String servidor;
	String puerto;
	String user;
	String password;
	String baseDatos;

	Statement stmt = null;
	ResultSet rset = null;
	ResultSetMetaData md = null;
	PreparedStatement pstm = null;

	Connection c;


	public BaseDeDatos(String driver, String servidor, String puerto, String user, String password, String baseDatos) {
		try {
			this.c = DriverManager.getConnection(driver+"://"+servidor+":"+puerto+"/"+baseDatos+"?useSSL=false", user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}//end constructor

	public void finBaseDeDatos()
	{
		try {
			rset.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end finBaseDeDatos()

	public ResultSet select(String sentencia)
	{
		try {
			stmt = c.createStatement();
			rset = stmt.executeQuery(sentencia);
			md = rset.getMetaData();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rset;
	}//end select

	public boolean update(String sentencia) throws SQLException
	{
		pstm = c.prepareStatement(sentencia);
		return pstm.execute();
	}//end

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBaseDatos() {
		return baseDatos;
	}

	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}

	public Connection getC() {
		return c;
	}

	public void setC(Connection c) {
		this.c = c;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public ResultSet getRset() {
		return rset;
	}

	public void setRset(ResultSet rset) {
		this.rset = rset;
	}

	public ResultSetMetaData getMd() {
		return md;
	}

	public void setMd(ResultSetMetaData md) {
		this.md = md;
	}

}// end class BaseDeDatos
