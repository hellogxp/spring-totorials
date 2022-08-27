import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

/**
 * @author Chopin
 * @date 2022/8/26
 */
public class MysqlTableApi {
    public static void main(String[] args) throws Exception {
        // Create execution environment
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        executionEnvironment.setParallelism(1);

        StreamTableEnvironment streamTableEnvironment = StreamTableEnvironment.create(executionEnvironment);

        streamTableEnvironment.executeSql("CREATE TABLE product (\n" +
            "    id INT primary key,\n" +
            "    name STRING" +
            ") WITH (\n" +
            "          'connector' = 'mysql-cdc',\n" +
            "          'hostname' = 'localhost',\n" +
            "          'port' = '3312',\n" +
            "          'username' = 'root',\n" +
            "          'password' = 'root',\n" +
            "          'database-name' = 'mall',\n" +
            "          'table-name' = 'product'," +
            // Full data and incremental data synchronization.
            "          'scan.startup.mode' = 'initial'      " +
            ")");

        Table table = streamTableEnvironment.sqlQuery("select * from product");
        DataStream<Tuple2<Boolean, Row>> reactStream = streamTableEnvironment.toRetractStream(table, Row.class);
        reactStream.print();

        executionEnvironment.execute("Flink CDC");
    }
}