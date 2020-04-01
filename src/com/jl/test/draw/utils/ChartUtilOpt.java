package com.jl.test.draw.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYZDataset;

/** 
* @version 创建时间：2020年3月26日 下午2:47:23
* 类说明 :优化坐标描绘所在位置的工具类
*/
public class ChartUtilOpt {
	//根据数据设置权限值
		/**
		 * 获取权限值
		 * @param x_data 横坐标
		 * @param y_data 纵坐标
		 * @return
		 */
		public double[][] getSize(double x_data[], double y_data[]) {
			
			double[] size = null;
			if(x_data.length == y_data.length) {
				size = new double[x_data.length];
				//数组转换成列表
				List<Double> list=new ArrayList(Arrays.asList(size));
				//遍历并判断
				double[] sub = null;
				for(int i = 0; i < x_data.length;i++) {
					sub = getSubs(sub, x_data, y_data);
					//设置权重
					if(sub[i] > 0 && sub[i] < 1) {
						list.add(sub[i]);
					}else {
						list.add(0.005);
					}
				}
				//截取列表
				list = list.subList(1,  list.size());
				Object[] obj = list.toArray();
				for(int i = 0;i <  obj.length;i++) {
					size[i] = Double.parseDouble(obj[i].toString());
				}
			}
			//将三个一维数组放到一个二维数组中
			double d[][] = {x_data, y_data, size};
			return d;
		}
		/**
		 * 两个数组做差运算
		 * @param sub 获得的差
		 * @param d1 减数
		 * @param d2 被减数
		 * @return
		 */
		public double[] getSubs(double[] sub,double[] d1, double[] d2) {
			if(d1.length == d2.length) {
				sub = new double[d1.length];
			}
			for(int i =0; i < d1.length ;i++) {
				sub[i] = d1[i] - d2[i];
			}
			return sub;
		}
		/**
		 * 两个数组做模运算
		 * @param mode 取模后的数组
		 * @param d1 
		 * @param d2
		 * @return
		 */
		public double[] getMode(double[] mode,double[] d1, double[] d2) {
			if(d1.length == d2.length) {
				mode = new double[d1.length];
			}
			for(int i =0; i < d1.length ;i++) {
				mode[i] = d1[i] % d2[i];
			}
			return mode;
		}
		
	/**
	 * 
	 * @param title  标题
	 * @param xTag   x坐标
	 * @param yTag   y坐标
	 * @param x_data x坐标的实际坐标值
	 * @param y_data y坐标的实际坐标值
	 */
		public void getFreeChart(String title,String xTag, String yTag, double x_data[], double y_data[]) {
			Toolkit tk=Toolkit.getDefaultToolkit();
	    	Dimension screensize=tk.getScreenSize();
	        //设置气泡表的数据集
	        DefaultXYZDataset dataset=new DefaultXYZDataset();
	        
	        dataset.addSeries(title, getSize(x_data, y_data));
	        //实现简单的气泡图，设置基本的数据
	        JFreeChart freeChart=ChartFactory.createBubbleChart(
	                title,// 图表标题
	                yTag,//y轴方向数据标签
	                xTag,//x轴方向数据标签
	                dataset,//数据集，即要显示在图表上的数据
	                PlotOrientation.HORIZONTAL,//设置方向
	                true,//是否显示图例
	                true,//是否显示提示
	                false//是否生成URL连接
	        );
	      //以面板显示
	        ChartPanel chartPanel=new ChartPanel(freeChart);
	        chartPanel.setPreferredSize(new java.awt.Dimension((int)screensize.getWidth(),(int)screensize.getHeight()));
	        //创建一个主窗口来显示面板
	        JFrame frame=new JFrame(title);
	        frame.setLocation(0,0);
	        frame.setSize((int)screensize.getWidth(),(int)screensize.getHeight());
	        //将主窗口的内容面板设置为图表面板
	        frame.setContentPane(chartPanel);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
		}
		/**
		 * 四舍五入
		 * @param number
		 * @return
		 */
		public int getRound(int number) {
			return Math.round(number/2);
		}
		
