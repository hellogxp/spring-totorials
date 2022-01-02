package com.chopin.rpc;

import com.rabbitmq.client.AMQP.Queue.Bind;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Project spring-tutorials
 * Package com.chopin.rpc
 *
 * @author Chopin
 * @date 2022/1/1 21:57
 */
@Profile("rpc")
@Configuration
public class Config {

    @Profile("client")
    private static class ClientConfig {

        @Bean
        public DirectExchange directExchange() {
            return new DirectExchange("rpc");
        }

        @Bean
        public Client client() {
            return new Client();
        }
    }

    @Profile("server")
    private static class ServerConfig {

        @Bean
        public Queue queue() {
            return new Queue("rpc.requests");
        }

        @Bean
        public DirectExchange directExchange() {
            return new DirectExchange("rpc");
        }

        @Bean
        public Binding binding(DirectExchange directExchange, Queue queue) {
            return BindingBuilder.bind(queue).to(directExchange).with("rpc");
        }

        @Bean
        public Server server() {
            return new Server();
        }
    }
}