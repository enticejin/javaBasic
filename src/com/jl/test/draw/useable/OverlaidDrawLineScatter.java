package com.jl.test.draw.useable;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.jl.test.draw.utils.ChartUtilOpt;
/**
 * A demonstration application show the line and scatter.
 */
public class OverlaidDrawLineScatter extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	public OverlaidDrawLineScatter(String title) {

        super(title);
        JFreeChart chart = createOverlaidChart();
        ChartPanel panel = new ChartPanel(chart, true, true, true, true, true);
        panel.setPreferredSize(new java.awt.Dimension(800, 500));
        setContentPane(panel);

    }

    /**
     * Creates an overlaid chart.
     *
     * @return The chart.
     */
    private JFreeChart createOverlaidChart() {
    	// Add first data set and rendererLine
        DefaultXYDataset dataLine = createDatasetLine();
        XYItemRenderer rendererLine = new XYLineAndShapeRenderer();        
        NumberAxis XAxis = new NumberAxis("X 值");
        NumberAxis YAxis = new NumberAxis("Y 值");
		NumberTickUnit unit=new NumberTickUnit(0.5);
		XAxis.setTickUnit(unit);
		YAxis.setTickUnit(unit);
        
        // create plot
        XYPlot plot = new XYPlot(dataLine, XAxis, YAxis, rendererLine);

        // Add second data set and rendererScatter
        DefaultXYDataset dataScatter = createDatasetScatter();
        XYItemRenderer rendererScatter = new XYShapeRenderer();
        plot.setDataset(1, dataScatter);
        plot.setRenderer(1, rendererScatter);

        // return a new chart containing the overlaid plot
        return new JFreeChart("线性拟合", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

    }

    /**
     * Generate the dataset
     * @return The dataset.
     */
    private DefaultXYDataset createDatasetLine() {
    	/*
//    	The list of one-dimensional convert to array of two-dimensional 
    	ArrayList<Double> x = new ArrayList<Double>();
    	ArrayList<Double> y = new ArrayList<Double>();
    	
    	x.add(1.0);
    	x.add(2.0);
    	x.add(3.0);
    	y.add(4.0);
    	y.add(5.0);
    	y.add(6.0);
    	
    	int size = x.size();
    	double[][] data= new double[2][size];
    	
    	for(int i=0; i<size; i++) {
    		data[0][i] = x.get(i);
    		data[1][i] = y.get(i);
    	}
    	*/
    	
    	
    	 // 初始化
 		ChartUtilOpt chartOpt = new ChartUtilOpt();
 		// 读取文件
 		List<String> csvColList = chartOpt.getCol("D:/work/pointinfo_solve.csv");
 		List<String> XListStr = new ArrayList<String>();
 		List<String> YListStr = new ArrayList<String>();
 		List<String> ZListStr = new ArrayList<String>();
 		for(int i = 0;i < csvColList.size();i++) {
 			XListStr.add(csvColList.get(i).split("\\|")[0]);
 			YListStr.add(csvColList.get(i).split("\\|")[1]);
 			ZListStr.add(csvColList.get(i).split("\\|")[2]);
 		}
 		double[] xList =  chartOpt.getArrayByStrList(XListStr);
 		double[] yList =  chartOpt.getArrayByStrList(YListStr);
 		double[] zList =  chartOpt.getArrayByStrList(ZListStr);
 		List<String> XListStrArea = new ArrayList<String>();
 		List<String> YListStrArea = new ArrayList<String>();
 		for(int i =0;i < XListStr.size();i++) {
 			if(Double.parseDouble(XListStr.get(i).toString()) < -11 && 
 					Double.parseDouble(XListStr.get(i).toString()) > -12 && 
 					Double.parseDouble(YListStr.get(i).toString()) < -2 && 
 					Double.parseDouble(YListStr.get(i).toString()) > -5) {
 				XListStrArea.add(XListStr.get(i));
 				YListStrArea.add(YListStr.get(i));
 			}
 		}
 		//ymin=-3.62 ymax=-3.34  xmin=-11.85  xmax=-11.5
 		double[] xListDou = chartOpt.getArrayByStrList(XListStrArea);
 		double[] yListDou = chartOpt.getArrayByStrList(YListStrArea);
         DefaultXYDataset dataset = new DefaultXYDataset();
         double[][] data= {xList,yList};
    	dataset.addSeries("拟合数据", data);
        return dataset;
    }
    
    private DefaultXYDataset createDatasetScatter() {
    	 // 初始化
 		ChartUtilOpt chartOpt = new ChartUtilOpt();
 		// 读取文件
 		List<String> csvColList = chartOpt.getCol("D:/work/pointinfo_solve.csv");
 		List<String> XListStr = new ArrayList<String>();
 		List<String> YListStr = new ArrayList<String>();
 		List<String> ZListStr = new ArrayList<String>();
 		for(int i = 0;i < csvColList.size();i++) {
 			XListStr.add(csvColList.get(i).split("\\|")[0]);
 			YListStr.add(csvColList.get(i).split("\\|")[1]);
 			ZListStr.add(csvColList.get(i).split("\\|")[2]);
 		}
 		double[] xList =  chartOpt.getArrayByStrList(XListStr);
 		double[] yList =  chartOpt.getArrayByStrList(YListStr);
 		double[] zList =  chartOpt.getArrayByStrList(ZListStr);
 		List<String> XListStrArea = new ArrayList<String>();
 		List<String> YListStrArea = new ArrayList<String>();
 		for(int i =0;i < XListStr.size();i++) {
 			if(Double.parseDouble(XListStr.get(i).toString()) < -11 && 
 					Double.parseDouble(XListStr.get(i).toString()) > -12 && 
 					Double.parseDouble(YListStr.get(i).toString()) < -2 && 
 					Double.parseDouble(YListStr.get(i).toString()) > -5) {
 				XListStrArea.add(XListStr.get(i));
 				YListStrArea.add(YListStr.get(i));
 			}
 		}
 		//ymin=-3.62 ymax=-3.34  xmin=-11.85  xmax=-11.5
 		double[] xListDou = chartOpt.getArrayByStrList(XListStrArea);
 		double[] yListDou = chartOpt.getArrayByStrList(YListStrArea);
         DefaultXYDataset dataset = new DefaultXYDataset();
         double[][] data= {xList,yList};
		dataset.addSeries("原始数据", data);
        return dataset;
    }
    
    public static void main(final String[] args) {
        OverlaidDrawLineScatter demo = new OverlaidDrawLineScatter("工程数据线性拟合");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
}
