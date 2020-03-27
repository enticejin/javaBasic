package com.jl.test.draw.useable;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.CubicCurve2D.Double;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Test  extends Applet{
	@Override
	public void paint(Graphics g) {
//		g.drawString("hello  java draw", 70, 30);
//		super.paint(g);
//		Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("weather-cloud.png"));
//		g.drawImage(image, 0, 0, 400, 500, this);
		
		Graphics2D g2 = (Graphics2D) g;
		//画点
		//setLocation(double x, double y), setLocation(Point2D p) 
		Point2D.Double origin = new Point2D.Double();
		Point2D.Double point = new Point2D.Double(300,400);
		origin.setLocation(point);
		origin.setLocation(500,  600);
		//画线	
		//Line2D.Float(float X1, float Y1, float X2, float Y2)
		//Line2D.Float(Point2D p1, Point2D p2)
		g2.draw(new Line2D.Double(111.0, 222.0, 333.0, 444.0));	
		
		//二次曲线
		//初始化
		QuadCurve2D q = new QuadCurve2D.Float();
		//画出曲线
		q.setCurve(123.0, 456.0, 678.0, 789.3, 654.3, 3.215);
		g2.draw(q);
		
		//三次曲线
		//初始化
		CubicCurve2D c = new CubicCurve2D.Double();
		//画出三次曲线
		c.setCurve(123.0, 456.0, 678.0, 789.3, 654.3, 3.215, 456.0, 678.0);
		g2.draw(c);
		
		//长方形
		//draw Rectangle2D.Double
		//g2.draw(new Rectangle2D.Double(x, y,rectwidth,rectheight));
        
		g2.draw(new Rectangle2D.Double(100,200,
				200,100));
		
		//圆角的长方形
		//draw RoundRectangle2D.Double
		g2.draw(new RoundRectangle2D.Double(300,  400,  400,  500,  50, 50));
		
		//椭圆
		//draw Ellipse2D.Double
		g2.draw(new Ellipse2D.Double(100, 400, 200, 300));
		
		//狐线
		//draw Arc2D.Double
		g2.draw(new Arc2D.Double(100,  250, 378, 423, 90, 135, Arc2D.OPEN));
	}
}
