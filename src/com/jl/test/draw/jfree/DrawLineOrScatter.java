package com.jl.test.draw.jfree;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.jl.test.draw.useable.OverlaidDrawLineScatter;

public class DrawLineOrScatter extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    public DrawLineOrScatter(String title) {

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
        DefaultXYDataset dataScatter = createDatasetLine();
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
        
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[][] data= {{1,2,3},{4,5,6}};
        dataset.addSeries("拟合数据", data);
        return dataset;
    }
    
    private DefaultXYDataset createDatasetScatter() {
    	
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[][] data= {{1,2,3},{4.6,4.8,6.8}};
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
