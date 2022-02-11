package com.chopin.delayqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Chopin
 * @date 2022/2/10
 */
@Component
public class DlxConsumer {
    private static final Logger logger = LoggerFactory.getLogger(DlxConsumer.class);

    @RabbitListener(queues = "#{dlxQueue.name}")
    public void receiver(String message) {
        logger.info("[x] Get message from dead letter exchange:" +message);
    }
}