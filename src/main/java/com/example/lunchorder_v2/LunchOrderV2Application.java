package com.example.lunchorder_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LunchOrderV2Application {

    public static void main(String[] args) {
        SpringApplication.run(LunchOrderV2Application.class, args);
    }
}
