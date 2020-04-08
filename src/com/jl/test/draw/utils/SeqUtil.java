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
* @version 创建时间：2020年4月7日 上午10:53:10
* 类说明 
*/
public class SeqUtil {
	/**
	 * 读取.csv文件数据
	 * @param filePath 文件路径
	 */
	public List<String> getAllCSV(String filePath) {
		File csv = new File(
				filePath); // CSV文件路径
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
			while ((line = br.readLine()) != null) // 读取到的内容给line变量
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
	 * 读取文件坐标seq区域的内容
	 * @param filePath 文件路径
	 * @return
	 */
	public List<String> getColSeq(String filePath, int seqStart, int seqEnd){
		List<String> allCSV =getAllCSV(filePath);
		//每一行seq的信息
		List<String> allCSVSeq = new ArrayList<String>();
		for(int i = 0 ; i < allCSV.size(); i++) {
			//遍历添加seq的序号
			int SeqNo = Integer.parseInt(allCSV.get(i).split(",")[1].trim());
			if(SeqNo >= seqStart && SeqNo <= seqEnd) {
				allCSVSeq.add(allCSV.get(i).trim());
			}
		}
		
		return allCSVSeq;
	}
	/**
	 * 读取文件坐标seq区域的内容的x，y坐标
	 * @param list seq区间列表
	 * @return
	 */
	public Map<String, double[]> getXYData(List<String> list){
		Map<String, double[]> map = new HashMap<String, double[]>();
		List<String> XListStr = new ArrayList<String>();
		List<String> YListStr = new ArrayList<String>();
		List<String> ZListStr = new ArrayList<String>();
		//遍历列表
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
	 * 将String列表转化成double数组
	 * @param list 字符串数组
	 * @return
	 */
	public double[] getDoubleArrayByStrList(List<String> list) {
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
	 * 获取列表中D列的内容
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
	 
	 /**
		 * 输入x坐标获取二次函数值
		 * @param xp x坐标
		 * @return
		 */
	 	private double a = -0.03275, b = - 0.1466, c= -0.8099;
		public double getFunctionX(double xp) {
			return xp * xp * a + b * xp + c;
		}
}
