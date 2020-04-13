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
        timer = new Timer(50, new ActionListener() {//ÿ50����ı�һ��̫����λ��, �����Լ��ӿ���߷���,�޸ĺ������Ϳ���
 
            public void actionPerformed(ActionEvent e) {
                sp.setLocation(px -= 2, py -= 1);// �����޸ĳ�px-=3 ,py-=2�ȷ�ʽ������ÿ���ƶ��Ŀ���
                if (px <= 0 || py <= 0) {
                    timer.stop();
                }
            }
        });
        timer.start();//��ʼ
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
