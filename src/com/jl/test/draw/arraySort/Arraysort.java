package com.jl.test.draw.arraySort;

import java.util.Arrays;
import java.util.Comparator;
/**
 * ��������
 * @author Administrator
 *
 */
public class Arraysort {
    public Point[] arr;
    
    public Arraysort(){
        arr=new Point[4];    //�����������arr��������洢�Ŀռ�
        for(int i=0;i<4;i++)
            arr[i]=new Point();
    }
    public Arraysort(int length){
    	arr=new Point[length];    //�����������arr��������洢�Ŀռ�
    	for(int i=0;i<length;i++)
    		arr[i]=new Point();
    }
    
    public static void main(String[] args) {
        
        Arraysort sort=new Arraysort();
        sort.arr[0].x=2;sort.arr[0].y=1;    //��ʼ�������������е�����
        sort.arr[1].x=2;sort.arr[1].y=2;
        sort.arr[2].x=1;sort.arr[2].y=2;
        sort.arr[3].x=0;sort.arr[3].y=1;
  
        Arrays.sort(sort.arr, new MyComprator());    //ʹ��ָ��������������������
        for(int i=0;i<4;i++)    //���������
            System.out.println("("+sort.arr[i].x+","+sort.arr[i].y+")");
    }
}

class Point{
    int x;
    int y;
}

//�Ƚ�����x�����С��������x��ͬʱ������y��С��������
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
