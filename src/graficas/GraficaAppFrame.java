package graficas;

import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.jfree.chart.ChartPanel;
import org.jfree.ui.ApplicationFrame;

import jkinect.JKinect;


public class GraficaAppFrame extends ApplicationFrame {

	private static final long serialVersionUID = 1L;
	Grafica g;
	//JDesktopPane d;
	public enum TipoGrafica{ Circular, Barras, Puntos } // ENUM de los tipos de gráficas

	
	public GraficaAppFrame(String title) {
		super(title);
		
	}//end constructor
	
	public GraficaAppFrame(String title, Grafica g, JKinect j) {
		super(title);
		
		this.g = g;
		//this.d = desktop;
		
		
		initialize(j);

	}//end constructor
	
	
	public Grafica getG() {
		return g;
	}

	public void setG(Grafica g) {
		this.g = g;
	}

//	public JDesktopPane getD() {
//		return d;
//	}
//
//	public void setD(JDesktopPane d) {
//		this.d = d;
//	}

	public void initialize(JKinect j)
	{
        setPreferredSize(new Dimension(600, 400));
        final JInternalFrame frame1 = new JInternalFrame(getTitle(), true, true, true, true);
        frame1.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				dispose();
				j.resAbierto2 = false;
			}
		});
        
        frame1.setContentPane(new ChartPanel( g.getGrafica()));
        j.desktopPane.add(frame1);
        frame1.pack();
        frame1.setVisible(true);
		
	}//end initialize()
	
	
	
	public static void main(String[] args) {


	}//end main()

	
}//end class GraficaAppFrame
