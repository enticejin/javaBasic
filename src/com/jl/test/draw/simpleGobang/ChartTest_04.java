package com.jl.test.draw.simpleGobang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jl.test.draw.DeleteString;
import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;
import com.jl.test.draw.utils.SeqUtil;

/** 
* @version 创建时间：2020年3月31日 下午2:27:14
* 类说明 
*/
public class ChartTest_04 {
	public static void main(String[] args) {
				//获取数据
		    	String filePath = "D:/work/pointinfo_solve.csv";
		    	SeqUtil seqUtil = new SeqUtil();
		    	//标签接收的频率200ms，取10s内的数据
		    	int num = 1000/200 * 10;
		    	//起始位置
				int seqStart = 1694385;
				//现在位置
				int seqNow = seqStart + num / 2;
				//终止位置
				int seqEnd = seqStart + num;
				//去除seq指定区间的数据
				List<String> seqListArea = seqUtil.getColSeq(filePath, seqStart, seqEnd);
				//根据取出的数据得到seq列表
				List<String> seqList = seqUtil.getCol_3(seqListArea);
				Map<String, double[]> map = seqUtil.getXYData(seqList);
				double[] xList = map.get("xList");
				double[] yList = map.get("yList");
				// 初始化
				ChartUtil chart = new ChartUtil();
				chart.getFreeChart("re-test", "y", "x", yList, xList);
				
				
	}
}
