package com.jl.java.draw.draw;

import com.jl.java.draw.utils.ChartUtilOpt;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 *指定区间取值放大
 */
class ChartTest05 extends JPanel
{
	ChartUtilOpt chartOpt = new ChartUtilOpt();
	Polygon po = new Polygon();
	//设置字体及大小
	Font fn = new Font("宋体", Font.BOLD, 22);
	Font fn2 = new Font("宋体", Font.BOLD, 20);
	public ChartTest05()
	{
		//获取当前屏幕的宽和高
		Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		setSize(width, height);
	}
	public void paint(Graphics g)
	{
		//获取数据
		String filePath = "D:/work/pointinfo_solve.csv";
		Map<String, double[]> map1 = getXYData(filePath);


		double[] xDataArray = map1.get("xList");
		double[] yDataArray = map1.get("yList");

		//int x0=getSize().width/2;
        /*
        int x0=getSize().width/2;
		int y0=getSize().height/2;//坐标原点
		*/
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
		Map<String, double[]> map = chartOpt.getAreaXY(-9,0, -2, -0.5, xDataArray, yDataArray);
		double[] xArr = map.get("xArray");
		double[] yArr =  map.get("yArray");
		double xMax= chartOpt.getMax(xArr);
		double yMax= chartOpt.getMax(yArr);
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
		//x, y排序
		//Arrays.sort(x_Array);
		//Arrays.sort(y_Array);
		//画出曲线
		QuadCurve2D q1 = new QuadCurve2D.Float();
		QuadCurve2D q2 = new QuadCurve2D.Float();
		QuadCurve2D q3 = new QuadCurve2D.Float();
		g2d.setColor(Color.BLACK);
		for(int i = 0;i < xArr.length;i++) {
			//在图上描出对应坐标
			x = (int) (x0+xArr[i]*xSize * 10);
			y = (int) (y0-yArr[i]*ySize * 10);
//			g2d.drawOval(x, y, 8, 8);
			for(int j =0;j < size;j++) {
				//颜色比例1:25,255-(j * 25)
				g2d.setColor(getColor(j));
				if(x != x0 && y!=y0) {
					g2d.drawOval(x+j, y+j, size-10*j, size-10*j);
				}
				if(j == 0) {
					if(xMax <= 0) {
						/*
						g2d.setColor(Color.BLUE);
						q1.setCurve(x0+xArr[0]*xSize * 10, y0+5,
								x0-xSize*50 +5, y0+ySize*20 +5,
								x0,y0+yArr[yArr.length - 1]*5+5);
						g2d.draw(q1);
						*/


					}
				}
			}
		}
		//画出折线图
		g2d.setColor(Color.BLUE);
		//g2d.drawPolyline(x_Array, y_Array, x_Array.length);

		g2d.setColor(Color.BLUE);
		q1.setCurve(x0+xDouble[0]*xSize * 10, y0-xDouble[1]*40,
				x0-xSize*50 +5, y0+ySize*20 +5,
				x0-yDouble[0],y0-yDouble[1]*50+5
		);
		g2d.draw(q1);
		q2.setCurve(x0+xDouble[0]*xSize * 10, y0-xDouble[1],
				x0-xSize*25 +5, y0+ySize*10 +5,
				x0-yDouble[0],y0-yDouble[1]*30+5
		);
		g2d.draw(q2);

		g2d.setColor(Color.BLACK);
		q3.setCurve(x0+xDouble[0]*xSize * 10, y0-xDouble[1]*20,
				x0-xSize*40 +5, y0+ySize*15 +5,
				x0-yDouble[0],y0-yDouble[1]*40+5);
		g2d.draw(q3);
		//画出折线图
//		g2d.setColor(Color.BLUE);
//		g2d.drawPolyline(x_Array, y_Array, x_Array.length);
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
			YListStr.add(csvColList.get(i).split("\\|")[1]);
			XListStr.add(csvColList.get(i).split("\\|")[0]);
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
		jf.getContentPane().add(new ChartTest05());
	}
}