package cn.house.dwd.fact;

import cn.mobile.common.bean.*;
import cn.mobile.common.util.Constants;
import cn.house.utils.FlinkUtils;
import cn.house.utils.JedisUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import redis.clients.jedis.Jedis;

import java.time.Duration;

/**
 * 将订单表以及商品表进行关联操作  将表拉宽 形成一张大款表
 */
public class DwdOrderFactDetail {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = FlinkUtils.getEnvironment("hdfs://bigdata01:8020/dwd_fact_order_detail_check_path");
    //    KafkaSource kafkaSource = FlinkUtils.getKafkaSource(Constants.ods_litemall_order_goods, "ods_litemall_order_goods_detail");
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setGroupId("ods_litemall_order_goods_detail")
                .setBootstrapServers(Constants.bootstrap_server)
                .setTopics(Constants.ods_litemall_order_goods)
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
        WatermarkStrategy<String> stringWatermarkStrategy = WatermarkStrategy.<String>forBoundedOutOfOrderness(Duration.ofSeconds(2)).withTimestampAssigner(new SerializableTimestampAssigner<String>() {
            @Override
            public long extractTimestamp(String element, long recordTimestamp) {
                LitemallOrderGoods litemallOrderGoods = JSONObject.parseObject(element, LitemallOrderGoods.class);
                long time = litemallOrderGoods.getAdd_time().getTime();
                return time;
            }
        });

        DataStreamSource kafkaSourceLitemallOrderGoods = environment.fromSource(kafkaSource, stringWatermarkStrategy, "kafkaSourceLitemallOrderGoods");

        SingleOutputStreamOperator orderGoodsDetail = kafkaSourceLitemallOrderGoods.map(new RichMapFunction<String, String>() {
            @Override
            public String map(String value) throws Exception {
                LitemallOrderGoods litemallOrderGoods = JSONObject.parseObject(value, LitemallOrderGoods.class);
                LitemallOrderGoodsDetail litemallOrderGoodsDetail = new LitemallOrderGoodsDetail();
                BeanUtils.copyProperties(litemallOrderGoodsDetail, litemallOrderGoods);
                return JSONObject.toJSONString(litemallOrderGoodsDetail);
            }
        });
        //将订单详细表获取到，然后通过redis当中的数据进行join，补全一级分类，二级分类
        SingleOutputStreamOperator orderGoodsDetailCategory = orderGoodsDetail.map(new RichMapFunction<String, String>() {
            private Jedis jedis;
            @Override
            public void open(Configuration parameters) throws Exception {
                jedis = JedisUtil.getPool().getResource();
            }

            @Override
            public void close() throws Exception {
                JedisUtil.getPool().returnResource(jedis);
            }
            @Override
            public String map(String value) throws Exception {
                LitemallOrderGoodsDetail litemallOrderGoodsDetail = JSONObject.parseObject(value, LitemallOrderGoodsDetail.class);
                LitemallOrderGoodsDetailCategory litemallOrderGoodsDetailCategory = new LitemallOrderGoodsDetailCategory();
                BeanUtils.copyProperties(litemallOrderGoodsDetailCategory, litemallOrderGoodsDetail);

                //根据商品id，获取商品信息  通过redis当中的维度数据的获取，进行维度表的join操作
                int goods_id = litemallOrderGoodsDetail.getGoods_id();
                String redisKey = "litemall:dwd:dim:litemall_goods";
                String goodsJson = jedis.hget(redisKey, goods_id + "");
                if (StringUtils.isNotEmpty(goodsJson)) {
                    LitemallGoods litemallGoods = JSONObject.parseObject(goodsJson, LitemallGoods.class);

                    //商品信息，关联品牌id，获取品牌信息
                    String brand_key = "litemall:dwd:dim:litemall_brand";
                    String brandJson = jedis.hget(brand_key, litemallGoods.getBrand_id() + "");
                    LitemallBrand litemallBrand = JSONObject.parseObject(brandJson, LitemallBrand.class);

                    //设置品牌id，品牌名称
                    litemallOrderGoodsDetailCategory.setBrand_id(litemallBrand.getId());
                    litemallOrderGoodsDetailCategory.setBrand_name(litemallBrand.getName());

                    String category_key = "litemall:dwd:dim:litemall_category";
                    String categoryJson = jedis.hget(category_key, litemallGoods.getCategory_id() + "");
                    LitemallCategory litemallCategory = JSONObject.parseObject(categoryJson, LitemallCategory.class);
                    //设置一级分类名称，一级分类ID
                    litemallOrderGoodsDetailCategory.setSecond_category_id(litemallCategory.getId());
                    litemallOrderGoodsDetailCategory.setSecond_category_name(litemallCategory.getName());
                    //设置二级分类名称，二级分类ID

                    String sednondCategoryJson = jedis.hget(category_key, litemallCategory.getPid() + "");
                    System.out.println(sednondCategoryJson);
                    litemallCategory = JSONObject.parseObject(sednondCategoryJson, LitemallCategory.class);
                    System.out.println(JSONObject.toJSONString(litemallCategory));
                    litemallOrderGoodsDetailCategory.setFirst_category_id(litemallCategory.getId());
                    litemallOrderGoodsDetailCategory.setFirst_category_name(litemallCategory.getName());
                    System.out.println(JSONObject.toJSONString(litemallOrderGoodsDetailCategory));
                    return JSONObject.toJSONString(litemallOrderGoodsDetailCategory);
                } else {
                    return "";
                }

            }
        }).filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                return StringUtils.isNotEmpty(value);
            }
        });
        FlinkKafkaProducer<String> dwd_fact_order_goods_detail_category = FlinkUtils.getKafkaProducer(Constants.dwd_fact_order_detail);  //"dwd_fact_order_detail"
        //将处理之后的数据保存到kafka当中去
        orderGoodsDetailCategory.addSink(dwd_fact_order_goods_detail_category);
        environment.execute();

    }
}
