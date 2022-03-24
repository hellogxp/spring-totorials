package com.chopin.hellorocket.producer;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Chopin
 * @date 2022/3/14
 */
@SpringBootApplication
public class OrderedProducer {
    public static final Logger LOGGER = LoggerFactory.getLogger(OrderedProducer.class);

    public static void main(String[] args)
        throws MQClientException, UnsupportedEncodingException, MQBrokerException, RemotingException,
        InterruptedException {

        int orders = 10;
        int types = 3;

        DefaultMQProducer producer = new DefaultMQProducer("OrderProducer");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        String[] tags = new String[] {"createTag", "payTag", "payTag"};
        // Iterate orders
        for (int i = 1; i <= orders; ++i) {
            // Three kinds of order
            for (int j = 0; j < types; ++j) {
                Message message = new Message("OrderTopic", tags[j % tags.length], i + ":" + j, (i + ":" + j).getBytes());

                SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                        Integer id = (Integer) o;
                        int index = id % list.size();
                        return list.get(index);
                    }
                }, i);
                System.out.println(sendResult);
            }
        }

        producer.shutdown();
    }
}