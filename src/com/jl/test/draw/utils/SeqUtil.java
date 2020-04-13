package com.jl.test.draw.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @version ����ʱ�䣺2020��4��7�� ����10:53:10
* ��˵�� 
*/
public class SeqUtil {
	/**
	 * ��ȡ.csv�ļ�����
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
	 * ��ȡ�ļ�����seq���������
	 * @param filePath �ļ�·��
	 * @return
	 */
	public List<String> getColSeq(String filePath, int seqStart, int seqEnd){
		List<String> allCSV =getAllCSV(filePath);
		//ÿһ��seq����Ϣ
		List<String> allCSVSeq = new ArrayList<String>();
		for(int i = 0 ; i < allCSV.size(); i++) {
			//�������seq�����
			int SeqNo = Integer.parseInt(allCSV.get(i).split(",")[1].trim());
			if(SeqNo >= seqStart && SeqNo <= seqEnd) {
				allCSVSeq.add(allCSV.get(i).trim());
			}
		}
		
		return allCSVSeq;
	}
	/**
	 * ��ȡ�б�seq������������
	 * @param 
	 * @return
	 */
	public List<String> getColSeq(List<String> list, int seqNow, int seqEnd){
		//ÿһ��seq����Ϣ
		List<String> allCSVSeq = new ArrayList<String>();
		for(int i = 0 ; i < list.size(); i++) {
			//�������seq�����
			int SeqNo = Integer.parseInt(list.get(i).split(",")[1].trim());
			if(SeqNo >= seqNow && SeqNo <= seqEnd) {
				allCSVSeq.add(list.get(i).trim());
			}
		}
		
		return allCSVSeq;
	}
	/**
	 * ��ȡ�ļ�seq���
	 * @param filePath �ļ�·��
	 * @return
	 */
	public List<Integer> getAllColSeq(String filePath){
		List<String> allCSV =getAllCSV(filePath);
		//ÿһ��seq����Ϣ
		List<Integer> allCSVSeq = new ArrayList<Integer>();
		for(int i = 0 ; i < allCSV.size(); i++) {
			//�������seq�����
			allCSVSeq.add(Integer.parseInt(allCSV.get(i).split(",")[1].trim()));
		}
		
		return allCSVSeq;
	}
	/**
	 * ��ȡ�ļ�����seq��������ݵ�x��y����
	 * @param list seq�����б�
	 * @return
	 */
	public Map<String, double[]> getXYData(List<String> list){
		Map<String, double[]> map = new HashMap<String, double[]>();
		List<String> XListStr = new ArrayList<String>();
		List<String> YListStr = new ArrayList<String>();
		List<String> ZListStr = new ArrayList<String>();
		//�����б�
		for(int i =0;i < list.size();i++) {
			XListStr.add(list.get(i).split("\\|")[0]);
			YListStr.add(list.get(i).split("\\|")[1]);
			ZListStr.add(list.get(i).split("\\|")[2]);
		}
		double[] xList = getDoubleArrayByStrList(XListStr);
		double[] yList = getDoubleArrayByStrList(YListStr);
		double[] zList = getDoubleArrayByStrList(ZListStr);
		map.put("xList", xList);
		map.put("yList", yList);
		map.put("zList", zList);
		return map;
	}
	/**
     * ��ȡ�ļ������������
     * @param filePath �ļ�·��
     * @return
     */
    public Map<String, double[]> getXYData(String filePath){
    	// ��ʼ��
    	ChartUtilOpt chartOpt = new ChartUtilOpt();
    	Map<String, double[]> map = new HashMap<String, double[]>();
    	// ��ȡ�ļ�
    	List<String> csvColList = chartOpt.getCol(filePath);
    	//��ȡ����
    	List<String> YListStr = new ArrayList<String>();
    	List<String> XListStr = new ArrayList<String>();
    	List<String> ZListStr = new ArrayList<String>();
    	for(int i = 0;i < csvColList.size();i++) {
    		YListStr.add(csvColList.get(i).split("\\|")[1]);
    		XListStr.add(csvColList.get(i).split("\\|")[0]);
    		ZListStr.add(csvColList.get(i).split("\\|")[2]);
    	}
    	double[] yList =  chartOpt.getArrayByStrList(YListStr);
    	double[] xList =  chartOpt.getArrayByStrList(XListStr);
    	double[] zList =  chartOpt.getArrayByStrList(ZListStr);
    	map.put("xList", xList);
    	map.put("yList", yList);
    	map.put("zList", zList);
    	return map;
    	
    }
	/**
	 * ��String�б�ת����double����
	 * @param list �ַ�������
	 * @return
	 */
	public double[] getDoubleArrayByStrList(List<String> list) {
		double[] d1 = new double[list.size()];
		if(list != null) {
			list= list.subList(0,  list.size());
			Object[]  obj = list.toArray();
			for(int i = 0;i < obj.length;i++ ) {
				d1[i] = Double.parseDouble(obj[i].toString());
			}
		}
		return d1;
	}
	/**
	 * ��ȡ�б���D�е�����
	 * @param list
	 * @return
	 */
	 public List<String> getCol_3(List<String> list){
		 List<String> xyzList = new ArrayList<String>();
		 for(int i = 0; i < list.size();i++) {
			 xyzList.add(list.get(i).split(",")[3].trim());
		 }
		 return xyzList;
	 }

