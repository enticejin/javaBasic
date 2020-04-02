package com.jl.test.draw.arraySort;

import java.util.Arrays;
import java.util.Comparator;
/**
 * 数组排序
 * @author Administrator
 *
 */
public class Arraysort {
    public Point[] arr;
    
    public Arraysort(){
        arr=new Point[4];    //定义对象数组arr，并分配存储的空间
        for(int i=0;i<4;i++)
            arr[i]=new Point();
    }
    public Arraysort(int length){
    	arr=new Point[length];    //定义对象数组arr，并分配存储的空间
    	for(int i=0;i<length;i++)
    		arr[i]=new Point();
    }
    
    public static void main(String[] args) {
        
        Arraysort sort=new Arraysort();
        sort.arr[0].x=2;sort.arr[0].y=1;    //初始化，对象数组中的数据
        sort.arr[1].x=2;sort.arr[1].y=2;
        sort.arr[2].x=1;sort.arr[2].y=2;
        sort.arr[3].x=0;sort.arr[3].y=1;
  
        Arrays.sort(sort.arr, new MyComprator());    //使用指定的排序器，进行排序
        for(int i=0;i<4;i++)    //输出排序结果
            System.out.println("("+sort.arr[i].x+","+sort.arr[i].y+")");
    }
}

class Point{
    int x;
    int y;
}

//比较器，x坐标从小到大排序；x相同时，按照y从小到大排序
class MyComprator implements Comparator {
    public int compare(Object arg0, Object arg1) {
        Point t1=(Point)arg0;
        Point t2=(Point)arg1;
        if(t1.x != t2.x)
            return t1.x>t2.x? 1:-1;
        else
            return t1.y>t2.y? 1:-1;
    }
}
