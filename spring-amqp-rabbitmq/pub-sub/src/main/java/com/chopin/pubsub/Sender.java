package com.chopin.pubsub;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author chopin
 * @version 1.0
 * @description: TODO
 * @date 2021/12/27 23:15
 */
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanoutExchange;

    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello");
        if (dots.getAndIncrement() == 3) {
            dots.set(1);
        }

        for (int i = 0; i < dots.get(); i++) {
            builder.append('.');
        }

        builder.append(count.incrementAndGet());
        String message = builder.toString();
        rabbitTemplate.convertAndSend(fanoutExchange.getType(), "", message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}