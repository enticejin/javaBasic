package com.jl.java.draw.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * @version 创建时间：2020年4月23日 下午3:37:15
 * 类说明
 */
public class Test01 extends JFrame {
    int red, green, blue;
    Color color;
    public static double[][] dotLight = new double[5000][5000];
    Test01(){
        setSize(600, 800); // 设置窗口大小
        setVisible(true); // 设置为可见
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置窗口关闭动作
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int x0 = 100;
        int y0 = 300;
        int x2 = 300;
        int y2 = 500;
        int x1 = 100;
        int y1 = 200;
        List<Double> list = calcuLight(x0, y0);
        List<Double> list1 = calcuLight(x1, y1);
        List<Double> list2 = calcuLight(x2, y2);
        for(int i =0-100 ;i <= 0+100; i++) {
            if(i < 0) {
                g.setColor(new Color(0,0,0));
                g.drawOval(x0-i, y0-i, i*2,i*2);
            }else {
                g.setColor(new Color(i,i,i));
                g.drawOval(x0-i,y0-i, i*2,i*2);
            }
        }

        double distance = calcuDistance(x0, y0, x1, y1);
        int d= (int)(100+100-distance);
        for(int i =0-100 ;i <= 0+100; i++) {
            if(i < 0) {
                g.setColor(new Color(0,0,0));
                g.drawOval(x1-i, y1-i, i*2,i*2);
            }else {
                g.setColor(new Color(i,i,i));
                g.drawOval(x1-i,y1-i, i*2,i*2);
                g2d.setColor(new Color(d,d,d));
                Shape shape1=new Ellipse2D.Double(x0-i,y0-i, i*2,i*2);
                Shape shape2=new Ellipse2D.Double(x1-i, y1-i, i*2,i*2);
                Area myShape=new Area(shape1);
                myShape.intersect(new Area(shape2));
                g2d.draw(myShape);
            }
        }

		/*
		for(int i =255; i >= 0;i--) {
			red = i;
			green = i;
			blue = i;
			color = new Color(red, green, blue);
			g2d.setColor(color);
			g2d.drawOval(x0+i, y0+i,i*2, i*2);
			g2d.drawOval(y0+i, x0+i,i*2, i*2);
			g2d.drawOval(x1, y1,i*2, i*2);
			g2d.drawOval(x2, y2,i*2, i*2);
		}
		*/
		/*
		for(int i =0; i < list.size();i++) {
			if(list.get(i)  <= 255 && list.get(i) >= 0) {
				red = (int) (list.get(i)*1);
				green = (int) (list.get(i)*1);
				blue = (int) (list.get(i)*1);
				color = new Color(red, green, blue);
				g2d.setColor(color);
				g2d.drawOval(x0+i, y0+i,250 - i*2, 250 - i*2);
				g2d.drawOval(x1+i, y1+i,250 - i*2, 250 - i*2);
			}else {
				g2d.setColor(new Color(255, 0, 0));
				g2d.drawOval(x0+i, y0+i,250 - i*2, 250 - i*2);
				g2d.drawOval(x1+i, y1+i,250 - i*2, 250 - i*2);
			}
		}

		for(int i =0; i < list1.size();i++) {
			if(list1.get(i)  < 255 && list1.get(i) >= 0) {
				red = (int) (list1.get(i)*1);
				green = (int) (list1.get(i)*1);
				blue = (int) (list1.get(i)*1);
				color = new Color(red, green, blue);
				g2d.setColor(color);
				g2d.drawOval(x1+i, y1+i,250 - i*2, 250 - i*2);
			}else {
				g2d.setColor(new Color(255, 0, 0));
				g2d.drawOval(x1+i, y1+i,250 - i*2, 250 - i*2);
			}
		}
		*/
		/*
		for(int i =0; i < list2.size();i++) {
			if(list2.get(i)  < 255 && list2.get(i) >= 0) {
				red = (int) (list2.get(i)*1);
				green = (int) (list2.get(i)*1);
				blue = (int) (list2.get(i)*1);
				color = new Color(red, green, blue);
				g2d.setColor(color);
				g2d.drawOval(x2+i, y2+i,250 - i*2, 250 - i*2);
			}else {
				if((int) (list2.get(i)*1) > 255) {
					g2d.setColor(new Color(255, 255, 255));
					g2d.drawOval(x2+i, y2+i,250 - i*2, 250 - i*2);
				}else if((int) (list2.get(i)*1) < 0){
					g2d.setColor(new Color(0, 0, 0));
					g2d.drawOval(x2+i, y2+i,250 - i*2, 250 - i*2);
				}else {
					g2d.setColor(new Color((int) (list2.get(i)*1), (int) (list2.get(i)*1), (int) (list2.get(i)*1)));
					g2d.drawOval(x2+i, y2+i,250 - i*2, 250 - i*2);
				}

			}
		}
		*/
    }
    /**
     * 计算距离
     * @param x0 起始坐标x
     * @param y0 起始坐标y
     * @param x1 终止坐标x
     * @param y1 终止坐标y
     * @return
     */
    public double calcuDistance(int x0, int y0, int x1, int y1) {
        return Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2));
    }
    /**
     * 距离对亮度的影响
     * @param distance 两点的距离
     * @return
     */
    public double distance2Light(double distance) {
        return 100 - (int)distance;
    }
    /**
     * 计算坐标范围100之内的亮度
     * @param x0
     * @param y0
     * @return
     */
    public List<Double> calcuLight(int x0, int y0) {
        List<Double> list = new ArrayList<Double>();
        for(int x = x0 - 100;x < x0 + 100;x++) {
            for(int y = y0 - 100;y < y0 +100;y++) {
                list.add(distance2Light(calcuDistance(x, y, x0, y0)));
            }
        }
        return list;
    }
    /**
     * 计算亮度
     * @param xlist 横坐标列表
     * @param ylist 纵坐标列表
     * @return
     */
    public double calcLightDouble(int x0, int y0) {
        for(int x=x0 -100 ;x < x0 + 100;x++) {
            for(int y = y0 - 100;y <= y0+100;y++) {
                return distance2Light((int) calcuDistance(x,y, x0, y0));
            }
        }
        return 0;
    }
    /**
     * 计算出所有 的亮度
     * @param x 横坐标
     * @param y 纵坐标
     * @return
     */
    double calcAllDotLightDouble(int x, int y)
    {
        return dotLight[x][y] += calcLightDouble(x, y);
    }
    public static void main(String[] args) {
        Test01 T = new Test01();

    }
}

