package graficas;

import java.util.ArrayList;
import org.jfree.chart.JFreeChart;
import resultados.Resultado;

public abstract class Grafica {
	
	JFreeChart grafica;
	ArrayList<Resultado> resultados;


	public enum TipoGrafica{ Circular, Barras, Puntos } // ENUM de los tipos de gráficas
	TipoGrafica tipo;
	
	
	public TipoGrafica getTipo() {
		return tipo;
	}

	public void setTipo(TipoGrafica tipo) {
		this.tipo = tipo;
	}
	
	public Grafica() {
		super();
	}//end constructor

	public Grafica(JFreeChart grafica, ArrayList<Resultado> resultados) {
		super();
		this.grafica = grafica;
		this.resultados = resultados;
	}//end constructor

	public JFreeChart getGrafica() {
		return grafica;
	}

	public void setGrafica(JFreeChart grafica) {
		this.grafica = grafica;
	}

	public ArrayList<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(ArrayList<Resultado> resultados) {
		this.resultados = resultados;
	}
	
	
}//end class Grafica
