package com.chopin.delayqueue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Chopin
 * @date 2022/2/11
 */
@Component
public class Sender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend(Config.ORDER_EXCHANGE, Config.ORDER_ROUTING_KEY, "Delay message");
    }
}