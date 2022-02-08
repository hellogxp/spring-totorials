package com.chopin.publisherconfirms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PublisherConfirmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublisherConfirmsApplication.class, args);
    }

}
