package com.jl.test.draw.seq;
import javax.swing.*;

import com.jl.test.draw.arraySort.Arraysort;
import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;
import com.jl.test.draw.utils.SeqUtil;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator
 *指定区间取值放大
 */
class ChartTestSeq01 extends JPanel
{
	ChartUtilOpt chartOpt = new ChartUtilOpt();
    Polygon po = new Polygon();
    //设置字体及大小
    Font fn = new Font("宋体", Font.BOLD, 22);
    Font fn2 = new Font("宋体", Font.BOLD, 20);
    public ChartTestSeq01()
    {
    	//获取当前屏幕的宽和高
        Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();
        setSize(width, height);
    }
    public void paint(Graphics g)
    {
    	SeqUtil seq = new SeqUtil();
		ChartUtilOpt chart = new ChartUtilOpt();
		String filePath = "D:/work/pointinfo_solve.csv";
		Map<String, double[]> map = chart.getXYData(filePath);
		
		double[] xList = map.get("xList");
		int[] xArray = new int[xList.length];
		double[] y_newList = new double[xList.length];
		int[] yArray = new int[xList.length];
		for(int i = 0 ;i < xList.length; i++) {
			y_newList[i] = seq.getFunctionX(xList[i]);
		}
        int x0 = 500;
        int y0 = 200;
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //设置背景色
        g2d.setColor(Color.black);
        //x坐标线
        g2d.fillRect(0, y0, x0*4, 2);
        //y坐标线
        g2d.fillRect(x0, y0, 2, y0*4);
        //对应的坐标比例
        //像素与比例1:10
        int xSize = 4;
        int ySize = 4;
        g2d.drawString("0", x0, y0-15);
      //横坐标刻度
        for(int i = 0;i < y0/2;i++) {
        	if(i % xSize == 0 && i / xSize != 0) {
        		g2d.drawString("-"+String.valueOf(i / xSize), x0-i*10*(xSize / 4), y0-15);
        		g2d.drawString(String.valueOf(i / xSize), x0+i*10*(ySize / 4), y0-15);
        	}
        }
      //纵坐标刻度
        for(int i =0;i < y0/2;i++) {
        	if (i % ySize == 0 && i / ySize != 0)
        	{
        		g2d.drawString("-"+String.valueOf(i / ySize), x0-20, y0+i*10*(ySize / 4));
        	}
        }
        int x = 0;
        int y = 0;
        //画圆的直径
        int size = 10;
        Map<String, double[]> map1 = chartOpt.getAreaXY(-20,20, -20, 20, xList, y_newList);
        double[] xArr = map1.get("xArray");
        double[] yArr =  map1.get("yArray");
        double xMin= chartOpt.getMin(xArr);
        double yMin= chartOpt.getMin(yArr);
        double[] xDouble = new double[2];
        double[] yDouble = new double[2];
        //找出对应的坐标值
        for(int i =0;i < xArr.length;i++) {
        	if(xMin == xArr[i]) {
        		xDouble[0] = xMin;
        		xDouble[1] = yArr[i];
        	}
        	if(yMin == yArr[i]) {
        		yDouble[0] = xArr[i];
        		yDouble[1] = yMin;
        	}
        }
        //将double类型转换成int类型
        int[] x_Array = new int[xArr.length];
        int[] y_Array = new int[yArr.length];
        for(int i = 0;i < xArr.length;i++) {
        	x_Array[i] = (int) 	(x0+xArr[i]*xSize * 10);
        	y_Array[i] = (int)  (y0-yArr[i]*ySize * 10);
        }
        //画出曲线
        QuadCurve2D q1 = new QuadCurve2D.Float();
        for(int i = 0;i < xArr.length;i++) {
        	//在图上描出对应坐标
    		x = (int) (x0+xArr[i]*xSize * 10);
			y = (int) (y0-yArr[i]*ySize * 10);
			for(int j =0;j < size;j++) {
				//颜色比例1:25,255-(j * 25)
				g2d.setColor(getColor(j));
				if(x != x0 && y!=y0) {
					g2d.drawOval(x+j, y+j, size-10*j, size-10*j); 
				}
			}
        }
        
        g2d.setColor(Color.BLUE);
		q1.setCurve(x0-20*10, y0+10.9779 * 10,
				x0,y0-0.8099*10,
				x0+20*10,y0+16.8419*10
				);
		/*
		for(int i =0 ;i < xArr.length;i++) {
			System.out.println("x = "+xArr[i]+" ----- y = " + yArr[i]);
		}
		*/
		g2d.draw(q1);
		//字体
        g2d.setFont(fn2);
        g2d.setFont(fn);
        g2d.setColor(Color.black);
        //在页面上写出X，Y
        g2d.drawString("X", x0-400, y0+25);
        g2d.drawString("Y", x0-30, y0+400);
        
    }
    //设置各层圈颜色
    /**
     * 获取要设置的颜色值
     * @param num
     * @return
     */
    Color getColor(int num)
    {
    	if(num > 10) {
    		num = 10;
    	}
    	return new Color(
    			(int)(255-(num*25)),
    			0,
    			0
    			);
    }
    
    
    public static void main(String[] args)
    {
    	//获取当前屏幕的宽和高
        Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();
        JFrame jf = new JFrame();
        //设置窗口大小
        jf.setSize(1000, 1000);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(3);
        jf.getContentPane().add(new ChartTestSeq01());
        
        
    }
}