package com.jl.test.draw.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
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
* 类说明 :根据坐标描所在位置的工具类
*/
public class ChartUtil {
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
					if(sub[i] > 0 && sub[i] < 0.05) {
						list.add(sub[i]);
					}else {
						list.add(0.05);
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
	        chartPanel.setPreferredSize(new java.awt.Dimension(201,400));
	        //创建一个主窗口来显示面板
	        JFrame frame=new JFrame(title);
	        frame.setLocation(0,0);
	        frame.setSize((int)screensize.getWidth(),(int)screensize.getHeight());
	        //将主窗口的内容面板设置为图表面板
	        frame.setContentPane(chartPanel);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
		}
}
