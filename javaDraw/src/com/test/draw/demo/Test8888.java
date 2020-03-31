package com.test.draw.demo;

import javax.swing.*;
import java.awt.*;

class test8888 extends JPanel
{
    Polygon po = new Polygon();
    Font fn = new Font("宋体", Font.BOLD, 22);
    Font fn2 = new Font("宋体", Font.BOLD, 20);
    int x = 100;
    int y = 100;
    int[] pox ={ 90, 100, 100 };
    int[] poy ={ 110, 90, 100 };
    int[] poxx ={ 100, 100, 110 };
    int[] poyy ={ 90, 90, 110 };
    int[] poxB = {687,697,707};
    int[] poyB = {690,700,700};
    int[] poxBB = {687,697,707};
    int[] poyBB = {710,700,700};
    public test8888()
    {
        setSize(900, 900);
    }
    public void paint(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(99, 100, 2, 600);
        g2d.fillRect(99, 700, 600, 2);
        g2d.fillRect(100, 685, 15, 15);
        for (int i = 0; i < 37; i++)
        {
            g2d.drawLine(100 + i * 15, 600 + y, y + i * 15, y + 45);
            g2d.drawLine(100, 600 + y - i * 15, y + 555, y + 600 - i * 15);
            g2d.drawString("0", x - 20, 720);
            if (i % 2 == 0 && i / 2 != 0)
            {
                g2d.drawString(String.valueOf(i / 2), x - 20, 705 - i / 2 * 30);
                g2d.drawString(String.valueOf(i / 2), x - 5 + i / 2 * 30, 720);
            }
        }
        g2d.setFont(fn2);
        g2d.setColor(Color.white);
        g2d.drawString("", 102, 700);
        g2d.setFont(fn);
        g2d.setColor(Color.black);
        g2d.drawString("Y", 80, 140);
        g2d.drawString("X", 670, 720);
        g2d.fillPolygon(pox,poy,3);
        g2d.fillPolygon(poxx,poyy,3);
        g2d.fillPolygon(poxB,poyB,3);
        g2d.fillPolygon(poxBB,poyBB,3);
        g2d.dispose();
    }
    public static void main(String[] args)
    {
        JFrame jf = new JFrame();
        jf.setSize(1000, 1000);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(3);
        jf.getContentPane().add(new test8888());
    }
}
