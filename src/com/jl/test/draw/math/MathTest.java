package com.jl.test.draw.math;

import java.awt.*;
import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;


import com.jl.test.draw.utils.ChartUtilOpt;
import com.jl.test.draw.utils.SeqUtil;

/** 
* @version 创建时间：2020年4月8日 上午9:50:07
* 类说明 
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
			System.out.println(xList[i] + " 对应新生成的y坐标值为：  "+ y_newList[i]+"  ---- 原始坐标为： "+yList[i]);
		}
	}
	/**
	 * 输入x坐标获取二次函数值
	 * @param xp x坐标
	 * @return
	 */
	public static double getFunctionX(double xp) {
		double a = -0.03275, b = - 0.1466, c= -0.8099;
		return xp * xp * a + b * xp + c;
	}
}
 