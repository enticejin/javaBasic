package com.jl.test.draw.test;
/** 
* @version 创建时间：2020年3月31日 上午10:12:56
* 类说明 
*/
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.axis.ValueAxis;  
import org.jfree.chart.plot.XYPlot;  
import org.jfree.data.time.Millisecond;  
import org.jfree.data.time.TimeSeries;  
import org.jfree.data.time.TimeSeriesCollection;  
 
public class RealTimeChart extends ChartPanel implements Runnable  
{  
   private static TimeSeries timeSeries;  
   private long value=0;  
     
   public RealTimeChart(String chartContent,String title,String yaxisName)  
   {  
       super(createChart(chartContent,title,yaxisName));  
   }  
     
   @SuppressWarnings("deprecation")
private static JFreeChart createChart(String chartContent,String title,String yaxisName){  
       //创建时序图对象  
       timeSeries = new TimeSeries(chartContent,Millisecond.class);  
       TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeSeries);  
       JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title,"time(s)",yaxisName,timeseriescollection,true,true,false);  
       XYPlot xyplot = jfreechart.getXYPlot();  
       //纵坐标设定  
       ValueAxis valueaxis = xyplot.getDomainAxis();  
       //自动设置数据轴数据范围  
       valueaxis.setAutoRange(true);  
       //数据轴固定数据范围 30s  
       valueaxis.setFixedAutoRange(30000D);  
 
       valueaxis = xyplot.getRangeAxis();  
       //valueaxis.setRange(0.0D,200D);  
 
       return jfreechart;  
     }  
 
   public void run()  
   {  
       while(true)  
       {  
       try  
       {  
           timeSeries.add(new Millisecond(), randomNum());  
           Thread.sleep(300);  
       }  
       catch (InterruptedException e)  {   }  
       }         
   }  
     
   private long randomNum()  
   {   
       System.out.println((Math.random()*20+80));        
       return (long)(Math.random()*20+80);  
   }  
}  
 

