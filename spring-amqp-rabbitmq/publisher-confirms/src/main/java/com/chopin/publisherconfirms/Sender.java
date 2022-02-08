package com.chopin.publisherconfirms;

import java.util.UUID;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Chopin
 * @date 2022/2/8
 */
public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder stringBuilder = new StringBuilder("Hello");
        String message = stringBuilder.toString();
        rabbitTemplate.convertAndSend(directExchange.getName(), "routing_key_one", message,
            new CorrelationData(UUID.randomUUID().toString()));
    }

    @Scheduled(fixedDelay = 2000, initialDelay = 500)
    public void sendToNonExistQueue() {
        String string = "Cool";
        rabbitTemplate.convertAndSend(directExchange.getName(), "routing_key_two", string,
            new CorrelationData(UUID.randomUUID().toString()));
    }

}