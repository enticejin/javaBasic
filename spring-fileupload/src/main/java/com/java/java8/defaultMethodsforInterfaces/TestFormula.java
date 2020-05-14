package com.java.java8.defaultMethodsforInterfaces;

public class TestFormula {
    public static void main(String[] args) {
        // TODO 通过匿名内部类方式访问接口
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a);
            }
        };
        System.out.println(formula.calculate(100));
        System.out.println(formula.sqrt(16));
    }


}
