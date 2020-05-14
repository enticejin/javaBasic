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



public class Test2WideArray extends JPanel {
	public static double[][] dotLight = new double[400][400];
//	public static double[][] dotLight = new double[100][100];
	public static double[][] dotLight1 = new double[400][400];

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		Point[] pointArr = {
				
				new Point(200,150), 
				new Point(205,150), 
				new Point(210,150), 
				new Point(270,150), 
//				new Point(150, 300),  
//				new Point(200, 300), 
//				new Point(250, 300), 
//				new Point(500, 500), 
//				new Point(300, 300), 
//				new Point(200, 400),
//				new Point(150,400),
		};
		// �����
		Point point, point1;
		List<Point> pointList1 = new ArrayList<Point>();
		Map<Point, Integer> mapPoint = new HashMap<Point, Integer>();
		// ��ʼ��ɫֵ
		int color = 255;
		// ��ʼ����
		double distance = 0;
		double mostDistance = 200;
		// ��ʼ����
		int light = 0;
		int lightSize = 10;
		if(pointArr.length == 1) {
			point =  pointArr[0];
			dotLight = getlight(point.x, point.y);
			for (int m = point.x - 100; m < point.x + 100; m++) {
				for (int n = point.y - 100; n < point.y + 100; n++) {
//					dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]
//							= distance2Light(calcuDistance(m,n, point.x, point.y));
					light = (int) (Math
							.abs(dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]));
					light = Math.abs( distance2Light(calcuDistance(m, n, point.x, point.y)));
					g2d.setColor(new Color(light, light, light));
					g2d.drawLine(m, n, m, n);
				}
			}
		}
		else {
			for(int i=0;i < pointArr.length;i++) {
				point = pointArr[i];
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
						
						for(int j = 0;j < pointArr.length;j++) {
							if(i != j && calcuDistance(pointArr[i].x, pointArr[i].y, pointArr[j].x, pointArr[j].y)  < 20) {
								if(calcuDistance(m, n, pointArr[j].x, pointArr[j].y) < 10) {
									light = (int) dotLight[(m + 10 - point.x)][(n + 10 - point.y)]
											+Math.abs(distance2Light(calcuDistance(m, n, pointArr[j].x, pointArr[j].y)));
									dotLight[(m + 10 - point.x)][(n + 10 - point.y)] = light;
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
		/*
		else if(pointArr.length >= 2) {
			for(int i=0;i < pointArr.length;i++) {
				point = pointArr[i];
				dotLight = getlight(point.x, point.y);
				for (int m = point.x - 100; m < point.x + 100; m++) {
					for (int n = point.y - 100; n < point.y + 100; n++) {

						light = (int) dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize];
						light = Math.abs(light);
						if(light > 255) {
							light = 255;
						}
						g2d.setColor(new Color(light, light, light));
						g2d.drawLine(m, n, m, n);
						
						for(int j = 0;j < pointArr.length;j++) {
							if(i != j && calcuDistance(pointArr[i].x, pointArr[i].y, pointArr[j].x, pointArr[j].y)  < 200) {
								if(calcuDistance(m, n, pointArr[j].x, pointArr[j].y) < 100) {
									light = (int) Math.abs(dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize])
											+Math.abs(distance2Light(calcuDistance(m, n, pointArr[j].x, pointArr[j].y)));
									dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize] = light;
									if(light > 255) {
										light = 255;
									}
									if(light > 100) {
										pointList1.add(new Point(m, n));
									}
									g2d.setColor(new Color(light, light, light));
									g2d.drawLine(m, n, m, n);
								}
							}
						}
						
					}
				}
			}
			
			for(int i =0; i < pointList1.size();i++) {
				g2d.setColor(new Color(255, 0, 0));
				g2d.drawLine(pointList1.get(i).x, pointList1.get(i).y, pointList1.get(i).x, pointList1.get(i).y);
			}
			/*
			for(int i =0;i < pointArr.length;i++) {
				point = pointArr[i];
				dotLight = getlight(point.x, point.y);
				for(int j =0;j < pointArr.length;j++) {
					point1 = pointArr[j];
					dotLight1 = getlight(point1.x, point1.y);
					distance = calcuDistance(point.x, point.y, point1.x, point1.y);
					if(distance < mostDistance && i != j) {
						for(int m = point.x - 100; m < point.x + 100; m++) {
							for(int n = point.y - 100; n < point.y + 100; n++) {
								for (int i1 = point1.x - 100; i1 < point1.x + 100; i1++) {
									for (int j1 = point1.y - 100; j1 < point1.y + 100; j1++) {
										if(i1 == m && j1 == n) {
											if(calcuDistance(i1, j1, point.x, point.y) < mostDistance) {
												light = (int) (Math.abs(dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]
														+dotLight1[(i1 + 100 - point1.x) / lightSize][(j1 + 100 - point1.y)/ lightSize]
														+Math.abs(distance2Light(calcuDistance(i1, j1, pointArr[i].x, pointArr[i].y)))));
												//�����ȴ���100�����������
												if(light > 100) {
													//light = color;
													pointList1.add(new Point(i1, j1));
//													mapPoint.put(new Point(i1, j1), light);
												}
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
			}
			*/
		}
		/*
		else if(pointArr.length > 2){
			for(int i=0;i < pointArr.length;i++) {
				point = pointArr[i];
				dotLight = getlight(point.x, point.y);
				for (int m = point.x - 100; m < point.x + 100; m++) {
					for (int n = point.y - 100; n < point.y + 100; n++) {
						light = (int) (Math
								.abs(dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]));
						//light = Math.abs( distance2Light(calcuDistance(m, n, point.x, point.y)));
						g2d.setColor(new Color(light, light, light));
						g2d.drawLine(m, n, m, n);
					}
				}
			}
			for(int i =0;i < pointArr.length;i++) {
				point = pointArr[i];
				dotLight = getlight(point.x, point.y);
				for(int j =0;j < pointArr.length;j++) {
					point1 = pointArr[j];
					dotLight1 = getlight(point1.x, point1.y);
					distance = calcuDistance(point.x, point.y, point1.x, point1.y);
					if(distance < mostDistance && i != j) {
						for(int m = point.x - 100; m < point.x + 100; m++) {
							for(int n = point.y - 100; n < point.y + 100; n++) {
								for (int i1 = point1.x - 100; i1 < point1.x + 100; i1++) {
									for (int j1 = point1.y - 100; j1 < point1.y + 100; j1++) {
										if(i1 == m && j1 == n) {
											if(calcuDistance(i1, j1, point.x, point.y) < mostDistance) {
												light = (int) (Math.abs(dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]
														+dotLight1[(i1 + 100 - point1.x) / lightSize][(j1 + 100 - point1.y)/ lightSize]
														+Math.abs(distance2Light(calcuDistance(i1, j1, pointArr[i].x, pointArr[i].y)))));
												//�����ȴ���100�����������
												if(light > 100) {
													//light = color;
													pointList1.add(new Point(i1, j1));
//													mapPoint.put(new Point(i1, j1), light);
												}
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
			}
			*/
		/*
		for(int i =0;i < pointList1.size();i++) {
//			light =  mapPoint.get(pointList1.get(i));
			if(light > 255) {
				light = 255;
			}
			g2d.setColor(new Color(light, light, light));
			g2d.setColor(new Color(255, 0, 0));
			g2d.drawLine(pointList1.get(i).x, pointList1.get(i).y, pointList1.get(i).x, pointList1.get(i).y);		
				
		}
		*/
		/*
		if(pointArr.length == 1) {
			point =  pointArr[0];
			dotLight = getlight(point.x, point.y);
			for (int m = point.x - 100; m < point.x + 100; m++) {
				for (int n = point.y - 100; n < point.y + 100; n++) {
					dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]
							= distance2Light(calcuDistance(m,n, point.x, point.y));
					light = (int) (Math
							.abs(dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]));
					//light = Math.abs( distance2Light(calcuDistance(m, n, point.x, point.y)));
					g2d.setColor(new Color(light, light, light));
					g2d.drawLine(m, n, m, n);
				}
			}
		}
		for(int i =0;i < pointArr.length;i++) {
			point = pointArr[i];
			dotLight = getlight(point.x, point.y);
			for(int j = 0;j < pointArr.length;j++) {
				if(i != j && calcuDistance(point.x, point.y, pointArr[j].x, pointArr[j].y) < mostDistance) {
					for (int m = point.x - 100; m < point.x + 100; m++) {
						for (int n = point.y - 100; n < point.y + 100; n++) {
							if(calcuDistance(m,n, pointArr[j].x, pointArr[j].y) < mostDistance) {
								dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize] = 
										distance2Light(calcuDistance(m,n, point.x, point.y))
										+distance2Light(calcuDistance(m,n, pointArr[j].x, pointArr[j].y));

								light = (int) (Math
										.abs(dotLight[(m + 100 - point.x) / lightSize][(n + 100 - point.y) / lightSize]));
								if(light > 130) {
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
			//g2d.drawLine(pointList1.get(i).x, pointList1.get(i).y, pointList1.get(i).x, pointList1.get(i).y);
		}
		*/
	}

	/**
	 * ��������ȵ�Ӱ��
	 * 
	 * @param distance ����
	 * @return
	 */
	private static int distance2Light(int distance) {
		return 100 - distance;
	}
	private static int distance2Light10(int distance) {
		return 100 - distance * 10;
	}

	/**
	 * �������
	 * 
	 * @param i ��ʼ������
	 * @param j ��ʼ������
	 * @param k ��ֹ������
	 * @param l ��ֹ������
	 * @return
	 */
	private static int calcuDistance(int i, int j, int k, int l) {

		return (int) Math.sqrt((k - i) * (k - i) + (l - j) * (l - j));
	}

	/**
	 * �����������
	 * 
	 * @param list �������б�
	 * @return
	 */
	public int allLight(List<Integer> list) {
		int allLight = 0;
		for (int i = 0; i < list.size(); i++) {
			allLight += distance2Light(list.get(i));
		}
		return allLight;

	}

	public double[][] calcuLight(int x, int y) {
		for (int i = x - 10; i < x + 10; i++) {
			for (int j = y - 10; j < y + 10; j++) {
				dotLight[i + 10 - x][j + 10 - y] = dotLight[i + 10 - x][j + 10 - y]
						+ distance2Light(calcuDistance(i, j, x, y));
			}
		}
		return dotLight;
	}

	public static double[][] getlight(int x0, int y0) {
		for (int i = x0 - 100; i < x0 + 100; i++) {
			for (int j = y0 - 100; j < y0 + 100; j++) {
				dotLight[(i + 100 - x0) / 10][(j + 100 - y0) / 10] = distance2Light(calcuDistance(x0, y0, i, j));
			}
		}
		return dotLight;
	}
	public static double[][] getlight10(int x0, int y0) {
		for (int i = x0 - 10; i < x0 + 10; i++) {
			for (int j = y0 - 10; j < y0 + 10; j++) {
				dotLight[(i + 10 - x0)][(j + 10 - y0)] = distance2Light10(calcuDistance(x0, y0, i, j));
			}
		}
		return dotLight;
	}

	public void clearArray(double[][] dotLight2) {
		for (int i = 0; i < dotLight2.length; i++) {
			for (int j = 0; j < dotLight2[i].length; j++) {
				dotLight2[i][j] = 0;
			}
		}
	}

	public static void main(String[] args) {
		Test2WideArray t = new Test2WideArray();
		JFrame frame = new JFrame("Smooth Curve");
		frame.setContentPane(new Test2WideArray());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(3);
		frame.setSize(1000, 1000);
		frame.add(t);
		//frame.setBackground(Color.black);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
