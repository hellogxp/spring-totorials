package com.chopin.springrabbitmq.tutorial.welcome;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author chopin
 * @version 1.0
 * @description: TODO
 * @date 2021/12/25 23:05
 */
@Profile("welcome")
@Configuration
public class Config {

    @Bean
    public Queue welcome() {
        return new Queue("welcome");
    }

    @Profile("receiver")
    @Bean
    public WelcomeReceiver receiver() {
        return new WelcomeReceiver();
    }

    @Profile("sender")
    @Bean
    public WelcomeSender sender() {
        return new WelcomeSender();
    }
}