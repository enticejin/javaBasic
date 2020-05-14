package com.jl.java.test0511;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.jl.java.draw.utils.ChartUtilOpt;

/**
 * @version ����ʱ�䣺2020��4��30�� ����8:55:06 ��˵��
 */
public class Test extends JPanel {
	static int[][] dotLight = new int[400][400];
	static int[][] dotLight1 = new int[400][400];
	ChartUtilOpt chartUtil = new ChartUtilOpt();
	// ����ʱ���ȡ����
	String filePath = "D:/work/pointinfo_solve.csv";
	// ת����ʽ
	String format = "yyyy-MM-dd HH:mm:ss";
	// ��ȡ����
	int seconds = 12;
	// ����
	String date = "2020-3-31 11:24:46";
	int xSize = 8;
	int ySize = 8;
	int x0 = 200;
	int y0 = 200;

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Map<String, double[]> map = chartUtil.getXY(filePath, date, format, seconds);
		double[] xArr = map.get("xList");
		double[] yArr = map.get("yList");
		// ������ת������Ļ����
		int[] x_Array = new int[xArr.length];
		int[] y_Array = new int[yArr.length];
		for (int i = 0; i < xArr.length; i++) {
			x_Array[i] = (int) Math.abs((x0 + xArr[i] * xSize / 4 * xSize * 10));
			y_Array[i] = (int) (y0 - yArr[i] * ySize / 4 * ySize * 10);
		}
		List<Point> pointList = new ArrayList<Point>();
		List<Point> pointList1 = new ArrayList<Point>();
		Map<Point, Integer> mapPoint = new HashMap<Point, Integer>();
		// ��������
		int x = 0;
		int y = 0;
		// ���ȱ���
		int lightSize = 10;
		int color = 255;
		// �����
		Point point, point1;
		// ������ʵ�ʾ���
		int distance = 0;
		double realDistance = 0;
		//����������������
		double mostDistance =  (200 * Math.sqrt(2));
		int mostLight = 30000;
		// ��Χ�ڵ���ɫ
		int light = 0;
		for (int q = 0; q < x_Array.length; q++) {
			x = x_Array[q];
			y = y_Array[q];
			pointList.add(new Point(x, y));
		}
		/*
		for(int i=0;i < pointList.size();i++) {
			point = pointList.get(i);
			dotLight = getlight10(point.x, point.y);
			for (int m = point.x - 10; m < point.x + 10; m++) {
				for (int n = point.y - 10; n < point.y + 10; n++) {

					light = (int) dotLight[(m + 10 - point.x)][(n + 10 - point.y)];
					light = Math.abs(light);
					if(light > 255) {
						light = 255;
					}
					g2d.setColor(new Color(light, light, light));
					g2d.drawLine(m, n, m, n);
					
					for(int j = 0;j < pointList.size();j++) {
						if(i != j && calcuDistance(pointList.get(i).x, pointList.get(i).y, pointList.get(j).x, pointList.get(j).y)  < 20) {
							if(calcuDistance(m, n, pointList.get(j).x, pointList.get(j).y) < mostDistance / 20) {
								light = (int) dotLight[(m + 10 - point.x)][(n + 10 - point.y)]
										+Math.abs(distance2Light(calcuDistance(m, n, pointList.get(j).x, pointList.get(j).y)));
								dotLight[(m + 10 - point.x)][(n + 10 - point.y)] = light;
								if(light > 255) {
									pointList1.add(new Point(m, n));
								}
								if(light > 255) {
									light = 255;
								}
								g2d.setColor(new Color(light, light, light));
								g2d.drawLine(m, n, m, n);
							}
						}
					}
				}
			}
			
		}
		for(int i =0;i < pointList1.size();i++) {
			g2d.setColor(Color.red);
			g2d.drawLine(pointList1.get(i).x, pointList1.get(i).y, pointList1.get(i).x, pointList1.get(i).y);
		}
		*/
		
