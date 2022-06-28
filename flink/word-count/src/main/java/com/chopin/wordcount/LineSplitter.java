package com.chopin.wordcount;

import java.util.stream.Stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author Chopin
 * @date 2022/6/22
 */
public class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
    @Override
    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
        String[] tokens = s.toLowerCase().split("\\W+");
        Stream.of(tokens).filter(t -> t.length() > 0).forEach(token -> collector.collect(new Tuple2<>(token, 1)));
    }
}