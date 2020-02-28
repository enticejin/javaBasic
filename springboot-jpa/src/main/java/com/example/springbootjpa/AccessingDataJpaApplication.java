package com.example.springbootjpa;


import com.example.springbootjpa.accessingdatajpa.Customer;
import com.example.springbootjpa.accessingdatajpa.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AccessingDataJpaApplication {
    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class);
    }
    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepository){
        return (args) -> {
          //保存一些customer
          customerRepository.save(new Customer("Jack", "michle"));
          customerRepository.save(new Customer("xiao", "hua"));
          customerRepository.save(new Customer("xiao", "ming"));
          customerRepository.save(new Customer("xiao", "hong"));
          customerRepository.save(new Customer("xiao", "japan"));

          //查询所有的customer
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : customerRepository.findAll()){
                log.info(customer.toString());
            }
            log.info(" ");
            //根据最后的名字查询customer
            Customer customer = customerRepository.findById(1L);
            log.info("Customer found with findById(1L): ");
            log.info("-------------------------------");
            log.info(customer.toString());
            log.info(" ");
            //根据最后的名字查询customer
            log.info("Customer found with findByLastName('ming'):");
            log.info("--------------------------------------------");
            for (Customer ming : customerRepository.findByLastName("ming")){
                log.info(ming.toString());
            }
            log.info(" ");
        };
    }
}
