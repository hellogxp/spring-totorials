package com.chopin.publisherconfirms;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Chopin
 * @date 2022/2/8
 */
@Profile("publisher-confirms")
@Configuration
public class Config implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {
    @Autowired
    RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct.exchange");
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("Exchange Receive message" + correlationData.getId());
            logger.info("{}: Exchange Receive message", correlationData.getId());
        } else {
            System.out.println("Exchange have not receive message" + correlationData.getId());
            logger.error("{}: Exchange have not receive message, cause {}", correlationData.getId(), cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        logger.info("Message sent to queue failed: {}",
            JSON.toJSONString(returnedMessage.getMessage().getMessageProperties()));
    }

    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Profile("receiver")
    private static class ReceiverConfig {
        @Bean
        public Queue queue() {
            return new Queue("queue_one");
        }

        @Bean
        public Binding binding(DirectExchange directExchange, Queue queue) {
            return BindingBuilder.bind(queue).to(directExchange).with("routing_key_one");
        }

        @Bean
        public Receiver receiver() {
            return new Receiver();
        }
    }

    @Profile("sender")
    @Bean
    public Sender sender() {
        return new Sender();
    }

}