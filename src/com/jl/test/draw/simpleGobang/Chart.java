package com.jl.test.draw.simpleGobang;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYZDataset;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
public class Chart {
    public static void main(String[] args) throws IOException {
    	Toolkit tk=Toolkit.getDefaultToolkit();
    	Dimension screensize=tk.getScreenSize();
        //设置气泡表的数据集
        DefaultXYZDataset dataset=new DefaultXYZDataset();
        double y_Data[]={10,20,30,40,50,100,200,300,400,500,550,600};//y坐标
        double x_Data[]={3.4,5.2,6.8,1.5,1,1,2,3,4,5,6,7};//x坐标
        double size[]={0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.07,0.05,0.02,0.03,0.05};//气泡的大小
        double d[][]={y_Data,x_Data,size};//将三个一维数组添加到二维数组中
        dataset.addSeries("test",d);//设置下面的目录标签
        //实现简单的气泡图，设置基本的数据
        JFreeChart freeChart=ChartFactory.createBubbleChart(
                "test",// 图表标题
                "side",//y轴方向数据标签
                "time",//x轴方向数据标签
                dataset,//数据集，即要显示在图表上的数据
                PlotOrientation.HORIZONTAL,//设置方向
                true,//是否显示图例
                true,//是否显示提示
                false//是否生成URL连接
        );
        //以面板显示
        ChartPanel chartPanel=new ChartPanel(freeChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(201,400));
        //创建一个主窗口来显示面板
        JFrame frame=new JFrame("图表");
        frame.setLocation(0,0);
        frame.setSize((int)screensize.getWidth(),(int)screensize.getHeight());
        //将主窗口的内容面板设置为图表面板
        frame.setContentPane(chartPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        /*
        //使用输出流输出图表文件
        //输出JPG文件
        OutputStream os=new FileOutputStream("c:/picture.jpg");
        ChartUtilities.writeChartAsJPEG(os,freeChart,500,500);
        //输出PNG文件
        OutputStream os_png=new FileOutputStream("c:/pictrue_png.png");
        ChartUtilities.writeChartAsPNG(os_png,freeChart,500,500);
        */
    }
}