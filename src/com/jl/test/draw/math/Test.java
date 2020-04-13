package com.jl.test.draw.math;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JPanel{
    int x =50;
    int y =50;
    @Override
    public void paint (Graphics g) {
        super.paint(g);
        g.setColor(Color.red);
        g.fillOval(x,y,30,30);
    }
    public static void main(String [] ars){
        JFrame frame = new JFrame("paintTest");
        Test panel = new Test();
        frame.add(panel);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        panel.repaint();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run ( ) {
                if(panel.x<=300){
                    panel.x++;
                    panel.repaint();
                }
            }
        },0,20);
    }
}
