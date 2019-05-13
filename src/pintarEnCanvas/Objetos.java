package pintarEnCanvas;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.Comparator;

public class Objetos {
	String nombre; 		// Nombre del objeto que se va a insertar en la partida
	Point p;			// Posicion x, y de la pantalla donde se va a insertar el objeto en la partida
	int tiempo;			// Tiempo que aparecerá en la pantalla el objeto que se va insertar
	int orden;			// Orden de aparición en la pantalla del objeto durante la partida 
	File img;			// Imagen correspondiente al objeto que se va a insertar
	Color c;			// Color del circulo que se va pintarEnCanvas asociado al objeto a introducir en la partida
	Boolean selected;	// Valor true --> el objeto está seleccionando en la tabla de la ventana --__--__ Valor false --> viceversa


	public Objetos(String nombre, Point p, int tiempo, int orden, File img, Color c) {
		super();
		this.nombre = nombre;
		this.p = p;
		this.tiempo = tiempo;
		this.orden = orden;
		this.img = img;
		this.c = c;
		this.selected = false;
	}// Constructor

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}

	public int getX(){
		return (int) getP().getX();
	}

	public int getY(){
		return (int) getP().getY();
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	/*Comparator for sorting the arraylist by field Orden*/
	public static Comparator<Objetos> ObjetosOrdenComparator = new Comparator<Objetos>() {

		@Override
		public int compare(Objetos s1, Objetos s2) {
			int orden1 = s1.getOrden();
			int orden2 = s2.getOrden();

			/*For ascending order*/
			return orden1-orden2;

			/*For ascending order*/
			//return orden2-orden1;
		}};


}//class Objetos
