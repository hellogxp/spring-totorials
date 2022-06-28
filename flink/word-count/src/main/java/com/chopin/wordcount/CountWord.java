package com.chopin.wordcount;

import java.util.List;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * @author Chopin
 * @date 2022/6/22
 */
public class CountWord {
    public static DataSet<Tuple2<String, Integer>> startWordCount(ExecutionEnvironment environment, List<String> list) throws Exception {
        DataSet<String> text = environment.fromCollection(list);
        return text.flatMap(new LineSplitter()).groupBy(0).aggregate(Aggregations.SUM, 1);
    }
}