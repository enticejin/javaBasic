package com.jl.test.draw.chart;
import javax.swing.*;

import com.jl.test.draw.arraySort.Arraysort;
import com.jl.test.draw.utils.ChartUtil;
import com.jl.test.draw.utils.ChartUtilOpt;

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
class ChartTest05 extends JPanel
{
	ChartUtilOpt chartOpt = new ChartUtilOpt();
    Polygon po = new Polygon();
    //�������弰��С
    Font fn = new Font("����", Font.BOLD, 22);
    Font fn2 = new Font("����", Font.BOLD, 20);
    public ChartTest05()
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
    	Map<String, double[]> map1 = getXYData(filePath);
    	
    
        double[] xDataArray = map1.get("xList");
        double[] yDataArray = map1.get("yList");
     
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
        		g2d.drawString("-"+String.valueOf(i / xSize), x0-i*10, y0-15);
        		g2d.drawString(String.valueOf(i / xSize), x0+i*10, y0-15);
        	}
        }
      //������̶�
        for(int i =0;i < y0/2;i++) {
        	if (i % ySize == 0 && i / ySize != 0)
        	{
        		g2d.drawString("-"+String.valueOf(i / ySize), x0-20, y0+i*10);
        	}
        }
      
        int x = 0;
        int y = 0;
        //��Բ��ֱ��
        int size = 10;
        Map<String, double[]> map = chartOpt.getAreaXY(-9,0, -4, 0, xDataArray, yDataArray);
        double[] xArr = map.get("xArray");
        double[] yArr =  map.get("yArray");
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
        int colorRedArray[] = new int[x_Array.length];
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
					
				}
			}
        }
        //��������ͼ
		g2d.setColor(Color.BLUE);
		//g2d.drawPolyline(x_Array, y_Array, x_Array.length);
		
        QuadCurve2D q1 = new QuadCurve2D.Float();
		g2d.setColor(Color.BLACK);
		//q1.setCurve(x0-xSize*50, y0, x0-xSize*30, y0+ySize*20, x0,y0+ySize*25);
		g2d.draw(q1);
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
    //��ȡ������ɫֵ
    public Color getPixel(int x,int y) throws AWTException{            //��������ֵΪ��ɫ��RGBֵ��
		   Robot rb = null;                                                   //java.awt.image���е��࣬��������ץȡ��Ļ����������
		   rb = new Robot();
		   Toolkit tk = Toolkit.getDefaultToolkit();              //��ȡȱʡ���߰�
		   Dimension di = tk.getScreenSize();                   //��Ļ�ߴ���
		   Rectangle rec = new Rectangle(0,0,di.width,di.height);
		   BufferedImage bi = rb.createScreenCapture(rec);
		   int pixelColor = bi.getRGB(x, y);
		   Color color=new Color(16777216 + pixelColor);  
	       return color; // pixelColor��ֵΪ��������ʵ���ó���������ɫ���ֵ����ʵ����ɫֵ��
	}
    /**
     * ��ȡ�ļ������������
     * @param filePath �ļ�·��
     * @return
     */
    public Map<String, double[]> getXYData(String filePath){
    	// ��ʼ��
    	ChartUtilOpt chartOpt = new ChartUtilOpt();
    	Map<String, double[]> map = new HashMap<String, double[]>();
    	// ��ȡ�ļ�
    	List<String> csvColList = chartOpt.getCol(filePath);
    	//��ȡy����
    	List<String> YListStr = new ArrayList<String>();
    	List<String> XListStr = new ArrayList<String>();
    	List<String> ZListStr = new ArrayList<String>();
    	for(int i = 0;i < csvColList.size();i++) {
    		YListStr.add(csvColList.get(i).split("\\|")[1]);
    		XListStr.add(csvColList.get(i).split("\\|")[0]);
    		ZListStr.add(csvColList.get(i).split("\\|")[2]);
    	}
    	double[] yList =  chartOpt.getArrayByStrList(YListStr);
    	double[] xList =  chartOpt.getArrayByStrList(XListStr);
    	double[] zList =  chartOpt.getArrayByStrList(ZListStr);
    	map.put("xList", xList);
    	map.put("yList", yList);
    	map.put("zList", zList);
    	return map;
    	
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
        jf.getContentPane().add(new ChartTest05());
    }
}