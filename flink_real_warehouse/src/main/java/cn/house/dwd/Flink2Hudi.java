package cn.house.dwd;

import cn.house.utils.FlinkUtils;
import cn.house.utils.FlinkSqlIntegeration;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;


public class Flink2Hudi {

    public static void main(String[] args) {
        //获取执行环境
        StreamTableEnvironment tableEnvironment = FlinkUtils.getTableEnvironment("hdfs://bigdata01:8020/flink2kafka22");
        //广告
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appAd);//定义kafka当中的原始的数据表映射
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appAd_hudi_sink);  //定义数据的保存hudi的表映射
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appAd_hudi_sink_insert);  //执行insert into select的sql将数据保存到hudi里面去
        //购物车
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appCar);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appCar_hudi_sink);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appCar_hudi_sink_insert);
        //评论
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appComment);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appComment_hudi_sink);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appComment_hudi_sink_insert);
        //列表
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appDisplay);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appDisplay_hudi_sink);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appDisplay_hudi_sink_insert);
        //收藏
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appFavorites);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appFavorites_hudi_sink);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_dwd_user_appFavorites_hudi_sink_insert);


        //加载
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appLoading);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appLoading_hudi_sink);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appLoading_hudi_sink_insert);

        //点赞
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appPraise);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appPraise_hudi_sink);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_appPraise_hudi_sink_insert);

        //启动
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_start);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_start_hudi_sink);
        tableEnvironment.executeSql(FlinkSqlIntegeration.dwd_user_start_hudi_sink_insert);
        //
        tableEnvironment.executeSql("select * from dwd_user_start_hudi_sink limit 20").print();
        tableEnvironment.executeSql("select * from dwd_user_start_hudi_sink limit 20").print();

    }

}
