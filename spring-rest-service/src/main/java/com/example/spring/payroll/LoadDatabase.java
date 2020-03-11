package com.example.spring.payroll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    CommandLineRunner initDatabase(EmployeeRepository employeeRepository){
        return args -> {
          log.info("预先加载："+employeeRepository.save(new Employee("Yili","manager")));
            log.info("预先加载："+employeeRepository.save(new Employee("XXX","employee")));
        };
    }
}
