package com.chopin.topics;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Chopin
 * @date  6/1/1112:54 PM
 */
@Profile("topics")
@Configuration
public class Config {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("rabbit.topic");
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Receiver receiver() {
            return new Receiver();
        }

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1a(TopicExchange topicExchange, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topicExchange).with("*.orange.*");
        }

        @Bean
        public Binding binding1b(TopicExchange topicExchange, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topicExchange).with("*.*.rabbit");
        }

        @Bean
        public Binding binding2a(TopicExchange topicExchange, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(topicExchange).with("lazy.#");
        }
    }

    @Profile("sender")
    @Bean
    public Sender sender() {
        return new Sender();
    }

}