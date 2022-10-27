package cn.house.dwd.fact;

import cn.mobile.common.bean.*;
import cn.mobile.common.util.Constants;
import cn.house.utils.FlinkUtils;
import cn.house.utils.JedisUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;

/**
 * 处理ods层的订单表，针对订单表的数据，对维度表的数据进行join，实现宽表处理。
 */
public class DwdOrderFact {
    public static void main(String[] args) throws Exception {
        String ods_order_fact_name = "ods_db_litemall_order";
        StreamExecutionEnvironment environment = FlinkUtils.getEnvironment("hdfs://bigdata01:8020/dwd_order_fact_path");
        KafkaSource kafkaSource = FlinkUtils.getKafkaSource(ods_order_fact_name, "dwd_db_litemall_order_fact_consum_group");
        WatermarkStrategy<String> watermarkStrategy = WatermarkStrategy.<String>forBoundedOutOfOrderness(Duration.ofSeconds(2)).withTimestampAssigner(new SerializableTimestampAssigner<String>() {
            @Override
            public long extractTimestamp(String element, long recordTimestamp) {
                System.out.println(element);
                LitemallOrder litemallOrder = JSONObject.parseObject(element, LitemallOrder.class);
                long time = litemallOrder.getAdd_time().getTime();
                return time;
            }
        });
        //ods层的订单表数据
        DataStreamSource<String> kafkaDataStream = (DataStreamSource<String>) environment.fromSource(kafkaSource, watermarkStrategy, "kafkaSourceConfig");
        DataStreamSource dwd_litemall_order_fact = environment.<String>fromSource(kafkaSource, watermarkStrategy, "dwd_litemall_order_fact");
        //将订单数据与地区数据进行join合并成为一张表 获取到省份名称，地市名称，区县名称三个名称

        SingleOutputStreamOperator orderAndRegion = dwd_litemall_order_fact.map(new RichMapFunction<String, String>() {
            private Jedis jedis;
            private JedisPool jedisPool;
            @Override
            public void open(Configuration parameters) throws Exception {
                jedisPool = JedisUtil.getPool();
                jedis = jedisPool.getResource();
            }

            @Override
            public void close() throws Exception {
                jedis.close();
                jedisPool.close();
            }

            //从redis当中获取维度的数据进行join  流式数据与静态数据进行join  redis当中的数据是静态的数据
            @Override
            public String map(String value) throws Exception {
                LitemallOrder litemallOrder = JSONObject.parseObject(value, LitemallOrder.class);
                LitemallOrderRegion orderRegion = new LitemallOrderRegion();
                BeanUtils.copyProperties(orderRegion,litemallOrder);


                String key = "litemall:dwd:dim:litemall_region";
                //省份名称
                String provinceName = jedis.hget(key, litemallOrder.getProvince()+"");
                //城市名称
                String cityName = jedis.hget(key, litemallOrder.getCity()+"");
                // 乡镇名称
                String countryName = jedis.hget(key, litemallOrder.getCountry()+"");

                orderRegion.setProvince_name(provinceName);
                orderRegion.setCity_name(cityName);
                orderRegion.setCountry_name(countryName);
                System.out.println(JSONObject.toJSONString(orderRegion));

                return JSONObject.toJSONString(orderRegion);
            }
        });

        //新建订单
        FlinkKafkaProducer<String> dwd_fact_order_info = FlinkUtils.getKafkaProducer(Constants.dwd_fact_order_info);

         orderAndRegion.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                LitemallOrderRegion orderRegion = JSONObject.parseObject(value, LitemallOrderRegion.class);
                int order_status = orderRegion.getOrder_status();
                if (order_status == Constants.STATUS_CREATE) {
                    return true;
                }
                return false;
            }
        }).map(new RichMapFunction<String,String>() {
            @Override
            public String map(String value) throws Exception {
                LitemallOrderRegion orderRegion = JSONObject.parseObject(value, LitemallOrderRegion.class);
                LitemallOrderCreate litemallOrderCreate = new LitemallOrderCreate();
                BeanUtils.copyProperties(litemallOrderCreate,orderRegion);
                System.out.println("订单下单数据"+JSONObject.toJSONString(litemallOrderCreate));
                return JSONObject.toJSONString(litemallOrderCreate);
            }
        }).addSink(dwd_fact_order_info);


        //订单支付数据
        FlinkKafkaProducer<String> dwd_fact_payment_info = FlinkUtils.getKafkaProducer(Constants.dwd_fact_payment_info);

        orderAndRegion.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                LitemallOrderRegion orderRegion = JSONObject.parseObject(value, LitemallOrderRegion.class);
                int order_status = orderRegion.getOrder_status();
                if (order_status == Constants.STATUS_PAY) {
                    return true;
                }
                return false;
            }
        }).map(new RichMapFunction<String,String>() {
            @Override
            public String map(String value) throws Exception {
                LitemallOrderRegion orderRegion = JSONObject.parseObject(value, LitemallOrderRegion.class);
                LitemallOrderPayment litemallOrderPayment = new LitemallOrderPayment();
                BeanUtils.copyProperties(litemallOrderPayment,orderRegion);

                System.out.println("订单支付数据"+JSONObject.toJSONString(litemallOrderPayment));
                return JSONObject.toJSONString(litemallOrderPayment);
            }
        }).addSink(dwd_fact_payment_info);


        //退款数据过滤
        FlinkKafkaProducer<String> dwd_fact_refund_info = FlinkUtils.getKafkaProducer(Constants.dwd_fact_refund_info);

        orderAndRegion.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                LitemallOrderRegion orderRegion = JSONObject.parseObject(value, LitemallOrderRegion.class);
                int order_status = orderRegion.getOrder_status();
                if (order_status == Constants.STATUS_REFUND) {
                    return true;
                }
                return false;
            }
        }).map(new RichMapFunction<String,String>() {
            @Override
            public String map(String value) throws Exception {
                LitemallOrderRegion orderRegion = JSONObject.parseObject(value, LitemallOrderRegion.class);
                LitemallOrderRefund litemallOrderRefund = new LitemallOrderRefund();
                BeanUtils.copyProperties(litemallOrderRefund,orderRegion);
                System.out.println("订单退款数据"+JSONObject.toJSONString(litemallOrderRefund));
                return JSONObject.toJSONString(litemallOrderRefund);
            }
        }).addSink(dwd_fact_refund_info);


        environment.execute();


        //orderAndRegion



    }
}
