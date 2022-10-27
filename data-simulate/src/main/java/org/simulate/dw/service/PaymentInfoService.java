package org.simulate.dw.service;

import lombok.extern.slf4j.Slf4j;
import org.mallmark.litemall.db.dao.LitemallOrderMapper;
import org.mallmark.litemall.db.domain.LitemallOrder;
import org.mallmark.litemall.db.domain.LitemallOrderExample;
import org.mallmark.litemall.db.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.simulate.dw.config.SimulateProperty;
import org.simulate.dw.util.ParamUtil;
import org.simulate.dw.util.RanOpt;
import org.simulate.dw.util.RandomOptionGroup;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 支付
 *
 *
 * @desc
 *
 */
@Slf4j
@Service
public class PaymentInfoService {

    @Autowired
    private SimulateProperty simulateProperty;

    @Autowired
    private LitemallOrderMapper orderMapper;

    public void genPayments() {
        Date date = this.simulateProperty.isUseDate() ? ParamUtil.checkDate(this.simulateProperty.getDate()) : new Date();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        int rate = this.simulateProperty.getPayment().getRate();
        RandomOptionGroup<Boolean> ifPay = new RandomOptionGroup<>(new RanOpt[]{new RanOpt(true, rate), new RanOpt(false, 100 - rate)});

        //1.查询下单的订单
        LitemallOrderExample example = new LitemallOrderExample();
        example.createCriteria().andOrderStatusEqualTo(OrderUtil.STATUS_CREATE);
        List<LitemallOrder> litemallOrders = this.orderMapper.selectByExample(example);
        if (ObjectUtils.isEmpty(litemallOrders)) {
            log.info("没有需要支付的订单 ");
            return;
        }
        //2.支付
        int payCount = 0;
        for (LitemallOrder litemallOrder : litemallOrders) {
            if (ifPay.getRandBoolValue()) {
                //状态
                litemallOrder.setOrderStatus(OrderUtil.STATUS_PAY);
                //支付信息
                litemallOrder.setPayId(UUID.randomUUID().toString());
                litemallOrder.setPayTime(localDateTime);
                //更新操作时间
                litemallOrder.setUpdateTime(localDateTime);
                litemallOrder.setDeleted(false);

                //更新数据
                this.orderMapper.updateByPrimaryKey(litemallOrder);
                payCount++;
            }
        }

        log.info("共生成支付{}条", payCount);
    }
}
