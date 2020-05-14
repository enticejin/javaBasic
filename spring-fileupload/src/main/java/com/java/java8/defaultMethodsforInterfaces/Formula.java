package com.java.java8.defaultMethodsforInterfaces;

interface Formula {
    double calculate(int a);

    default double sqrt(int a){
        return Math.sqrt(a);
    }
}