	 	private double a = -0.03275, b = - 0.1466, c= -0.8099;
	 /**
		 * ����x�����ȡ���κ���ֵ
		 * @param xp x����
		 * @return
		 */
		public double getFunctionX(double xp) {
			return xp * xp * a + b * xp + c;
		}
		/**
		 * ����x���������ȡ���κ���ֵ
		 * @param xp x����
		 * @return
		 */
		public double[] getFunctionX(double[] xArray) {
			double[] y_newList = new double[xArray.length];
			for(int i =0 ; i < xArray.length;i++) {
				y_newList[i] = xArray[i] * xArray[i] * a + b * xArray[i] + c;
			}
			return y_newList;
		}
		/**
		 * �Ż�����
		 * @param d double��������
		 * @return
		 */
		public double[] getArrayOpt(double[] d) {
			int index = d.length;
			double[] d1 = new double[index];
			for(int i = 0 ;i < index - 1; i++) {
				if(i == index -1) {
					d1[i] = (d[i] + d[i-1]) / 2;
				}
				if((d[i] < 0 && d[i+1] >0) || (d[i] > 0 && d[i+1] < 0)) {
					d1[i] = (d[i] - d[i-1]) / 2;
				}
				d1[i] = (d[i] + d[i+1])/2;
			}
			return d1;
		}
		/**
		 * 
		 * @param x0 xԭ������
		 * @param y0 yԭ������
		 * @param size ����
		 * @param dx x����
		 * @param dy y����
		 * @return
		 */
		public Map<String , List<Integer>> getIntList(int x0, int y0, int size,double[] dx, double[] dy){
			Map<String , List<Integer>> map = new HashMap<String, List<Integer>>();
			List<Integer> xList = new ArrayList<Integer>();
			List<Integer> yList = new ArrayList<Integer>();
			for(int i = 0;i < dx.length;i++) {
				xList.add((int)(x0 + dx[i] * size));
				yList.add((int)(y0 - dy[i] * size));
			}
			map.put("xList", xList);
			map.put("yList", yList);
			return map;
			
		}
		
		/**
		 * ��ȡseq��ʼ�����ڣ�����ֹλ��
		 * @param index ����λ��
		 * @param filePath �ļ�·��
		 * @return
		 */
		public Map<String, Object> getSeqPosition(int index, String filePath){
			Map<String, Object> map = new HashMap<String, Object>();
			SeqUtil seqUtil = new SeqUtil();
			// ��ȡseq�������
			List<Integer> seqAllList = seqUtil.getAllColSeq(filePath);
			// ����seq���
			Integer finalSeqNo = seqAllList.get(seqAllList.size() - 1);
			// seq��ʼλ��
			int seqStart = seqAllList.get(index);
			// seq��ǰλ��
			int seqNow = seqStart + 50;
			// seq��ֹλ��
			int seqEnd = seqNow + 50;
			
			
			map.put("seqStart", seqStart);
			map.put("seqNow", seqNow);
			map.put("seqEnd", seqEnd);
			map.put("finalSeqNo", finalSeqNo);
			return map;
		}
		/**
		 * ��ȡseq��ʼ�����ڣ�����ֹλ��
		 * @param index ����λ��
		 * @param filePath �ļ�·��
		 * @param num ���ݰ�������
		 * @return 
		 */
		public Map<String, Object> getSeqPosition(int index, String filePath, int num){
			Map<String, Object> map = new HashMap<String, Object>();
			SeqUtil seqUtil = new SeqUtil();
			// ��ȡseq�������
			List<Integer> seqAllList = seqUtil.getAllColSeq(filePath);
			// ����seq���
			Integer finalSeqNo = seqAllList.get(seqAllList.size() - 1);
			// seq��ʼλ��
			int seqStart = seqAllList.get(index);
			// seq��ǰλ��
			int seqNow = seqStart + num;
			// seq��ֹλ��
			int seqEnd = seqNow + num;
			
			
			map.put("seqStart", seqStart);
			map.put("seqNow", seqNow);
			map.put("seqEnd", seqEnd);
			map.put("finalSeqNo", finalSeqNo);
			return map;
		}
}
