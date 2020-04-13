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
class ChartSeq extends JPanel
{
	Polygon po = new Polygon();
    //设置字体及大小
    Font fn = new Font("宋体", Font.BOLD, 22);
    Font fn2 = new Font("宋体", Font.BOLD, 20);
    public ChartSeq()
    {
        setSize(900, 900);
    }
    public void paint(Graphics g)
    {
    	//文件路径
    	String filePath = "D:/work/pointinfo_solve.csv";
        SeqUtil seqUtil = new SeqUtil();
        //获取seq所有序号
        List<Integer> seqAllList =  seqUtil.getAllColSeq(filePath);
        //最后的seq序号
        Integer finalSeqNo = seqAllList.get(seqAllList.size() - 1);
        //seq当前位置
		int seqNow = seqAllList.get(100);
		//seq起始位置
		int seqStart = seqNow - 50;
		//seq终止位置
		int seqEnd = seqNow + 50;
		List<String> seqListArea = seqUtil.getColSeq(filePath, seqStart, seqEnd);
		//获取seq起始位置到终止位置的坐标
		List<String> seqAreaList = seqUtil.getCol_3(seqListArea);
		Map<String, double[]> map = seqUtil.getXYData(seqAreaList);
		double[] xList = map.get("xList");
		double[] yList = map.get("yList");
		
        int x0=getSize().width/2;
		int y0=getSize().height/2;//坐标原点 
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //设置背景色
        g2d.setColor(Color.black);
        //x坐标线
        g2d.fillRect(0, y0, x0*2, 2);
        //y坐标线
        g2d.fillRect(x0, 0, 2, y0*2);
        int num = 2;
        g2d.drawString("0", x0, y0);
      //纵坐标刻度
        //一个刻度10个像素点
        for(int i =0;i < 40;i++) {
        	if (i % 2 == 0 && i / 2 != 0)
        	{
        		//纵坐标刻度
        		g2d.drawString("-"+String.valueOf(i / num), x0-20, y0+i*20);
        		g2d.drawString(String.valueOf(i / num), x0-20, y0-i*20);
        	}
        }
      //横坐标刻度
        //一个刻度10个像素点
        for(int i = 0;i < 60;i++) {
        	if(i % 2 == 0 && i / 2 != 0) {
        		g2d.drawString("-"+String.valueOf(i / num), x0-i*20, y0-15);
        		g2d.drawString(String.valueOf(i / num), x0+i*20, y0-15);
        	}
        }
        int x = 0;
        int y = 0;
        //画圆的直径
        int d = 10;
        //遍历数组画出坐标
        for(int i = 0;i < xList.length;i++) {
        	x = (int) (x0+xList[i]*40);
			y= (int) (y0-yList[i]*40);
			
			if(i == xList.length / 2) {
				g2d.setColor(Color.BLUE);
				g2d.drawOval((int)((x0+xList[i]*40)), (int)(y0-yList[i]*40), 20, 20);
			}
			//x < 0, y < 0
			for(int j =0;j < d;j++) {
				g2d.setColor(getColor(j));
				if(x != x0 && y != y0) {
					g2d.drawOval(x+j, y+j, d-10*j, d-10*j);
				}
			}
        }
        g2d.setFont(fn2);
        g2d.setColor(Color.white);
        g2d.drawString("", 102, 700);
        g2d.setFont(fn);
        g2d.setColor(Color.black);
        //在页面上写出X，Y
        g2d.drawString("X", x0+900, y0+25);
        g2d.drawString("Y", x0-30, y0-400);
    }
    
    //设置各层圈颜色
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
        jf.getContentPane().add(new ChartSeq());
    }
}