package com.chopin.hellokafka.listener;

import com.chopin.hellokafka.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Project spring-tutorials
 * Package com.chopin.hellokafka.listener
 *
 * @author Chopin
 * @date 2022/1/7 14:19
 */
@Component
public class KafkaMessageListener {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

/*    @KafkaListener(topics = "hellokafka", groupId = "consumer-group-one",
        topicPartitions = @TopicPartition(topic = "hellokafka", partitionOffsets = {@PartitionOffset(partition = "0",
         initialOffset = "0")}))
    public void listener(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("Receive message: {}, partition: {}", message, partition);
    }*/

    @KafkaListener(topics = "topic-one", groupId = "consumer-group-one")
    public void listen(Message message) {
        logger.info("Received message: {}", message);
    }

    @KafkaListener(topics = "topic-two", groupId = "consumer-group-one", containerFactory = "stringConcurrentKafkaListenerContainerFactory")
    public void stringListen(String message) {
        logger.info("Received message: {}", message);
    }
}