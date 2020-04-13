package com.jl.test.draw.math;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jl.test.draw.utils.SeqUtil;

public class MyPanel extends JPanel {
	int x = 0, y = 400;
	Ball b;
	public MyPanel(Ball b) {
		this.b = b;
		b.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(b.flag == true) {
			//显示区域内所有的坐标点
			g.setColor(Color.RED);
			for(int i =0;i < b.xListScreen.size();i++) {
				g.drawOval(b.xListScreen.get(i), b.yListScreen.get(i), 10, 10);
				b.seqStart++;
			}
			//显示当前位置的坐标点
			g.setColor(Color.BLUE);
			g.drawOval(b.x, b.y, 10, 10);
		}else if(b.flag == false) {
			g.setColor(Color.BLUE);
			g.drawOval(b.x, b.y, 20, 20);
		}
		int x0 = 450;
		int y0 = 450;
		//设置背景色
	    g.setColor(Color.black);
	    //x坐标线
	    g.fillRect(0, y0, x0*2, 2);
	    //y坐标线
	    g.fillRect(x0, 0, 2, y0*2);
	    
	    int num = 2;
	  //纵坐标刻度
        //一个刻度10个像素点
        for(int i =0;i < 40;i++) {
        	if (i % 2 == 0 && i / 2 != 0)
        	{
        		//纵坐标刻度
        		g.drawString("-"+String.valueOf(i / num), x0-20, y0+i*10);
        		g.drawString(String.valueOf(i / num), x0-20, y0-i*10);
        	}
        }
      //横坐标刻度
        //一个刻度10个像素点
        for(int i = 0;i < 60;i++) {
        	if(i % 2 == 0 && i / 2 != 0) {
        		g.drawString("-"+String.valueOf(i / num), x0-i*10, y0-15);
        		g.drawString(String.valueOf(i / num), x0+i*10, y0-15);
        	}
        }
        
      //在页面上写出X，Y
        g.drawString("X", x0+300, y0+25);
        g.drawString("Y", x0-30, y0-400);
	}

	public static void main(String[] args) {
		Ball b = new Ball();
		MyPanel p = new MyPanel(b);
		b.setPanel(p);
		/* panel thread, paint the monkey */
		JFrame frame = new JFrame();
		frame.add(p);
		frame.setSize(900, 900);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}

class Ball extends Thread {

	boolean flag = true;
	int x0 = 900 / 2;
	int y0 = 900 / 2;// 坐标原点
	// 文件路径
	String filePath = "D:/work/pointinfo_solve.csv";
	SeqUtil seqUtil = new SeqUtil();
	
	int index = 600;
	Map<String, Object> mapSeq = seqUtil.getSeqPosition(index, filePath, 100);
	// 最后的seq序号
	int finalSeqNo = (int) mapSeq.get("finalSeqNo");
	// seq起始位置
	int seqStart = (int) mapSeq.get("seqStart");
	// seq当前位置
	int seqNow = (int) mapSeq.get("seqNow");
	// seq终止位置
	int seqEnd = (int) mapSeq.get("seqEnd");
	
	//seq序号区间内的所有信息
	List<String> seqOriginListArea = seqUtil.getColSeq(filePath, seqNow, seqEnd);
	
	//List<String> seqOriginListArea = seqUtil.getColSeq(filePath, seqNow, seqEnd);
	// 获取seq起始位置到终止位置的坐标
	List<String> seqOriginAreaList = seqUtil.getCol_3(seqOriginListArea);
	Map<String, double[]> map = seqUtil.getXYData(seqOriginAreaList);
	
	//指定区间内的x，y
	double[] xList = map.get("xList");
	double[] yList = map.get("yList");
	
	//打印到屏幕的x,y坐标点列表
	Map<String , List<Integer>> mapXY = seqUtil.getIntList(x0, y0, 20, xList, yList);
	List<Integer> xListScreen =  mapXY.get("xList");
	List<Integer> yListScreen =  mapXY.get("yList");
	
	MyPanel p;

	public void setPanel(MyPanel p) {
		this.p = p;
	}

	//初始化x，y坐标点
	int x, y;
	@Override
	public void run() {
		for(int i= 0;i < xListScreen.size();i++) {
			//屏幕x, y 坐标
			x = xListScreen.get(i);
			y = yListScreen.get(i);
			
			if(i == xListScreen.size()/2) {
				//中间点开关
				//flag = false;
				flag = true;
			}else {
				flag = true;
			}
			p.repaint();
			try {
				sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
