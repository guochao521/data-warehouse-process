package cn.house.ods;

import cn.mobile.common.bean.commonoperate.CommonBaseBean;
import cn.mobile.common.bean.commonoperate.operateBean.*;
import cn.mobile.common.util.Constants;
import cn.house.utils.FlinkUtils;
import cn.house.utils.CommonBeanAndOperateUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class DwdParseExtended {

    /**
     * kafka当中的数据进一步解析，主要是将用户行为数据，解析出来成为一个个的表
     *
     * @param args
     *
     * {"ap":"app","cm":{"sv":"V2.8.4","os":"8.2.9","g":"0088@gmail.com","mid":"260","l":"en","vc":"3","hw":"1080*1920","ar":"MX","uid":185,"t":"1647228899711","md":"Huawei-12","vn":"1.1.2","ba":"Huawei","sr":"S"},"et":[{"ett":"1647152063034","en":"ad","kv":{"activityId":"1","displayMills":"35913","entry":"3","itemId":0,"action":"1","contentType":"0"}},{"ett":"1647223703455","en":"comment","kv":{"valueId":266,"addTime":"1647212094313","star":4,"type":1,"userId":713,"content":"癣桅焚疗器祷狄戍酒披际没宽渊傻"}},{"ett":"1647175961129","en":"favorites","kv":{"addTime":"1647202584984","id":0,"courseId":1127052,"userId":1}},{"ett":"1647143259496","en":"addCar","kv":{"addTime":"1647131982887","goodsId":1152031,"num":7,"userId":99,"skuId":234}}],"timeStamp":"1647230400875"}
     * 解析jsonArray字段将所有的jsonArray全部都解析出来成为一个个的表
     */
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = FlinkUtils.getEnvironment("file:///D:\\ck_dir2");
        //构建kafkasource 数据源
        KafkaSource kafkaSourceConfig = FlinkUtils.getKafkaSource(Constants.dwd_user_operate, "ods2dwd_group_extended");

        DataStreamSource<String> kafkaSource = environment.fromSource(kafkaSourceConfig, WatermarkStrategy.noWatermarks(), "kafkaSource_operate");

        SingleOutputStreamOperator<Object> flatMapResult = kafkaSource.flatMap(new FlatMapFunction<String, Object>() {
            @Override
            public void flatMap(String line, Collector<Object> collector) throws Exception {

                JSONObject jsonObj = JSONObject.parseObject(line);
                String timeStamp = jsonObj.getString("timeStamp");//产生时间戳
                System.out.println(timeStamp);

                String ap = jsonObj.getString("ap");
                //获取到了commonBase这个对象
                CommonBaseBean commonBaseBean = jsonObj.parseObject(String.valueOf(jsonObj.getJSONObject("cm")), CommonBaseBean.class);
                commonBaseBean.setAp(ap);
                commonBaseBean.setTimeStamp(timeStamp);


                JSONArray jsonArray = jsonObj.getJSONArray("et");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String ett = jsonObject.getString("ett");
                    String en = jsonObject.getString("en");//获取事件类型
                    //事件类型对应的json格式字符串
                    JSONObject kv = jsonObject.getJSONObject("kv");

                    switch (en) {
                        case "display":
                            //   new AppDisplay
                            AppDisplay appDisplay = JSONObject.parseObject(String.valueOf(kv), AppDisplay.class);
                            appDisplay.setEtt(ett);
                            appDisplay.setEn(en);
                            System.out.println(JSONObject.toJSONString(appDisplay));
                            Object appDisplayObj = CommonBeanAndOperateUtils.getCommonBeanAndOperate(commonBaseBean, appDisplay);
                            collector.collect(appDisplayObj);
                            break;
                        case "loading":
                            //   new AppDisplay
                            AppLoading appLoading = JSONObject.parseObject(String.valueOf(kv), AppLoading.class);

                            appLoading.setEtt(ett);
                            appLoading.setEn(en);
                            System.out.println(JSONObject.toJSONString(appLoading));
                            Object appLoadingObj = CommonBeanAndOperateUtils.getCommonBeanAndOperate(commonBaseBean, appLoading);
                            collector.collect(appLoadingObj);
                            break;
                        case "ad":
                            //   new AppDisplay
                            AppAd appAd = JSONObject.parseObject(String.valueOf(kv), AppAd.class);
                            appAd.setEtt(ett);
                            appAd.setEn(en);
                            System.out.println(JSONObject.toJSONString(appAd));
                            Object appAdObj = CommonBeanAndOperateUtils.getCommonBeanAndOperate(commonBaseBean, appAd);
                            collector.collect(appAdObj);
                            break;

                        case "comment":
                            //   new AppDisplay
                            AppComment appComment = JSONObject.parseObject(String.valueOf(kv), AppComment.class);
                            appComment.setEtt(ett);
                            appComment.setEn(en);
                            System.out.println(JSONObject.toJSONString(appComment));
                            Object appCommentObj = CommonBeanAndOperateUtils.getCommonBeanAndOperate(commonBaseBean, appComment);
                            collector.collect(appCommentObj);
                            break;
                        case "favorites":
                            //   new AppDisplay
                            AppFavorites appFavorites = JSONObject.parseObject(String.valueOf(kv), AppFavorites.class);
                            appFavorites.setEtt(ett);
                            appFavorites.setEn(en);
                            System.out.println(JSONObject.toJSONString(appFavorites));
                            Object appFavoritesObj = CommonBeanAndOperateUtils.getCommonBeanAndOperate(commonBaseBean, appFavorites);
                            collector.collect(appFavoritesObj);
                            break;
                        case "praise":
                            //   new AppDisplay
                            AppPraise appPraise = JSONObject.parseObject(String.valueOf(kv), AppPraise.class);
                            appPraise.setEtt(ett);
                            appPraise.setEn(en);
                            System.out.println(JSONObject.toJSONString(appPraise));
                            Object appPariseObj = CommonBeanAndOperateUtils.getCommonBeanAndOperate(commonBaseBean, appPraise);
                            collector.collect(appPariseObj);
                            break;
                        case "addCar":
                            //   new AppDisplay
                            AppCar appCar = JSONObject.parseObject(String.valueOf(kv), AppCar.class);
                            appCar.setEtt(ett);
                            appCar.setEn(en);
                            System.out.println(JSONObject.toJSONString(appCar));
                            Object appCarObj = CommonBeanAndOperateUtils.getCommonBeanAndOperate(commonBaseBean, appCar);
                            collector.collect(appCarObj);
                            break;
                        default:
                            System.out.println("没有匹配上的额外事件");
                    }

                }
            }
        });


        //对object对象进行过滤，过滤解析成为一个个的
        flatMapResult.filter(new FilterFunction<Object>() {
            @Override
            public boolean filter(Object object) throws Exception {
                if (object instanceof AppAd) {
                    return true;
                } else {
                    return false;
                }

            }
        }).map(new MapFunction<Object, String>() {
            @Override
            public String map(Object o) throws Exception {

                return JSONObject.toJSONString(o);
            }
        }).addSink(FlinkUtils.getKafkaProducer(Constants.dwd_user_appAd));//解析出来数据之后，继续添加到广告的topic里面去了


        //对object对象进行过滤，过滤解析成为一个个的
         flatMapResult.filter(new FilterFunction<Object>() {
            @Override
            public boolean filter(Object object) throws Exception {
                if (object instanceof AppCar) {
                    return true;
                } else {
                    return false;
                }

            }
        }).map(new MapFunction<Object, String>() {
            @Override
            public String map(Object o) throws Exception {

                return JSONObject.toJSONString(o);
            }
        }).addSink(FlinkUtils.getKafkaProducer(Constants.dwd_user_appCar));

        //对object对象进行过滤，过滤解析成为一个个的
       flatMapResult.filter(new FilterFunction<Object>() {
            @Override
            public boolean filter(Object object) throws Exception {
                if (object instanceof AppComment) {
                    return true;
                } else {
                    return false;
                }

            }
        }).map(new MapFunction<Object, String>() {
            @Override
            public String map(Object o) throws Exception {
                System.out.println(JSONObject.toJSONString(o));

                return JSONObject.toJSONString(o);
            }
        }).addSink(FlinkUtils.getKafkaProducer(Constants.dwd_user_appComment));
        //对object对象进行过滤，过滤解析成为一个个的
        flatMapResult.filter(new FilterFunction<Object>() {
            @Override
            public boolean filter(Object object) throws Exception {
                if (object instanceof AppDisplay) {
                    return true;
                } else {
                    return false;
                }

            }
        }).map(new MapFunction<Object, String>() {
            @Override
            public String map(Object o) throws Exception {
                System.out.println(JSONObject.toJSONString(o));
                return JSONObject.toJSONString(o);
            }
        }).addSink(FlinkUtils.getKafkaProducer(Constants.dwd_user_appDisplay));

        //对object对象进行过滤，过滤解析成为一个个的
        flatMapResult.filter(new FilterFunction<Object>() {
            @Override
            public boolean filter(Object object) throws Exception {
                if (object instanceof AppFavorites) {
                    return true;
                } else {
                    return false;
                }

            }
        }).map(new MapFunction<Object, String>() {
            @Override
            public String map(Object o) throws Exception {
                System.out.println(JSONObject.toJSONString(o));
                return JSONObject.toJSONString(o);
            }
        }).addSink(FlinkUtils.getKafkaProducer(Constants.dwd_user_appFavorites));
        //对object对象进行过滤，过滤解析成为一个个的
         flatMapResult.filter(new FilterFunction<Object>() {
            @Override
            public boolean filter(Object object) throws Exception {
                if (object instanceof AppLoading) {
                    return true;
                } else {
                    return false;
                }

            }
        }).map(new MapFunction<Object, String>() {
            @Override
            public String map(Object o) throws Exception {
                System.out.println(JSONObject.toJSONString(o));
                return JSONObject.toJSONString(o);
            }
        }).addSink(FlinkUtils.getKafkaProducer(Constants.dwd_user_appLoading));


        //对object对象进行过滤，过滤解析成为一个个的
       flatMapResult.filter(new FilterFunction<Object>() {
            @Override
            public boolean filter(Object object) throws Exception {
                if (object instanceof AppPraise) {
                    return true;
                } else {
                    return false;
                }

            }
        }).map(new MapFunction<Object, String>() {
            @Override
            public String map(Object o) throws Exception {
                System.out.println(JSONObject.toJSONString(o));
                return JSONObject.toJSONString(o);
            }
        }).addSink(FlinkUtils.getKafkaProducer(Constants.dwd_user_appPraise));

        environment.execute();

    }

}