		for(int i =0 ; i< pointList.size();i++) {
			point = pointList.get(i);
			dotLight = getlight(point.x, point.y);
			for(int m = point.x - 100; m < point.x + 100; m++) {
				for(int n = point.y - 100; n < point.y + 100; n++) {
					light = Math.abs(dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]);
					light = distance2Light(calcuDistance(m, n, point.x, point.y));
					if(light > 255) {
						light = 255;
					}
					//light = Math.abs(distance2Light(calcuDistance(m, n, point.x, point.y)));
					g2d.setColor(new Color(light, light, light));
					g2d.drawLine(m, n, m, n);
					
					for(int j =0;j < pointList.size();j++) {
						point1 = pointList.get(j);
						if(i != j && calcuDistance(point.x, point.y, point1.x, point1.y) < mostDistance) {
							if(calcuDistance(m, n, point1.x, point1.y) < mostDistance / 2) {
								dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]
										= dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]
										+distance2Light(calcuDistance(m, n, point1.x, point1.y));
//								light = distance2Light(calcuDistance(m, n, point.x, point.y))
//										+distance2Light(calcuDistance(m, n, point1.x, point1.y));
								light = dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize];
								
								light = Math.abs(light);
								
								if(light > mostLight) {
									pointList1.add(new Point(m, n));
								}
								if(light >255) {
									light = 255;
								}
								g2d.setColor(new Color(light, light, light));
								g2d.drawLine(m, n, m, n);
								
							}
						}
					}
					
				}
			}
		}
		
	
		for(int i =0;i < pointList1.size();i++) {
			g2d.setColor(Color.red);
			g2d.drawLine(pointList1.get(i).x, pointList1.get(i).y, pointList1.get(i).x, pointList1.get(i).y);
		}
		
		/*
		for(int i =0 ; i< pointList.size();i++) {
			point = pointList.get(i);
			dotLight = getlight(point.x, point.y);
			for(int j=0;j < pointList.size();j++) {
				point1 = pointList.get(j);
				dotLight1 = getlight(point1.x, point1.y);
				distance = calcuDistance(point.x, point.y, point1.x, point1.y);
				if(i != j &&distance < mostDistance ) {
					for(int m = point.x - 100; m < point.x + 100; m++) {
						for(int n = point.y - 100; n < point.y + 100; n++) {
							for (int i1 = point1.x - 100; i1 < point1.x + 100; i1++) {
								for (int j1 = point1.y - 100; j1 < point1.y + 100; j1++) {
									if(i1 == m && j1 == n) {
										light = (int) (Math.abs(dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]
												+dotLight1[(i1 + 100 - point1.x) / lightSize][(j1 + 100 - point1.y)/ lightSize]
												+Math.abs(distance2Light(calcuDistance(m, n, pointList.get(j).x, pointList.get(j).y)))));
										/*
										if(light > color) {
											light = color;
											pointList1.add(new Point(i1, j1));
//											mapPoint.put(new Point(i1, j1), light);
										}
										*/
			/*
										if(light > 255) {
											light = 255;
											
										}
										g2d.setColor(new Color(light, light, light));
										//g2d.setColor(new Color(255, 0, 0));
										g2d.drawLine(i1, j1, i1, j1);
									}
								}
							}
						}
					}
				}
			}
		}
		*/
		/*
		for(int i =0;i < pointList1.size();i++) {
			light =  mapPoint.get(pointList1.get(i));
			if(light > 255) {
				light = 255;
			}
			g2d.setColor(new Color(light, light, light));
			g2d.drawLine(pointList1.get(i).x, pointList1.get(i).y, pointList1.get(i).x, pointList1.get(i).y);		
				
		}
		*/
	}

	/**
	 * �������
	 * 
	 * @param x1 ��ʼ�������
	 * @param y1 ��ʼ��������
	 * @param x2 ��ֹ�������
	 * @param y2 ��ֹ��������
	 * @return
	 */
	private static int calcuDistance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}

	// ����GUI
	private static void createAndShowGui() {
		JFrame frame = new JFrame("Smooth Curve");
		frame.setContentPane(new Test());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(3);
		frame.setSize(1000, 1000);
//		frame.setBackground(Color.black);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * �������
	 * 
	 * @param x1 ��ʼ�������
	 * @param y1 ��ʼ��������
	 * @param x2 ��ֹ�������
	 * @param y2 ��ֹ��������
	 * @return
	 */
	public static double calDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	/**
	 * ��������ȵ�Ӱ��
	 * 
	 * @param distance ����
	 * @return
	 */
	public static int distance2Light(double distance) {
		if (distance > 100) {
			distance = 100;
		}
		return (int) (100 - distance);
	}

	/**
	 * ������ȡ����
	 * 
	 * @param x0 ������
	 * @return
	 */
	public static int[][] getlight(int x0, int y0) {
		for (int i = x0 - 100; i < x0 + 100; i++) {
			for (int j = y0 - 100; j < y0 + 100; j++) {
				dotLight[(i + 100 - x0) / 10][(j + 100 - y0) / 10] = distance2Light(calDistance(x0, y0, i, j));
			}
		}
		return dotLight;
	}
	
	public static int[][] getlight10(int x0, int y0) {
		for (int i = x0 - 10; i < x0 + 10; i++) {
			for (int j = y0 - 10; j < y0 + 10; j++) {
				dotLight[(i + 10 - x0)][(j + 10 - y0)] = distance2Light10(calcuDistance(x0, y0, i, j));
			}
		}
		return dotLight;
	}

	private static int distance2Light10(int distance) {
		return 100 - distance * 10;
	}

	/**
	 * ��������
	 * 
	 * @param dotLight
	 */
	public static void clearArray(int[][] dotLight) {
		for (int i = 0; i < dotLight.length; i++) {
			for (int j = 0; j < dotLight[i].length; j++) {
				dotLight[i][j] = 0;
			}
		}
	}
}
