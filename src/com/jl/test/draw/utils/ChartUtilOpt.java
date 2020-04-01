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
* @version ����ʱ�䣺2020��3��26�� ����2:47:23
* ��˵�� :�Ż������������λ�õĹ�����
*/
public class ChartUtilOpt {
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
					if(sub[i] > 0 && sub[i] < 1) {
						list.add(sub[i]);
					}else {
						list.add(0.005);
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
		
	/**
	 * 
	 * @param title  ����
	 * @param xTag   x����
	 * @param yTag   y����
	 * @param x_data x�����ʵ������ֵ
	 * @param y_data y�����ʵ������ֵ
	 */
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
	        chartPanel.setPreferredSize(new java.awt.Dimension((int)screensize.getWidth(),(int)screensize.getHeight()));
	        //����һ������������ʾ���
	        JFrame frame=new JFrame(title);
	        frame.setLocation(0,0);
	        frame.setSize((int)screensize.getWidth(),(int)screensize.getHeight());
	        //�������ڵ������������Ϊͼ�����
	        frame.setContentPane(chartPanel);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
		}
		/**
		 * ��������
		 * @param number
		 * @return
		 */
		public int getRound(int number) {
			return Math.round(number/2);
		}
		
		/**
		 * ��ȡ����
		 * @param a ����
		 * @param start ��ʼλ
		 * @param end �յ�λ
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
	 * �������ҳ�����Ԫ��
	 * 
	 * @param d ��Ҫ�����ֵ������
	 * @return
	 */
	public double getMax(double[] d) {
		double maxDouble = d[0];
		// ��������
		for (int i = 0; i < d.length; i++) {
			if (maxDouble < d[i]) {
				maxDouble = d[i];
			}
		}
		return maxDouble;
	}
		/**
		 * �������ҳ���С��Ԫ��
		 * @param d ��Ҫ����Сֵ������
		 * @return
		 */
		public double getMin(double[] d) {
			double minDouble = d[0];
			//��������
			for(int i = 0;i < d.length;i++) {
				if(minDouble > d[i]) {
					minDouble = d[i];
				}
			}
			return minDouble;
		}
		/**
		 * ȡ��
		 * @param d1 ����1
		 * @param d2 ����2
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
		 * ȡ��ֵ
		 * @param d1 ����1
		 * @param d2 ����2
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
		 * ����������ƽ��ֵ
		 * @param d1
		 * @param d2
		 * @return
		 */
		public  double getAve(double d1, double d2) {
			return (d2+d1)/2;
		}
		
		/**
		 * ��������
		 * @param d1 ����1
		 * @param d2 ����2
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
		 * ��Double�б�ת��������
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
		 * ��String�б�ת����double����
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
		   * �ַ���ɾ��ָ���ַ���
		   * @param str1 ԭ�ַ���
		   * @param str2 ָ�����ַ���
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
				//�����ڷ���-1
				obj[0] = -1;
				obj[1] = -1;
			}
			
			return obj;
		}
		/**
		 * ��ȡ.csv�ļ�����
		 * @param row ��
		 * @param col ��
		 * @param filePath �ļ�·��
		 */
		public List<String> getAllCSV(String filePath) {
			File csv = new File(
					filePath); // CSV�ļ�·��
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
				while ((line = br.readLine()) != null) // ��ȡ�������ݸ�line����
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
		 * ��ȡ�ļ������е�����
		 * @param filePath �ļ�·��
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
