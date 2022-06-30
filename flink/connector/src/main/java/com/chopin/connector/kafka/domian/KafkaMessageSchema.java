package com.chopin.connector.kafka.domian;

import java.io.IOException;

import com.alibaba.fastjson2.JSON;

import org.apache.flink.api.common.serialization.DeserializationSchema.InitializationContext;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chopin
 * @date 2022/6/30
 */
public class KafkaMessageSchema implements KafkaDeserializationSchema<Message> {
    @Override
    public void open(InitializationContext context) throws Exception {
        KafkaDeserializationSchema.super.open(context);
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> message, Collector<Message> out) throws Exception {
        out.collect(JSON.parseObject(message.value(), Message.class));
        //KafkaDeserializationSchema.super.deserialize(message, out);
    }

    @Override
    public boolean isEndOfStream(Message nextElement) {
        return false;
    }

    @Override
    public Message deserialize(ConsumerRecord<byte[], byte[]> record) throws Exception {
        return null;
    }

    @Override
    public TypeInformation<Message> getProducedType() {
        return TypeInformation.of(Message.class);
    }
}