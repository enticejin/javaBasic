package com.jl.test.draw.math;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class SeqArea_01 extends JPanel {
  int x = 0, y = 400;
  Ball01 b;

  public SeqArea_01(Ball01 b) {
    this.b = b;
    b.start();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(Color.RED);
    g.drawOval(b.x, b.y, 10, 10);
  }

  public static void main(String[] args) {
    Ball01 b = new Ball01();
    SeqArea_01 p = new SeqArea_01(b);
    b.setPanel(p);
    /* panel thread, paint the monkey */
    JFrame frame = new JFrame();
    frame.add(p);
    frame.setSize(1000, 800);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
}


class Ball01 extends Thread {
  int x = 0;
  int y = 400;
  SeqArea_01 p;

  public void setPanel(SeqArea_01 p) {
    this.p = p;
  }

  @Override
  public void run() {
    while (true) {
      if (x > 800) {
        x = 0;
      } else {
        x = x + 10;
      }
      p.repaint();
      try {
        sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
