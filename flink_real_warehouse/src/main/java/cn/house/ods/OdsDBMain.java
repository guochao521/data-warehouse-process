package cn.house.ods;

import cn.mobile.common.bean.MaxwellEntity;
import cn.mobile.common.util.Constants;
import cn.mobile.common.util.FilterUtils;
import cn.house.utils.FlinkUtils;
import cn.house.sinks.KafkaSinkFromDB;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.time.Duration;


public class OdsDBMain {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment = FlinkUtils.getEnvironment("hdfs://bigdata01:8020/db_check_path");
        //构建kafkasource 数据源
        KafkaSource kafkaSourceConfig = FlinkUtils.getKafkaSource(Constants.ods_lite_mall, "ods_litemall_group_consum");
        //设置watermark策略
        WatermarkStrategy.<String>forBoundedOutOfOrderness(Duration.ofSeconds(2)).withTimestampAssigner(new SerializableTimestampAssigner<String>() {
            @Override
            public long extractTimestamp(String element, long recordTimestamp) {
                MaxwellEntity maxwellEntity = JSONObject.parseObject(element, MaxwellEntity.class);
                long l = maxwellEntity.getTs() * 1000;
                return l;
            }
        });
        DataStreamSource ods_litemall_source = environment.fromSource(kafkaSourceConfig, WatermarkStrategy.noWatermarks(), "ods_litemall_source");
        SingleOutputStreamOperator filterStream = ods_litemall_source.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                System.out.println(value.toString());
                MaxwellEntity maxwellEntity = JSONObject.parseObject(value, MaxwellEntity.class);
                boolean b = FilterUtils.filterTableWithName(FilterUtils.list,maxwellEntity.getTable()) && !maxwellEntity.getData().isEmpty();
                return b;
            }
        });
        filterStream.print();
        filterStream.addSink(new KafkaSinkFromDB());
        environment.execute();
    }
}
