package com.jl.test.draw.demo;
/** 
* @version 创建时间：2020年3月31日 下午1:52:58
* 类说明 
*/
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
 
public class LineChartDemo2 extends ApplicationFrame
{
  public LineChartDemo2(String paramString)
  {
    super(paramString);
    XYDataset localXYDataset = createDataset();
    JFreeChart localJFreeChart = createChart(localXYDataset);
    ChartPanel localChartPanel = new ChartPanel(localJFreeChart);
    localChartPanel.setPreferredSize(new Dimension(500, 270));
    setContentPane(localChartPanel);
  }
 
  private static XYDataset createDataset()
  {
    XYSeries localXYSeries1 = new XYSeries("First");
    localXYSeries1.add(1.0D, 1.0D);
    localXYSeries1.add(2.0D, 4.0D);
    localXYSeries1.add(3.0D, 3.0D);
    localXYSeries1.add(4.0D, 5.0D);
    localXYSeries1.add(5.0D, 5.0D);
    localXYSeries1.add(6.0D, 7.0D);
    localXYSeries1.add(7.0D, 7.0D);
    localXYSeries1.add(8.0D, 8.0D);
    XYSeries localXYSeries2 = new XYSeries("Second");
    localXYSeries2.add(1.0D, 5.0D);
    localXYSeries2.add(2.0D, 7.0D);
    localXYSeries2.add(3.0D, 6.0D);
    localXYSeries2.add(4.0D, 8.0D);
    localXYSeries2.add(5.0D, 4.0D);
    localXYSeries2.add(6.0D, 4.0D);
    localXYSeries2.add(7.0D, 2.0D);
    localXYSeries2.add(8.0D, 1.0D);
  
    XYSeries localXYSeries3 = new XYSeries("Third");
    localXYSeries3.add(3.0D, 4.0D);
    localXYSeries3.add(4.0D, 3.0D);
    localXYSeries3.add(5.0D, 2.0D);
    localXYSeries3.add(6.0D, 3.0D);
    localXYSeries3.add(7.0D, 6.0D);
    localXYSeries3.add(8.0D, 3.0D);
    localXYSeries3.add(9.0D, 4.0D);
    localXYSeries3.add(10.0D, 3.0D);
    XYSeriesCollection localXYSeriesCollection = new XYSeriesCollection();
    localXYSeriesCollection.addSeries(localXYSeries1);
    localXYSeriesCollection.addSeries(localXYSeries2);
    localXYSeriesCollection.addSeries(localXYSeries3);
    return localXYSeriesCollection;
  }
 
  private static JFreeChart createChart(XYDataset paramXYDataset)
  {
    JFreeChart localJFreeChart = ChartFactory.createXYLineChart("Line Chart Demo 2", "X", "Y", paramXYDataset, PlotOrientation.VERTICAL, true, true, false);
    localJFreeChart.setBackgroundPaint(Color.white);
    XYPlot localXYPlot = (XYPlot)localJFreeChart.getPlot();
    localXYPlot.setBackgroundPaint(Color.lightGray);
    localXYPlot.setAxisOffset(new RectangleInsets(5.0D, 5.0D, 5.0D, 5.0D));
    localXYPlot.setDomainGridlinePaint(Color.white);
    localXYPlot.setRangeGridlinePaint(Color.white);
    XYLineAndShapeRenderer localXYLineAndShapeRenderer = (XYLineAndShapeRenderer)localXYPlot.getRenderer();
    localXYLineAndShapeRenderer.setBaseShapesVisible(true);
    localXYLineAndShapeRenderer.setBaseShapesFilled(true);
    NumberAxis localNumberAxis = (NumberAxis)localXYPlot.getRangeAxis();
    localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    return localJFreeChart;
  }
 
  public static JPanel createDemoPanel()
  {
    JFreeChart localJFreeChart = createChart(createDataset());
    return new ChartPanel(localJFreeChart);
  }
 
  public static void main(String[] paramArrayOfString)
  {
    LineChartDemo2 localLineChartDemo2 = new LineChartDemo2("Line Chart Demo 2");
    localLineChartDemo2.pack();
    RefineryUtilities.centerFrameOnScreen(localLineChartDemo2);
    localLineChartDemo2.setVisible(true);
  }
}