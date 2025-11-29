package com.prologapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.prologapp")
public class TireManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TireManagerApplication.class, args);
    }
}
