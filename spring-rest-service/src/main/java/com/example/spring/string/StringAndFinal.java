package com.example.spring.string;

/**
 * String 类和常量池
 */
public class StringAndFinal {
    public static void main(String[] args) {
        /*
        //String 类的两种创建方式
        //方式一：
        String str1 = "abc";
        //方式二：
        String str2 = new String("abc");
        System.out.println("str1 = "+str1);
        System.out.println("str2 = "+str2);
        System.out.println(str1 == str2);
        System.out.println(str1.equals(str2));
         * 运行结果：
         * str1 = abc
         * str2 = abc
         * false
         * true
         */

        /**
         * String 类型的常量池比较特殊。它的主要使用方法有两种：
         * String.intern() 是一个 Native 方法，它的作用是：如果运行时常量池中已经包含一个等于此 String 对象内容的字符串，
         * 则返回常量池中该字符串的引用；如果没有，则在常量池中创建与此 String 内容相同的字符串，并返回常量池中创建的字符串的引用。
         */
        /*
        String s1 = "计算机";
        String s2 = s1.intern();
        String s3 = new String("计算机");
        System.out.println("s2 =" + s2);
        System.out.println(s1 == s2); //true
        System.out.println(s3 == s2); //false

         */

        //String 字符串拼接
        String str1 = "str";
        String str2 = "ing";
        String str3 = "str"+"ing";//常量池中的对象
        String str5 = "string";//常量池中的对象
        String str4 = str1 + str2;//在堆上创建的新对象
        System.out.println(str3 == str4);//false
        System.out.println(str3 == str5);//true
        System.out.println(str4 == str5);//false
    }
}
