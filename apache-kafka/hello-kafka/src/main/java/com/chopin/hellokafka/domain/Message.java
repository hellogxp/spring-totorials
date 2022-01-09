package com.chopin.hellokafka.domain;

import java.io.Serializable;

/**
 * Project spring-tutorials
 * Package com.chopin.hellokafka.domain
 *
 * @author Chopin
 * @date 2022/1/7 15:22
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -2524251461724835519L;

    private String from;

    private String message;

    public Message() {

    }

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
            "from='" + from + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}