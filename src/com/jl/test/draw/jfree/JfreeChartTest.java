package com.jl.test.draw.jfree;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/** 
* @version ����ʱ�䣺2020��3��25�� ����11:45:00
* ��˵�� 
*/
public class JfreeChartTest {

    public static void main(String[] args) {

//     �������ͼ��Category�����ݶ���

       DefaultCategoryDataset dataset = new DefaultCategoryDataset();

       dataset.addValue(100, "����", "ƻ��");

       dataset.addValue(100, "�Ϻ�", "ƻ��");

       dataset.addValue(100, "����", "ƻ��");

       dataset.addValue(200, "����", "����");

       dataset.addValue(200, "�Ϻ�", "����");

       dataset.addValue(200, "����", "����");

       dataset.addValue(300, "����", "����");

       dataset.addValue(300, "�Ϻ�", "����");

       dataset.addValue(300, "����", "����");

       dataset.addValue(400, "����", "�㽶");

       dataset.addValue(400, "�Ϻ�", "�㽶");

       dataset.addValue(400, "����", "�㽶");

       dataset.addValue(500, "����", "��֦");

       dataset.addValue(500, "�Ϻ�", "��֦");

       dataset.addValue(500, "����", "��֦");

       JFreeChart chart=ChartFactory.createBarChart3D("ˮ������ͼ", "ˮ��", "ˮ��", dataset, PlotOrientation.VERTICAL, true, true, true);

       ChartFrame  frame=new ChartFrame ("ˮ������ͼ ",chart,true);

       frame.pack();

       frame.setVisible(true);

    }

}
