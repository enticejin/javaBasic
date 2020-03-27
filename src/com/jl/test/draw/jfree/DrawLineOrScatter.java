package com.jl.test.draw.jfree;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;

public class DrawLineOrScatter {
	
	//　Draw the line 
	public void drawLine() {
		//First parameter: title of main
		String title="数据线性拟合";
		//Second parameter: axis of x 
		String categoryAxisLabel="X值";
		//Third parameter: axis of y
		String valueAxisLabel="Y值";
		//Fourth parameter: data set of jFreeChart
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		dataset.addValue(1, "", "3");
		dataset.addValue(2, "", "6");
		dataset.addValue(3, "", "9");
		//Fifth parameter: graphic form		
		PlotOrientation orientation=PlotOrientation.VERTICAL;
		//Sixth parameter: whether or not there is subtitle
		boolean legend = false;
		//Seventh parameter: whether or not generate prompt tag
		boolean tooltips=true;
		//Eighth parameter: whether or not generate ulr
		boolean urls=false;		
		
		// Initial lineChart
		JFreeChart lineChart=ChartFactory.createLineChart(title, categoryAxisLabel,
				valueAxisLabel, dataset, orientation, legend, tooltips, urls);
		
		// Set the font 
		Font font1=new Font("宋体",Font.BOLD,18);
		Font font2=new Font("宋体",Font.BOLD,15);
		// Set the title of main
		lineChart.getTitle().setFont(font1);
		// Set the subtitle 
		// lineChart.getLegend().setItemFont(font1);
		
		// Get the object of graphic
		CategoryPlot categoryPlot=(CategoryPlot) lineChart.getPlot();
		// Get the object Axis of X
		CategoryAxis categoryAxis=(CategoryAxis) categoryPlot.getDomainAxis();
		// Get the object Axis of Y
		NumberAxis numberAxis=(NumberAxis) categoryPlot.getRangeAxis();
		
		// Set the X-axis
		categoryAxis.setTickLabelFont(font2);
		// Set the label for the X-axis
		categoryAxis.setLabelFont(font2);
		
		// Set the axis of y
		numberAxis.setTickLabelFont(font2);
		// Set the label for the Y-axis
		numberAxis.setLabelFont(font2);		
		// Set the scale and unit on the Y-axis 
		numberAxis.setAutoTickUnitSelection(false);
		// The unit is 0.1
		NumberTickUnit unit=new NumberTickUnit(0.5);
		numberAxis.setTickUnit(unit);
		
		// Get the object of Drawing 
		LineAndShapeRenderer lineAndShapeRenderer=(LineAndShapeRenderer) categoryPlot.getRenderer();
		// Showing the number in the graphic
		StandardCategoryItemLabelGenerator generator=new StandardCategoryItemLabelGenerator();
		lineAndShapeRenderer.setBaseItemLabelGenerator(generator);
		lineAndShapeRenderer.setBaseItemLabelsVisible(true);
		lineAndShapeRenderer.setBaseItemLabelFont(font2);
	
		/*
		// First parameter: number of line, Second parameter: shape
		lineAndShapeRenderer.setSeriesShape(0, new java.awt.Rectangle(10,10));
		lineAndShapeRenderer.setSeriesShapesVisible(0, false);
		*/
		// Show the image in the chartFrame
		ChartFrame frame=new ChartFrame("线性拟合",lineChart);
		frame.setVisible(true);
		frame.pack();
	}
	
	//　Draw the scatter 
	public void drawScatterChart() {
		//First parameter: title of main
		String title="数据线性拟合";
		//Second parameter: axis of x 
		String xAxisLabel="X值";
		//Third parameter: axis of y
		String yAxisLabel="Y值";
		//Fourth parameter: data set of jFreeChart
		DefaultXYDataset dataset = new DefaultXYDataset();
		double[][] data= {{1,2,3},{4,5,6}};
		dataset.addSeries("", data);
		//Fifth parameter: graphic form		
		PlotOrientation orientation=PlotOrientation.VERTICAL;
		//Sixth parameter: whether or not there is subtitle
		boolean legend = false;
		//Seventh parameter: whether or not generate prompt tag
		boolean tooltips=true;
		//Eighth parameter: whether or not generate ulr
		boolean urls=false;	
		
		// Initial scatterChart
		JFreeChart scatterChart=ChartFactory.createScatterPlot(title, xAxisLabel,
				yAxisLabel, dataset, orientation, legend, tooltips, urls);
		
		// Set the font 
		Font font1=new Font("宋体",Font.BOLD,18);
		Font font2=new Font("宋体",Font.BOLD,15);
		// Set the title of main
		scatterChart.getTitle().setFont(font1);
		// Set the subtitle 
		// lineChart.getLegend().setItemFont(font1);
		
		// Get the object of graphic
		XYPlot xyPlot= (XYPlot) scatterChart.getPlot();
		// Get the object Axis of X
		ValueAxis xAxis=(ValueAxis) xyPlot.getDomainAxis();
		// Set the X-axis
		xAxis.setTickLabelFont(font2);
		// Set the label for the X-axis
		xAxis.setLabelFont(font2);
		
		// Get the object Axis of X
		ValueAxis yAxis = xyPlot.getRangeAxis();
		// Set the X-axis
		yAxis.setTickLabelFont(font2);
		// Set the label for the X-axis
		yAxis.setLabelFont(font2);
		
		// Get the object of Drawing 
		XYLineAndShapeRenderer lineAndShapeRenderer=(XYLineAndShapeRenderer) xyPlot.getRenderer();
		// Showing the number in the graphic
		lineAndShapeRenderer.setBaseItemLabelsVisible(true);
		lineAndShapeRenderer.setBaseItemLabelFont(font2);
	
		/*
		// First parameter: number of line, Second parameter: shape
		lineAndShapeRenderer.setSeriesShape(0, new java.awt.Rectangle(10,10));
		lineAndShapeRenderer.setSeriesShapesVisible(0, false);
		*/
		// Show the image in the chartFrame
		ChartFrame frame=new ChartFrame("线性拟合",scatterChart);
		frame.setVisible(true);
		frame.pack();
	}
	
	public static void main(String[] args) {
		DrawLineOrScatter img = new DrawLineOrScatter();
		img.drawLine();
		img.drawScatterChart();
	}
	
}
