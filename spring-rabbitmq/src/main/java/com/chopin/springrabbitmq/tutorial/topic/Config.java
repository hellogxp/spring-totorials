package com.chopin.springrabbitmq.tutorial.topic;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author chopin
 * @version 1.0
 * @description: TODO
 * @date 2021/12/26 17:04
 */
@Profile("work-queues")
@Configuration
public class Config {

    @Bean
    public Queue hello() {
        return new Queue("Hello");
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Receiver receiver1() {
            return new Receiver(1);
        }

        @Bean
        public Receiver receiver2() {
            return new Receiver(2);
        }
    }

    @Profile("sender")
    @Bean
    public Sender sender() {
        return new Sender();
    }
}