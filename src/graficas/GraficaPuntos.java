package graficas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;
import resultados.Resultado;

public class GraficaPuntos extends Grafica {

	static int total = -1;

	public GraficaPuntos( String title , ArrayList<Resultado> res) {
		super( );

		setTipo(TipoGrafica.Puntos);
		setResultados(res);

		setGrafica(createChart( res ));
	}//end constructor


	private static DefaultCategoryDataset createDataset(ArrayList<Resultado> res)
	{	
		final String aciertos = "ACIERTOS";        
		final String fallos = "FALLOS";
		final String puntuacion = "PUNTUACION";
		final String duracion = "DURACION";

		ArrayList<String> categoria1 = new ArrayList<String>();
		int i = 0;


		final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  

		categoria1.add(res.get(i).getFecha().toString());

		if(res.get(i).getIndicadores().get(0).getValor() != -1 && 
				res.get(i).getIndicadores().get(1).getValor() != -1 &&
				res.get(i).getIndicadores().get(2).getValor() != -1 &&
				res.get(i).getIndicadores().get(3).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(0).getValor() , aciertos , res.get(i+j).getFecha().toString() );                   
				dataset.addValue( res.get(i+j).getIndicadores().get(1).getValor() , fallos , res.get(i+j).getFecha().toString() );
				dataset.addValue( res.get(i+j).getIndicadores().get(2).getValor() , puntuacion , res.get(i+j).getFecha().toString() );
				dataset.addValue( res.get(i+j).getIndicadores().get(3).getValor() , duracion , res.get(i+j).getFecha().toString() );

			}
		}else if(res.get(i).getIndicadores().get(0).getValor() != -1 && 
				res.get(i).getIndicadores().get(1).getValor() != -1 &&
				res.get(i).getIndicadores().get(2).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(0).getValor() , aciertos , res.get(i+j).getFecha().toString() );                   
				dataset.addValue( res.get(i+j).getIndicadores().get(1).getValor() , fallos , res.get(i+j).getFecha().toString() );
				dataset.addValue( res.get(i+j).getIndicadores().get(2).getValor() , puntuacion , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(0).getValor() != -1 && 
				res.get(i).getIndicadores().get(1).getValor() != -1 &&
				res.get(i).getIndicadores().get(3).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(0).getValor() , aciertos , res.get(i+j).getFecha().toString() );                   
				dataset.addValue( res.get(i+j).getIndicadores().get(1).getValor() , fallos , res.get(i+j).getFecha().toString() );
				dataset.addValue( res.get(i+j).getIndicadores().get(3).getValor() , duracion , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(0).getValor() != -1 && 
				res.get(i).getIndicadores().get(2).getValor() != -1 &&
				res.get(i).getIndicadores().get(3).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(0).getValor() , aciertos , res.get(i+j).getFecha().toString() );                   
				dataset.addValue( res.get(i+j).getIndicadores().get(2).getValor() , puntuacion , res.get(i+j).getFecha().toString() );
				dataset.addValue( res.get(i+j).getIndicadores().get(3).getValor() , duracion , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(1).getValor() != -1 && 
				res.get(i).getIndicadores().get(2).getValor() != -1 &&
				res.get(i).getIndicadores().get(3).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(1).getValor() , fallos , res.get(i+j).getFecha().toString() );                   
				dataset.addValue( res.get(i+j).getIndicadores().get(2).getValor() , puntuacion , res.get(i+j).getFecha().toString() );
				dataset.addValue( res.get(i+j).getIndicadores().get(3).getValor() , duracion , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(0).getValor() != -1 && 
				res.get(i).getIndicadores().get(1).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(0).getValor() , aciertos , res.get(i+j).getFecha().toString());                   
				dataset.addValue( res.get(i+j).getIndicadores().get(1).getValor() , fallos , res.get(i+j).getFecha().toString());
			}

		}else if(res.get(i).getIndicadores().get(0).getValor() != -1 && 
				res.get(i).getIndicadores().get(2).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(0).getValor() , aciertos , res.get(i+j).getFecha().toString() );                   
				dataset.addValue( res.get(i+j).getIndicadores().get(2).getValor() , puntuacion , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(0).getValor() != -1 && 
				res.get(i).getIndicadores().get(3).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(0).getValor() , aciertos , res.get(i+j).getFecha().toString() );                   
				dataset.addValue( res.get(i+j).getIndicadores().get(3).getValor() , duracion , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(1).getValor() != -1 && 
				res.get(i).getIndicadores().get(2).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(1).getValor() , fallos , res.get(i+j).getFecha().toString() );                   
				dataset.addValue( res.get(i+j).getIndicadores().get(2).getValor() , puntuacion , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(1).getValor() != -1 && 
				res.get(i).getIndicadores().get(3).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(1).getValor() , fallos , res.get(i+j).getFecha().toString() );                   
				dataset.addValue( res.get(i+j).getIndicadores().get(3).getValor() , duracion , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(2).getValor() != -1 && 
				res.get(i).getIndicadores().get(3).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(2).getValor() , puntuacion , res.get(i+j).getFecha().toString() );                   
				dataset.addValue( res.get(i+j).getIndicadores().get(3).getValor() , duracion , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(0).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(0).getValor() , aciertos , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(1).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(1).getValor() , fallos , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(2).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(2).getValor() , puntuacion , res.get(i+j).getFecha().toString() );
			}

		}else if(res.get(i).getIndicadores().get(3).getValor() != -1 )
		{
			for(int j = 0; j < res.size(); j++)
			{
				dataset.addValue( res.get(i+j).getIndicadores().get(3).getValor() , duracion , res.get(i+j).getFecha().toString() );
			}

		}
		return dataset;
	}


	private static JFreeChart createChart(ArrayList<Resultado> res) {
		JFreeChart lineChart = ChartFactory.createLineChart(
				"Evolución de los indicadores",
				"Fechas",
				"Valor",
				createDataset(res),
				PlotOrientation.VERTICAL,
				true,true,false);


		CategoryPlot plot = lineChart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		Stroke grosor = new BasicStroke(5.0f);
		plot.getRenderer().setStroke(grosor);


		if(res.get(0).getIndicadores().get(0).getValor() != -1 && 
				res.get(0).getIndicadores().get(1).getValor() != -1 &&
				res.get(0).getIndicadores().get(2).getValor() != -1 &&
				res.get(0).getIndicadores().get(3).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, new Color(49, 247, 79));
			plot.getRenderer().setSeriesPaint(1, Color.RED);
			plot.getRenderer().setSeriesPaint(2, Color.MAGENTA);
			plot.getRenderer().setSeriesPaint(3, Color.ORANGE);

		}else if(res.get(0).getIndicadores().get(0).getValor() != -1 && 
				res.get(0).getIndicadores().get(1).getValor() != -1 &&
				res.get(0).getIndicadores().get(2).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, new Color(49, 247, 79));
			plot.getRenderer().setSeriesPaint(1, Color.RED);
			plot.getRenderer().setSeriesPaint(2, Color.MAGENTA);

		}else if(res.get(0).getIndicadores().get(0).getValor() != -1 && 
				res.get(0).getIndicadores().get(1).getValor() != -1 &&
				res.get(0).getIndicadores().get(3).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, new Color(49, 247, 79));
			plot.getRenderer().setSeriesPaint(1, Color.RED);
			plot.getRenderer().setSeriesPaint(2, Color.ORANGE);

		}else if(res.get(0).getIndicadores().get(0).getValor() != -1 && 
				res.get(0).getIndicadores().get(2).getValor() != -1 &&
				res.get(0).getIndicadores().get(3).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, new Color(49, 247, 79));
			plot.getRenderer().setSeriesPaint(1, Color.MAGENTA);
			plot.getRenderer().setSeriesPaint(2, Color.ORANGE);

		}else if(res.get(0).getIndicadores().get(1).getValor() != -1 && 
				res.get(0).getIndicadores().get(2).getValor() != -1 &&
				res.get(0).getIndicadores().get(3).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, Color.RED);
			plot.getRenderer().setSeriesPaint(1, Color.MAGENTA);
			plot.getRenderer().setSeriesPaint(2, Color.ORANGE);

		}else if(res.get(0).getIndicadores().get(0).getValor() != -1 && 
				res.get(0).getIndicadores().get(1).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, new Color(49, 247, 79));
			plot.getRenderer().setSeriesPaint(1, Color.RED);
		}else if(res.get(0).getIndicadores().get(0).getValor() != -1 && 
				res.get(0).getIndicadores().get(2).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, new Color(49, 247, 79));
			plot.getRenderer().setSeriesPaint(1, Color.MAGENTA);
		}else if(res.get(0).getIndicadores().get(0).getValor() != -1 && 
				res.get(0).getIndicadores().get(3).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, new Color(49, 247, 79));
			plot.getRenderer().setSeriesPaint(1, Color.ORANGE);
		}else if(res.get(0).getIndicadores().get(1).getValor() != -1 && 
				res.get(0).getIndicadores().get(2).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, Color.RED);
			plot.getRenderer().setSeriesPaint(1, Color.MAGENTA);
		}else if(res.get(0).getIndicadores().get(1).getValor() != -1 && 
				res.get(0).getIndicadores().get(3).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, Color.RED);
			plot.getRenderer().setSeriesPaint(1, Color.ORANGE);
		}else if(res.get(0).getIndicadores().get(2).getValor() != -1 && 
				res.get(0).getIndicadores().get(3).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, Color.MAGENTA);
			plot.getRenderer().setSeriesPaint(1, Color.ORANGE);
		}else if(res.get(0).getIndicadores().get(0).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, new Color(49, 247, 79));
		}else if(res.get(0).getIndicadores().get(1).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, Color.RED);
		}else if(res.get(0).getIndicadores().get(2).getValor() != -1 )
		{
			plot.getRenderer().setSeriesPaint(0, Color.MAGENTA);
		}else
		{
			plot.getRenderer().setSeriesPaint(0, Color.ORANGE);
		}


		TextTitle legendText = new TextTitle("Paciente: "+ res.get(0).getId_paciente()+
				"    Especialista: "+ res.get(0).getId_especialista()+"    Fecha: "+ res.get(0).getFecha());
		legendText.setPosition(RectangleEdge.BOTTOM);
		legendText.setPaint(Color.BLUE);
		lineChart.addSubtitle(legendText);

		return lineChart;        
	}

	//	public static JPanel createDemoPanel( ) {
	//		JFreeChart chart = createChart();  
	//		return new ChartPanel( chart ); 
	//	}


	public static void main( String[ ] args ) {
		//		GraficaPuntos chart = new GraficaPuntos( "", getR() );
		//		chart.pack( );
		//		RefineryUtilities.centerFrameOnScreen( chart );
		//		chart.setVisible( true );
	}
}