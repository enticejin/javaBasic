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
* @version ����ʱ�䣺2020��3��26�� ����2:47:23
* ��˵�� :��������������λ�õĹ�����
*/
public class ChartUtil {
	//������������Ȩ��ֵ
		/**
		 * ��ȡȨ��ֵ
		 * @param x_data ������
		 * @param y_data ������
		 * @return
		 */
		public double[][] getSize(double x_data[], double y_data[]) {
			
			double[] size = null;
			if(x_data.length == y_data.length) {
				size = new double[x_data.length];
				//����ת�����б�
				List<Double> list=new ArrayList(Arrays.asList(size));
				//�������ж�
				double[] sub = null;
				for(int i = 0; i < x_data.length;i++) {
					sub = getSubs(sub, x_data, y_data);
					//����Ȩ��
					if(sub[i] > 0 && sub[i] < 0.05) {
						list.add(sub[i]);
					}else {
						list.add(0.05);
					}
				}
				//��ȡ�б�
				list = list.subList(1,  list.size());
				Object[] obj = list.toArray();
				for(int i = 0;i <  obj.length;i++) {
					size[i] = Double.parseDouble(obj[i].toString());
				}
			}
			//������һά����ŵ�һ����ά������
			double d[][] = {x_data, y_data, size};
			return d;
		}
		/**
		 * ����������������
		 * @param sub ��õĲ�
		 * @param d1 ����
		 * @param d2 ������
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
		 * ����������ģ����
		 * @param mode ȡģ�������
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
	        //�������ݱ�����ݼ�
	        DefaultXYZDataset dataset=new DefaultXYZDataset();
	        
	        dataset.addSeries(title, getSize(x_data, y_data));
	        //ʵ�ּ򵥵�����ͼ�����û���������
	        JFreeChart freeChart=ChartFactory.createBubbleChart(
	                title,// ͼ�����
	                yTag,//y�᷽�����ݱ�ǩ
	                xTag,//x�᷽�����ݱ�ǩ
	                dataset,//���ݼ�����Ҫ��ʾ��ͼ���ϵ�����
	                PlotOrientation.HORIZONTAL,//���÷���
	                true,//�Ƿ���ʾͼ��
	                true,//�Ƿ���ʾ��ʾ
	                false//�Ƿ�����URL����
	        );
	      //�������ʾ
	        ChartPanel chartPanel=new ChartPanel(freeChart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(201,400));
	        //����һ������������ʾ���
	        JFrame frame=new JFrame(title);
	        frame.setLocation(0,0);
	        frame.setSize((int)screensize.getWidth(),(int)screensize.getHeight());
	        //�������ڵ������������Ϊͼ�����
	        frame.setContentPane(chartPanel);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
		}
}
