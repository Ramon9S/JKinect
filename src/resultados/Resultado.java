package resultados;

import java.time.LocalDate;
import java.util.ArrayList;

import indicadores.Indicador;

public class Resultado {

	ArrayList<Indicador> indicadores;
	ArrayList<LocalDate> fecha;
	Integer id_paciente;
	Integer id_especialista;
	
	public Resultado()
	{
		this.indicadores = new ArrayList<Indicador>();
		this.fecha = new ArrayList<LocalDate>();
		
		
	}//end constructor
	
	public Resultado(ArrayList<Indicador> indicadores, ArrayList<LocalDate> fecha, Integer id_especialista, Integer id_paciente)
	{
		this.indicadores = indicadores;
		this.fecha = fecha;
		this.id_paciente = id_paciente;
		this.id_especialista = id_especialista;
	}//end constructor

	public Integer getId_paciente() {
		return id_paciente;
	}

	public void setId_paciente(Integer id_paciente) {
		this.id_paciente = id_paciente;
	}

	public Integer getId_especialista() {
		return id_especialista;
	}

	public void setId_especialista(Integer id_especialista) {
		this.id_especialista = id_especialista;
	}

	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public ArrayList<LocalDate> getFecha() {
		return fecha;
	}

	public void setFecha(ArrayList<LocalDate> fecha) {
		this.fecha = fecha;
	}
	
}//end class Resultado
