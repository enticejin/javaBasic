package com.jl.test.draw.chart;
import javax.swing.*;

import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class ChartTest01 extends JPanel
{
    Polygon po = new Polygon();
    //�������弰��С
    Font fn = new Font("����", Font.BOLD, 22);
    Font fn2 = new Font("����", Font.BOLD, 20);
    public ChartTest01()
    {
        setSize(900, 900);
    }
    public void paint(Graphics g)
    {
    	String filePath = "D:/work/pointinfo_solve.csv";
        double[] xDataArray = getXData(filePath);
        double[] yDataArray = getYData(filePath);
        int x0=getSize().width/2;
		int y0=getSize().height/2;//����ԭ�� 
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //���ñ���ɫ
        g2d.setColor(Color.black);
        //x������
        g2d.fillRect(0, y0, x0*2, 2);
        //y������
        //g2d.fillRect(700, 145, 2, 600);
        g2d.fillRect(x0, 0, 2, x0*2);
        int num = 2;
        g2d.drawString("0", x0, y0);
      //������̶�
        for(int i =0;i < 40;i++) {
        	if (i % 2 == 0 && i / 2 != 0)
        	{
        		//������̶�
        		g2d.drawString("-"+String.valueOf(i / num), x0-20, y0+i*15);
        		g2d.drawString(String.valueOf(i / num), x0-20, y0-i*15);
        	}
        }
      //������̶�
        for(int i = 0;i < 60;i++) {
        	if(i % 2 == 0 && i / 2 != 0) {
        		g2d.drawString("-"+String.valueOf(i / num), x0-i*15, y0-15);
        		g2d.drawString(String.valueOf(i / num), x0+i*15, y0-15);
        	}
        }
        
        //����X,YΪ��ɫ����
        g2d.setFont(fn2);
        g2d.setColor(Color.white);
        g2d.drawString("", 102, 700);
        g2d.setFont(fn);
        g2d.setColor(Color.black);
        //��������д��X��Y
        g2d.drawString("X", x0+900, y0+25);
        g2d.drawString("Y", x0-30, y0-400);
    }
    
    Color getRandomColor()//�����
	{
		return new Color(
				(int)(Math.random()*255),
				(int)(Math.random()*255),
				(int)(Math.random()*255)
			);
	}
    //"D:/work/pointinfo_solve.csv"
    /**
     * ��ȡ�ļ��к����������
     * @param filePath �ļ�·��
     * @return
     */
    public double[] getXData(String filePath){
    			// ��ʼ��
    			ChartUtilOpt chartOpt = new ChartUtilOpt();
    			// ��ȡ�ļ�
    			List<String> csvColList = chartOpt.getCol(filePath);
    			//��ȡxyz����
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
     * ��ȡ�ļ��������������
     * @param filePath �ļ�·��
     * @return
     */
    public double[] getYData(String filePath){
    	// ��ʼ��
    	ChartUtilOpt chartOpt = new ChartUtilOpt();
    	// ��ȡ�ļ�
    	List<String> csvColList = chartOpt.getCol(filePath);
    	//��ȡy����
    	List<String> YListStr = new ArrayList<String>();
    	for(int i = 0;i < csvColList.size();i++) {
    		YListStr.add(csvColList.get(i).split("\\|")[1]);
    	}
    	double[] yList =  chartOpt.getArrayByStrList(YListStr);
    	return yList;
    	
    }
    public static void main(String[] args)
    {
    	//��ȡ��ǰ��Ļ�Ŀ�͸�
        Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();
        JFrame jf = new JFrame();
        jf.setSize(width, height);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(3);
        jf.getContentPane().add(new ChartTest01());
    }
}

