package manejoXML;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
//import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import jkinect.JKinect;
import pintarEnCanvas.Objetos;

public class ManejadorXML {

	String nombre;				// Nombre de la actividad
	String descripcion;			// Breve descripcion de la actividad
	Integer duracion;			// Duracion total de la actividad
	File fondo;					// Imagen de fonde de pantalla de la actividad
	Integer id_especialista;	// Identificador del especialista de la actividad
	public ArrayList<Objetos> obj;		// ArrayList de objetos que componen la actividad


	public ManejadorXML() {
		super();
	}// Constructor

	public ManejadorXML(String nombre, String descripcion, int duracion, File fondo, Integer id_especialista, ArrayList<Objetos> obj, JKinect j) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.duracion = duracion;
		this.fondo = fondo;
		this.id_especialista = id_especialista;
		this.obj = obj;
	}// Constructor

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public File getFondo() {
		return fondo;
	}

	public void setFondo(File fondo) {
		this.fondo = fondo;
	}

	public Integer getId_especialista() {
		return id_especialista;
	}

	public void setId_especialista(Integer id_especialista) {
		this.id_especialista = id_especialista;
	}

	public ArrayList<Objetos> getObj() {
		return obj;
	}

	public void setObj(ArrayList<Objetos> obj) {
		this.obj = obj;
	}

	public int generarXML(JKinect j){

		int exito = -1; // exito = 1 --> xml NO CREADO, exito = 0 --> xml CREADO

		// Si la partida tiene al menos 1 objeto podemos crear el fichero XML
		if(getObj().size() > 0)
		{

			// Comprobamos que la duracion de la partida sea como minimo la suma de los tiempos de cada objeto de la partida
			int sum = 0;
			for(int i = 0; i < getObj().size(); i++)
			{
				sum += ( getObj().get(i).getTiempo() );
			}//end for

			if(duracion >= sum)
			{

				try{
//					String nuevoDirectorio = getDirResultados().toString() + '/' + getNombre() + '/';
//					System.out.println(nuevoDirectorio);

					//Creamos un directorio para dicho fichero de configuracion
					//File dir = new File(nuevoDirectorio); // crea objeto
					//dir.mkdir();//crea directorio real

					//Abrimos stream --> creamos el fichero si no existe
					//FileWriter fw = new FileWriter(nuevoDirectorio+getNombre()+".xml");
					FileWriter fw = new FileWriter(j.globalXML+getNombre()+".xml");
					
					// Escribimos en el fichero el codigo correspondiente a nuestro XML
					fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					fw.write("\r\n");
					fw.write("<motriKinect>");
					fw.write("\r\n");
					fw.write("<nombre>"+getNombre()+"</nombre>");
					fw.write("\r\n");
					fw.write("<descripcion>"+getDescripcion()+"</descripcion>");
					fw.write("\r\n");
					fw.write("<duracion>"+getDuracion()+"</duracion>");
					fw.write("\r\n");
					fw.write("<fondo>"+getFondo()+"</fondo>");
					fw.write("\r\n");
					fw.write("<especialista>"+getId_especialista()+"</especialista>");
					fw.write("\r\n");
					fw.write("<objetos>");
					fw.write("\r\n");

					///// BUCLEEEEE PARA EL ARRAYLIST DE OBJETOS!!!
					for(int i = 0; i < getObj().size(); i++)
					{
						fw.write("<elemento>");
						fw.write("\r\n");
						fw.write("<imagen>images"+getObj().get(i).getImg().toString().split("/images")[1]+"</imagen>");
						fw.write("\r\n");
						fw.write("<tiempo>"+getObj().get(i).getTiempo()+"</tiempo>");
						fw.write("\r\n");
						fw.write("<orden>"+getObj().get(i).getOrden()+"</orden>");
						fw.write("\r\n");
						fw.write("<posicionX>"+(int)getObj().get(i).getX()+"</posicionX>");
						fw.write("\r\n");
						fw.write("<posicionY>"+(int)getObj().get(i).getY()+"</posicionY>");
						fw.write("\r\n");
						fw.write("<color>"+getObj().get(i).getC().getRed()+ "," + getObj().get(i).getC().getGreen() + "," + getObj().get(i).getC().getBlue()+ "</color>");
						fw.write("\r\n");
						fw.write("</elemento>");
						fw.write("\r\n");
					}//end for

					fw.write("</objetos>");
					fw.write("\r\n");
					fw.write("</motriKinect>");
					fw.write("\r\n");

					//Cierro el stream
					fw.close();


					// XML CREADO SIN PROBLEMAS
					exito = 0;


					JOptionPane optionPane = new JOptionPane("Archivo de configuración guardado con exito!");
					optionPane.setIcon(j.getIconoInfo());
					JDialog dialog = optionPane.createDialog("");
					dialog.setTitle("Configuración correcta");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);

				}catch(IOException e){
					System.out.println("Error E/S: "+e);
					exito = 1; // XML NO CREADO

					JOptionPane optionPane = new JOptionPane("Error de entrada/salida!!. No ha podido crearse el fichero XML.");
					optionPane.setIcon(j.getIconoInfo());
					JDialog dialog = optionPane.createDialog("");
					dialog.setTitle("Error de E/S");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}//end try-catch

			}else{
				exito = 1; // XML NO CREADO
				JOptionPane optionPane = new JOptionPane("Duracion demasiado corta!!. La duracion debe de ser como mínimo "
						+ "la suma de los tiempos de los objetos insertados en la partida.");
				optionPane.setIcon(j.getIconoInfo());
				JDialog dialog = optionPane.createDialog("");
				dialog.setTitle("Error de configuración");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
			}//end if duracion

		}else{
			exito = 1; // XML NO CREADO 
			JOptionPane optionPane = new JOptionPane("Partida sin elementos!!. La partida debe tener como mínimo 1 objeto "
					+ "insertado para generar el fichero XML.");
			optionPane.setIcon(j.getIconoInfo());
			JDialog dialog = optionPane.createDialog("");
			dialog.setTitle("Error de configuración");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		}//end if size

		return exito;
	}//generarXML()


	public void cargarXML(File fileXML, JKinect j){

		//ManejadorXML xml = null;
		obj = new ArrayList<Objetos>();
		//String directorio = j.globalXML;
		//String subDir = fileXML.getName().substring(0, fileXML.getName().length()-4)+"/";
		
		
		Point pEle = null;
		int pEleX = 0, pEleY = 0;
		int tiempoEle = -1;
		int ordenEle = 0; 
		File imgEle = null;
		Color cEle = null;
		
		
		// TERMINAR !!!

		//Abro el stream, el fichero debe existir
		BufferedReader br = null;
		try {
			//br = new BufferedReader(new FileReader(directorio+subDir+fileXML.getName()));
			br = new BufferedReader(new FileReader(fileXML));

		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}

		String line;
		String[] splittedLine = null;
		String[] splittedLine2 = null;

		try {
			line = br.readLine(); // SALTAMOS PRIMERA LINEA
			line = br.readLine(); // SALTAMOS SEGUNDA LINEA
			
			while ((line = br.readLine()) != null) {

				// Procesamos la linea.
				splittedLine = (line.trim()).split(">");
				
				String aux = null;
				if( splittedLine.length > 1 ){ aux = splittedLine[1]; }
				
				if( aux != null && aux.length() > 1 ){ splittedLine2 = (aux.trim()).split("<"); }


				if( splittedLine[0].contains("nombre") ){
					setNombre(splittedLine2[0]);
					
				}else if( splittedLine[0].contains("descripcion") ){
					setDescripcion(splittedLine2[0]);
					
				}else if( splittedLine[0].contains("duracion") ){
					setDuracion(Integer.parseInt( splittedLine2[0] ));
					
				}else if( splittedLine[0].contains("fondo") ){
					setFondo( new File( splittedLine2[0]) );
					
				}else if( splittedLine[0].contains("especialista") ){
					setId_especialista(Integer.parseInt(splittedLine2[0]) );
					
				}else if( splittedLine[0].contains("objetos") ){
					/// NO HACEMOS NADA
					
				}else if( splittedLine[0].contains("elemento") ){
					/// NO HACEMOS NADA
					
				}else if( splittedLine[0].contains("imagen") ){
					imgEle = new File( splittedLine2[0] );
					
				}else if( splittedLine[0].contains("tiempo") ){
					tiempoEle = Integer.parseInt( splittedLine2[0] );
					
				}else if( splittedLine[0].contains("orden") ){
					ordenEle = Integer.parseInt( splittedLine2[0] );
					
				}else if( splittedLine[0].contains("posicionX") ){
					pEleX = Integer.parseInt( splittedLine2[0] );
					
				}else if( splittedLine[0].contains("posicionY") ){
					pEleY = Integer.parseInt( splittedLine2[0] );
					
					pEle = new Point( pEleX, pEleY); 
				}else if( splittedLine[0].contains("color") ){
					String rgb[] = splittedLine2[0].split(",");
					cEle = new Color( Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]) );

					
					/// CUANDO ENTRAMOS AQUI TENEMOS TODOS LOS VALORES DE UN NUEVO OBJETO EN NUESTRAS VARIABLES AUXILIARES
					Objetos o = new Objetos(imgEle.toString().trim().split("/")[3], pEle, tiempoEle, ordenEle, imgEle, cEle);
					obj.add(o);
					
					
				}// end procesamiento
			
				
			}//end while
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}//end while readLine()


		//Cerramos el stream
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}//cargarXML()



}// Class ManejadorXML
