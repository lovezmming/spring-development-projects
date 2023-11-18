package com.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ScheduleJobServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleJobServiceApplication.class, args);
    }

}
