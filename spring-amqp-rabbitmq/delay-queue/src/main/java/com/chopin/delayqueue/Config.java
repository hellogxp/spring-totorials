package com.chopin.delayqueue;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Chopin
 * @date 2022/2/10
 */
@Configuration
public class Config {
    public static final String ORDER_QUEUE = "order-queue";
    public static final String ORDER_EXCHANGE = "order-exchange";
    public static final String ORDER_ROUTING_KEY = "order-routing-key";
    public static final String DLX_QUEUE = "dlx-queue";
    public static final String DLX_EXCHANGE = "dlx-exchange";
    public static final String DLX_ROUTING_KEY = "dlx-routing-key";

    @Bean
    DirectExchange orderDirectExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    @Bean
    public Queue orderQueue() {
        Map<String,Object> args = new HashMap<>(16);
        args.put("x-message-ttl", 1000*20);
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        return new Queue(ORDER_QUEUE, true, false, false, args);
/*        return QueueBuilder.durable(ORDER_QUEUE).withArgument("dead-letter-exchange", DLX_EXCHANGE).withArgument(
            "x-dead-letter-routing-key", DLX_ROUTING_KEY).build();*/
    }

    @Bean
    Binding orderBinding(DirectExchange orderDirectExchange, Queue orderQueue) {
        return BindingBuilder.bind(orderQueue).to(orderDirectExchange).with(ORDER_ROUTING_KEY);
    }

    /**
     * DLX queue
     *
     * @return DLX queue, actually it is a normal queue, but it would be bound to the dead letter change
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue(DLX_QUEUE, true, false, false);
    }

    /**
     * Dead letter exchange
     *
     * @return DLX, it is a normal exchange, and will be bound to a queue as a dead letter exchange
     */
    @Bean
    public DirectExchange dlxDirectExchange() {
        return new DirectExchange(DLX_EXCHANGE, true, false);
    }

    @Bean
    public Binding dlxBinding(DirectExchange dlxDirectExchange, Queue dlxQueue) {
        return BindingBuilder.bind(dlxQueue).to(dlxDirectExchange).with(DLX_ROUTING_KEY);
    }



}