package com.chopin.connector.kafka.domian;

import java.io.IOException;

import com.alibaba.fastjson2.JSON;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

/**
 * @author Chopin
 * @date 2022/6/29
 */
public class MessageSchema implements DeserializationSchema<Message>, SerializationSchema<Message> {

    @Override
    public Message deserialize(byte[] bytes) throws IOException {
        return JSON.parseObject(bytes, Message.class);
    }

    @Override
    public boolean isEndOfStream(Message message) {
        return false;
    }

    @Override
    public byte[] serialize(Message message) {
        return new byte[0];
    }

    @Override
    public TypeInformation<Message> getProducedType() {
        return TypeInformation.of(Message.class);
    }
}