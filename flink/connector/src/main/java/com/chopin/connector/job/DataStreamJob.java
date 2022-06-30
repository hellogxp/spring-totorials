package com.chopin.connector.job;

import java.util.Properties;

import com.chopin.connector.kafka.domian.KafkaMessageSchema;
import com.chopin.connector.kafka.domian.Message;
import com.chopin.connector.kafka.domian.MessageSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;

/**
 * @author Chopin
 * @date 2022/6/29
 */
@RestController
public class DataStreamJob {
    final static Logger logger = LoggerFactory.getLogger(DataStreamJob.class);
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @GetMapping("kafka/string")
    public void kafkaSource() throws Exception {
        final StreamExecutionEnvironment streamExecutionEnvironment
            = StreamExecutionEnvironment.getExecutionEnvironment();

        streamExecutionEnvironment.enableCheckpointing(5000);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", bootstrapServers);
        properties.setProperty("group.id", "consumer-group-one");

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<String>("topic-one", new SimpleStringSchema(),
            properties);
        DataStream<String> stream = streamExecutionEnvironment.addSource(consumer).flatMap(new ToUpper());
        stream.print();

/*        KafkaSource<String> source = KafkaSource.<String>builder().setBootstrapServers(bootstrapServers).setTopics(
            "topic-one").setGroupId("consumer-group-one").setStartingOffsets(
            OffsetsInitializer.earliest()).setValueOnlyDeserializer(new SimpleStringSchema()).build();*/

        streamExecutionEnvironment.execute("read from kafka");
    }

    @GetMapping("kafka/object")
    public void readObjectFromKafka() throws Exception {
        final StreamExecutionEnvironment streamExecutionEnvironment
            = StreamExecutionEnvironment.getExecutionEnvironment();

        streamExecutionEnvironment.enableCheckpointing(5000);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", bootstrapServers);
        properties.setProperty("group.id", "consumer-group-one");

        FlinkKafkaConsumer<Message> consumer = new FlinkKafkaConsumer<Message>("topic-two", new MessageSchema(),
            properties);

        DataStream<Message> stream = streamExecutionEnvironment.addSource(consumer);

        stream.map(new MapFunction<Message, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(Message message) throws Exception {
                return new Tuple2<>(message.getMessage(), 1);
            }
        }).keyBy(0).sum(1).print();

        streamExecutionEnvironment.execute("Read java bean from kafka");
    }

    @GetMapping("kafka/source/string")
    public void readStringFromKafkaSource() throws Exception {
        final StreamExecutionEnvironment streamExecutionEnvironment
            = StreamExecutionEnvironment.getExecutionEnvironment();
        streamExecutionEnvironment.enableCheckpointing(5000);

        KafkaSource<String> source = KafkaSource.<String>builder().setBootstrapServers(bootstrapServers).setTopics(
            "topic-one").setGroupId("consumer-group-one").setStartingOffsets(
            OffsetsInitializer.earliest()).setValueOnlyDeserializer(new SimpleStringSchema()).build();

        DataStream<String> stream = streamExecutionEnvironment.fromSource(source, WatermarkStrategy.noWatermarks(),
            "Kafka Source").flatMap(new ToUpper());
        stream.print();

        streamExecutionEnvironment.execute("Read string from kafka source");
    }

    @GetMapping("kafka/source/message")
    public void readObjectFromKafkaSource() throws Exception {
        final StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        streamExecutionEnvironment.setParallelism(1);

        //streamExecutionEnvironment.enableCheckpointing(5000);

        KafkaSource<Message> source = KafkaSource.<Message>builder().setBootstrapServers(bootstrapServers).setTopics(
            "topic-two").setGroupId("consumer-group-one").setStartingOffsets(
            OffsetsInitializer.latest()).setDeserializer(KafkaRecordDeserializationSchema.of(new KafkaMessageSchema())).build();

        DataStream<Message> stream = streamExecutionEnvironment.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");
        //stream.map(new SumAgg()).keyBy(0).sum(1).print();

        stream.map(new SumAgg()).keyBy(0).sum(1).print();

        streamExecutionEnvironment.execute("Read object from kafka");

    }

    public static final class ToUpper implements FlatMapFunction<String, String> {

        @Override
        public void flatMap(String s, Collector<String> collector) throws Exception {
            collector.collect(s.toUpperCase());
        }
    }

    public static final class SumAgg implements MapFunction<Message, Tuple2<String, Integer>>{
        @Override
        public Tuple2<String, Integer> map(Message message) throws Exception {
            logger.info("SumAgg received message " + message.getMessage());
            return new Tuple2<String, Integer>(message.getMessage(), 1);
        }
    }
}