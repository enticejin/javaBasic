package com.jl.test.draw.math;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
 
public class FrameDemo extends JFrame {
 
    SunPanel sp;
    Timer timer;
    int w = 1000;
    int h = 800;
    int px = w - 20;
    int py = h - 20;
 
    public FrameDemo() {
        
        setSize(w, h);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        sp = new SunPanel();
        sp.setBounds(px, py, 600, 600);
 
        add(sp);
        timer = new Timer(50, new ActionListener() {//每50毫秒改变一次太阳的位置, 可以自己加快或者放慢,修改毫秒数就可以
 
            public void actionPerformed(ActionEvent e) {
                sp.setLocation(px -= 2, py -= 1);// 可以修改成px-=3 ,py-=2等方式来控制每次移动的快慢
                if (px <= 0 || py <= 0) {
                    timer.stop();
                }
            }
        });
        timer.start();//开始
    }
 
    public static void main(String[] args) {
        new FrameDemo().setVisible(true);
    }
 
    class SunPanel extends JPanel {
        public void paint(Graphics g) {
            g.setColor(Color.RED);
            g.drawOval(2, 2, 10, 10);
        }
    }
}
