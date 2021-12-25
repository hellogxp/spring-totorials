package com.chopin.springrabbitmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringRabbitmqApplication {

    @Profile("usage_message")
    @Bean
    public CommandLineRunner usage() {
        return args -> {
            System.out.println("This app uses Spring Profiles to control its behaviour. \n");
            System.out.println("Step 1: ./mvnw -s /path/to/settings.xml clean package");
            System.out.println("Step 2: java -jar rabbit-tutorials.jar --spring.profiles.active=welcome,receiver");
            System.out.println("Step 3: java -jar rabbit-tutorials.jar --spring.profiles.active=welcome,sender");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRabbitmqApplication.class, args);
    }

}
