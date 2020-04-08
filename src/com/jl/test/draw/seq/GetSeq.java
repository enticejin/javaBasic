package com.jl.test.draw.seq;


import java.util.List;
import java.util.Map;

import com.jl.test.draw.utils.SeqUtil;

/** 
* @version ����ʱ�䣺2020��4��7�� ����10:52:45
* ��˵�� 
*/
public class GetSeq {
	public static void main(String[] args) {
		SeqUtil seqUtil = new SeqUtil();
		String filePath = "D:/work/pointinfo_solve.csv";
		int seqStart = 1694337;
		int seqEnd = seqStart + 50;
		List<String> seqListArea = seqUtil.getColSeq(filePath, seqStart, seqEnd);
		List<String> seqList = seqUtil.getCol_3(seqListArea);
		Map<String, double[]> map = seqUtil.getXYData(seqList);
		double[] xList = map.get("xList");
		double[] yList = map.get("yList");
		double[] zList = map.get("zList");
		for(int i = 0;i < xList.length;i++) {
			System.out.println("x = "+ xList[i]);
			System.out.println("y = "+ yList[i]);
			System.out.println("z = "+ zList[i]);
		}
		//System.out.println(seqListArea);
	}
}
