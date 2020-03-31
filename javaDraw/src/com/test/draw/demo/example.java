package com.test.draw.demo;
import java.awt.Canvas;

import java.awt.Color;

import java.awt.FlowLayout;

import java.awt.Graphics;

import java.util.Random;

import javax.swing.JFrame;

public class example {

    JFrame random;

    Random r;

    canvas c;

    static class canvas extends Canvas {

        public canvas() {

            setSize(1000, 800);

// TODO Auto-generated constructor stub

        }

        @Override

        public void paint(Graphics g) {

// TODO Auto-generated method stub

            super.paint(g);

            for (int i = 0; i < 30; i++) {

                g.fillOval(new Random().nextInt(80), new Random().nextInt(40),

                        1, 1);

            }

        }

        public static void p() {

            JFrame random;

            Random r;

            canvas c;

            random = new JFrame();

            c = new canvas();

            c.setBackground(Color.white);

            random.setSize(300, 100);

            random.add(c);

            random.setLayout(new FlowLayout());

            random.setVisible(true);

        }

        public static void main(String[] args) {

            p();

        }

    }

}
