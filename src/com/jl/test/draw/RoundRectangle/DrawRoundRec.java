package com.jl.test.draw.RoundRectangle;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/** 
* @version 创建时间：2020年3月26日 上午9:30:45
* 类说明 
*/
public class DrawRoundRec extends Applet{
	Toolkit tk=Toolkit.getDefaultToolkit();
	Dimension screensize=tk.getScreenSize();
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//在页面中画出横线
		for(double d = 0.0 ; d < screensize.getHeight() ; d +=10.0) {
			g2.draw(new Line2D.Double(0.0, d, screensize.getWidth(), d));
		}
		//在页面中画出竖线
		for(double d= 0.0; d < screensize.getWidth(); d+=10.0) {
			g2.draw(new Line2D.Double(d, screensize.getHeight(), d, 0.0));
		}
		//在已有的网格中描出点
		Point2D.Double point = new Point2D.Double(20,50);
		point.setLocation(point);
	}
}
