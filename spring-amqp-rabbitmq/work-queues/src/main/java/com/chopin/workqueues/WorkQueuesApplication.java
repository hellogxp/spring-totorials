package com.chopin.workqueues;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WorkQueuesApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkQueuesApplication.class, args);
    }

}
