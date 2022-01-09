package com.chopin.hellokafka.controller;

import com.chopin.hellokafka.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project spring-tutorials
 * Package com.chopin.hellokafka.controller
 *
 * @author Chopin
 * @date 2022/1/7 13:38
 */
@RestController
public class SendMessageController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    @GetMapping("string/{message}")
    public void sendString(@PathVariable String message) {
        ListenableFuture<SendResult<String, String>> future = this.stringKafkaTemplate.send("topic-two", message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.info("Unable to send message=[" + message + "] due to" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Send message successfully: {}, offset=[{}]", message, result.getRecordMetadata().offset());
            }
        });
    }

    @GetMapping("message/{message}")
    public void send(@PathVariable String message) {
        //this.kafkaTemplate.send("hellokafka", new Message("Edinburgh", message));
        ListenableFuture<SendResult<String, Message>> future = this.kafkaTemplate.send("topic-one", new Message("Edinburgh", message));
        future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("Unable to send message=[" + message + "] due to" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Message> result) {
                logger.info("Send message successfully: {}, offset = [{}]", message, result.getRecordMetadata().offset());
            }
        });

    }
}