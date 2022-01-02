package com.chopin.rpc;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Project spring-tutorials
 * Package com.chopin.rpc
 *
 * @author Chopin
 * @date 2022/1/1 21:58
 */

public class Client {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    Integer start = 0;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        System.out.println(" [x] Requesting fib(" + start + ")");
        Integer response = (Integer) rabbitTemplate.convertSendAndReceive(directExchange.getName(), "rpc", start++);
        System.out.println(" [.] Got '" + response + "'");
    }
}