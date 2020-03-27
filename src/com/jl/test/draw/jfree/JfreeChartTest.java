package com.jl.test.draw.jfree;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/** 
* @version 创建时间：2020年3月25日 上午11:45:00
* 类说明 
*/
public class JfreeChartTest {

    public static void main(String[] args) {

//     创建类别图（Category）数据对象

       DefaultCategoryDataset dataset = new DefaultCategoryDataset();

       dataset.addValue(100, "北京", "苹果");

       dataset.addValue(100, "上海", "苹果");

       dataset.addValue(100, "广州", "苹果");

       dataset.addValue(200, "北京", "梨子");

       dataset.addValue(200, "上海", "梨子");

       dataset.addValue(200, "广州", "梨子");

       dataset.addValue(300, "北京", "葡萄");

       dataset.addValue(300, "上海", "葡萄");

       dataset.addValue(300, "广州", "葡萄");

       dataset.addValue(400, "北京", "香蕉");

       dataset.addValue(400, "上海", "香蕉");

       dataset.addValue(400, "广州", "香蕉");

       dataset.addValue(500, "北京", "荔枝");

       dataset.addValue(500, "上海", "荔枝");

       dataset.addValue(500, "广州", "荔枝");

       JFreeChart chart=ChartFactory.createBarChart3D("水果产量图", "水果", "水果", dataset, PlotOrientation.VERTICAL, true, true, true);

       ChartFrame  frame=new ChartFrame ("水果产量图 ",chart,true);

       frame.pack();

       frame.setVisible(true);

    }

}
