package org.simulate.dw.service;

import lombok.extern.slf4j.Slf4j;
import org.mallmark.litemall.db.dao.LitemallCouponUserMapper;
import org.mallmark.litemall.db.domain.LitemallCoupon;
import org.mallmark.litemall.db.domain.LitemallCouponUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.simulate.dw.config.SimulateProperty;
import org.simulate.dw.util.ParamUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 领劵
 *
 */
@Slf4j
@Service
public class CouPonUseService {

    @Autowired
    private SimulateProperty simulateProperty;

    @Autowired
    private CommonDataService commonDataService;

    @Autowired
    private LitemallCouponUserMapper couponUserMapper;

    public void genCouPonUse() {
        Date date = this.simulateProperty.isUseDate() ? ParamUtil.checkDate(this.simulateProperty.getDate()) : new Date();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        int userCount = this.simulateProperty.getCoupon().getUserCount();
        for (int i = 0; i < userCount; i++) {
            int userId = commonDataService.randomUserId();
            LitemallCoupon litemallCoupon = commonDataService.randomCouPonId();
            LitemallCouponUser couponUser = new LitemallCouponUser();
            couponUser.setUserId(userId);
            couponUser.setCouponId(litemallCoupon.getId());
            couponUser.setStatus(litemallCoupon.getStatus());
            couponUser.setStartTime(litemallCoupon.getStartTime());
            couponUser.setEndTime(litemallCoupon.getEndTime());
            couponUser.setAddTime(localDateTime);
            couponUser.setDeleted(false);
            this.couponUserMapper.insert(couponUser);
        }
        log.info("共生成{}个用户领劵", userCount);
    }

}
