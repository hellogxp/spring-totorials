package com.chopin.topics;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Project: spring-tutorials
 * Package: com.chopin.topics
 *
 * @author Chopin
 */
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange topicExchange;

    AtomicInteger index = new AtomicInteger(0);
    AtomicInteger count = new AtomicInteger(0);

    private final String[] keys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox", "lazy.brown.fox",
        "lazy.pink.rabbit", "quick.brown.fox"};

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    private void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (this.index.getAndIncrement() == keys.length) {
            this.index.set(1);
        }
        String key = keys[this.index.get() - 1];
        builder.append(key).append(' ');
        builder.append(this.count.incrementAndGet());
        String message = builder.toString();
        rabbitTemplate.convertAndSend(topicExchange.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");

    }
}