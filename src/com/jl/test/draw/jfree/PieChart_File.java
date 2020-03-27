package com.jl.test.draw.jfree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/** 
* @version 创建时间：2020年3月24日 下午5:10:30
* 类说明 
*/
public class PieChart_File {
	public static void main(String[] args) throws Exception {
		String mobilebrands[] = {
			      "IPhone 5s",   
			      "SamSung Grand",   
			      "MotoG",            
			      "Nokia Lumia" 
			      };
			      
			      /* Create MySQL Database Connection */
			      Class.forName( "com.mysql.jdbc.Driver" );
			      Connection connect = DriverManager.getConnection( 
			      "jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useSSL=false" ,     
			      "root",     
			      "123456");
			      
			      Statement statement = connect.createStatement( );
			      ResultSet resultSet = statement.executeQuery("select * from dataset_tb" );
			      DefaultPieDataset dataset = new DefaultPieDataset( );
			      while( resultSet.next( ) ) 
			      {
			         dataset.setValue( 
			         resultSet.getString( "brandname" ) ,
			         Double.parseDouble( resultSet.getString( "datavalue" )));
			      }
			      JFreeChart chart = ChartFactory.createPieChart(
			         "Mobile Sales",  // chart title           
			         dataset,         // data           
			         true,            // include legend          
			         true,           
			         false );
			      int width = 560; /* Width of the image */
			      int height = 370; /* Height of the image */ 
			      File pieChart = new File( "D:/Pie_Chart.jpeg" );
			      ChartUtilities.saveChartAsJPEG( pieChart , chart , width , height );
			   }


}
