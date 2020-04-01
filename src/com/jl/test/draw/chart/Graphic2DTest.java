package com.jl.test.draw.chart;
  
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
public class Graphic2DTest extends JFrame//����
{
	public static void main(String[]argv)
	{
		JFrame frame=new Graphic2DTest();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رղ���
		frame.setSize(600, 600);
		frame.setVisible(true);//��ʾ ���ô�С
	}
	public void paint(Graphics g)//��ͼ���� 
	{
		g.drawString("Circle 99", 20, 20);//���� ��λ�� ��λ��
		int x0=getSize().width/2;
		int y0=getSize().height/2;//Բ��
		for(int r=0;r<getSize().height/2;r+=10)
		{
			g.setColor(getRandomColor());
			g.drawOval(x0-r,y0-r,r*2,r*2);//��Բ
		}
	}
	Color getRandomColor()//�����
	{
		return new Color(
				(int)(Math.random()*255),
				(int)(Math.random()*255),
				(int)(Math.random()*255)
			);
	}
	
}
