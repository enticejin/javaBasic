package com.jl.test.draw;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** 
* @version 创建时间：2020年3月27日 下午2:49:37
* 类说明 
*/
	public class DeleteString {
		 
		public static void main(String[] args) {
	 
			String str1 = "abc|123def123asdkfjl|123";
			String[] str3 = str1.split("|");
			for(String str : str3) {
				System.out.println(str);
			}
			String str2 = "123";
			DeleteString d = new DeleteString();
			Object[] result = d.deleteSubString(str1, str2);
			System.out.println("删除字串后："+result[0]);
			System.out.println("删除了" +result[1] + "次");
		}
		
		/**
		 * 删除指定字符串
		 * @param str1 原字符串
		 * @param str2 需要删除的字符串
		 * @return
		 */
		public Object[] deleteSubString(String str1,String str2) {
			StringBuffer sb = new StringBuffer(str1);
			int delCount = 0;
			Object[] obj = new Object[2];
	 
			while (true) {
				int index = sb.indexOf(str2);
				if(index == -1) {
					break;
				}
				sb.delete(index, index+str2.length());
				delCount++;
				
			}
			if(delCount!=0) {
				obj[0] = sb.toString();
				obj[1] = delCount;
			}else {
				//不存在返回-1
				obj[0] = -1;
				obj[1] = -1;
			}
			
			return obj;
		}
		/**
		 * 获取切割后的字符串
		 * @param str1 原字符串
		 * @param str2 切割的字符串
		 * @return
		 */
		public Map<String, Object> getSubString(String str1,String str2) {
			List<String> stringList1 = new ArrayList<String>();
			List<String> stringList2 = new ArrayList<String>();
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuffer sb = new StringBuffer(str1);
			int delCount = 0;
			Object[] obj = new Object[2];
			
			while (true) {
				int index = sb.indexOf(str2);
				if(index == -1) {
					break;
				}
				sb.delete(index, index+str2.length());
				
				delCount++;
				
			}
			if(delCount!=0) {
				obj[0] = sb.toString();
				obj[1] = delCount;
			}else {
				//不存在返回-1
				obj[0] = -1;
				obj[1] = -1;
			}
			map.put("stringList1", stringList1);
			map.put("stringList1", stringList2);
			return map;
		}
		
	 
	}
