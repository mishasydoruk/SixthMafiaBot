package com.example.sixthmafiabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableTransactionManagement(proxyTargetClass = false)
public class SixthMafiaBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SixthMafiaBotApplication.class, args);
    }

}
