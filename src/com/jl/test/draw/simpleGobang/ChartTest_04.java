package com.jl.test.draw.simpleGobang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jl.test.draw.DeleteString;
import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;
import com.jl.test.draw.utils.SeqUtil;

/** 
* @version ����ʱ�䣺2020��3��31�� ����2:27:14
* ��˵�� 
*/
public class ChartTest_04 {
	public static void main(String[] args) {
				//��ȡ����
		    	String filePath = "D:/work/pointinfo_solve.csv";
		    	SeqUtil seqUtil = new SeqUtil();
		    	//��ǩ���յ�Ƶ��200ms��ȡ10s�ڵ�����
		    	int num = 1000/200 * 10;
		    	//��ʼλ��
				int seqStart = 1694385;
				//����λ��
				int seqNow = seqStart + num / 2;
				//��ֹλ��
				int seqEnd = seqStart + num;
				//ȥ��seqָ�����������
				List<String> seqListArea = seqUtil.getColSeq(filePath, seqStart, seqEnd);
				//����ȡ�������ݵõ�seq�б�
				List<String> seqList = seqUtil.getCol_3(seqListArea);
				Map<String, double[]> map = seqUtil.getXYData(seqList);
				double[] xList = map.get("xList");
				double[] yList = map.get("yList");
				// ��ʼ��
				ChartUtil chart = new ChartUtil();
				chart.getFreeChart("re-test", "y", "x", yList, xList);
				
				
	}
}