		/**
		 * 截取数组
		 * @param a 数组
		 * @param start 起始位
		 * @param end 终点位
		 * @return
		 */
		public double[] arraysub(double[]a,int start,int end){
			if(a==null){
				throw new IllegalArgumentException("a  must be not null.");
			}
			if(start<0||end<0){
				throw new IllegalArgumentException("start or end must be >0.");
			}
			if(start>=a.length){
				throw new IllegalArgumentException("start must be <a.leng.");
			}
			if(start>end){
				throw new IllegalArgumentException("start must be <end.");
			}
			int count = end-start;
			if(count>a.length){
				throw new IllegalArgumentException("count must be <=a.length.");
			}
			double[] b = new double[count];
			for(int i = start;i<end;i++){
				b[i-start]=a[i];
			}
			return b;
			}
		
	/**
	 * 数组中找出最大的元素
	 * 
	 * @param d 需要求最大值的数组
	 * @return
	 */
	public double getMax(double[] d) {
		double maxDouble = d[0];
		// 遍历数组
		for (int i = 0; i < d.length; i++) {
			if (maxDouble < d[i]) {
				maxDouble = d[i];
			}
		}
		return maxDouble;
	}
		/**
		 * 数组中找出最小的元素
		 * @param d 需要求最小值的数组
		 * @return
		 */
		public double getMin(double[] d) {
			double minDouble = d[0];
			//遍历数组
			for(int i = 0;i < d.length;i++) {
				if(minDouble > d[i]) {
					minDouble = d[i];
				}
			}
			return minDouble;
		}
		/**
		 * 取商
		 * @param d1 参数1
		 * @param d2 参数2
		 * @return
		 */
		public double getQuotient(double d1, double d2) {
			if(d2 > d1) {
				return d2 / d1;
			}
			else
			return d1 / d2;
		}
		/**
		 * 取差值
		 * @param d1 参数1
		 * @param d2 参数2
		 * @return
		 */
		public double getSus(double d1, double d2) {
			if(d2 > d1) {
				return d2 - d1;
			}
			else
				return d1 - d2;
		}
		/**
		 * 两个参数的平均值
		 * @param d1
		 * @param d2
		 * @return
		 */
		public  double getAve(double d1, double d2) {
			return (d2+d1)/2;
		}
		
		/**
		 * 数组连接
		 * @param d1 数组1
		 * @param d2 数组2
		 * @return
		 */
		public double[] ArrayLinked(double[] d1, double[] d2) {
			double[] d3 = new double[d1.length+d2.length];
			int num = 0;
			if(d1 !=null && d2 != null) {
				for(int i =0;i < d1.length ;i++) {
					d3[i] = d1[i];
					if(i == d1.length - 1) {
						num = i;
					}
				}
				for(int i =0;i < d2.length;i++) {
					d3[num + i] = d2[i];
				}
			}
			return d3;
		}
		/**
		 * 将Double列表转化成数组
		 * @param list
		 * @return
		 */
		public double[] getArrayByList(List<Double> list) {
			double[] d1 = new double[list.size()];
			if(list != null) {
				list = list.subList(1, list.size());
				Object[]  obj = list.toArray();
				for(int i = 0;i < obj.length;i++) {
					d1[i] = Double.parseDouble(obj[i].toString());
				}
			}
			return d1;
		}
		/**
		 * 将String列表转化成double数组
		 * @param list
		 * @return
		 */
		public double[] getArrayByStrList(List<String> list) {
			double[] d1 = new double[list.size()];
			if(list != null) {
				list= list.subList(1,  list.size());
				Object[]  obj = list.toArray();
				for(int i = 0;i < obj.length;i++ ) {
					d1[i] = Double.parseDouble(obj[i].toString());
				}
			}
			return d1;
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
		/**
		 * 读取.csv文件数据
		 * @param row 行
		 * @param col 列
		 * @param filePath 文件路径
		 */
		public List<String> getAllCSV(String filePath) {
			File csv = new File(
					filePath); // CSV文件路径
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(csv));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String line = "";
			String everyLine = "";
			List<String> allString = new ArrayList<>();
			try {
				while ((line = br.readLine()) != null) // 读取到的内容给line变量
				{
					everyLine = line;
					allString.add(everyLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return allString;
		}
		/**
		 * 读取文件坐标列的内容
		 * @param filePath 文件路径
		 * @return
		 */
		public List<String> getCol(String filePath){
			List<String> allCSV =getAllCSV(filePath);
			List<String> allCSVIndex = new ArrayList<String>();
			for(int i = 0 ; i < allCSV.size(); i++) {
				allCSVIndex.add(allCSV.get(i).split(",")[3]);
			}
			return allCSVIndex;
		}
		
}
