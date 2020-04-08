package com.jl.test.draw.seq;
import javax.swing.*;

import com.jl.test.draw.arraySort.Arraysort;
import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;
import com.jl.test.draw.utils.SeqUtil;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator
 *ָ������ȡֵ�Ŵ�
 */
class ChartTestSeq extends JPanel
{
	ChartUtilOpt chartOpt = new ChartUtilOpt();
    Polygon po = new Polygon();
    //�������弰��С
    Font fn = new Font("����", Font.BOLD, 22);
    Font fn2 = new Font("����", Font.BOLD, 20);
    public ChartTestSeq()
    {
    	//��ȡ��ǰ��Ļ�Ŀ�͸�
        Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();
        setSize(width, height);
    }
    public void paint(Graphics g)
    {
    	//��ȡ����
    	String filePath = "D:/work/pointinfo_solve.csv";
    	SeqUtil seqUtil = new SeqUtil();
    	//��ǩ���յ�Ƶ��200ms��ȡ10s�ڵ�����
    	int num = 1000/200 * 1000;
		//��ֹλ��
		int seqEnd = 1694386;
    	//��ʼλ��
		int seqStart = seqEnd - num;
		//����λ��
		int seqNow = seqStart + num / 2;
		//ȥ��seqָ�����������
		List<String> seqListArea = seqUtil.getColSeq(filePath, seqStart, seqEnd*2);
		//����ȡ�������ݵõ�seq�б�
		List<String> seqList = seqUtil.getCol_3(seqListArea);
		Map<String, double[]> map = seqUtil.getXYData(seqList);
		double[] xList = map.get("xList");
		double[] yList = map.get("yList");
        //int x0=getSize().width/2;
        /*
        int x0=getSize().width/2;
		int y0=getSize().height/2;//����ԭ�� 
		*/
        int x0 = 500;
        int y0 = 200;
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //���ñ���ɫ
        g2d.setColor(Color.black);
        //x������
        g2d.fillRect(0, y0, x0*4, 2);
        //y������
        g2d.fillRect(x0, y0, 2, y0*4);
        //��Ӧ���������
        //���������1:10
        int xSize = 4;
        int ySize = 4;
        g2d.drawString("0", x0, y0-15);
      //������̶�
        for(int i = 0;i < y0/2;i++) {
        	if(i % xSize == 0 && i / xSize != 0) {
        		g2d.drawString("-"+String.valueOf(i / xSize), x0-i*10*(xSize / 4), y0-15);
        		g2d.drawString(String.valueOf(i / xSize), x0+i*10*(ySize / 4), y0-15);
        	}
        }
      //������̶�
        for(int i =0;i < y0/2;i++) {
        	if (i % ySize == 0 && i / ySize != 0)
        	{
        		g2d.drawString("-"+String.valueOf(i / ySize), x0-20, y0+i*10*(ySize / 4));
        	}
        }
        int x = 0;
        int y = 0;
        //��Բ��ֱ��
        int size = 10;
        Map<String, double[]> map1 = chartOpt.getAreaXY(-12,12, -12, 12, xList, yList);
        double[] xArr = map1.get("xArray");
        double[] yArr =  map1.get("yArray");
        double xMax= chartOpt.getMax(xArr);
        double xMin= chartOpt.getMin(xArr);
        double yMin= chartOpt.getMin(yArr);
        double[] xDouble = new double[2];
        double[] yDouble = new double[2];
        //�ҳ���Ӧ������ֵ
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
        //��double����ת����int����
        int[] x_Array = new int[xArr.length];
        int[] y_Array = new int[yArr.length];
        for(int i = 0;i < xArr.length;i++) {
        	x_Array[i] = (int) 	(x0+xArr[i]*xSize * 10);
        	y_Array[i] = (int)  (y0-yArr[i]*ySize * 10);
        }
        //x, y����
        //Arrays.sort(x_Array);
        //Arrays.sort(y_Array);
        //��������
        QuadCurve2D q1 = new QuadCurve2D.Float();
        QuadCurve2D q2 = new QuadCurve2D.Float();
        QuadCurve2D q3 = new QuadCurve2D.Float();
		g2d.setColor(Color.BLACK);
        for(int i = 0;i < xArr.length;i++) {
        	//��ͼ�������Ӧ����
    		x = (int) (x0+xArr[i]*xSize * 10);
			y = (int) (y0-yArr[i]*ySize * 10);
//			g2d.drawOval(x, y, 8, 8);
			for(int j =0;j < size;j++) {
				//��ɫ����1:25,255-(j * 25)
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
        //��������ͼ
        //g2d.setColor(Color.BLUE);
		//g2d.drawPolyline(x_Array, y_Array, x_Array.length);
		
//		g2d.setColor(Color.BLUE);
//		q1.setCurve(x0+xDouble[0]*xSize * 10, y0-xDouble[1]*40,
//				x0-xSize*50 +5, y0+ySize*20 +5,
//				x0-yDouble[0],y0-yDouble[1]*50+5
//				);
//		g2d.draw(q1);
        /*
        g2d.setColor(Color.BLUE);
		q1.setCurve(x0+xDouble[0]*xSize * 10, y0-xDouble[1]*40,
				x0-xDouble[0]*xSize * 8, y0+yDouble[1]*8,
				x0-yDouble[0]*xSize * 10,y0-yDouble[1]*30
				);
		g2d.draw(q1);
		*/
        
        g2d.setColor(Color.BLUE);
		q1.setCurve(x0-20*10, y0+10.9779 * 10,
				x0,y0-0.8099*10,
				x0+20*10,y0+16.8419*10
				);
		for(int i =0 ;i < xArr.length;i++) {
			System.out.println("x = "+xArr[i]+" ----- y = " + yArr[i]);
		}
		g2d.draw(q1);
//		q2.setCurve(x0+xDouble[0]*xSize * 10, y0-xDouble[1],
//				x0-xSize*25 +5, y0+ySize*10 +5,
//				x0-yDouble[0],y0-yDouble[1]*30+5
//				);
//		g2d.draw(q2);
		
//		g2d.setColor(Color.BLACK);
//		q3.setCurve(x0+xDouble[0]*xSize * 10, y0-xDouble[1]*20,
//				x0-xSize*40 +5, y0+ySize*15 +5,
//				x0-yDouble[0],y0-yDouble[1]*40+5);
//		g2d.draw(q3);
		//��������ͼ
//		g2d.setColor(Color.BLUE);
//		g2d.drawPolyline(x_Array, y_Array, x_Array.length);
		//����
        g2d.setFont(fn2);
        g2d.setFont(fn);
        g2d.setColor(Color.black);
        //��ҳ����д��X��Y
        g2d.drawString("X", x0-400, y0+25);
        g2d.drawString("Y", x0-30, y0+400);
        
    }
    //���ø���Ȧ��ɫ
    /**
     * ��ȡҪ���õ���ɫֵ
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
    
    
    public static void main(String[] args)
    {
    	//��ȡ��ǰ��Ļ�Ŀ�͸�
        Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();
        //960
//        System.out.println("width / 2 ="+(width / 2));
        //540
//        System.out.println("height / 2 ="+(height / 2));
        JFrame jf = new JFrame();
        //���ô��ڴ�С
        jf.setSize(1000, 1000);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(3);
        jf.getContentPane().add(new ChartTestSeq());
        
        
    }
}