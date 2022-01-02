package com.chopin.rpc;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * Project spring-tutorials
 * Package com.chopin.rpc
 *
 * @author Chopin
 * @date 2022/1/1 22:03
 */
public class Server {

    @RabbitListener(queues = "rpc.requests")
    public int fibonacci(int n) {
        System.out.println(" [x] Received request for " + n);
        int result = fib(n);
        System.out.println(" [.] Returned " + result);
        return result;
    }

    public int fib(int n) {
        return n == 0 ? 0 : n == 1 ? 1 : (fib(n -1) + fib(n -2));
    }
}