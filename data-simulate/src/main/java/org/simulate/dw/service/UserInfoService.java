package org.simulate.dw.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.mallmark.litemall.db.dao.LitemallAddressMapper;
import org.mallmark.litemall.db.dao.LitemallUserMapper;
import org.mallmark.litemall.db.domain.LitemallAddress;
import org.mallmark.litemall.db.domain.LitemallUser;
import org.mallmark.litemall.db.service.CouponAssignService;
import org.simulate.dw.bean.RegionInfo;
import org.simulate.dw.config.SimulateProperty;
import org.simulate.dw.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.simulate.dw.util.*;
import org.simulate.dw.util.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 *
 * @desc
 *
 */
@Slf4j
@Service
public class UserInfoService extends Observable {

    public static final String PASSWORD = "123456";

    @Autowired
    private LitemallUserMapper userMapper;

    @Autowired
    private CouponAssignService couponAssignService;

    @Autowired
    private SimulateProperty simulateProperty;

    @Autowired
    private CommonDataService commonDataService;

    @Transactional
    public void genUserInfo() {
        Date date = this.simulateProperty.isUseDate() ? ParamUtil.checkDate(this.simulateProperty.getDate()) : new Date();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // 更新用户时间
        updateUsers(localDateTime);
        //添加用户
        for (int i = 0; i < simulateProperty.getUser().getCount(); i++) {
            LitemallUser user = initUserInfo(localDateTime);
            Date birthday = DateUtils.addMonths(date, -1 * RandomNum.getRandInt(15, 55) * 12);
            user.setBirthday(birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            //注册新用户，涉及到的表litemall_user
            this.userMapper.insertSelective(user);
            //生成start log
            this.setChanged();
            this.notifyObservers(user.getId());

            // 给新用户发送注册优惠券  新注册用户发送新人优惠券 涉及到的表 litemall_coupon_user
            couponAssignService.assignForRegister(user.getId());
            // 记录新增用户的ID
            this.commonDataService.updateUserId(user.getId());
            //生成用户地址
            this.addressProcess(user.getId(), user.getNickname(), user.getMobile(), localDateTime);
        }
        log.info("共生成{}名用户", simulateProperty.getUser().getCount());
    }

    private LitemallUser initUserInfo(LocalDateTime localDateTime) {
        Integer maleRateWeight = ParamUtil.checkRatioNum(this.simulateProperty.getUser().getMaleRate());

        String email = RandomEmail.getEmail(6, 12);
        String username = email.split("@")[0];

        LitemallUser user = new LitemallUser();
        user.setUsername(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(PASSWORD);
        user.setPassword(encodedPassword);
        user.setMobile("13" + RandomNumString.getRandNumString(1, 9, 9, ""));
        user.setWeixinOpenid(UUID.randomUUID().toString());
        user.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        user.setGender((byte) (new RandomOptionGroup(new RanOpt[]{new RanOpt(2, maleRateWeight.intValue()), new RanOpt(1, 100 - maleRateWeight.intValue())})).getRandIntValue());
        String lastName = RandomName.insideLastName(user.getGender());
        user.setNickname(RandomName.getNickName(user.getGender(), lastName));
        user.setUserLevel((byte) (new RandomOptionGroup(new RanOpt[]{new RanOpt(1, 7), new RanOpt(2, 2), new RanOpt(3, 1)})).getRandIntValue());
        user.setStatus((byte) 0);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(RandomIp.getIp());

        user.setAddTime(LocalDateTime.from(localDateTime));

        return user;
    }

    private void updateUsers(LocalDateTime date) {
        Integer updateRateWeight = ParamUtil.checkRatioNum(this.simulateProperty.getUser().getUpdateRate());
        if (updateRateWeight.intValue() == 0) {
            return;
        }

        Set<Integer> userInfoList = this.commonDataService.randomUserId(updateRateWeight);
        for (int id : userInfoList) {
            LitemallUser userInfo = this.userMapper.selectByPrimaryKey(id);
            if (userInfo == null) {
                continue;
            }
            int randInt = RandomNum.getRandInt(2, 7);
            if (randInt % 2 == 0) {
                String lastName = RandomName.insideLastName(userInfo.getGender());
                userInfo.setNickname(RandomName.getNickName(userInfo.getGender(), lastName));
            }
            if (randInt % 3 == 0) {
                userInfo.setUserLevel((byte) (new RandomOptionGroup(new RanOpt[]{new RanOpt(1, 7), new RanOpt(2, 2), new RanOpt(3, 1)})).getRandIntValue());
            }
            if (randInt % 7 == 0) {
                userInfo.setMobile("13" + RandomNumString.getRandNumString(1, 9, 9, ""));
            }
            //设置更新时间
            userInfo.setUpdateTime(date);
            //更新用户
            this.userMapper.updateByPrimaryKey(userInfo);
        }
        log.info("共有{}名用户发生变更", Integer.valueOf(userInfoList.size()));

    }

    @Autowired
    private LitemallAddressMapper addressMapper;

    private Random random = new Random();

    private void addressProcess(int userId, String userName, String mobile, LocalDateTime dateTime) {
        RegionInfo regionInfo = this.commonDataService.randomRegion();
        LitemallAddress litemallAddress = new LitemallAddress();
        litemallAddress.setName(userName);
        litemallAddress.setUserId(userId);
        litemallAddress.setProvince(regionInfo.getPId());
        litemallAddress.setCity(regionInfo.getCId());
        litemallAddress.setCounty(regionInfo.getTId());

        StringBuilder detail = new StringBuilder(regionInfo.getPName())
                .append(regionInfo.getCName())
                .append(regionInfo.getTName())
                .append("第").append(RandomNum.getRandInt(1, 20)).append("大街第")
                .append(RandomNum.getRandInt(1, 40)).append("号楼")
                .append(RandomNum.getRandInt(1, 9)).append("单元")
                .append(RandomNumString.getRandNumString(1, 9, 3, "")).append("门");

        litemallAddress.setAddressDetail(detail.toString());
        litemallAddress.setAreaCode(regionInfo.getCode());
        litemallAddress.setPostalCode(regionInfo.getCode());
        litemallAddress.setTel(mobile);
        litemallAddress.setIsDefault(random.nextBoolean());
        litemallAddress.setDeleted(false);

        litemallAddress.setAddTime(dateTime);
        this.addressMapper.insert(litemallAddress);
    }

}
