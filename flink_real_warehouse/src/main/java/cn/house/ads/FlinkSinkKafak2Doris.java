package cn.house.ads;

import cn.house.utils.FlinkUtils;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class FlinkSinkKafak2Doris {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment = FlinkUtils.getEnvironment("hdfs://bigdata01:8020/flink_warehouse/ads_check_path");

        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()//设置流式模式
                .useBlinkPlanner()
                .build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(environment, settings);
        String dws_order_fact_detail = "CREATE TABLE  if not exists  dws_order_fact_detail( \n" +
                "add_time String,\n" +
                "brand_id int,\n" +
                "brand_name String,\n" +
                "capitation_price Double,\n" +
                "first_category_id int,\n" +
                "first_category_name String,\n" +
                "goods_id int,\n" +
                "goods_name String,\n" +
                "goods_sn int,\n" +
                "id int,\n" +
                "number String,\n" +
                "order_id int,\n" +
                "pic_url String,\n" +
                "price Double,\n" +
                "product_id int,\n" +
                "second_category_id int,\n" +
                "second_category_name String,\n" +
                "specifications String \n" +
                " ) WITH(  \n" +
                "'connector' = 'kafka',  \n" +
                "'topic'='dws_order_fact_detail',  \n" +
                "'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092',  \n" +
                "'properties.group.id' = 'dws_order_fact_detail_consum_group',  \n" +
                "'scan.startup.mode' = 'earliest-offset',  \n" +
                "'format' = 'json',  \n" +
                "'json.fail-on-missing-field' = 'false',  \n" +
                "'json.ignore-parse-errors' = 'true'  \n" +
                ")";

        String ads_doris = "CREATE TABLE ads_order_fact_detail ( \n" +
                "add_time String,\n" +
                "brand_id int,\n" +
                "brand_name String,\n" +
                "capitation_price Double,\n" +
                "first_category_id int,\n" +
                "first_category_name String,\n" +
                "goods_id int,\n" +
                "goods_name String,\n" +
                "goods_sn int,\n" +
                "id int,\n" +
                "number String,\n" +
                "order_id int,\n" +
                "pic_url String,\n" +
                "price Double,\n" +
                "product_id int,\n" +
                "second_category_id int,\n" +
                "second_category_name String,\n" +
                "specifications String \n" +
                ") WITH (\n" +
                " 'connector' = 'doris', \n" +
                " 'fenodes' = 'bigdata03:8030,bigdata02:8030,bigdata01:8030', \n" +
                "'table.identifier' = 'litemall_result.ads_order_fact_detail', \n" +
                "'username' = 'root', 'password' = '', \n" +
                "'sink.batch.size' = '1' \n" +
                ")";

        String insertIntoDoris = "insert into ads_order_fact_detail select * from dws_order_fact_detail";

        tableEnv.executeSql(dws_order_fact_detail);
        tableEnv.executeSql("select * from dws_order_fact_detail limit 20").print();
        tableEnv.executeSql(ads_doris);
        tableEnv.executeSql(insertIntoDoris);
        tableEnv.executeSql("select * from ads_order_fact_detail limit 50").print();
        environment.execute();

    }
}
