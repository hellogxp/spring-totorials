package com.chopin.delayqueue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DelayQueueApplicationTests {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        System.out.println(new Date());
        rabbitTemplate.convertAndSend(Config.ORDER_EXCHANGE, Config.ORDER_ROUTING_KEY, "delay message");
    }

}
