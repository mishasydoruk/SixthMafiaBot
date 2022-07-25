package com.example.sixthmafiabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;


@Configuration
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SixthMafiaBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SixthMafiaBotApplication.class, args);
    }

}
