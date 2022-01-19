package com.chopin.serviceproviderone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Project spring-tutorials
 * Package com.chopin.serviceproviderone.controller
 *
 * @author Chopin
 * @date 2022/1/16 22:34
 */
@RestController
@RequestMapping("welcome")
public class WelcomeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String welcome() {
        logger.info("Invoke service-provider-one");
        return this.restTemplate.getForEntity("http://service-provider-two/welcome/hello", String.class).getBody();
    }
}