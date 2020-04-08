package com.jl.test.draw.math;

import java.awt.*;
import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;


import com.jl.test.draw.utils.ChartUtilOpt;
import com.jl.test.draw.utils.SeqUtil;

/** 
* @version ����ʱ�䣺2020��4��8�� ����9:50:07
* ��˵�� 
*/
public class MathTest{
	public static void main(String[] args) {
		SeqUtil seq = new SeqUtil();
		ChartUtilOpt chart = new ChartUtilOpt();
		String filePath = "D:/work/pointinfo_solve.csv";
		Map<String, double[]> map = chart.getXYData(filePath);
		
		double[] xList = map.get("xList");
		double[] yList = map.get("yList");
		int[] xArray = new int[xList.length];
		double[] y_newList = new double[xList.length];
		int[] yArray = new int[xList.length];
		for(int i = 0 ;i < xList.length; i++) {
			y_newList[i] = seq.getFunctionX(xList[i]);
			xArray[i] = (int)(xList[i] * 10);
			yArray[i] = (int)(y_newList[i] * 10);
			System.out.println(xList[i] + " ��Ӧ�����ɵ�y����ֵΪ��  "+ y_newList[i]+"  ---- ԭʼ����Ϊ�� "+yList[i]);
		}
	}
	/**
	 * ����x�����ȡ���κ���ֵ
	 * @param xp x����
	 * @return
	 */
	public static double getFunctionX(double xp) {
		double a = -0.03275, b = - 0.1466, c= -0.8099;
		return xp * xp * a + b * xp + c;
	}
}
 