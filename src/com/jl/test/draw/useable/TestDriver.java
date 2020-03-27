package com.jl.test.draw.useable;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

class GridsCanvas extends JPanel {
	//设置宽度和高度
  int width, height;
  //设置行列数
  int rows;
  int cols;
  //构造函数
  /**
   * 
   * @param w 宽度
   * @param h 高度
   * @param r 行数
   * @param c 列数
   */
  GridsCanvas(int w, int h, int r, int c) {
    setSize(width = w, height = h);
    rows = r;
    cols = c;
  }
  /**
   * 画图方法
   */
  public void paint(Graphics g) {
    int i;
    width = getSize().width;
    height = getSize().height;

    //行数
    int rowHt = height / (rows);
    for (i = 0; i < rows; i++)
      g.drawLine(0, i * rowHt, width, i * rowHt);

    //列数
    int rowWid = width / (cols);
    for (i = 0; i < cols; i++)
      g.drawLine(i * rowWid, 0, i * rowWid, height);
  }
}
   
public class TestDriver extends JFrame {
  public TestDriver() {
    GridsCanvas xyz = new GridsCanvas(200, 200, 200, 200);
    add(xyz);
    pack();
  }

  public static void main(String[] a) {
    new TestDriver().setVisible(true);
  }
}