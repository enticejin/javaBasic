package com.jl.test.draw.simpleGobang;

import java.util.ArrayList;
import java.util.List;

import com.jl.test.draw.DeleteString;
import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;

/** 
* @version 创建时间：2020年3月31日 下午2:27:14
* 类说明 
*/
public class ChartTest_03 {
	public static void main(String[] args) {
				// 初始化
				ChartUtilOpt chartOpt = new ChartUtilOpt();
				ChartUtil chart = new ChartUtil();
				// 读取文件
				List<String> csvColList = chartOpt.getCol("D:/work/pointinfo_solve.csv");
				List<String> XListStr = new ArrayList<String>();
				List<String> YListStr = new ArrayList<String>();
				List<String> ZListStr = new ArrayList<String>();
				for(int i = 0;i < csvColList.size();i++) {
					XListStr.add(csvColList.get(i).split("\\|")[0]);
					YListStr.add(csvColList.get(i).split("\\|")[1]);
					ZListStr.add(csvColList.get(i).split("\\|")[2]);
				}
				double[] xList =  chartOpt.getArrayByStrList(XListStr);
				double[] yList =  chartOpt.getArrayByStrList(YListStr);
				double[] zList =  chartOpt.getArrayByStrList(ZListStr);
				List<String> XListStrArea = new ArrayList<String>();
				List<String> YListStrArea = new ArrayList<String>();
				for(int i =0;i < XListStr.size();i++) {
					if(Double.parseDouble(XListStr.get(i).toString()) < -11 && 
							Double.parseDouble(XListStr.get(i).toString()) > -12 && 
							Double.parseDouble(YListStr.get(i).toString()) < -2 && 
							Double.parseDouble(YListStr.get(i).toString()) > -5) {
						XListStrArea.add(XListStr.get(i));
						YListStrArea.add(YListStr.get(i));
					}
				}
				//ymin=-3.62 ymax=-3.34  xmin=-11.85  xmax=-11.5
				double[] xListDou = chartOpt.getArrayByStrList(XListStrArea);
				double[] yListDou = chartOpt.getArrayByStrList(YListStrArea);
				//chart.getFreeChart("re-test", "y", "x", yList, xList);
				chart.getFreeChart("re-test", "y", "x", yListDou, xListDou);
				
				
	}
}
