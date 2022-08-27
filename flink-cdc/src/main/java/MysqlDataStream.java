import java.util.Properties;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumSourceFunction;
import com.ververica.cdc.debezium.StringDebeziumDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @author Chopin
 * @date 2022/8/27
 */
public class MysqlDataStream {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        streamExecutionEnvironment.setParallelism(1);

        Properties properties = new Properties();
        // do not use lock
        properties.put("snapshot.locking.mode", "none");
        MySqlSource<String> debeziumSourceFunction = MySqlSource.<String>builder().hostname("localhost")
            .port(3312).username("root").password("root").databaseList("mall").tableList("mall.product").deserializer(
                new StringDebeziumDeserializationSchema()).startupOptions(StartupOptions.initial()).debeziumProperties(
                properties).build();

        streamExecutionEnvironment.fromSource(debeziumSourceFunction, WatermarkStrategy.noWatermarks(),
            "Flink CDC MySQL source").setParallelism(1).print();
        streamExecutionEnvironment.execute("Flink CDC DataStream");
    }
}