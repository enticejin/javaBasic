package com.jl.java.draw.utils;

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
 * 类说明  seq工具类
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
     * 读取列表seq序号区域的内容
     * @param
     * @return
     */
    public List<String> getColSeq(List<String> list, int seqNow, int seqEnd){
        //每一行seq的信息
        List<String> allCSVSeq = new ArrayList<String>();
        for(int i = 0 ; i < list.size(); i++) {
            //遍历添加seq的序号
            int SeqNo = Integer.parseInt(list.get(i).split(",")[1].trim());
            if(SeqNo >= seqNow && SeqNo <= seqEnd) {
                allCSVSeq.add(list.get(i).trim());
            }
        }

        return allCSVSeq;
    }
    /**
     * 读取文件seq序号
     * @param filePath 文件路径
     * @return
     */
    public List<Integer> getAllColSeq(String filePath){
        List<String> allCSV =getAllCSV(filePath);
        //每一行seq的信息
        List<Integer> allCSVSeq = new ArrayList<Integer>();
        for(int i = 0 ; i < allCSV.size(); i++) {
            //遍历添加seq的序号
            allCSVSeq.add(Integer.parseInt(allCSV.get(i).split(",")[1].trim()));
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
     * 读取文件中坐标的数据
     * @param filePath 文件路径
     * @return
     */
    public Map<String, double[]> getXYData(String filePath){
        // 初始化
        ChartUtilOpt chartOpt = new ChartUtilOpt();
        Map<String, double[]> map = new HashMap<String, double[]>();
        // 读取文件
        List<String> csvColList = chartOpt.getCol(filePath);
        //获取数据
        List<String> YListStr = new ArrayList<String>();
        List<String> XListStr = new ArrayList<String>();
        List<String> ZListStr = new ArrayList<String>();
        for(int i = 0;i < csvColList.size();i++) {
            XListStr.add(csvColList.get(i).split("\\|")[0]);
            YListStr.add(csvColList.get(i).split("\\|")[1]);
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
     * 将String列表转化成double数组
     * @param list 字符串数组
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

    private double a = -0.03275, b = - 0.1466, c= -0.8099;
    /**
     * 输入x坐标获取二次函数值
     * @param xp x坐标
     * @return
     */
    public double getFunctionX(double xp) {
        return xp * xp * a + b * xp + c;
    }
    /**
     * 输入x坐标数组获取二次函数值
     * @param xp x坐标
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
     * 优化数组
     * @param d double类型数组
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
     * @param x0 x原点坐标
     * @param y0 y原点坐标
     * @param size 倍数
     * @param dx x数组
     * @param dy y数组
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
     * 获取seq起始，现在，和终止位置
     * @param index 索引位置
     * @param filePath 文件路径
     * @return
     */
    public Map<String, Object> getSeqPosition(int index, String filePath){
        Map<String, Object> map = new HashMap<String, Object>();
        SeqUtil seqUtil = new SeqUtil();
        // 获取seq所有序号
        List<Integer> seqAllList = seqUtil.getAllColSeq(filePath);
        // 最后的seq序号
        Integer finalSeqNo = seqAllList.get(seqAllList.size() - 1);
        // seq起始位置
        int seqStart = seqAllList.get(index);
        // seq当前位置
        int seqNow = seqStart + 50;
        // seq终止位置
        int seqEnd = seqNow + 50;


        map.put("seqStart", seqStart);
        map.put("seqNow", seqNow);
        map.put("seqEnd", seqEnd);
        map.put("finalSeqNo", finalSeqNo);
        return map;
    }
    /**
     * 获取seq起始，现在，和终止位置
     * @param index 索引位置
     * @param filePath 文件路径
     * @param num 数据包的数量
     * @return
     */
    public Map<String, Object> getSeqPosition(int index, String filePath, int num){
        Map<String, Object> map = new HashMap<String, Object>();
        SeqUtil seqUtil = new SeqUtil();
        // 获取seq所有序号
        List<Integer> seqAllList = seqUtil.getAllColSeq(filePath);
        // 最后的seq序号
        Integer finalSeqNo = seqAllList.get(seqAllList.size() - 1);
        // seq起始位置
        int seqStart = seqAllList.get(index);
        // seq当前位置
        int seqNow = seqStart + num;
        // seq终止位置
        int seqEnd = seqNow + num;


        map.put("seqStart", seqStart);
        map.put("seqNow", seqNow);
        map.put("seqEnd", seqEnd);
        map.put("finalSeqNo", finalSeqNo);
        return map;
    }
}
