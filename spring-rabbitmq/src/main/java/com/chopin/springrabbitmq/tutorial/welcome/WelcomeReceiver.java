package com.chopin.springrabbitmq.tutorial.welcome;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author chopin
 * @version 1.0
 * @description: TODO
 * @date 2021/12/25 23:30
 */
@RabbitListener(queues = "welcome")
public class WelcomeReceiver {
    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [x] Received: '" + in + "'");
    }
}