package com.chopin.connector.kafka.domian;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Chopin
 * @date 2022/6/29
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = -2524251461724835519L;

    private String from;

    private String message;

    private Integer count;

    public Message() {

    }

    public Message(String from, String message, Integer count) {
        this.from = from;
        this.message = message;
        this.count = count;
    }


    @Override
    public String toString() {
        return "Message{" +
            "from='" + from + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}