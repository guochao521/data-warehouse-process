package cn.house.dws;

import cn.mobile.common.bean.LitemallOrderDetailCap;
import cn.mobile.common.bean.LitemallOrderDetailWide;
import cn.mobile.common.bean.LitemallOrderWide;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction;
import org.apache.flink.util.Collector;

public class OrderDetailIntervalJoinPorcess  extends ProcessJoinFunction<String,String,String > {
    //记录历史累计金额
    private MapState<Integer,Double> orderSumState;
    //记录历史均摊金额
    private MapState<Integer,Double> orderCapSumState;

    @Override
    public void open(Configuration parameters) throws Exception {
        StateTtlConfig stateTtlConfig = StateTtlConfig.newBuilder(Time.seconds(60)) //设置过期时间
                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                .build();
        //设置订单历史累加
        MapStateDescriptor<Integer, Double> orderSumStateDesc = new MapStateDescriptor<>("order_sum", Integer.class, Double.class);
        orderSumStateDesc.enableTimeToLive(stateTtlConfig);
        orderSumState = getRuntimeContext().getMapState(orderSumStateDesc);

        //设置订单均摊累加
        MapStateDescriptor<Integer, Double> orderCapSumStateDesc = new MapStateDescriptor<>("order_cap_sum", Integer.class, Double.class);
        orderCapSumStateDesc.enableTimeToLive(stateTtlConfig);
        orderCapSumState =  getRuntimeContext().getMapState(orderCapSumStateDesc);


    }

    @Override
    public void processElement(String orderWide, String orderDetailWide , Context context, Collector<String> collector) throws Exception {
        //LitemallOrderWide   ==> orderWide
        // LitemallOrderDetailWide  ==> orderDetailWide
        LitemallOrderDetailWide litemallOrderDetailWide = JSONObject.parseObject(orderDetailWide, LitemallOrderDetailWide.class);
        LitemallOrderDetailCap litemallOrderDetailCap = new LitemallOrderDetailCap();
        BeanUtils.copyProperties(litemallOrderDetailCap,litemallOrderDetailWide);


        LitemallOrderWide litemallOrderWide = JSONObject.parseObject(orderWide, LitemallOrderWide.class);
        //历史订单累计金额
        Double order_his_sum = orderSumState.get(litemallOrderWide.getId());
        if(null == order_his_sum){
            orderSumState.put(litemallOrderWide.getId(),0d);
            order_his_sum = orderSumState.get(litemallOrderWide.getId());
        }
        //当前商品金额
        double current_order_sum = litemallOrderDetailWide.getPrice() * litemallOrderDetailWide.getNumber();

        //获取订单历史累加金额
        Double order_cap_sum_his = orderCapSumState.get(litemallOrderWide.getId());
        if(null == order_cap_sum_his){
            orderCapSumState.put(litemallOrderWide.getId(),0d);
            order_cap_sum_his = orderCapSumState.get(litemallOrderWide.getId());
        }


        Double current_order_cap = 0d;
        // 判断是否是最后一个订单详情  商品总金额=历史商品总金额+当前详情的金额
        if(litemallOrderWide.getGoods_price() == order_his_sum + current_order_sum){
            //最后一个订单详情
            current_order_cap = litemallOrderWide.getActual_price() - order_cap_sum_his;
        }else{
            current_order_cap = litemallOrderWide.getActual_price() * (current_order_sum / litemallOrderWide.getGoods_price() );
        }
        //更新最后订单表金额
        litemallOrderDetailCap.setCapitation_price(current_order_cap);

        //更新历史状态
        orderSumState.put(litemallOrderWide.getId(),(order_his_sum + current_order_sum));
        orderCapSumState.put(litemallOrderWide.getId(),(order_cap_sum_his + current_order_cap));

        //将数据保存
        collector.collect(JSONObject.toJSONString(litemallOrderDetailCap));



    }
}
