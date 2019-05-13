package bbdd;

public class ElementoBD {

	String atributo;
	String valor;
	String tabla;
	
	
	public ElementoBD()
	{
		
	}//end constructor

	public ElementoBD(String atributo, String valor, String tabla) {
		this.atributo = atributo;
		this.valor = valor;
		this.tabla = tabla;
	}//end constructor

	public String getAtributo() {
		return atributo;
	}


	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	public String getTabla() {
		return tabla;
	}


	public void setTabla(String tabla) {
		this.tabla = tabla;
	}	
	
}//end class ElementoBD
