package cn.house.utils;

import cn.mobile.common.util.Constants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class KafkaUtils {

    public static KafkaProducer<String,String> getKafkaProducer (){
        Properties prop = new Properties();
        // 指定请求的kafka集群列表
        prop.put("bootstrap.servers", Constants.bootstrap_server); // 指定响应方式
        prop.put("acks", "all");
        // 指定key的序列化方式, key是用于存放数据对应的offset
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 指定value的序列化方式
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 配置超时时间
        prop.put("request.timeout.ms", "60000");
        // 设置幂等性
        prop.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        // 得到生产者的实例
        KafkaProducer<String, String> stringStringKafkaProducer = new KafkaProducer<>(prop);
        return stringStringKafkaProducer;

    }

}
