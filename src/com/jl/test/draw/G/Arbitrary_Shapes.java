package com.jl.test.draw.G;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.GeneralPath;

/** 
* @version 创建时间：2020年3月24日 下午12:05:46
* 类说明 :绘制任意形状
*/
public class Arbitrary_Shapes extends Applet {
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// draw GeneralPath (polyline)
		int x2Point[] = {0, 100, 0, 100};
		int y2Point[] = {0, 50, 50, 0};
		
		GeneralPath polyLine = 	new GeneralPath(GeneralPath.WIND_EVEN_ODD, x2Point.length);
		polyLine.moveTo(x2Point[0], y2Point[0]);
		
		for(int i = 0; i < x2Point.length ;i++) {
			polyLine.lineTo(x2Point[i],  y2Point[i]);
		}
		
		g2.draw(polyLine);
		
		// draw GeneralPath (polygon)
		int x1Points[] = {400, 800,400 , 800};
		int y1Points[] = {400, 600,600,400};
		
		GeneralPath polgyn = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				x1Points.length);
		
		polgyn.moveTo(x1Points[0], y1Points[0]);
		
		for(int i =0; i<x1Points.length ;i++) {
			polgyn.lineTo(x1Points[i],  y1Points[i]);
		}
		
		polgyn.closePath();
		
		g2.draw(polgyn);
		
	}

}
