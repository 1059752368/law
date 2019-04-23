package com.biyesheji.law;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
        //(exclude = DataSourceAutoConfiguration.class)
public class LawApplication {

    public static void main(String[] args) {
        SpringApplication.run(LawApplication.class, args);
    }

}
