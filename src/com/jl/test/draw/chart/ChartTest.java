package com.jl.test.draw.chart;
import javax.swing.*;

import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class ChartTest extends JPanel
{
    Polygon po = new Polygon();
    //�������弰��С
    Font fn = new Font("����", Font.BOLD, 22);
    Font fn2 = new Font("����", Font.BOLD, 20);
    int x = 145;
    int y = 100;
    //���������ͷ����
    int[] pox ={ 90, 100, 100 };
    int[] poy ={ 110, 90, 100 };
    int[] poxx ={ 100, 100, 110 };
    int[] poyy ={ 90, 90, 110 };
    int[] poxB = {687,697,707};
    int[] poyB = {690,700,700};
    int[] poxBB = {687,697,707};
    int[] poyBB = {710,700,700};
    public ChartTest()
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
        g2d.fillRect(99, 145, 600, 2);
        //y������
        g2d.fillRect(700, 145, 2, 600);
        int num = 2;
        for (int i = 0; i < 40; i++)
        {
        	//�趨ԭ��
        	g2d.drawString("0", 700, x);
        	if (i % 2 == 0 && i / 2 != 0)
        	{
        		//������̶�
        		g2d.drawString("-"+String.valueOf(i / num), 700, x+i*15);
        		//������̶�
        		g2d.drawString("-"+String.valueOf(i / num), 700-i*15, x-5);
        	}
        }
        //����X,YΪ��ɫ����
        g2d.setFont(fn2);
        g2d.setColor(Color.white);
        g2d.drawString("", 102, 700);
        g2d.setFont(fn);
        g2d.setColor(Color.black);
        //��������д��X��Y
        g2d.drawString("X", 80, 140);
        g2d.drawString("Y", 730, 720);
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
        jf.getContentPane().add(new ChartTest());
    }
}
