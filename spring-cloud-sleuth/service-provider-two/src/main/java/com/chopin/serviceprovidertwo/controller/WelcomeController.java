package com.chopin.serviceprovidertwo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project spring-tutorials
 * Package com.chopin.serviceprovidertwo.controller
 *
 * @author Chopin
 * @date 2022/1/16 22:42
 */
@RestController
@RequestMapping("welcome")
public class WelcomeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/hello")
    public String welcome() {
        logger.info("Invoke service-provider-two");
        return "Welcome to sleuth";
    }
}