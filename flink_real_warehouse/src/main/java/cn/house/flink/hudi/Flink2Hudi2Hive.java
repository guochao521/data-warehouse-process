package cn.house.flink.hudi;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class Flink2Hudi2Hive {
    public static void main(String[] args) throws Exception {
        //1.获取表的执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        //并行度设置为1
        env.setParallelism(1);
        //TODO: 由于增量将数据写入到Hudi表，所以需要启动Flink Checkpoint 检查点
        env.enableCheckpointing(5 * 1000);


        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()//设置流式模式
                .useBlinkPlanner()
                .build();
        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(env, settings);
    /*    //定义表结构，直接从mysql当中的binlog获取数据
        String createTable = "CREATE TABLE mysql_binlog " +
                "( id INT ,   NAME STRING,  age INT ,birthday STRING )" +
                " WITH (  'connector' = 'mysql-cdc'" +
                ", 'hostname' = 'bigdata03'" +
                ", 'port' = '3306'" +
                ", 'username' = 'root'" +
                ", 'password' = '123456'" +
                ", 'database-name' = 'testdb'" +
                ", 'table-name' = 'mysql_binlog','scan.incremental.snapshot.enabled'='false' )";



        //数据写入到kafka的ods层表当中去
        String ods_kafka_table  = "CREATE TABLE  if not exists  ods_mysql_binlog( \n" +
                "`id` INT ,   \n" +
                "`NAME` STRING,  \n" +
                "`age` INT , \n" +
                "`birthday` STRING \n" +
                ") WITH(   \n" +
                "'connector' = 'kafka',  \n" +
                "'topic'='ods_mysql_binlog',  \n" +
                "'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092',  \n" +
                "'properties.group.id' = 'ods_mysql_binlog_group_consum',  \n" +
                "'scan.startup.mode' = 'earliest-offset',  \n" +
                "'format' = 'changelog-json'\n" +
                ");";


        String  insertCDC2kafka = "insert into table ods_mysql_binlog select * from mysql_binlog";



        String flink_cdc_sink_hudi_hive = "CREATE TABLE flink_cdc_sink_hudi_hive( \n" +
                "                id bigint , \n" +
                "                NAME string, \n" +
                "                age int, \n" +
                "                birthday string , \n" +
                "                ts TIMESTAMP(3), \n" +
                "                partition_day VARCHAR(20) , \n" +
                "                primary key(id) not enforced \n" +
                "                ) \n" +
                "                PARTITIONED BY (partition_day) \n" +
                "                with( \n" +
                "                'connector'='hudi', \n" +
                "                'path'= 'hdfs://bigdata01:8020/flink_cdc_sink_hudi_hive',  \n" +
                "                'table.type'= 'MERGE_ON_READ', \n" +
                "                'hoodie.datasource.write.recordkey.field'= 'id',  \n" +
                "                'write.precombine.field'= 'ts', \n" +
                "                'write.tasks'= '1', \n" +
                "                'write.rate.limit'= '2000',  \n" +
                "                'compaction.tasks'= '1',  \n" +
                "                'compaction.async.enabled'= 'true', \n" +
                "                'compaction.trigger.strategy'= 'num_commits', \n" +
                "                'compaction.delta_commits'= '1', \n" +
                "                'changelog.enabled'= 'true', \n" +
                "                'read.streaming.enabled'= 'true', \n" +
                "                'read.streaming.check-interval'= '3'\n" +
                "                )";

        String insertSQL = "insert into flink_cdc_sink_hudi_hive \n" +
                "select id,\n" +
                "NAME,\n" +
                "age ,\n" +
                "birthday,\n" +
                "cast(birthday as timestamp(3)) as ts ,\n" +
                "substring(birthday,0,10) as partition_day \n" +
                "from mysql_binlog";

        //定义查询sql一句
        String queryTable = "select * from mysql_binlog";*/
     /*   tableEnvironment.executeSql(createTable);
        tableEnvironment.executeSql(flink_cdc_sink_hudi_hive);
        tableEnvironment.executeSql(insertSQL);



        Table result = tableEnvironment.sqlQuery(queryTable);*/




        //查询到的数据进行提取出来，转换成为DataStream进行打印
     //   DataStream<Tuple2<Boolean, UserBean>> tuple2DataStream = tableEnvironment.toRetractStream(result, UserBean.class);
      //  tuple2DataStream.print();

        String mysql_binlog = "CREATE TABLE mysql_binlog \n" +
                "( id INT ,   NAME STRING,  age INT ,birthday STRING )\n" +
                " WITH (  \n" +
                " 'connector' = 'mysql-cdc' ,\n" +
                " 'hostname' = 'bigdata03' ,\n" +
                " 'port' = '3306' ,\n" +
                " 'username' = 'root' ,\n" +
                " 'password' = '123456' ,\n" +
                " 'database-name' = 'testdb' ,\n" +
                " 'table-name' = 'mysql_binlog','scan.incremental.snapshot.enabled'='false' \n" +
                " )";

    String ods_mysql_binlog_kafka = "CREATE TABLE  if not exists  ods_mysql_binlog_kafka( \n" +
            "`id` INT ,   \n" +
            "`NAME` STRING,  \n" +
            "`age` INT , \n" +
            "`birthday` STRING \n" +
            ") WITH(  \n" +
            "'connector' = 'kafka',  \n" +
            "'topic'='ods_mysql_binlog_kafka',  \n" +
            "'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092',  \n" +
            "'properties.group.id' = 'ods_mysql_binlog_group_consum',  \n" +
            "'scan.startup.mode' = 'earliest-offset',  \n" +
            "'format' = 'debezium-json' ,\n" +  //changelog-json
          "  'debezium-json.ignore-parse-errors'='true' \n" +
            ")";


    //将cdc捕获的数据，写入到kafka里面去了
    String insertmysql2kafka = "insert into ods_mysql_binlog_kafka select * from mysql_binlog";

    //定义hudi表，准备将kafka的数据写入到hudi里面来
    String ods_mysql_binlog_hudi = "CREATE TABLE ods_mysql_binlog_hudi(  \n" +
            "id bigint ,  \n" +
            "NAME string,  \n" +
            "age int,  \n" +
            "birthday string ,  \n" +
            "ts TIMESTAMP(3),  \n" +
            "partition_day VARCHAR(20) ,  \n" +
            "primary key(id) not enforced  \n" +
            ")  \n" +
            "PARTITIONED BY (partition_day)  \n" +
            "with(  \n" +
            "'connector'='hudi',  \n" +
            "'path'= 'hdfs://bigdata01:8020/flink_cdc_sink_hudi_hive',   \n" +
            "'table.type'= 'MERGE_ON_READ',  \n" +
            "'hoodie.datasource.write.recordkey.field'= 'id',   \n" +
            "'write.precombine.field'= 'ts',  \n" +
            "'write.tasks'= '1',  \n" +
            "'write.rate.limit'= '2000',   \n" +
            "'compaction.tasks'= '1',   \n" +
            "'compaction.async.enabled'= 'true',  \n" +
            "'compaction.trigger.strategy'= 'num_commits',  \n" +
            "'compaction.delta_commits'= '1',  \n" +
            "'changelog.enabled'= 'true',  \n" +
            "'read.streaming.enabled'= 'true',  \n" +
            "'read.streaming.check-interval'= '3' \n" +
            ")";


    //将kafka的数据，插入到hudi表里面来
    String insertkafka2hudi = "insert into ods_mysql_binlog_hudi  \n" +
            "select id, \n" +
            "NAME, \n" +
            "age , \n" +
            "birthday, \n" +
            "cast(birthday as timestamp(3)) as ts , \n" +
            "substring(birthday,0,10) as partition_day  \n" +
            "from ods_mysql_binlog_kafka";

        tableEnvironment.executeSql(mysql_binlog);
        tableEnvironment.executeSql(ods_mysql_binlog_kafka);
        tableEnvironment.executeSql(insertmysql2kafka);
        tableEnvironment.executeSql(ods_mysql_binlog_hudi);
        tableEnvironment.executeSql(insertkafka2hudi);

        tableEnvironment.executeSql("select * from ods_mysql_binlog_hudi limit 20").print();

        env.execute();


    }
}
