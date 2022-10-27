package cn.house.dwd.dim;

import cn.mobile.common.util.Constants;
import cn.mobile.common.util.FilterUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class DimRedisSink {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setString(RestOptions.BIND_PORT,"8081-9999");
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setGroupId("dim_consum_group")
                .setBootstrapServers(Constants.bootstrap_server)
                .setTopics("ods_litemall")
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
        DataStreamSource<String> kafkaDataStream = (DataStreamSource<String>) environment.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "kafkaSourceConfig");
        kafkaDataStream.print();
        kafkaDataStream.addSink(new RedisSink());
        kafkaDataStream.print();
        environment.execute();
    }
}

class RedisSink extends RichSinkFunction<String>{
    private JedisPool jedisPool;
    private Jedis jedis;


    @Override
    public void open(Configuration parameters) throws Exception {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10000);
        config.setMaxIdle(2000);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        // redis如果设置了密码：
        //jedisPool = new JedisPool(config, "192.168.46.130",3679, 10000,"12345");

        jedisPool = new JedisPool(
                config,
                Constants.jedisHost,
                Constants.jedisPort,
                10000);
        jedis   = jedisPool.getResource();

    }

    @Override
    public void close() throws Exception {
        if(null != jedis){
            jedis.close();
        }

        jedisPool.close();

    }

    @Override
    public void invoke(String value, Context context) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(value);
        String database = jsonObject.getString("database");
        String table = jsonObject.getString("table");
        if(database.equals("litemall")&& FilterUtils.dimList.contains(table)){
            String redisKey = "litemall:dwd:dim:" + table;
            String data = jsonObject.getString("data");
            JSONObject jsonMap = JSONObject.parseObject(data);
            String dataId = jsonMap.getIntValue("id") + "";
            String type = jsonObject.getString("type");
            if(type.equals("delete")){
                jedis.hdel(redisKey,dataId+"");
            }else{
                jedis.hset(redisKey,dataId,data);
            }
        }
    }
}


