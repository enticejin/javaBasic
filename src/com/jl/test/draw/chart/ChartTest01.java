package com.jl.test.draw.chart;
import javax.swing.*;

import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class ChartTest01 extends JPanel
{
    Polygon po = new Polygon();
    //设置字体及大小
    Font fn = new Font("宋体", Font.BOLD, 22);
    Font fn2 = new Font("宋体", Font.BOLD, 20);
    public ChartTest01()
    {
        setSize(900, 900);
    }
    public void paint(Graphics g)
    {
    	String filePath = "D:/work/pointinfo_solve.csv";
        double[] xDataArray = getXData(filePath);
        double[] yDataArray = getYData(filePath);
        int x0=getSize().width/2;
		int y0=getSize().height/2;//坐标原点 
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //设置背景色
        g2d.setColor(Color.black);
        //x坐标线
        g2d.fillRect(0, y0, x0*2, 2);
        //y坐标线
        //g2d.fillRect(700, 145, 2, 600);
        g2d.fillRect(x0, 0, 2, x0*2);
        int num = 2;
        g2d.drawString("0", x0, y0);
      //纵坐标刻度
        for(int i =0;i < 40;i++) {
        	if (i % 2 == 0 && i / 2 != 0)
        	{
        		//纵坐标刻度
        		g2d.drawString("-"+String.valueOf(i / num), x0-20, y0+i*15);
        		g2d.drawString(String.valueOf(i / num), x0-20, y0-i*15);
        	}
        }
      //横坐标刻度
        for(int i = 0;i < 60;i++) {
        	if(i % 2 == 0 && i / 2 != 0) {
        		g2d.drawString("-"+String.valueOf(i / num), x0-i*15, y0-15);
        		g2d.drawString(String.valueOf(i / num), x0+i*15, y0-15);
        	}
        }
        
        //设置X,Y为黑色粗体
        g2d.setFont(fn2);
        g2d.setColor(Color.white);
        g2d.drawString("", 102, 700);
        g2d.setFont(fn);
        g2d.setColor(Color.black);
        //在坐标上写出X，Y
        g2d.drawString("X", x0+900, y0+25);
        g2d.drawString("Y", x0-30, y0-400);
    }
    
    Color getRandomColor()//随机数
	{
		return new Color(
				(int)(Math.random()*255),
				(int)(Math.random()*255),
				(int)(Math.random()*255)
			);
	}
    //"D:/work/pointinfo_solve.csv"
    /**
     * 读取文件中横坐标的数据
     * @param filePath 文件路径
     * @return
     */
    public double[] getXData(String filePath){
    			// 初始化
    			ChartUtilOpt chartOpt = new ChartUtilOpt();
    			// 读取文件
    			List<String> csvColList = chartOpt.getCol(filePath);
    			//获取xyz数据
    			List<String> XListStr = new ArrayList<String>();
    			//List<String> ZListStr = new ArrayList<String>();
    			for(int i = 0;i < csvColList.size();i++) {
    				XListStr.add(csvColList.get(i).split("\\|")[0]);
    				//ZListStr.add(csvColList.get(i).split("\\|")[2]);
    			}
    			double[] xList =  chartOpt.getArrayByStrList(XListStr);
    	return xList;
    	
    }
    /**
     * 读取文件中纵坐标的数据
     * @param filePath 文件路径
     * @return
     */
    public double[] getYData(String filePath){
    	// 初始化
    	ChartUtilOpt chartOpt = new ChartUtilOpt();
    	// 读取文件
    	List<String> csvColList = chartOpt.getCol(filePath);
    	//获取y数据
    	List<String> YListStr = new ArrayList<String>();
    	for(int i = 0;i < csvColList.size();i++) {
    		YListStr.add(csvColList.get(i).split("\\|")[1]);
    	}
    	double[] yList =  chartOpt.getArrayByStrList(YListStr);
    	return yList;
    	
    }
    public static void main(String[] args)
    {
    	//获取当前屏幕的宽和高
        Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();
        JFrame jf = new JFrame();
        jf.setSize(width, height);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(3);
        jf.getContentPane().add(new ChartTest01());
    }
}

