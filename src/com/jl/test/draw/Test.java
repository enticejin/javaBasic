package com.jl.test.draw;

import java.util.Arrays;

/** 
* @version 创建时间：2020年3月26日 下午2:57:02
* 类说明 
*/
public class Test {
	public static void main(String[] args) {
		double y_Data[]={10,20,30,40,50,100,200,300,400,500,550,600};//y坐标
        double x_Data[]={3.4,5.2,6.8,1.5,1,1,2,3,4,5,6,7};//x坐标
        Arrays.sort(y_Data);
        Arrays.sort(x_Data);
        double x_max = getMax(x_Data);
        double y_max = getMax(y_Data);
        double x_min = getMin(x_Data);
        double y_min = getMin(x_Data);
        double d1 = getSus(x_max, x_min);
        double d2 = getSus(y_max, y_min);
        double x_ave = getAve(x_max, x_min);
        double y_ave = getAve(y_max, y_min);
//        getAve(y_max, y_min);
//        System.out.println("d2 = "+d2);
//        System.out.println("d1 = "+d1);
//        System.out.println("取模运算：" +  d2 % d1);
//        System.out.println("x的平均值是："+x_ave);
//        System.out.println("y的平均值是："+y_ave);
//        System.out.println(x_Data.length);
       double d[] = arraysub(x_Data, getRound(getRound(x_Data.length)), getRound(x_Data.length)+getRound(getRound(x_Data.length)));
       for(double dx : d) {
    	   System.out.println("d1 = "+dx);
       }
	}
	/**
	 * 数组中找出最大的元素
	 * @param d 需要求最大值的数组
	 * @return
	 */
	public static  double getMax(double[] d) {
		double maxDouble= d[0];
		//遍历数组
		for(int i =0;i < d.length; i++) {
			if(maxDouble < d[i]) {
				maxDouble = d[i];
			}
		}
		return maxDouble;
	}
	/**
	 * 数组中找出最小的元素
	 * @param d 需要求最小值的数组
	 * @return
	 */
	public static double getMin(double[] d) {
		double minDouble = d[0];
		//遍历数组
		for(int i = 0;i < d.length;i++) {
			if(minDouble > d[i]) {
				minDouble = d[i];
			}
		}
		return minDouble;
	}
	/**
	 * 取商
	 * @param d1 参数1
	 * @param d2 参数2
	 * @return
	 */
	public static double getQuotient(double d1, double d2) {
		if(d2 > d1) {
			return d2 / d1;
		}
		else
		return d1 / d2;
	}
	/**
	 * 取差值
	 * @param d1 参数1
	 * @param d2 参数2
	 * @return
	 */
	public static double getSus(double d1, double d2) {
		if(d2 > d1) {
			return d2 - d1;
		}
		else
			return d1 - d2;
	}
	/**
	 * 去两个参数的平均值
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double getAve(double d1, double d2) {
		return (d2+d1)/2;
	}
	/**
	 * 四舍五入
	 * @param number
	 * @return
	 */
	public static int getRound(int number) {
		return Math.round(number/2);
	}
	
	/**
	 * 截取数组
	 * @param a 数组
	 * @param start 起始位
	 * @param end 终点位
	 * @return
	 */
	public static double[] arraysub(double[]a,int start,int end){
		if(a==null){
	throw new IllegalArgumentException("a  must be not null.");
		}
		if(start<0||end<0){
	throw new IllegalArgumentException("start or end must be >0.");
		}
		if(start>=a.length){
	 throw new IllegalArgumentException("start must be <a.leng.");
		}
		if(start>end){
		throw new IllegalArgumentException("start must be <end.");
		}
		int count = end-start;
		if(count>a.length){
		throw new IllegalArgumentException("count must be <=a.length.");
		}
		double[] b = new double[count];
		for(int i = start;i<end;i++){
			b[i-start]=a[i];
		}
		return b;
		}
}
