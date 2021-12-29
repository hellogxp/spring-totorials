package com.chopin.routing;

import com.rabbitmq.client.AMQP.Queue.Bind;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Project: spring-tutorials
 * Package: com.chopin.routing
 * @author chopin
 * @date 2021/12/29
 */
@Profile("routing")
@Configuration
public class Config {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct");
    }

    @Profile("receiver")
    private static class ReceiverConfig{

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1a(DirectExchange directExchange, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(directExchange).with("orange");
        }

        @Bean
        public Binding binding1b(DirectExchange directExchange, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(directExchange).with("black");
        }

        @Bean
        public Binding binding2a(DirectExchange directExchange, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(directExchange).with("green");
        }

        @Bean
        public Binding binding2b(DirectExchange directExchange, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(directExchange).with("black");
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