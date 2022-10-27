package cn.house.ods;


import cn.mobile.common.bean.BaseLog;
import cn.mobile.common.bean.Ods_user_operate;
import cn.mobile.common.bean.Ods_user_start;
import cn.mobile.common.util.Constants;
import cn.mobile.common.util.JsonUtils;
import cn.house.utils.FlinkUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;


/**
 * 从ods层将数据做处理，然后形成标准json数据，存入到DWD层
 */
public class Ods2dwdMain {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment = FlinkUtils.getEnvironment("file:///D:\\ck_dir_ods");
        //构建kafkasource 数据源
        KafkaSource kafkaSourceConfig = FlinkUtils.getKafkaSource(Constants.ods_user_operate, "ods2dwd_group_consumer");

        DataStreamSource<String> kafkaSource = environment.fromSource(kafkaSourceConfig, WatermarkStrategy.noWatermarks(), "kafkaSource");


        SingleOutputStreamOperator<String> resultMap = kafkaSource.map(new RichMapFunction<String, String>() {
            @Override
            public String map(String line) throws Exception {
                System.out.println(line);
                String[] split = line.split("#CS#");
                if(split.length >=4){
                    return split[4];
                }else{
                    return "";
                }
            }
        });

        /**
         * 对数据进行解析，解析成为一个个的对象
         */
        SingleOutputStreamOperator<BaseLog> process = resultMap.process(new ProcessFunction<String, BaseLog>() {
            @Override
            public void processElement(String line, Context context, Collector<BaseLog> collector) throws Exception {
                System.out.println(line);
                if (line.split("\\|").length == 2) {
                    //用户行为日志
                    String[] split = line.split("\\|");
                    String timeStamp = split[0];
                    String user_operate = split[1];
                    Ods_user_operate ods_user_operate = JsonUtils.parseUserOperate(user_operate);
                    ods_user_operate.setTimeStamp(timeStamp);
                    BaseLog baseLog = new BaseLog();
                    baseLog.setOds_user_operate(ods_user_operate);
                    collector.collect(baseLog);
                } else {
                    //用户启动日志
                    Ods_user_start ods_user_start = JsonUtils.parseUserStart(line);
                    BaseLog baseLog = new BaseLog();
                    baseLog.setOds_user_start(ods_user_start);
                    collector.collect(baseLog);
                }
            }
        });


        //对数据进行过滤
        SingleOutputStreamOperator<String> ods_user_operateSingleOutputStreamOperator = process.filter(new FilterFunction<BaseLog>() {
            @Override
            public boolean filter(BaseLog baseLog) throws Exception {
                if (baseLog.getOds_user_operate() != null) {
                    return true;
                } else {
                    return false;
                }
            }
        }).map(new MapFunction<BaseLog, String>() {
            @Override
            public String map(BaseLog baseLog) throws Exception {
                return JSONObject.toJSONString( baseLog.getOds_user_operate());
            }
        });

        SingleOutputStreamOperator<String> ods_user_start_outputStream = process.filter(new FilterFunction<BaseLog>() {
            @Override
            public boolean filter(BaseLog baseLog) throws Exception {
                if (baseLog.getOds_user_start() != null) {
                    return true;
                } else {
                    return false;
                }
            }
        }).map(new MapFunction<BaseLog, String>() {
            @Override
            public String map(BaseLog baseLog) throws Exception {

                return JSONObject.toJSONString(baseLog.getOds_user_start());
            }
        });

        //数据写入到kafka
        ods_user_operateSingleOutputStreamOperator.addSink(FlinkUtils.getKafkaProducer(Constants.dwd_user_operate));
        ods_user_start_outputStream.addSink(FlinkUtils.getKafkaProducer(Constants.dwd_user_start));

        environment.execute();

    }


}
