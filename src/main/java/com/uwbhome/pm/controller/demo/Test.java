package com.uwbhome.pm.controller.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
	public static void main(String[] args){
	   StringBuilder sb = new StringBuilder();
	   for(int i = 0;i < 10;i++) {
		   sb.append(i+",");
	   }
	   System.out.println(sb.substring(0, sb.length()-1));
	}
}
