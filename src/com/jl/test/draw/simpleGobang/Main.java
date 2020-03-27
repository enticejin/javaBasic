package com.jl.test.draw.simpleGobang;
import java.awt.Container;
import javax.swing.JFrame;
 
public class Main extends JFrame{
	private DrawChessBoard drawChessBoard;
	public Main() {		
		drawChessBoard = new DrawChessBoard();
		
		//Frame标题
		setTitle("总示意图");
		
		Container containerPane =getContentPane();
		containerPane.add(drawChessBoard);		
	}
	public static void main(String[] args) {
		Main m = new Main();
		m.setVisible(true);
	}
}