package pintarEnCanvas;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
//import java.awt.Stroke;
import java.util.ArrayList;

public class MiCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private ArrayList<Objetos> obj = null;
	private Image img;

	public MiCanvas(ArrayList<Objetos> obj, Image img) {
		super();
		this.obj = obj;
		this.img = img;
	}

	public MiCanvas(GraphicsConfiguration config) {
		super(config);
	}
	
	public void setImagen(Image img){
		this.img = img;
	}
	
	public Image getImagen(){
		return img;
	}
	
	public ArrayList<Objetos> getObj() {
		return obj;
	}

	public void setObj(ArrayList<Objetos> obj) {
		this.obj = obj;
	}
	
	
	@Override
	public void paint(Graphics g){

		drawScaledImage(img, this, g);
		
		// CREAR OBJETO PARA REMACHACAR!!!!!
		
		
		BasicStroke grosor = new BasicStroke(5.0f);
		BasicStroke grosorSelected = new BasicStroke(10.0f);
		
		Graphics2D g2=null;
		if(g instanceof Graphics2D){
			g2 = (Graphics2D) g;
		}//end if
		
		for(Objetos o : obj)
		{
			g2.setColor(o.getC());
			
			if(o.getSelected()){
				g2.setStroke(grosorSelected);
			}else{
				g2.setStroke(grosor);
			}
			
			g2.drawOval((int) o.getX(), (int) o.getY(), 20, 20);
		}//for
		
	}//paint()

	
	public void drawScaledImage(Image image, MiCanvas canvas, Graphics g) {

		if(image != null){
			int imgWidth = image.getWidth(canvas.getParent());
			int imgHeight = image.getHeight(canvas.getParent());

			double imgAspect = (double) imgHeight / imgWidth;

			int canvasWidth = canvas.getWidth();
			int canvasHeight = canvas.getHeight();

			double canvasAspect = (double) canvasHeight / canvasWidth;

			int x1 = 0; // top left X position
			int y1 = 0; // top left Y position
			int x2 = 0; // bottom right X position
			int y2 = 0; // bottom right Y position

			if (imgWidth < canvasWidth && imgHeight < canvasHeight) {
				// the image is smaller than the canvas
				x1 = (canvasWidth - imgWidth)  / 2;
				y1 = (canvasHeight - imgHeight) / 2;
				x2 = imgWidth + x1;
				y2 = imgHeight + y1;

			} else {
				if (canvasAspect > imgAspect) {
					y1 = canvasHeight;
					// keep image aspect ratio
					canvasHeight = (int) (canvasWidth * imgAspect);
					y1 = (y1 - canvasHeight) / 2;
				} else {
					x1 = canvasWidth;
					// keep image aspect ratio
					canvasWidth = (int) (canvasHeight / imgAspect);
					x1 = (x1 - canvasWidth) / 2;
				}
				x2 = canvasWidth + x1;
				y2 = canvasHeight + y1;
			}

			g.drawImage(image, x1, y1, x2, y2, 0, 0, imgWidth, imgHeight, canvas.getParent());

		}//if image != null
		
	}//drawScaledImage() 
 

}//class MiCanvas
