package com.jl.test.draw.chart;

import java.applet.Applet;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/** 
* @version 创建时间：2020年4月1日 下午5:02:15
* 类说明 
*/
public class AppletCircle  extends Applet{
	public void init() {
		this.setSize(400, 400);
	}
	public void paint(Graphics g) {
		Color color;
		for (int i=0; i<20; i++){
		 color = new Color((int)(Math.random()*256),
				 (int)(Math.random()*256),
				 (int)(Math.random()*256));
		  g.setColor(color);
		  g.drawOval(20+5*i, 30+5*i, 300-10*i, 300-10*i); 
		}
	}
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
	public static void main(String[] args) {
		 new AppletCircle();
	}
}
