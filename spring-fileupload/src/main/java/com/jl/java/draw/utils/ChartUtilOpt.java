package com.jl.java.draw.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
public class ChartUtilOpt {

	public double getMax(double[] d) {
		double maxDouble = d[0];
		for (int i = 0; i < d.length; i++) {
			if (maxDouble < d[i]) {
				maxDouble = d[i];
			}
		}
		return maxDouble;
	}
		public double getMin(double[] d) {
			double minDouble = d[0];
			for(int i = 0;i < d.length;i++) {
				if(minDouble > d[i]) {
					minDouble = d[i];
				}
			}
			return minDouble;
		}

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

	public List<String> getAllCSV(String filePath) {
			File csv = new File(
					filePath);
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
				while ((line = br.readLine()) != null)
				{
					everyLine = line;
					allString.add(everyLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(allString);
			return allString;
		}
		public List<String> getCol(String filePath){
			List<String> allCSV =getAllCSV(filePath);
			List<String> allCSVIndex = new ArrayList<String>();
			for(int i = 0 ; i < allCSV.size(); i++) {
				allCSVIndex.add(allCSV.get(i).split(",")[3]);
			}
			return allCSVIndex;
		}
		public Map<String, double[]> getAreaXY(double x1,double x2, double y1, double y2,double[] xArr, double[] yArr) {
			List<Double> xlist = new ArrayList<Double>();
			List<Double> ylist = new ArrayList<Double>();
			if(xArr.length == yArr.length && x1 != x2 && y1 != y2) {

				Map<String, double[]> map = new HashMap<String, double[]>();
				for(int i = 0;i < yArr.length; i++) {
					if(yArr[i] >= y1 && yArr[i] <= y2 && xArr[i] >= x1 && xArr[i] <= x2) {
						xlist.add(xArr[i]);
						ylist.add(yArr[i]);
					}
				}
				map.put("yArray", getArrayByList(ylist));
				map.put("xArray", getArrayByList(xlist));
				return map;
			}else {
				return null;
			}
		}
		
}
