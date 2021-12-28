package com.chopin.workqueues;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * @author chopin
 * @version 1.0
 * @description: TODO
 * @date 2021/12/26 20:42
 */
@RabbitListener(queues = "hello")
public class Receiver {

    private final Integer instance;

    public Receiver(int i) {
        this.instance = i;
    }

    @RabbitHandler
    public void receive(String in) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("Instance " + this.instance + " [x] Received: '" + in + "'");
        doWork(in);
        watch.stop();
        System.out.println("Instance " + this.instance + "[x] Done in '" + watch.getTotalTimeSeconds() + "'s");
    }

    private void doWork(String in) throws InterruptedException{
        for (char ch : in.toCharArray()) {
            if(ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}