package com.chopin.hellorocket.producer;

import java.io.UnsupportedEncodingException;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Chopin
 * @date 2022/2/15
 */
@SpringBootApplication
public class SyncProducer {
    public static void main(String ...args)
        throws MQClientException, UnsupportedEncodingException, MQBrokerException, RemotingException,
        InterruptedException {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("Group_Producer_Daily");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        producer.setSendMsgTimeout(15000);
        //Launch the instance.
        producer.start();
        int messageCount = 3;
        for (int i = 0; i < messageCount; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message message = new Message("TopicA", "TagSyncProducer", ("Hello RocketMQ" + i).getBytes(
                RemotingHelper.DEFAULT_CHARSET));
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(message);
            System.out.printf("Thread: %s, sendResult: %s %n", Thread.currentThread().getContextClassLoader(), sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}