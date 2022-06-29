package com.chopin.connector.kafka.controller;

import com.chopin.connector.kafka.domian.Message;
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
 * @author Chopin
 * @date 2022/6/28
 */
@RestController
public class SendMessageController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Message> messageKafkaTemplate;

    @GetMapping("string/{message}")
    public void sendString(@PathVariable String message) {
        ListenableFuture<SendResult<String, String>> future = this.stringKafkaTemplate.send("topic-one", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.info("Send message failed=[" + message + "] due to" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Send message successfully: {}, offset=[{}]", message, result.getRecordMetadata().offset());
            }
        });
    }

    @GetMapping("message/{message}")
    public void sendMessage(@PathVariable String message) {
        ListenableFuture<SendResult<String, Message>> future = this.messageKafkaTemplate.send("topic-two",
            new Message("London", message, 2));

        future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("Fail to send message=[" + message + "] due to" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Message> result) {
                logger.info("Send message successfully: {}, offset = [{}]", message, result.getRecordMetadata().offset());
            }
        });
    }
}