package com.jl.test.draw.simpleGobang;

import java.util.ArrayList;
import java.util.List;

import com.jl.test.draw.DeleteString;
import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;

/** 
* @version ����ʱ�䣺2020��3��30�� ����10:39:09
* ��˵�� 
*/
public class ChartTest_02 {
	public static void main(String[] args) {
		// ��ʼ��
		ChartUtilOpt chartOpt = new ChartUtilOpt();
		ChartUtil chart = new ChartUtil();
		// ��ȡ�ļ�
		List<String> csvColList = chartOpt.getCol("D:/pointinfo_solve.csv");
		DeleteString delete = new DeleteString();
		// �и��ַ���
		String splitZ1 = "|1.0, ";
		Object[] result = delete.deleteSubString(csvColList.toString(), splitZ1);
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
		System.out.println("XListStr"+XListStr.toString());
		System.out.println("YListStr"+YListStr.toString());
		System.out.println(YListStr.size() == XListStr.size());
		// �ַ���ת����double����
		double[] xList = chartOpt.getArrayByStrList(XListStr);
		double[] yList = chartOpt.getArrayByStrList(YListStr);

		for (int i = 0; i < xList.length; i++) {
			xList[i] = 0 - xList[i];
			yList[i] = 0 - yList[i];
		}
		double Ymax = chartOpt.getMax(yList);
		double Ymin = chartOpt.getMin(yList);
		double Xmax = chartOpt.getMax(xList);
		double Xmin = chartOpt.getMin(xList);
		double Ymiddle = (Ymax + Ymin)/2;
		double xmiddle = (Xmax + Xmin)/2;
		List<Double> XlistTestMiddle = new ArrayList<Double>();
		List<Double> YlistTestMiddle = new ArrayList<Double>();
		for(int i =0;i < xList.length;i++) {
			if(xList[i] > xmiddle) {
				XlistTestMiddle.add(xList[i]);
				YlistTestMiddle.add(yList[i]);
			}
		}
		double[] xlistTest = chartOpt.getArrayByList(XlistTestMiddle);
		double[] ylistTest = chartOpt.getArrayByList(YlistTestMiddle);
		//chart.getFreeChart("re-test", "y", "x", yList, xList);
		chart.getFreeChart("re-test", "y", "x", ylistTest, xlistTest);
	}
}
