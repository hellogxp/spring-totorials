package com.chopin.delayqueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chopin
 * @date 2022/2/11
 */
@RestController
public class Controller {
    @Autowired
    private Sender sender;

    @GetMapping("/send/{message}")
    public void testDelayMessage(@PathVariable String message) {
        sender.send(message);
    }
}