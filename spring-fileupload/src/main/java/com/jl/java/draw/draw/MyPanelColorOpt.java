package com.jl.java.draw.draw;

import com.jl.java.draw.utils.SeqUtil;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

public class MyPanelColorOpt extends Canvas implements Runnable  {
    int x0 = 900 / 2;
    int y0 = 900 / 2;// 坐标原点
    //圆的宽和高
    int width = 10;
    int height = 10;
    // 文件路径
    String filePath = "D:/work/pointinfo_solve.csv";
    SeqUtil seqUtil = new SeqUtil();

    int index = 600;
    int countSeq = 100;
    Map<String, List<Integer>> XYScreen = getXYScreen(index, countSeq);
    //start to end的x，y坐标
    List<Integer>xListScreenStart = XYScreen.get("xListScreenStart");
    List<Integer>yListScreenStart = XYScreen.get("yListScreenStart");

    //now to end的x，y坐标
    List<Integer>xListScreenNow = XYScreen.get("xListScreenNow");
    List<Integer>yListScreenNow = XYScreen.get("yListScreenNow");
    //所有的x，y坐标
    List<Integer>xAllListScreen = XYScreen.get("xAllListScreen");
    List<Integer>yAllListScreen = XYScreen.get("yAllListScreen");
    //从起始到终止的屏幕坐标
    List<Integer> xListScreenNowToEnd = new ArrayList<Integer>();
    List<Integer> yListScreenNowToEnd = new ArrayList<Integer>();
    //初始化x，y坐标点
    int x, y, xs, ys;
    int xSize = xListScreenStart.size();
    int xAllSize = xAllListScreen.size();

