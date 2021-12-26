package com.chopin.springrabbitmq.tutorial.topic;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author chopin
 * @version 1.0
 * @description: TODO
 * @date 2021/12/26 17:05
 */
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello");

        if (dots.getAndIncrement() == 4) {
            dots.set(1);
        }

        for (int i = 0; i < dots.get(); i++) {
            builder.append('.');
        }

        builder.append(count.incrementAndGet());

        String message = builder.toString();

        rabbitTemplate.convertAndSend(queue.getName(), message);

        System.out.println(" [x] Sent '" + message + "'");
    }
}