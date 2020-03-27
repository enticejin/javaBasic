package com.jl.test.draw.G;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


/** 
* @version 创建时间：2020年3月24日 下午3:54:34
* 类说明 
*/
public class LineChart_AWT extends ApplicationFrame {
	public LineChart_AWT( String applicationTitle , String chartTitle )
	   {
	      super(applicationTitle);
	      JFreeChart lineChart = ChartFactory.createLineChart(
	         chartTitle,
	         "t","s",
	         createDataset(),
	         PlotOrientation.VERTICAL,
	         true,true,false);
	         
	      ChartPanel chartPanel = new ChartPanel( lineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane( chartPanel );
	   }

	   private DefaultCategoryDataset createDataset( )
	   {
	      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      dataset.addValue( 15 , "t" , "0" );
	      dataset.addValue( 30 , "t" , "1" );
	      dataset.addValue( 60 , "t" ,  "2" );
	      dataset.addValue( 120 , "t" , "3" );
	      dataset.addValue( 240 , "t" , "4" );
	      dataset.addValue( 300 , "t" , "5" );
	      return dataset;
	   }
	   public static void main( String[ ] args ) 
	   {
	      LineChart_AWT chart = new LineChart_AWT(
	      "t" ,
	      "s");

	      chart.pack( );
	      RefineryUtilities.centerFrameOnScreen( chart );
	      chart.setVisible( true );
	   }
}
