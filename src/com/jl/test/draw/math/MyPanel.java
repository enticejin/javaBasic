package com.jl.test.draw.math;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jl.test.draw.utils.SeqUtil;

public class MyPanel extends JPanel {
	int x = 0, y = 400;
	Ball b;
	public MyPanel(Ball b) {
		this.b = b;
		b.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(b.flag == true) {
			//��ʾ���������е������
			g.setColor(Color.RED);
			for(int i =0;i < b.xListScreen.size();i++) {
				g.drawOval(b.xListScreen.get(i), b.yListScreen.get(i), 10, 10);
				b.seqStart++;
			}
			//��ʾ��ǰλ�õ������
			g.setColor(Color.BLUE);
			g.drawOval(b.x, b.y, 10, 10);
		}else if(b.flag == false) {
			g.setColor(Color.BLUE);
			g.drawOval(b.x, b.y, 20, 20);
		}
		int x0 = 450;
		int y0 = 450;
		//���ñ���ɫ
	    g.setColor(Color.black);
	    //x������
	    g.fillRect(0, y0, x0*2, 2);
	    //y������
	    g.fillRect(x0, 0, 2, y0*2);
	    
	    int num = 2;
	  //������̶�
        //һ���̶�10�����ص�
        for(int i =0;i < 40;i++) {
        	if (i % 2 == 0 && i / 2 != 0)
        	{
        		//������̶�
        		g.drawString("-"+String.valueOf(i / num), x0-20, y0+i*10);
        		g.drawString(String.valueOf(i / num), x0-20, y0-i*10);
        	}
        }
      //������̶�
        //һ���̶�10�����ص�
        for(int i = 0;i < 60;i++) {
        	if(i % 2 == 0 && i / 2 != 0) {
        		g.drawString("-"+String.valueOf(i / num), x0-i*10, y0-15);
        		g.drawString(String.valueOf(i / num), x0+i*10, y0-15);
        	}
        }
        
      //��ҳ����д��X��Y
        g.drawString("X", x0+300, y0+25);
        g.drawString("Y", x0-30, y0-400);
	}

	public static void main(String[] args) {
		Ball b = new Ball();
		MyPanel p = new MyPanel(b);
		b.setPanel(p);
		/* panel thread, paint the monkey */
		JFrame frame = new JFrame();
		frame.add(p);
		frame.setSize(900, 900);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}

class Ball extends Thread {

	boolean flag = true;
	int x0 = 900 / 2;
	int y0 = 900 / 2;// ����ԭ��
	// �ļ�·��
	String filePath = "D:/work/pointinfo_solve.csv";
	SeqUtil seqUtil = new SeqUtil();
	
	int index = 600;
	Map<String, Object> mapSeq = seqUtil.getSeqPosition(index, filePath, 100);
	// ����seq���
	int finalSeqNo = (int) mapSeq.get("finalSeqNo");
	// seq��ʼλ��
	int seqStart = (int) mapSeq.get("seqStart");
	// seq��ǰλ��
	int seqNow = (int) mapSeq.get("seqNow");
	// seq��ֹλ��
	int seqEnd = (int) mapSeq.get("seqEnd");
	
	//seq��������ڵ�������Ϣ
	List<String> seqOriginListArea = seqUtil.getColSeq(filePath, seqNow, seqEnd);
	
	//List<String> seqOriginListArea = seqUtil.getColSeq(filePath, seqNow, seqEnd);
	// ��ȡseq��ʼλ�õ���ֹλ�õ�����
	List<String> seqOriginAreaList = seqUtil.getCol_3(seqOriginListArea);
	Map<String, double[]> map = seqUtil.getXYData(seqOriginAreaList);
	
	//ָ�������ڵ�x��y
	double[] xList = map.get("xList");
	double[] yList = map.get("yList");
	
	//��ӡ����Ļ��x,y������б�
	Map<String , List<Integer>> mapXY = seqUtil.getIntList(x0, y0, 20, xList, yList);
	List<Integer> xListScreen =  mapXY.get("xList");
	List<Integer> yListScreen =  mapXY.get("yList");
	
	MyPanel p;

	public void setPanel(MyPanel p) {
		this.p = p;
	}

	//��ʼ��x��y�����
	int x, y;
	@Override
	public void run() {
		for(int i= 0;i < xListScreen.size();i++) {
			//��Ļx, y ����
			x = xListScreen.get(i);
			y = yListScreen.get(i);
			
			if(i == xListScreen.size()/2) {
				//�м�㿪��
				//flag = false;
				flag = true;
			}else {
				flag = true;
			}
			p.repaint();
			try {
				sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
