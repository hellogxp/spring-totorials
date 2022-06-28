package com.chopin.wordcount;

import java.util.Arrays;
import java.util.List;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WordCountApplicationTests {
    private final ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();

    @Test
    void contextLoads() {
    }

    @Test
    void givenDataSet_whenExecuteWordCount_thenReturnWordCount() throws Exception {
        // given
        List<String> list = Arrays.asList("This is a first sentence", "This is a second sentence with a one word");

        // when
        DataSet<Tuple2<String, Integer>> result = CountWord.startWordCount(environment, list);

        List<Tuple2<String, Integer>> collect = result.collect();

        assertThat(collect).containsExactlyInAnyOrder(new Tuple2<>("a", 3), new Tuple2<>("sentence", 2),
            new Tuple2<>("word", 1), new Tuple2<>("is", 2), new Tuple2<>("this", 2), new Tuple2<>("second", 1),
            new Tuple2<>("first", 1), new Tuple2<>("with", 1),
            new Tuple2<>("one", 1));
    }

    @Test
    void givenStreamOfEvents_whenProcessEvents_thenShouldPrintResultsOnSinkOperation() throws Exception{
        // given
        final StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> stringDataStream = streamExecutionEnvironment.fromElements("This is a first sentence", "This is a second sentence with a one word");

        SingleOutputStreamOperator<String> upperCase = stringDataStream.map(String::toUpperCase);

        upperCase.print();

        // when
        streamExecutionEnvironment.execute();
    }

}
