package indicadores;

import java.awt.Color;

public abstract class Indicador {
	
	String nombre;
	float valor;
	Color color;
	
	public Indicador()
	{
		
	}//end constructor
	
	public Indicador(String nombre, float valor, Color color){
		this.nombre = nombre;
		this.valor = valor;
		this.color = color;
	}//end constructor
	
	public Indicador(String nombre, float valor){
		this.nombre = nombre;
		this.valor = valor;
	}//end constructor
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	
}//end class Indicador
