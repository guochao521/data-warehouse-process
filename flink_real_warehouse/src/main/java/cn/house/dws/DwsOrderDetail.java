package cn.house.dws;

import cn.mobile.common.bean.LitemallOrderCreate;
import cn.mobile.common.bean.LitemallOrderDetailWide;
import cn.mobile.common.bean.LitemallOrderGoodsDetailCategory;
import cn.mobile.common.bean.LitemallOrderWide;
import cn.mobile.common.util.Constants;
import cn.house.utils.FlinkUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

public class DwsOrderDetail {
    //针对订单表来构建DWS层的结果数据
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = FlinkUtils.getEnvironment("hdfs://bigdata01:8020/flink_warehouse/dwd_order_detail");

        KafkaSource dwd2dws_fact_order_info = FlinkUtils.getKafkaSource(Constants.dwd_fact_order_info, "dwd2dws_fact_order_detail");

        DataStreamSource dwd2dws_fact_order_info_source = environment.fromSource(dwd2dws_fact_order_info, WatermarkStrategy.noWatermarks(), "dwd2dws_order_detail_opt");
        //获取订单创建数据
        SingleOutputStreamOperator dws_order_create_wide = dwd2dws_fact_order_info_source.map(new MapFunction<String, String>() {
            @Override
            public String map(String value) throws Exception {
                LitemallOrderCreate litemallOrderCreate = JSONObject.parseObject(value, LitemallOrderCreate.class);
                LitemallOrderWide litemallOrderWide = new LitemallOrderWide();
                BeanUtils.copyProperties(litemallOrderWide, litemallOrderCreate);
             //   System.out.println(JSONObject.toJSONString(litemallOrderWide));
                return JSONObject.toJSONString(litemallOrderWide);
            }
        });


        //获取订单数据详情
        KafkaSource dwd_fact_order_detail = FlinkUtils.getKafkaSource(Constants.dwd_fact_order_detail, "litemall_dwd_fact_order_detail_consum_group");
        DataStreamSource dwd_fact_order_detail_app = environment.fromSource(dwd_fact_order_detail, WatermarkStrategy.noWatermarks(), "dwd_fact_order_detail_app");

        SingleOutputStreamOperator dws_order_detail_wide = dwd_fact_order_detail_app.map(new MapFunction<String, String>() {
            @Override
            public String map(String value) throws Exception {
                LitemallOrderGoodsDetailCategory litemallOrderGoodsDetailCategory = JSONObject.parseObject(value, LitemallOrderGoodsDetailCategory.class);
                LitemallOrderDetailWide litemallOrderDetailWide = new LitemallOrderDetailWide();
                BeanUtils.copyProperties(litemallOrderDetailWide, litemallOrderGoodsDetailCategory);
          //      System.out.println(JSONObject.toJSONString(litemallOrderDetailWide));
                return JSONObject.toJSONString(litemallOrderDetailWide);
            }
        });
        SingleOutputStreamOperator joinProcessStream = dws_order_create_wide.keyBy(new KeySelector<String, Integer>() {
            @Override
            public Integer getKey(String value) throws Exception {
                LitemallOrderWide litemallOrderWide = JSONObject.parseObject(value, LitemallOrderWide.class);
         //       System.out.println(JSONObject.toJSONString(litemallOrderWide));
                int id = litemallOrderWide.getId();
          //      System.out.println(id);

                return id;
            }
        }).intervalJoin(dws_order_detail_wide.keyBy(new KeySelector<String, Integer>() {
            @Override
            public Integer getKey(String value) throws Exception {
                LitemallOrderDetailWide litemallOrderDetailWide = JSONObject.parseObject(value, LitemallOrderDetailWide.class);
                System.out.println(JSONObject.toJSONString(litemallOrderDetailWide));
         //       System.out.println(litemallOrderDetailWide.getOrder_id());
                return litemallOrderDetailWide.getOrder_id();
            }
        })).between(Time.seconds(-60), Time.seconds(60))  //设置正负都是60s，防止在业务系统中主表与从表保存的时间差。
                .process(new OrderDetailIntervalJoinPorcess());

        FlinkKafkaProducer<String> dws_order_fact_detail = FlinkUtils.getKafkaProducer(Constants.dws_order_fact_detail);
        joinProcessStream.addSink(dws_order_fact_detail);
        environment.execute();
    }
}




