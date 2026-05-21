package com.fastproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RunWeb {
    static void main() {
        SpringApplication.run(RunWeb.class);
    }
}
