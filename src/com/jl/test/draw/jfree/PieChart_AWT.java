package com.jl.test.draw.jfree;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/** 
* @version ����ʱ�䣺2020��3��24�� ����4:55:57
* ��˵�� 
*/
public class PieChart_AWT extends ApplicationFrame{

	public PieChart_AWT(String title) {
		super(title);
		setContentPane(createDemoPanle());
	}
	public static PieDataset createDataset() {
		DefaultPieDataset dataset =	new DefaultPieDataset();
		dataset.setValue("Iphone 12",  new Double(20));
		dataset.setValue("Samsung Galaxy",  new Double(20));
		dataset.setValue("MotoG",  new Double(40));
		dataset.setValue("Nokia Lumia",  new Double(10));
		return dataset;
	}
	public static JPanel createDemoPanle() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
	private static JFreeChart createChart(PieDataset createDataset) {
		// TODO Auto-generated method stub
		JFreeChart chart = ChartFactory.createPieChart("Mobile Sales", createDataset, 
				true,
				true,
				false);
		
		return chart;
	}
	
	public static void main(String[] args) {

	      PieChart_AWT demo = new PieChart_AWT( "Mobile Sales" );  
	      demo.setSize( 560 , 367 );    
	      RefineryUtilities.centerFrameOnScreen( demo );    
	      demo.setVisible( true ); 


	}
}
