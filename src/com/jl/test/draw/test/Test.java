package com.jl.test.draw.test;
/** 
* @version 创建时间：2020年3月31日 上午10:13:56
* 类说明 
*/
//Test.java  
import java.awt.BorderLayout;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  
import javax.swing.JFrame;  

public class Test  
{  

 /** 
  * @param args 
  */  
 public static void main(String[] args)  
 {  
	 JFrame frame=new JFrame("Test Chart");  
	 RealTimeChart rtcp=new RealTimeChart("Random Data","x","y");  
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
