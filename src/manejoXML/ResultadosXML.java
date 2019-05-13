package manejoXML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jkinect.JKinect;

public class ResultadosXML {
	String nombre;					// Nombre de la sesion
	Integer id_paciente;			// Identificador del paciente de la sesion
	Integer id_especialista;		// Identificador del especialista de la sesion
	LocalDate fecha;				// Fecha de la sesion
	LocalTime hora;					// Hora de la sesion
	Integer duracion;				// Duracion total de la sesion
	Integer aciertos;				// Aciertos totales de la sesion
	Integer fallos;					// Fallos totales de la sesion
	Integer puntuacion;				// Puntuacion total de la sesion

	public ResultadosXML() {
		super();
	}// Constructor



	public ResultadosXML(String nombre, Integer id_paciente, Integer id_especialista, LocalDate fecha, LocalTime hora,
			Integer duracion, Integer aciertos, Integer fallos, Integer puntuacion) {
		super();
		this.nombre = nombre;
		this.id_paciente = id_paciente;
		this.id_especialista = id_especialista;
		this.fecha = fecha;
		this.hora = hora;
		this.duracion = duracion;
		this.aciertos = aciertos;
		this.fallos = fallos;
		this.puntuacion = puntuacion;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



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



	public LocalDate getFecha() {
		return fecha;
	}



	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}



	public LocalTime getHora() {
		return hora;
	}



	public void setHora(LocalTime hora) {
		this.hora = hora;
	}



	public Integer getDuracion() {
		return duracion;
	}



	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}


/**
 * 
 * @return
 */
	public Integer getAciertos() {
		return aciertos;
	}



	public void setAciertos(Integer aciertos) {
		this.aciertos = aciertos;
	}



	public Integer getFallos() {
		return fallos;
	}



	public void setFallos(Integer fallos) {
		this.fallos = fallos;
	}



	public Integer getPuntuacion() {
		return puntuacion;
	}



	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}


/**
 * 
 * @param fileXML
 * @param j
 */
	public void cargarXML(File fileXML, JKinect j){

		String directorio = j.globalResul;
		String subDir = fileXML.toString().split("-")[3];
		subDir = subDir.split(".xml")[0]+"/xml/";

		//Abro el stream, el fichero debe existir
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(directorio+subDir+fileXML));

		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}

		String line;
		String[] splittedLine = null;
		String[] splittedLine2 = null;

		try {
			line = br.readLine(); // SALTAMOS PRIMERA LINEA
			//line = br.readLine(); // SALTAMOS SEGUNDA LINEA

			while ((line = br.readLine()) != null) {

				// Procesamos la linea.
				splittedLine = (line.trim()).split(">");

				String aux = null;
				if( splittedLine.length > 1 ){ aux = splittedLine[1]; }

				if( aux != null && aux.length() > 1 ){ splittedLine2 = (aux.trim()).split("<"); }


				if( splittedLine[0].contains("nombre_juego") ){
					setNombre(splittedLine2[0]);

				}else if( splittedLine[0].contains("id_paciente") ){
					setId_paciente(Integer.parseInt(splittedLine2[0]));

				}else if( splittedLine[0].contains("id_especialista") ){
					setId_especialista(Integer.parseInt(splittedLine2[0]));

				}else if( splittedLine[0].contains("fecha") ){
					// PROCESAMIENTO FECHAS
					
					//Converting String in format 'dd-MMM-yyyy' to LocalDate
					//					String dateStr_1="28-Sep-2016";
					//					DateTimeFormatter formatter_1=DateTimeFormatter.ofPattern("dd-MMM-yyyy");
					//					LocalDate localDate_1= LocalDate.parse(dateStr_1,formatter_1);
					//					System.out.println("Input String with value: "+dateStr_1);
					//					System.out.println("Converted Date in default ISO format: "+localDate_1+"\n");
					
					//					//Converting String in format 'EEEE, MMM d yyyy' to LocalDate
					//					String dateStr_2="Wednesday, Sep 28 2016";
					//					DateTimeFormatter formatter_2=DateTimeFormatter.ofPattern("EEEE, MMM d yyyy");
					//					LocalDate localDate_2= LocalDate.parse(dateStr_2,formatter_2);
					//					System.out.println("Input String with value: "+dateStr_2);
					//					System.out.println("Converted Date in default ISO format: "+localDate_2+"\n");

					//Converting String in format 'dd/MM/YY' to LocalDate
					String dateStr_3 = splittedLine2[0];
					DateTimeFormatter formatter_3=DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate localDate_3= LocalDate.parse(dateStr_3,formatter_3);
					System.out.println("Input String with value: "+dateStr_3);
					System.out.println("Converted Date in default ISO format: "+localDate_3);


					setFecha( localDate_3 );

				}else if( splittedLine[0].contains("hora") ){

					// PROCESAMIENTO LINEA HORA
					//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm");

					setHora(LocalTime.parse( splittedLine2[0] ));

				}else if( splittedLine[0].contains("duracion") ){
					setDuracion(Integer.parseInt( splittedLine2[0] ));

				}else if( splittedLine[0].contains("aciertos") ){
					setAciertos(Integer.parseInt( splittedLine2[0] ));

				}else if( splittedLine[0].contains("fallos") ){
					setFallos(Integer.parseInt( splittedLine2[0] ));

				}else if( splittedLine[0].contains("puntuacion") ){
					setPuntuacion(Integer.parseInt( splittedLine2[0] ));

				}else if( splittedLine[0].contains("resultado_Kinect") ){
					// EN LA ULTIMA LINEA DEL XML NO HACEMOS NADA
					
				}//end if-else-if					

			}//end while procesamiento XML


		}catch (IOException e) {
			e.printStackTrace();
		}//end try readline()

		// CERRAMOS el stream
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}//cargarXML()

}// Class ResultadosXML
