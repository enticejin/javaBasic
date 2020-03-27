package com.jl.test.draw.useable;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

class GridsCanvas extends JPanel {
	//���ÿ�Ⱥ͸߶�
  int width, height;
  //����������
  int rows;
  int cols;
  //���캯��
  /**
   * 
   * @param w ���
   * @param h �߶�
   * @param r ����
   * @param c ����
   */
  GridsCanvas(int w, int h, int r, int c) {
    setSize(width = w, height = h);
    rows = r;
    cols = c;
  }
  /**
   * ��ͼ����
   */
  public void paint(Graphics g) {
    int i;
    width = getSize().width;
    height = getSize().height;

    //����
    int rowHt = height / (rows);
    for (i = 0; i < rows; i++)
      g.drawLine(0, i * rowHt, width, i * rowHt);

    //����
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