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
* @version ����ʱ�䣺2020��3��26�� ����9:30:45
* ��˵�� 
*/
public class DrawRoundRec extends Applet{
	Toolkit tk=Toolkit.getDefaultToolkit();
	Dimension screensize=tk.getScreenSize();
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//��ҳ���л�������
		for(double d = 0.0 ; d < screensize.getHeight() ; d +=10.0) {
			g2.draw(new Line2D.Double(0.0, d, screensize.getWidth(), d));
		}
		//��ҳ���л�������
		for(double d= 0.0; d < screensize.getWidth(); d+=10.0) {
			g2.draw(new Line2D.Double(d, screensize.getHeight(), d, 0.0));
		}
		//�����е������������
		Point2D.Double point = new Point2D.Double(20,50);
		point.setLocation(point);
	}
}
