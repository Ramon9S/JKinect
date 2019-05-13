package pintarEnCanvas;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;


public class MiCanvasColor extends Canvas {

	private static final long serialVersionUID = 1L;
	Color c;

	public MiCanvasColor(Color c) {
		super();
		this.c = c;
	}

	public MiCanvasColor(GraphicsConfiguration config) {
		super(config);
	}

	@Override
	public void paint(Graphics g){

		BasicStroke grosor = new BasicStroke(15.0f);
		Graphics2D g2=null;

		if(g instanceof Graphics2D){
			g2 = (Graphics2D) g;
		}//end if

		g2.setColor(c);
		g2.setStroke(grosor);
		g2.drawOval(20, 10, (int) (this.getHeight()*0.7), (int) (this.getHeight()*0.7));

	}//paint()

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}


}//class MiCanvasColor
