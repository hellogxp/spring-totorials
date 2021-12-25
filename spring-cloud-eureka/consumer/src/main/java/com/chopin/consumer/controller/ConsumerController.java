package com.chopin.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author chopin
 * @version 1.0
 * @description: TODO
 * @date 2021/12/21 23:35
 */
@RestController
public class ConsumerController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/appinfo")
    public String getInfo() {
        return restTemplate.getForEntity("http://service-provider/service-instances", String.class)
            .getBody();
    }
}