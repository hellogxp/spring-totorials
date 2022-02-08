package com.chopin.publisherconfirms;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * @author Chopin
 * @date 2022/2/8
 */
public class Receiver {
    @RabbitListener(queues = "#{queue.name}")
    public void receive(String in) throws InterruptedException{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("[x] Receive '" + in + "'");
        stopWatch.stop();
        System.out.println("[x] Done in " + stopWatch.getTotalTimeSeconds() + "s");
    }
}