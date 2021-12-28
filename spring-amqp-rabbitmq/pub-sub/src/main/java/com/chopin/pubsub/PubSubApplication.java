package com.chopin.pubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PubSubApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubSubApplication.class, args);
    }

}
