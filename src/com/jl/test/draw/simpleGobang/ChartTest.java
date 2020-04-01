package com.jl.test.draw.simpleGobang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.jl.test.draw.DeleteString;
import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;

/** 
* @version 创建时间：2020年3月26日 上午11:15:39
* 类说明 
*/
public class ChartTest extends ApplicationFrame {
	public ChartTest(String title) {
		super(title);
	}

	public static void main(String[] args) throws IOException {
		//double y_Data[]={10,20,30,40,50,11,22,32,40,50,55,13};//y坐标
		double y_Data[]={-4.287544763077644,
				-2.4098368857533434,
				-2.4436862318228334,
				-2.4234545833612833,
				-2.432478964314477,
				-2.393130533394534,
				-2.2974986083934215,
				-2.4306333590825933,
				-2.372405470614599,
				-2.40717572959088,
				-2.425429118363598,
				-2.396698049689745,
				-2.4236761362456507,
				-2.425013249574575,
				-2.459762203972785,
				-2.37166436321854,
				-2.3792567011985426,
				-2.375018190543897};//y坐标
		double z_Data[]={1,0,3,4,5,10,2,3.5,4.3,5,6,1};//y坐标
        //double x_Data[]={3.4,5.2,6.8,1.5,1,1,2,3,4,5,6,7};//x坐标
        double x_Data[]={-0.9752943187208484
        		,-3.9423985894705753
        		,-3.792898241189376
        		,-3.8592025290820713
        		,-3.8440203147436667
        		,-3.826994983839434
        		,-3.980965599922959
        		,-3.906066875111779
        		,-3.853363711555778
        		,-3.903540236639506
        		,-3.9086011399589067
        		,-3.886398786857531
        		,-3.8332343012345733
        		,-3.925152279557878
        		,-3.849258857473251
        		,-3.922483494802719
        		,-3.898763218110023
        		,-3.911179977223942};//x坐标
        ChartUtil chart = new ChartUtil();
       // chart.getFreeChart("test", "x", "y", y_Data, x_Data);
        //chart.getFreeChart("pre-test", "x", "y", y_Data, x_Data);
        ChartUtilOpt chartOpt = new ChartUtilOpt();
        double xMax = chartOpt.getMax(x_Data);
        double xMin = chartOpt.getMin(x_Data);
        double yMax = chartOpt.getMax(y_Data);
        double yMin = chartOpt.getMin(y_Data);
        //截取数据
        double[] newXData = chartOpt.arraysub(x_Data, chartOpt.getRound(chartOpt.getRound(x_Data.length)), chartOpt.getRound(x_Data.length)+chartOpt.getRound(chartOpt.getRound(x_Data.length)));
        double[] newYData = chartOpt.arraysub(y_Data, chartOpt.getRound(chartOpt.getRound(y_Data.length)), chartOpt.getRound(y_Data.length)+chartOpt.getRound(chartOpt.getRound(y_Data.length)));
        //坐标最大值最小值
        double[] XmaxMin= {xMax, xMin};
        //System.out.println("xMax="+XmaxMin[0]);
        double[] YmaxMin= {yMax, yMin};
        //数组连接
        double[] xData = chartOpt.ArrayLinked(newXData, XmaxMin);
        double[] yData = chartOpt.ArrayLinked(newYData, YmaxMin);
        
        //构造新数组
        double[] xPlusData = Arrays.copyOf(x_Data, x_Data.length);
        double[] yPlusData = getPlus(0.1, y_Data);
        double[] xnewPlusData = chartOpt.ArrayLinked(x_Data, xPlusData);
        double[] ynewPlusData = chartOpt.ArrayLinked(y_Data, yPlusData);
        //构造新数组
        double[] x1PlusData = getAdd(0.2, xPlusData);
        double[] y1PlusData = getAdd(20, yPlusData);
        double[] x1newPlusData = chartOpt.ArrayLinked(xnewPlusData, x1PlusData);
        double[] y1newPlusData = chartOpt.ArrayLinked(ynewPlusData, y1PlusData);
       //chart.getFreeChart("pre-test", "x", "y", y1newPlusData, x1newPlusData);
        //double[] distance = getDistance(x1newPlusData, y1newPlusData);
     //  chart.getFreeChart("re-test", "x", "y", distance, x1newPlusData);
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
       YList.add(chartOpt.getMax(y1newPlusData));
       XList.add(chartOpt.getMax(x1newPlusData));
       double[] xArray = chartOpt.getArrayByList(XList);
       double[] yArray = chartOpt.getArrayByList(YList);
       //chart.getFreeChart("pre1-test", "x", "y", yArray, xArray);
       /*
        File file = new File("D:/point_info.txt");
      
       String str = txt2String(file);
       DeleteString delete = new DeleteString();
       Object[] result =  delete.deleteSubString(str, "|1.0");
       //System.out.println("删除字串后："+result[0]);
       String strResult = result[0].toString();
       String strResult1 = delete.deleteSubString(strResult, "|")[0].toString();
       System.out.println("strResult1 = "+strResult1);
       String[] strArray = strResult1.split("-");
       List<String> XListStr = new ArrayList<String>();
       List<String> YListStr = new ArrayList<String>();
       for(int i = 0;i < strArray.length;i++) {
    	   if(strArray[i].length() == 0) {
    		   continue;
    	   }
    	   if( i % 2 == 0) {
    		   YListStr.add(strArray[i]);
    	   }else {
    		   XListStr.add(strArray[i]);
    	   }
    	   
       }
       if(XListStr.size() - YListStr.size() > 0) {
    	   XListStr =  XListStr.subList(0, YListStr.size());
       }else {
    	   YListStr = YListStr.subList(0, XListStr.size());
       }
       //System.out.println(XListStr.subList(1, XListStr.size()));
       double[] xListStr = chartOpt.getArrayByStrList(XListStr);
       double[] yListStr = chartOpt.getArrayByStrList(YListStr);
       for(int i = 0; i < xListStr.length;i++) {
    	   xListStr[i] = 0 - xListStr[i];
    	   yListStr[i] = 0 - yListStr[i];
       }
       chart.getFreeChart("re-test", "x", "y", xListStr, yListStr);
       */
       
       
       List<String> csvColList = chartOpt.getCol("D:\\pointinfo_solve.csv");
       DeleteString delete = new DeleteString();
       Object[] result =  delete.deleteSubString(csvColList.toString(), "|1.0, ");
       
       //System.out.println("删除后："+result[0]);
       //String strResult = result[0].toString();
       String strResult1 = delete.deleteSubString(result[0].toString(), "|")[0].toString();
       String[] xyArray = strResult1.split("-");
       List<String> XListStr = new ArrayList<String>();
       List<String> YListStr = new ArrayList<String>();
       for(int i = 0; i < xyArray.length;i++) {
    	   if(xyArray[i].length() == 0) {
    		   continue;
    	   }
    	   if( i % 2 == 0) {
    		   YListStr.add(xyArray[i]);
    	   }else {
    		   XListStr.add(xyArray[i]);
    	   }
       }
       if(XListStr.size() - YListStr.size() > 0) {
    	   XListStr =  XListStr.subList(0, YListStr.size());
       }else {
    	   YListStr = YListStr.subList(0, XListStr.size());
       }
       //System.out.println(XListStr.subList(1, XListStr.size()));
       double[] xListStr = chartOpt.getArrayByStrList(XListStr);
       double[] yListStr = chartOpt.getArrayByStrList(YListStr);
       for(int i = 0; i < xListStr.length;i++) {
    	   xListStr[i] = 0 - xListStr[i];
    	   yListStr[i] = 0 - yListStr[i];
       }
       chart.getFreeChart("re-test", "x", "y", xListStr, yListStr);
	}
	/**
	 * 求出数组与数字相加
	 * @param num 加的参数
	 * @param d1 数组
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
	 * 求出数组与数字相乘
	 * @param num 加的参数
	 * @param d1 数组
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
	 * 获取数组中x , y 之间的距离
	 * @param d1 x坐标数组
	 * @param d2 y坐标数组
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
	 * 取出Y坐标的平均值
	 * @param y1 参数1
	 * @param y2 参数2
	 * @return
	 */
	public static double getRoundY(double y1, double y2) {
			return (y1 + y2)/2;
	}
	/**
	 * 读取坐标
	 * @param file 读取的文件
	 * @return
	 */
	  public static String txt2String(File file){
	        StringBuilder result = new StringBuilder();
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	                result.append(System.lineSeparator()+s);
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return result.toString();
	    }
	  /**
	   * 字符串删除指定字符串
	   * @param str1 原字符串
	   * @param str2 指定的字符串
	   * @return
	   */
	  public Object[] deleteSubString(String str1,String str2) {
			StringBuffer sb = new StringBuffer(str1);
			int delCount = 0;
			Object[] obj = new Object[2];
	 
			while (true) {
				int index = sb.indexOf(str2);
				if(index == -1) {
					break;
				}
				sb.delete(index, index+str2.length());
				delCount++;
				
			}
			if(delCount!=0) {
				obj[0] = sb.toString();
				obj[1] = delCount;
			}else {
				//不存在返回-1
				obj[0] = -1;
				obj[1] = -1;
			}
			
			return obj;
		}
}
