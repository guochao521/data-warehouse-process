package cn.house.sinks;

import cn.mobile.common.bean.MaxwellEntity;
import cn.house.utils.KafkaUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class KafkaSinkFromDB extends RichSinkFunction<String> {

    private   Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private KafkaProducer<String ,String> kafkaProducer = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        logger.info("init kafka producer");
        kafkaProducer = KafkaUtils.getKafkaProducer();

    }

    @Override
    public void close() throws Exception {
        if(kafkaProducer != null){
            kafkaProducer.close();
            logger.info("kafkaProducer closed");
        }

    }

    @Override
    public void invoke(String element, Context context) throws Exception {
        MaxwellEntity maxwellEntity = JSONObject.parseObject(element, MaxwellEntity.class);
        Map<String, Object> jsonMap = maxwellEntity.getData();
        String table = maxwellEntity.getTable();
        String topicName = "ods_db_" +table ;
        switch (maxwellEntity.getType()) {
            case "insert":
                kafkaProducer.send(new ProducerRecord<String,String>(topicName,JSONObject.toJSONString(jsonMap)));

                break;
            case "update":
                if("litemall_order".equals(maxwellEntity.getTable())){
                    kafkaProducer.send(new ProducerRecord<String,String>(topicName,JSONObject.toJSONString(jsonMap)));
                }
                break;
            default:
                logger.info("没有匹配上的表数据");
                break;


        }


    }
}
