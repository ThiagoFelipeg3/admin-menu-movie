package com.admin.menu.movie.infrastructure;

import com.admin.menu.movie.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Init!");
        SpringApplication.run(WebServerConfig.class, args);
    }
}