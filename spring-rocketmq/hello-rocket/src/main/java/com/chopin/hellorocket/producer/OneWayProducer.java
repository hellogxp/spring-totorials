package com.chopin.hellorocket.producer;

import java.io.UnsupportedEncodingException;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Chopin
 * @date 2022/3/1
 */
@SpringBootApplication
public class OneWayProducer {
    public static void main(String... args)
        throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        final Logger LOGGER = LoggerFactory.getLogger(OneWayProducer.class);
        DefaultMQProducer producer = new DefaultMQProducer("Group_One_Way");

        producer.setNamesrvAddr("localhost:9876");
        producer.setRetryTimesWhenSendAsyncFailed(15000);
        producer.start();

        for (int i = 0; i < 10; ++i) {
            Message message = new Message("TopicA", "TagA",
                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendOneway(message);
            LOGGER.info("[x] {} sent", message);
        }

        Thread.sleep(1000);
        producer.shutdown();
    }

}