package com.jl.test.draw.test;
import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

/** 
* @version ����ʱ�䣺2020��3��31�� ����10:12:56
* ��˵�� 
*/
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;  
import org.jfree.data.time.Millisecond;  
import org.jfree.data.time.TimeSeries;  
import org.jfree.data.time.TimeSeriesCollection;

import com.jl.test.draw.DeleteString;
import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;
 
public class RealTimeChart_01 extends ChartPanel implements Runnable  
{  
		   private static TimeSeries timeSeries;  
		   private long value=0;  
	       private Millisecond millSecond = new Millisecond();
		   public RealTimeChart_01(String chartContent,String title,String yaxisName)  
		   {  
		       super(createChart(chartContent,title,yaxisName));  
		   }  
		     
		   @SuppressWarnings("deprecation")
		private static JFreeChart createChart(String chartContent,String title,String yaxisName){  
		       //����ʱ��ͼ����  
		       timeSeries = new TimeSeries(chartContent,Millisecond.class);  
		       TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeSeries);  
		       JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title,"time(s)",yaxisName,timeseriescollection,true,true,false);  
		       XYPlot xyplot = jfreechart.getXYPlot();  
		       //�������趨  
		       ValueAxis valueaxis = xyplot.getDomainAxis();  
		       ValueAxis valueayis = xyplot.getDomainAxis();  
		       //�Զ��������������ݷ�Χ  
		       valueaxis.setAutoRange(true);    
		       valueayis.setAutoRange(true);    
		       //������̶����ݷ�Χ 30s  
		       valueaxis.setFixedAutoRange(30000D);   
		       valueayis.setFixedAutoRange(30000D);   
		 
		       valueaxis = xyplot.getRangeAxis();   
		       valueayis = xyplot.getRangeAxis();   
		       //valueaxis.setRange(0.0D,200D);  
		 
		       return jfreechart;  
		     }  
		 
		   public void run()  
		   {  
		       while(true)  
		       {  
		       try  
		       {   String filePath = "D:\\work\\pointinfo_solve.csv";
		    	   double[] xList = getXData(filePath);
		    	   double[] yList = getYData(filePath);
		    	   double[] distance = getDistance(xList, yList);
		    	   for(int i = 0 ;i < distance.length; i++) {
			           timeSeries.add(new Millisecond(), distance[i]); 
			           Thread.sleep(300); 
		    	   } 
		       }  
		       catch (InterruptedException e)  {   }  
		       }         
		   }  
		     
		   private long randomNum()  
		   {   
		       System.out.println((Math.random()*20+80));        
		       return (long)(Math.random()*20+80);  
		   }
		   Map<String, Object> map = new HashMap<String, Object>();	   
	/**
	 * ��ȡX��������
	 * @return
	 */
	private double[] getXData(String filePath) {
		ChartUtilOpt chartOpt = new ChartUtilOpt();
		// ��ȡ�ļ�
		List<String> csvColList = chartOpt.getCol(filePath);
		DeleteString delete = new DeleteString();
		// �и��ַ���
		Object[] result = delete.deleteSubString(csvColList.toString(), "|1.0, ");

		// System.out.println("ɾ����"+result[0]);
		// String strResult = result[0].toString();
		// ɾ���ַ�
		String strResult1 = delete.deleteSubString(result[0].toString(), "|")[0].toString();
		String[] xyArray = strResult1.split("-");
		List<String> YListStr = new ArrayList<String>();
		List<String> XListStr = new ArrayList<String>();
		// �жϲ�װ��x��y����
		for (int i = 0; i < xyArray.length; i++) {
			if (xyArray[i].length() == 0) {
				continue;
			}
			if (i % 2 == 0) {
				YListStr.add(xyArray[i]);
			} else {
				XListStr.add(xyArray[i]);
			}
		}
		if (XListStr.size() - YListStr.size() > 0) {
			XListStr = XListStr.subList(0, YListStr.size());
		} else {
			YListStr = YListStr.subList(0, XListStr.size());
		}
		// �ַ���ת����double����
		double[] xList1 = chartOpt.getArrayByStrList(XListStr);
		double[] yList1 = chartOpt.getArrayByStrList(YListStr);
		
		for (int i = 0; i < xList1.length; i++) {
			xList1[i] = 0 - xList1[i];
			yList1[i] = 0 - yList1[i];
		}
		return xList1;
	}
	/**
	 * ��ȡY��������
	 * @return
	 */
	private double[] getYData(String filePath) {
		ChartUtilOpt chartOpt = new ChartUtilOpt();
		ChartUtil chart = new ChartUtil();
		// ��ȡ�ļ�
		List<String> csvColList = chartOpt.getCol(filePath);
		DeleteString delete = new DeleteString();
		// �и��ַ���
		Object[] result = delete.deleteSubString(csvColList.toString(), "|1.0, ");

		// System.out.println("ɾ����"+result[0]);
		// String strResult = result[0].toString();
		// ɾ���ַ�
		String strResult1 = delete.deleteSubString(result[0].toString(), "|")[0].toString();
		String[] xyArray = strResult1.split("-");
		List<String> YListStr = new ArrayList<String>();
		List<String> XListStr = new ArrayList<String>();
		// �жϲ�װ��x��y����
		for (int i = 0; i < xyArray.length; i++) {
			if (xyArray[i].length() == 0) {
				continue;
			}
			if (i % 2 == 0) {
				YListStr.add(xyArray[i]);
			} else {
				XListStr.add(xyArray[i]);
			}
		}
		if (XListStr.size() - YListStr.size() > 0) {
			XListStr = XListStr.subList(0, YListStr.size());
		} else {
			YListStr = YListStr.subList(0, XListStr.size());
		}
		// �ַ���ת����double����
		double[] xList1 = chartOpt.getArrayByStrList(XListStr);
		double[] yList1 = chartOpt.getArrayByStrList(YListStr);
		
		for (int i = 0; i < xList1.length; i++) {
			xList1[i] = 0 - xList1[i];
			yList1[i] = 0 - yList1[i];
		}
		return yList1;
	}
	
	/**
	 * ��ȡx��y��ԭ��ľ���
	 * @param d1 x��������
	 * @param d2 y��������
	 * @return
	 */
	public double[] getDistance(double[] d1, double[] d2) {
		if(d1.length == d2.length) {
			double[] d3 = new double[d1.length];
			for (int i = 0; i < d1.length; i++) {
				d3[i] = Math.sqrt(Math.pow(d1[i], 2) + Math.pow(d1[i], 2));
			}
			return d3;
		}else {
			return null;
		}
	}
}

		 
 

