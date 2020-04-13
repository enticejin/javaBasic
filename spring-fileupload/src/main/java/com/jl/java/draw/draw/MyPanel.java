package com.jl.java.draw.draw;

import com.jl.java.draw.utils.SeqUtil;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class MyPanel extends JPanel {
    int x = 0, y = 400;
    //圆的宽和高
    int width = 10;
    int height = 10;
    Ball b;
    public MyPanel(Ball b) {
        this.b = b;
        b.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        int xSize = b.xListScreen.size();
        int xAllSize = b.xAllListScreen.size();
        //显示区域内所有的坐标点
        int countSeq = 0;
        g.setColor(Color.RED);
        for(int i =0;i < xSize;i++) {
            g.drawOval(b.xListScreen.get(i), b.yListScreen.get(i), width, height);
            //填充颜色--red
            GradientPaint redtowhite = new GradientPaint(b.xListScreen.get(i), b.yListScreen.get(i),Color.RED,b.xListScreen.get(i) + width, b.yListScreen.get(i) + height,Color.white);
            redtowhite = new GradientPaint(b.xListScreen.get(i), b.yListScreen.get(i),Color.RED,b.xListScreen.get(i) + width , b.yListScreen.get(i) + height,Color.white);
            g2.setPaint(redtowhite);
            g2.fill (new Ellipse2D.Double(b.xListScreen.get(i), b.yListScreen.get(i), width, height));
        }
		/*
		for(int i = xSize;i < xAllSize;i++) {
			g.drawOval(b.xAllListScreen.get(i), b.yAllListScreen.get(i), width, height);
		}
		*/
        //显示当前位置的坐标点
        g.setColor(Color.blue);
        g.drawOval(b.x, b.y, width, height);
        //填充颜色--blue
        GradientPaint bluetowhite = new GradientPaint(b.x,b.y,Color.blue,b.x+width, b.y+height,Color.white);
        bluetowhite = new GradientPaint(b.x,b.y,Color.blue,b.x+width, b.y+height,Color.white);
        g2.setPaint(bluetowhite);
        g2.fill (new Ellipse2D.Double(b.x, b.y, width, height));
        g2.setPaint(Color.black);
        x += width;

        int x0 = 450;
        int y0 = 450;
        //设置背景色
        g.setColor(Color.black);
        //x坐标线
        g.fillRect(0, y0, x0*2, 2);
        //y坐标线
        g.fillRect(x0, 0, 2, y0*2);

        int num = 2;
        //纵坐标刻度
        //一个刻度10个像素点
        for(int i =0;i < 40;i++) {
            if (i % 2 == 0 && i / 2 != 0)
            {
                //纵坐标刻度
                g.drawString("-"+String.valueOf(i / num), x0-20, y0+i*10);
                g.drawString(String.valueOf(i / num), x0-20, y0-i*10);
            }
        }
        //横坐标刻度
        //一个刻度10个像素点
        for(int i = 0;i < 60;i++) {
            if(i % 2 == 0 && i / 2 != 0) {
                g.drawString("-"+String.valueOf(i / num), x0-i*10, y0-15);
                g.drawString(String.valueOf(i / num), x0+i*10, y0-15);
            }
        }

        //在页面上写出X，Y
        g.drawString("X", x0+300, y0+25);
        g.drawString("Y", x0-30, y0-400);
    }

    public static void main(String[] args) {
        Ball b = new Ball();
        MyPanel p = new MyPanel(b);
        b.setPanel(p);
        JFrame frame = new JFrame();
        frame.add(p);
        frame.setSize(900, 900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Ball extends Thread {

    int x0 = 900 / 2;
    int y0 = 900 / 2;// 坐标原点
    // 文件路径
    String filePath = "D:/work/pointinfo_solve.csv";
    SeqUtil seqUtil = new SeqUtil();

    int index = 600;
    int countSeq = 150;
    Map<String, List<Integer>> XYScreen = getXYScreen(index, countSeq);
    //区域内的x，y坐标
    List<Integer>xListScreen = XYScreen.get("xListScreen");
    List<Integer>yListScreen = XYScreen.get("yListScreen");
    //所有的x，y坐标
    List<Integer>xAllListScreen = XYScreen.get("xAllListScreen");
    List<Integer>yAllListScreen = XYScreen.get("yAllListScreen");

    MyPanel p;

    public void setPanel(MyPanel p) {
        this.p = p;
    }

    //初始化x，y坐标点
    int x, y, xAll, yAll, counter = 0;
    /**
     * 线程的run方法
     */
    @Override
    public void run() {
        for(int i= 0;i < xListScreen.size();i++) {
            //屏幕x, y 坐标
            x = xListScreen.get(i);
            y = yListScreen.get(i);
            counter++;
            if(counter >= xListScreen.size()) {
                xAll = xAllListScreen.get(i);
                yAll = yAllListScreen.get(i);
            }
            p.repaint();
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		/*
		if(counter >= xListScreen.size()) {
			for(int i= counter;i < xAllListScreen.size();i++) {
				//屏幕x, y 坐标
				xAll = xAllListScreen.get(i);
				yAll = yAllListScreen.get(i);

				p.repaint();
				try {
					sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		*/
    }
    /**
     * 获取在屏幕上显示的大小
     * @param index 起始值
     * @param count seq信息的个数
     * @return
     */
    public Map<String, List<Integer>> getXYScreen(int index,int numSeq){
        Map<String, List<Integer>> mapXYScreen = new HashMap<String, List<Integer>>();
        this.index = index;
        Map<String, Object> mapSeq = seqUtil.getSeqPosition(index, filePath, numSeq);
        // 最后的seq序号
        int finalSeqNo = (int) mapSeq.get("finalSeqNo");
        // seq起始位置
        int seqStart = (int) mapSeq.get("seqStart");
        // seq当前位置
        int seqNow = (int) mapSeq.get("seqNow");
        // seq终止位置
        int seqEnd = (int) mapSeq.get("seqEnd");
        //获取所有的x，y数据
        Map<String, double[]> mapAllXY = seqUtil.getXYData(filePath);
        double[] xAllList = mapAllXY.get("xList");
        double[] yAllList = mapAllXY.get("yList");
        //打印到屏幕所有x,y坐标点列表
        Map<String , List<Integer>> mapAllXYScreen = seqUtil.getIntList(x0, y0, 20, xAllList, yAllList);

        List<Integer> xAllListScreen = mapAllXYScreen.get("xList");
        List<Integer> yAllListScreen = mapAllXYScreen.get("yList");

        //seq序号区间内的所有信息
        List<String> seqOriginListArea = seqUtil.getColSeq(filePath, seqNow, seqEnd);
        //List<String> seqOriginListArea = seqUtil.getColSeq(filePath, seqNow, seqEnd);
        // 获取seq起始位置到终止位置的坐标
        List<String> seqOriginAreaList = seqUtil.getCol_3(seqOriginListArea);
        Map<String, double[]> map = seqUtil.getXYData(seqOriginAreaList);

        //指定区间内的x，y
        double[] xList = map.get("xList");
        double[] yList = map.get("yList");

        //打印到屏幕的区间的x,y坐标点列表
        Map<String , List<Integer>> mapXY = seqUtil.getIntList(x0, y0, 20, xList, yList);
        List<Integer> xListScreen =  mapXY.get("xList");
        List<Integer> yListScreen =  mapXY.get("yList");
        mapXYScreen.put("xListScreen", xListScreen);
        mapXYScreen.put("yListScreen", yListScreen);
        mapXYScreen.put("xAllListScreen", xAllListScreen);
        mapXYScreen.put("yAllListScreen", yAllListScreen);
        return mapXYScreen;
    }
}