    Image offScreenImage = null;
    //解决调用repaint()方法闪烁的问题
    public void update(Graphics g) { // 双缓冲
        if (offScreenImage == null) {
            offScreenImage = this.createImage(900, 900);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.white);
        gOffScreen.fillRect(0, 0, 900, 900);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        //模拟画出可能出现的点
        for(int i =0;i < yListScreenNowToEnd.size() ;i++) {
            g.setColor(Color.red);
            g.drawOval(xListScreenNowToEnd.get(i), yListScreenNowToEnd.get(i), width, height);
            //填充颜色--blue
            GradientPaint redtowhite1 = new GradientPaint(xListScreenNowToEnd.get(i), yListScreenNowToEnd.get(i),Color.red,xListScreenNowToEnd.get(i)+width, yListScreenNowToEnd.get(i)+height,Color.white);
            redtowhite1 = new GradientPaint(xListScreenNowToEnd.get(i), yListScreenNowToEnd.get(i),Color.red,xListScreenNowToEnd.get(i)+width, yListScreenNowToEnd.get(i)+height,Color.white);
            g2.setPaint(redtowhite1);
            g2.fill (new Ellipse2D.Double(xListScreenNowToEnd.get(i), yListScreenNowToEnd.get(i), width, height));
            g2.setPaint(Color.black);
            //System.out.println(seqUtil.getScreenPixel(xListScreenNowToEnd.get(i), yListScreenNowToEnd.get(i)));
        }
        /*
		//当前所在位置的坐标点
        g.setColor(Color.blue);
		g.drawOval(x, y, width, height);
		//填充颜色--blue
		GradientPaint bluetowhite = new GradientPaint(x,y,Color.blue,x+width, y+height,Color.white);
		bluetowhite = new GradientPaint(x,y,Color.blue,x+width, y+height,Color.white);
        g2.setPaint(bluetowhite);
        g2.fill (new Ellipse2D.Double(x, y, width, height));
        g2.setPaint(Color.black);

        //终止位置坐标点
        g.setColor(Color.yellow);
		g.drawOval(xs, ys, width, height);
		//填充颜色--green
		GradientPaint yellowtowhite = new GradientPaint(xs, ys,Color.yellow,xs+width, ys+height,Color.white);
		yellowtowhite = new GradientPaint(xs, ys,Color.yellow,xs+width, ys+height,Color.white);
        g2.setPaint(yellowtowhite);
        g2.fill (new Ellipse2D.Double(xs, ys, width, height));
        g2.setPaint(Color.black);
        */
        //坐标原点
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
    public void run() {
        for(int i= 0;i < xAllListScreen.size();i++) {
            if(i < xListScreenNow.size()) {
                //屏幕x, y 坐标现在的坐标点
                x = xListScreenNow.get(i);
                y = yListScreenNow.get(i);
                //终止坐标点
                xs = xListScreenStart.get(i+50);
                ys = yListScreenStart.get(i+50);
                //画粗线
                for(int j = 0;j < 50;j++) {
                    xListScreenNowToEnd.add(xListScreenStart.get(i+j));
                    yListScreenNowToEnd.add(yListScreenStart.get(i+j));
                }
                yListScreenNowToEnd = seqUtil.getArrayOpt(yListScreenNowToEnd);
                xListScreenNowToEnd = seqUtil.getArrayOpt(xListScreenNowToEnd);
                System.out.println("x ="+xListScreenNowToEnd.get(i)+"---------"+"y ="+yListScreenNowToEnd.get(i));
                System.out.println("xListScreenNowToEnd.size = "+xListScreenNowToEnd.size());
            }
            repaint();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取在屏幕上显示的大小
     * @param index 起始值
     * @param numSeq seq信息的个数
     * @return
     */
    public Map<String, List<Integer>> getXYScreen(int index,int numSeq){
        Map<String, List<Integer>> mapXYScreen = new HashMap<String, List<Integer>>();
        this.index = index;
        Map<String, Object> mapSeq = seqUtil.getSeqPosition(index, filePath, numSeq);
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
        List<String> seqOriginListStartToEnd = seqUtil.getColSeq(filePath, seqStart, seqEnd);
        List<String> seqOriginListNowToEnd = seqUtil.getColSeq(filePath, seqNow, seqEnd);
        // 获取seq起始位置到终止位置的坐标
        List<String> seqOriginListStartToEndList = seqUtil.getCol_3(seqOriginListStartToEnd);
        List<String> seqOriginListNowToEndList = seqUtil.getCol_3(seqOriginListNowToEnd);
        Map<String, double[]> mapStart = seqUtil.getXYData(seqOriginListStartToEndList);
        Map<String, double[]> mapNow = seqUtil.getXYData(seqOriginListNowToEndList);

        //start to end区间内的x，y
        double[] xListStart = mapStart.get("xList");
        double[] yListStart = mapStart.get("yList");
        //now to end区间内的x， y
        double[] xListNow = mapNow.get("xList");
        double[] yListNow = mapNow.get("yList");

        //start to end区间的x,y坐标点列表
        Map<String , List<Integer>> mapXYStart = seqUtil.getIntList(x0, y0, 20, xListStart, yListStart);
        List<Integer> xListScreenStart =  mapXYStart.get("xList");
        List<Integer> yListScreenStart =  mapXYStart.get("yList");

        //now to end区间的x,y坐标点列表
        Map<String , List<Integer>> mapXYNow = seqUtil.getIntList(x0, y0, 20, xListNow, yListNow);
        List<Integer> xListScreenNow =  mapXYNow.get("xList");
        List<Integer> yListScreenNow =  mapXYNow.get("yList");
        mapXYScreen.put("xListScreenStart", xListScreenStart);
        mapXYScreen.put("yListScreenStart", yListScreenStart);
        mapXYScreen.put("xAllListScreen", xAllListScreen);
        mapXYScreen.put("yAllListScreen", yAllListScreen);
        mapXYScreen.put("xListScreenNow", xListScreenNow);
        mapXYScreen.put("yListScreenNow", yListScreenNow);
        return mapXYScreen;
    }

    public static void main(String[] args)throws InterruptedException{
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,900);
        frame.setLayout(new BorderLayout());
        //frame.add(new TestDemo(),BorderLayout.CENTER);
        MyPanelColorOpt myPanel=new MyPanelColorOpt();
        frame.add(myPanel);
        new Thread(myPanel).start();//使用线程实现,
        frame.setVisible(true);
    }


}

