package pintarEnCanvas;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;

public class MiCanvasPreview extends Canvas {

	private static final long serialVersionUID = 1L;
	private Image img;

	public MiCanvasPreview(Image img) {
		super();
		this.img = img;
	}
	
	public void setImagen(Image img){
		this.img = img;
	}

	public MiCanvasPreview(GraphicsConfiguration config) {
		super(config);
	}

	@Override
	public void paint(Graphics g){

		drawScaledImage(img, this, g);
	}//paint()


	public void drawScaledImage(Image image, MiCanvasPreview canvas, Graphics g) {

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
 

}//class MiCanvasPreview
