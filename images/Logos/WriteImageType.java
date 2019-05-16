import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;

public class WriteImageType {
  
  private static final Font font = new Font("Sansserif", Font.BOLD, 36);

  static public void main(String args[]) throws Exception {
    try {
  
      int width = 200, height = 200;
  
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = bi.createGraphics();

      // MARCA DE AGUA
      g2.drawImage(bi, null, 0, 0);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
      g2.setColor(Color.MAGENTA);
      g2.setComposite(AlphaComposite.SrcOver.derive(0.3f));
      g2.fillRoundRect(50, 40, 100, 80, 32, 32);
      g2.setComposite(AlphaComposite.SrcOver);
      g2.setColor(Color.RED);
      g2.setFont(font);
      g2.drawString("JKinect", 65, 90);

      // GRABAMOS LAS IMAGENES EN DISTINTOS FORMATOS
      ImageIO.write(bi, "PNG", new File("JKinectRed.png"));
      ImageIO.write(bi, "JPEG", new File("JKinectRed.jpg"));
      ImageIO.write(bi, "gif", new File("JKinectRed.gif"));
      ImageIO.write(bi, "BMP", new File("JKinectRed.bmp"));
      
      System.exit(0);

    } catch (IOException ie) {
      ie.printStackTrace();
    }


  }
}//end class