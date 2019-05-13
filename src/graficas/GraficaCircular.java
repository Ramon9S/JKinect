package graficas;

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleEdge;

import resultados.Resultado;

public class GraficaCircular extends Grafica {  

	static int total = -1;

	public GraficaCircular( String title , ArrayList<Resultado> res) {
		super( );

		setTipo(TipoGrafica.Circular);
		setResultados(res);

		setGrafica(createChart( createDataset( res ), res ));
	}//end constructor


	private static PieDataset createDataset(ArrayList<Resultado> res )
	{
		total = (int)( res.get(0).getIndicadores().get(0).getValor() + res.get(0).getIndicadores().get(1).getValor() );

		DefaultPieDataset dataset = new DefaultPieDataset( );

		if(res.get(0).getIndicadores().get(0).getValor() != -1 && res.get(0).getIndicadores().get(1).getValor() != -1)
		{
			dataset.setValue( "FALLOS" , new Double( res.get(0).getIndicadores().get(1).getValor() ) );
			dataset.setValue( "ACIERTOS" , new Double( res.get(0).getIndicadores().get(0).getValor() ) );     
		}else if(res.get(0).getIndicadores().get(0).getValor() != -1)
		{
			dataset.setValue( "ACIERTOS" , new Double( res.get(0).getIndicadores().get(0).getValor() ) );
		}else if(res.get(0).getIndicadores().get(1).getValor() != -1)
		{
			dataset.setValue( "FALLOS" , new Double( res.get(0).getIndicadores().get(1).getValor() ) );
		}

		return dataset;         
	}

	private static JFreeChart createChart( PieDataset dataset, ArrayList<Resultado> res ) {
		JFreeChart chart = ChartFactory.createPieChart(      
				"Aciertos vs Fallos",   // chart title 
				dataset,          		// data    
				true,             		// include legend   
				true, 
				false);

		PiePlot piePlot = (PiePlot) chart.getPlot();
		piePlot.setBackgroundPaint(Color.WHITE); // set background color white

		if(res.get(0).getIndicadores().get(0).getValor() != -1 && res.get(0).getIndicadores().get(1).getValor() != -1)
		{
			piePlot.setSectionPaint(0, Color.RED);
			piePlot.setSectionPaint(1, new Color(49, 247, 79));     
		}else if(res.get(0).getIndicadores().get(0).getValor() != -1)
		{
			piePlot.setSectionPaint(0, new Color(49, 247, 79));
		}else if(res.get(0).getIndicadores().get(1).getValor() != -1)
		{
			piePlot.setSectionPaint(0, Color.RED);
		}


		TextTitle legendText = new TextTitle("Paciente: "+ res.get(0).getId_paciente()+
				"    Especialista: "+ res.get(0).getId_especialista()+"    Fecha: "+ res.get(0).getFecha());
		legendText.setPosition(RectangleEdge.BOTTOM);
		legendText.setPaint(Color.BLUE);
		chart.addSubtitle(legendText);


		if(res.get(0).getIndicadores().get(2).getValor() != -1 && res.get(0).getIndicadores().get(3).getValor() != -1)
		{
			TextTitle legendText2 = new TextTitle(res.get(0).getIndicadores().get(2).getNombre()+
					" de la partida : "+ res.get(0).getIndicadores().get(2).getValor() );
			legendText2.setPosition(RectangleEdge.TOP);
			legendText2.setPaint(Color.MAGENTA);
			chart.addSubtitle(legendText2);
			
			TextTitle legendText3 = new TextTitle(res.get(0).getIndicadores().get(3).getNombre()+" de la partida : "+ (int)
					res.get(0).getIndicadores().get(3).getValor()+" segundos");
			legendText3.setPosition(RectangleEdge.TOP);
			legendText3.setPaint(Color.ORANGE);
			chart.addSubtitle(legendText3);
		}else if(res.get(0).getIndicadores().get(2).getValor() != -1)
		{
			TextTitle legendText2 = new TextTitle(res.get(0).getIndicadores().get(2).getNombre()+
					" de la partida : "+ res.get(0).getIndicadores().get(2).getValor());
			legendText2.setPosition(RectangleEdge.TOP);
			legendText2.setPaint(Color.MAGENTA);
			chart.addSubtitle(legendText2);
		}else if(res.get(0).getIndicadores().get(3).getValor() != -1)
		{
			TextTitle legendText2 = new TextTitle(res.get(0).getIndicadores().get(3).getNombre()+" de la partida : "+ (int)
					res.get(0).getIndicadores().get(3).getValor()+" segundos");
			legendText2.setPosition(RectangleEdge.TOP);
			legendText2.setPaint(Color.ORANGE);
			chart.addSubtitle(legendText2);
		}


		return chart;
	}


	public static void main( String[ ] args ) {
		//					GraficaCircular demo = new GraficaCircular( "", getR() );
		//					demo.pack();
		//					RefineryUtilities.centerFrameOnScreen( demo );    
		//					demo.setVisible( true );
	}//end main


}//end class