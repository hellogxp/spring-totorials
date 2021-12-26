package com.chopin.helloworld;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author chopin
 * @version 1.0
 * @description: TODO
 * @date 2021/12/26 20:11
 */
@RabbitListener(queues = "welcome")
public class Receiver {
    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [x] Received: '" + in + "'");
    }
}