package org.simulate.dw.mock;

import lombok.extern.slf4j.Slf4j;
import org.simulate.dw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.simulate.dw.service.*;


@Slf4j
@Component
public class BusinessService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FavorInfoService favorInfoService;

    @Autowired
    private CartInfoService cartInfoService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private OrderShipService orderShipService;

    @Autowired
    private OrderRefundInfoService orderRefundInfoService;

    @Autowired
    private OrderConfirmService orderConfirmService;

    @Autowired
    private CommentInfoService commentInfoService;

    @Autowired
    private CouPonUseService couPonUseService;

    public void process() {
        log.info("business simulate process .....");
        log.info("--------开始生成用户数据--------");
        //生成新注册用户，并给用户发送新人注册优惠券
        //涉及到两张表litemall_user   litemall_coupon_user
        this.userInfoService.genUserInfo();
        log.info("--------开始领卷数据--------");
        this.couPonUseService.genCouPonUse();
        log.info("--------开始生成收藏数据--------");
        this.favorInfoService.genFavors();
        log.info("--------开始生成购物车数据--------");
        this.cartInfoService.genCartInfo();
        log.info("--------开始生成订单数据--------");
        this.orderInfoService.genOrderInfo();
        log.info("--------开始生成支付数据--------");
        this.paymentInfoService.genPayments();
        log.info("--------开始生成发货数据--------");
        this.orderShipService.genShip();
        log.info("--------开始生成退单数据--------");
        this.orderRefundInfoService.genRefundsOrFinish();
        log.info("--------开始生成确认收货数据--------");
        this.orderConfirmService.genConfirm();
        log.info("--------开始生成评论数据--------");
        this.commentInfoService.genComments();
    }

    public void processOnlyShip(){
        log.info("--------开始生成发货数据--------");
        this.orderShipService.genShip();
    }

}
