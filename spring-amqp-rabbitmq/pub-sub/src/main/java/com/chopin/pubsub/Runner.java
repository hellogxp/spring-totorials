package com.chopin.pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author chopin
 * @version 1.0
 * @description: TODO
 * @date 2021/12/28 13:37
 */
public class Runner implements CommandLineRunner {

    @Value("${tutorial.client.duration:0}")
    private Integer duration;

    @Autowired
    private ConfigurableApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Ready ... running for " + duration + "ms");
        Thread.sleep(duration);
        context.close();
    }
}