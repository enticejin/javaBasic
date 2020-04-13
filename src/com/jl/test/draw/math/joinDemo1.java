package com.jl.test.draw.math;

import java.awt.Graphics;

import javax.swing.JFrame;

public class joinDemo1 extends JFrame {
	// 继承
	private int x = 100, y = 100, r = 100;

	// 初始值
	public joinDemo1() {
		super("小图形");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(800, 600);
		this.setVisible(true);
		Thread thread = new Thread(new Graphicss());
		thread.start();

	}

	public void paint(Graphics g) {
		super.paint(g);
		g.fillOval(x, y, r, r);
	}

	public static void main(String[] args) {
		new joinDemo1();
	}

	class Graphicss implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			for (int j = 0; j <= 240; j+=10) {
				revalidate();
				// System.out.println(j);
				try {
					Thread.sleep(1000);// 当前线程休眠0.01秒

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				y = y + j;
				repaint();

			}

		}
	}
}