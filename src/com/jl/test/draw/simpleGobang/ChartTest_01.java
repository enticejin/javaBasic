package com.jl.test.draw.simpleGobang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;

/** 
* @version ����ʱ�䣺2020��3��27�� ����10:55:11
* ��˵�� 
*/
public class ChartTest_01 {
	public static void main(String[] args) {
		double y_Data[]={10,20,30,40,50,11,22,32,40,50,55,13};//y����
		double z_Data[]={1,0,3,4,5,10,2,3.5,4.3,5,6,1};//y����
        double x_Data[]={3.4,5.2,6.8,1.5,1,1,2,3,4,5,6,7,};//x����
        ChartUtil chart = new ChartUtil();
       // chart.getFreeChart("test", "x", "y", y_Data, x_Data);
        //chart.getFreeChart("pre-test", "x", "y", y_Data, x_Data);
        ChartUtilOpt chartOpt = new ChartUtilOpt();
        double xMax = chartOpt.getMax(x_Data);
        double xMin = chartOpt.getMin(x_Data);
        double yMax = chartOpt.getMax(y_Data);
        double yMin = chartOpt.getMin(y_Data);
        //��ȡ����
        double[] newXData = chartOpt.arraysub(x_Data, chartOpt.getRound(chartOpt.getRound(x_Data.length)), chartOpt.getRound(x_Data.length)+chartOpt.getRound(chartOpt.getRound(x_Data.length)));
        double[] newYData = chartOpt.arraysub(y_Data, chartOpt.getRound(chartOpt.getRound(y_Data.length)), chartOpt.getRound(y_Data.length)+chartOpt.getRound(chartOpt.getRound(y_Data.length)));
        //�������ֵ��Сֵ
        double[] XmaxMin= {xMax, xMin};
        double[] YmaxMin= {yMax, yMin};
        //��������
        double[] xData = chartOpt.ArrayLinked(newXData, XmaxMin);
        double[] yData = chartOpt.ArrayLinked(newYData, YmaxMin);
        
        //����������
        double[] xPlusData = Arrays.copyOf(x_Data, x_Data.length);
        double[] yPlusData = getPlus(0.1, y_Data);
        double[] xnewPlusData = chartOpt.ArrayLinked(x_Data, xPlusData);
        double[] ynewPlusData = chartOpt.ArrayLinked(y_Data, yPlusData);
        //����������
        double[] x1PlusData = getAdd(0.2, xPlusData);
        double[] y1PlusData = getAdd(20, yPlusData);
        double[] x1newPlusData = chartOpt.ArrayLinked(xnewPlusData, x1PlusData);
        double[] y1newPlusData = chartOpt.ArrayLinked(ynewPlusData, y1PlusData);
        chart.getFreeChart("pre-test", "x", "y", y1newPlusData, x1newPlusData);
//      double[] distance = getDistance(x1newPlusData, y1newPlusData);
//      chart.getFreeChart("re-test", "x", "y", distance, x1newPlusData);
	     double y1Max =  chartOpt.getMax(y1newPlusData);
	     double y1Min =  chartOpt.getMin(y1newPlusData);
	     double  middelY=  getRoundY(y1Max, y1Min);
	     double  yMinMiddelY=  getRoundY(middelY, y1Min);
	     double  yMaxMiddelY=  getRoundY(middelY, middelY+yMinMiddelY);
	     List<Double> YList = new ArrayList<Double>();
	     List<Double> XList = new ArrayList<Double>();
	     for(int i =0; i < y1newPlusData.length;i++) {
	  	   if(y1newPlusData[i] > yMinMiddelY && y1newPlusData[i] < yMaxMiddelY) {
	  		   YList.add(y1newPlusData[i]);
	  		   XList.add(x1newPlusData[i]);
	  	   }
	     }
	     YList.add(chartOpt.getMax(y1newPlusData)*10);
	     XList.add(chartOpt.getMax(x1newPlusData));
	     double[] xArray = chartOpt.getArrayByList(XList);
	     double[] yArray = chartOpt.getArrayByList(YList);
	     chart.getFreeChart("pre1-test", "x", "y", yArray, xArray);
        
        //�Ż�����
        
        
        
	}
	/**
	 * ����������������
	 * @param num �ӵĲ���
	 * @param d1 ����
	 * @return
	 */
	public static double[] getAdd(double num, double[] d1) {
		double d2[] = null;
		if(d1.length > 0) {
			d2 = new double[d1.length];
		}
		for(int i = 0;i < d1.length;i++) {
			d2[i] = d1[i] + num;
		}
		return d2;
	}
	/**
	 * ����������������
	 * @param num �ӵĲ���
	 * @param d1 ����
	 * @return
	 */
	public static double[] getPlus(double num, double[] d1) {
		double d2[] = null;
		if(d1.length > 0) {
			d2 = new double[d1.length];
		}
		for(int i = 0;i < d1.length;i++) {
			d2[i] = d1[i] * num;
		}
		return d2;
	}
	/**
	 * ��ȡ������x , y ֮��ľ���
	 * @param d1 x��������
	 * @param d2 y��������
	 * @return
	 */
	public static double[] getDistance(double[] d1, double[] d2) {
		int x1 = 0, y1 = 0;
		double d = 0.0;
		double[] d3 = new double[d1.length];
		if(d1!=null && d1.length == d2.length) {
			for(int i = 0; i < d1.length; i++) {
				d=Math.sqrt(Math.pow((x1 - d1[i]), 2) + Math.pow((y1 - d2[i]), 2));
				d3[i] = d;
			}
		}
		return d3;
	}
	/**
	 * ȡ��Y�����ƽ��ֵ
	 * @param y1 ����1
	 * @param y2 ����2
	 * @return
	 */
	public static double getRoundY(double y1, double y2) {
			return (y1 + y2)/2;
	}
}
