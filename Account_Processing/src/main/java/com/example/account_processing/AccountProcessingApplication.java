package com.example.account_processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountProcessingApplication.class, args);
    }

}
