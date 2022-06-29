package com.chopin.connector.kafka.listen;

import com.chopin.connector.kafka.domian.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Chopin
 * @date 2022/6/28
 */
@Component
public class KafkaMessageListener {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

/*    @KafkaListener(topics = "topic-one", groupId = "consumer-group-one", containerFactory = "stringConcurrentKafkaListenerContainerFactory")
    public void stringListen(String message) {
        logger.info("Received message: {}", message);
    }*/

/*    @KafkaListener(topics = "topic-two", groupId = "consumer-group-one", containerFactory = "messageConcurrentKafkaListenerContainerFactory")
    public void messageListen(Message message) {
        logger.info("Received message: {}", message);
    }*/
}