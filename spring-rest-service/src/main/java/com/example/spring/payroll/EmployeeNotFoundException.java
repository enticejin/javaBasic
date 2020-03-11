package com.example.spring.payroll;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("没有找到对应的值："+id);
    }
}
