package com.jl.test.draw.chart;
import javax.swing.*;

import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator
 *指定区间取值
 */
class ChartTest03 extends JPanel
{
	ChartUtilOpt chartOpt = new ChartUtilOpt();
    Polygon po = new Polygon();
    //设置字体及大小
    Font fn = new Font("宋体", Font.BOLD, 22);
    Font fn2 = new Font("宋体", Font.BOLD, 20);
    public ChartTest03()
    {
    	//获取当前屏幕的宽和高
        Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();
        setSize(width, height);
    }
    public void paint(Graphics g)
    {
    	String filePath = "D:/work/pointinfo_solve.csv";
        double[] xDataArray = getXData(filePath);
        double[] yDataArray = getYData(filePath);
        //Arrays.sort(xDataArray);
        //Arrays.sort(yDataArray);
        //int x0=getSize().width/2;
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
        g2d.drawString("0", x0, y0-15);
        
      //纵坐标刻度
        for(int i =0;i < 40;i++) {
        	if (i % num == 0 && i / num != 0)
        	{
        		//纵坐标刻度
        		//g2d.drawOval(x0, y0, 10, 10);
        		g2d.drawString("-"+String.valueOf(i / num), x0-20, y0+i*10);
        		g2d.drawString(String.valueOf(i / num), x0-20, y0-i*10);
        	}
        }
      //横坐标刻度
        for(int i = 0;i < 60;i++) {
        	if(i % num == 0 && i / num != 0) {
        		g2d.drawString("-"+String.valueOf(i / num), x0-i*10, y0-15);
        		g2d.drawString(String.valueOf(i / num), x0+i*10, y0-15);
        	}
        }
        int x = 0;
        int y = 0;
        //画圆的直径
        int size = 10;
        Map<String, double[]> map = chartOpt.getAreaXY(-9,0, -2, 0, xDataArray, yDataArray);
        double[] xArr = map.get("xArray");
        double[] yArr =  map.get("yArray");
        for(int i = 0;i < xArr.length;i++) {
        	//在图上描出对应坐标
    		x = (int) (x0+xArr[i]*20);
			y= (int) (y0-yArr[i]*20);
//			g2d.drawOval(x, y, 8, 8);
			for(int j =0;j < size;j++) {
				g2d.setColor(getColor(j));
				if(x != x0 && y != y0) {
					g2d.drawOval(x+j, y+j, size-10*j, size-10*j); 
				}
			}
        }
        g2d.setFont(fn2);
        g2d.setColor(Color.white);
        g2d.drawString("", 103, 700);
        g2d.setFont(fn);
        g2d.setColor(Color.black);
        //在页面上写出X，Y
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
    //获取坐标颜色值
    public Color getPixel(int x,int y) throws AWTException{            //函数返回值为颜色的RGB值。
		   Robot rb = null;                                                   //java.awt.image包中的类，可以用来抓取屏幕，即截屏。
		   rb = new Robot();
		   Toolkit tk = Toolkit.getDefaultToolkit();              //获取缺省工具包
		   Dimension di = tk.getScreenSize();                   //屏幕尺寸规格
		   Rectangle rec = new Rectangle(0,0,di.width,di.height);
		   BufferedImage bi = rb.createScreenCapture(rec);
		   int pixelColor = bi.getRGB(x, y);
		   Color color=new Color(16777216 + pixelColor);  
	       return color; // pixelColor的值为负，经过实践得出：加上颜色最大值就是实际颜色值。
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
        //960
//        System.out.println("width / 2 ="+(width / 2));
        //540
//        System.out.println("height / 2 ="+(height / 2));
        JFrame jf = new JFrame();
        //设置窗口大小
        jf.setSize(1000, 1000);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(3);
        jf.getContentPane().add(new ChartTest03());
    }
}
