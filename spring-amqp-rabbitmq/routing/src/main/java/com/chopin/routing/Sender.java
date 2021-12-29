package com.chopin.routing;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Project: spring-tutorials
 * Package: com.chopin.routing
 * @author chopin
 * @date 2021/12/29
 */
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    AtomicInteger index = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    private final String[] keys = {"orange", "black", "green"};

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if(this.index.getAndIncrement() == keys.length) {
            this.index.set(1);
        }
        String key = keys[this.index.get() - 1];

        builder.append(key);
        for (int i = 0; i < index.get(); i++) {
            builder.append('.');
        }
        builder.append(this.count.incrementAndGet());
        String message = builder.toString();
        rabbitTemplate.convertAndSend(directExchange.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }

}