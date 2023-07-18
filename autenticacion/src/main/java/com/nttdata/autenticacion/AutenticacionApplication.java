package com.nttdata.autenticacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AutenticacionApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutenticacionApplication.class, args);
    }
}