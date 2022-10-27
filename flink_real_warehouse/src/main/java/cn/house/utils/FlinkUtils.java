package cn.house.utils;

import cn.mobile.common.util.Constants;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class FlinkUtils {

    public static StreamExecutionEnvironment getEnvironment(String checkPointPath){

        Configuration configuration = new Configuration();
        configuration.setString(RestOptions.BIND_PORT,"8081-9999");

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        environment.setParallelism(3);
        // 设置hdfs用户
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        // 设置状态后端 增量ck
        environment.setStateBackend(  new EmbeddedRocksDBStateBackend(true));
        // 配置checkpoint
        environment.enableCheckpointing(60 * 1000);
        // 设置存储目录
        environment.getCheckpointConfig().setCheckpointStorage(checkPointPath);
        // 设置同时进行的ck数
        environment.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        // 设置多长时间丢弃ck
        environment.getCheckpointConfig().setCheckpointTimeout(10 * 60 * 1000);
        // 设置ck间的最小间隙
        environment.getCheckpointConfig().setMinPauseBetweenCheckpoints(10 * 1000);
        // 设置ck模式
        environment.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        // 设置容错数
        environment.getCheckpointConfig().setTolerableCheckpointFailureNumber(2);
        // job取消后保留ck
        environment.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        // 开始实验特征
        environment.getCheckpointConfig().enableUnalignedCheckpoints();
        // 快照压缩
        environment.getConfig().setUseSnapshotCompression(true);
        return  environment;
    }


    public static KafkaSource getKafkaSource(String topicName,String groupId){
        Properties properties = new Properties();
        properties.setProperty("flink.partition-discovery.interval-millis","5000");
        //动态感知 kafka 主题分区的增加 单位毫秒
        KafkaSource<String> kafkaSourceConfig = KafkaSource.<String>builder()
           //     .setStartingOffsets(OffsetsInitializer.committedOffsets())
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setGroupId(groupId)
                .setProperties(properties)
                .setBootstrapServers(Constants.bootstrap_server)
                .setTopics(topicName)
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
        return kafkaSourceConfig;
    }



//    public static KafkaSink getKafkaSink(String topicName){
//
//        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
//                .setBootstrapServers(Constants.bootstrap_server)
//                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
//                        .setTopic(topicName)
//                        .setValueSerializationSchema(new SimpleStringSchema())
//                        .build()
//                ) .setDeliverGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
//                .build();
//        return kafkaSink;
//
//    }


    public static FlinkKafkaProducer<String> getKafkaProducer(String topic){
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers",Constants.bootstrap_server);
        properties.setProperty("transaction.timeout.ms",1000*60*5+"");
        KafkaSerializationSchema<String> kafkaSerializationSchema = new KafkaSerializationSchema<String>() {
            @Override
            public ProducerRecord<byte[], byte[]> serialize(String element, @Nullable Long aLong) {
                return new ProducerRecord<>(topic, element.getBytes(StandardCharsets.UTF_8));
            }
        };

        /**
         * 注意：如果FlinkKafkaProducer.Semantic.EXACTLY_ONCE 配置为EXACTLY_ONCE 那么就会报错如下
         * The transaction timeout is larger than the maximum value allowed by the broker (as configured by transaction.max.timeout.ms).
         *
         * 如果需要配置FlinkKafkaProducer.Semantic.EXACTLY_ONCE  那么需要配置     properties.setProperty("transaction.timeout.ms",1000*60*5+"");
         *  参见  https://www.cnblogs.com/lillcol/p/12092869.html
         */
        FlinkKafkaProducer<String> stringFlinkKafkaProducer = new FlinkKafkaProducer<>(topic, kafkaSerializationSchema, properties, FlinkKafkaProducer.Semantic.EXACTLY_ONCE);
        return stringFlinkKafkaProducer;


    }

    public static StreamTableEnvironment  getTableEnvironment(String checkPointPath){
        StreamExecutionEnvironment environment = FlinkUtils.getEnvironment(checkPointPath);

        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()//设置流式模式
                .useBlinkPlanner()
                .build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(environment, settings);
        return tableEnv;
    }










}
