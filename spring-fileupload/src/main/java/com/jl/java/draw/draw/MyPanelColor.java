package com.jl.java.draw.draw;

import com.jl.java.draw.utils.SeqUtil;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class MyPanelColor extends Canvas implements Runnable  {
    int x0 = 900 / 2;
    int y0 = 900 / 2;// 坐标原点
    //圆的宽和高
    int width = 10;
    int height = 10;
    // 文件路径
    String filePath = "D:/work/pointinfo_solve.csv";
    SeqUtil seqUtil = new SeqUtil();

    int index = 600;
    int countSeq = 150;
    Map<String, List<Integer>> XYScreen = getXYScreen(index, countSeq);
    //start to end的x，y坐标
    List<Integer>xListScreen = XYScreen.get("xListScreen");
    List<Integer>yListScreen = XYScreen.get("yListScreen");

    //now to end的x，y坐标
    List<Integer>xListScreenNow = XYScreen.get("xListScreenNow");
    List<Integer>yListScreenNow = XYScreen.get("yListScreenNow");
    //所有的x，y坐标
    List<Integer>xAllListScreen = XYScreen.get("xAllListScreen");
    List<Integer>yAllListScreen = XYScreen.get("yAllListScreen");
    //初始化x，y坐标点
    int x, y, xs, ys, counter = 0;
    public static void main(String[] args)throws InterruptedException{
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setLayout(new BorderLayout());
        //frame.add(new TestDemo(),BorderLayout.CENTER);
        MyPanelColor myPanel=new MyPanelColor();
        frame.add(myPanel);
        new Thread(myPanel).start();//使用线程实现,
        frame.setVisible(true);
    }

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
        int xSize = xListScreen.size();
        int xAllSize = xAllListScreen.size();
        //所有的点
        g.setColor(Color.RED);
        for(int i =0;i < xAllSize;i++) {
            g.drawOval(xAllListScreen.get(i), yAllListScreen.get(i), width, height);
            //填充颜色--red
            GradientPaint redtowhite = new GradientPaint(xAllListScreen.get(i), yAllListScreen.get(i),Color.RED,xAllListScreen.get(i) + width, yAllListScreen.get(i) + height,Color.white);
            redtowhite = new GradientPaint(xAllListScreen.get(i), yAllListScreen.get(i),Color.RED,xAllListScreen.get(i) + width , yAllListScreen.get(i) + height,Color.white);
            g2.setPaint(redtowhite);
            g2.fill (new Ellipse2D.Double(xAllListScreen.get(i), yAllListScreen.get(i), width, height));
        }
        //当前所在位置的坐标点
        g.setColor(Color.blue);
        g.drawOval(x, y, width, height);
        //填充颜色--blue
        GradientPaint bluetowhite = new GradientPaint(x,y,Color.blue,x+width, y+height,Color.white);
        bluetowhite = new GradientPaint(x,y,Color.blue,x+width, y+height,Color.white);
        g2.setPaint(bluetowhite);
        g2.fill (new Ellipse2D.Double(x, y, width, height));
        g2.setPaint(Color.black);
        //起始坐标到终止坐标点
        g.setColor(Color.green);
        g.drawOval(xs, ys, width, height);
        //填充颜色--blue
        GradientPaint greentowhite = new GradientPaint(xs, ys,Color.green,xs+width, ys+height,Color.white);
        greentowhite = new GradientPaint(xs, ys,Color.green,xs+width, ys+height,Color.white);
        g2.setPaint(greentowhite);
        g2.fill (new Ellipse2D.Double(xs, ys, width, height));
        g2.setPaint(Color.black);

        //画出坐标
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


    public void run(){
        for(int i= 0;i < xAllListScreen.size();i++) {
            if(i < xListScreenNow.size()) {
                //屏幕x, y 坐标
                x = xListScreenNow.get(i);
                y = yListScreenNow.get(i);
                xs = xListScreen.get(i+50);
                ys = yListScreen.get(i+50);
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
     * @param count seq信息的个数
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
        mapXYScreen.put("xListScreen", xListScreenStart);
        mapXYScreen.put("yListScreen", yListScreenStart);
        mapXYScreen.put("xAllListScreen", xAllListScreen);
        mapXYScreen.put("yAllListScreen", yAllListScreen);
        mapXYScreen.put("xListScreenNow", xListScreenNow);
        mapXYScreen.put("yListScreenNow", yListScreenNow);
        return mapXYScreen;
    }


}

