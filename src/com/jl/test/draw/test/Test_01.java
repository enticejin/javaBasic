package com.jl.test.draw.test;
/** 
* @version ����ʱ�䣺2020��3��31�� ����10:13:56
* ��˵�� 
*/
//Test.java  
import java.awt.BorderLayout;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class Test_01  
{  

 /** 
  * @param args 
  */  
 public static void main(String[] args)  
 {  
	 JFrame frame=new JFrame("Test Chart");  
	 RealTimeChart_01 rtcp=new RealTimeChart_01("Distance Data","","distance");  
	 frame.getContentPane().add(rtcp,new BorderLayout().CENTER);  
	 frame.pack();  
	 frame.setVisible(true);  
	 (new Thread(rtcp)).start();  
	 frame.addWindowListener(new WindowAdapter()   
	 {  
	     public void windowClosing(WindowEvent windowevent)  
	     {  
	         System.exit(0);  
	     }  
	
	 });  
 }  
} 
