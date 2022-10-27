package org.simulate.dw.service;

import lombok.extern.slf4j.Slf4j;
import org.mallmark.litemall.db.dao.LitemallCollectMapper;
import org.mallmark.litemall.db.domain.LitemallCollect;
import org.mallmark.litemall.db.domain.LitemallCollectExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.simulate.dw.config.SimulateProperty;
import org.simulate.dw.util.ParamUtil;
import org.simulate.dw.util.RanOpt;
import org.simulate.dw.util.RandomOptionGroup;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

/**
 * 收藏
 * 周期性快照表
 *
 *
 * @desc
 *
 */
@Slf4j
@Service
public class FavorInfoService {

    @Autowired
    private SimulateProperty simulateProperty;

    @Autowired
    private CommonDataService commonDataService;

    @Autowired
    private LitemallCollectMapper collectMapper;

    public void genFavors() {
        Date date = this.simulateProperty.isUseDate() ? ParamUtil.checkDate(this.simulateProperty.getDate()) : new Date();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Random random = new Random();
        int count = this.simulateProperty.getFavor().getCount();
        for (int i = 0; i < count; i++) {
            int type = random.nextInt(2);
            int userId = this.commonDataService.randomUserId();
            int valueId = type == 0 ? this.commonDataService.randomGoodId() : this.commonDataService.randomTopicId();
            this.collectOrCancel(userId, valueId, type, localDateTime);
        }

        log.info("共生成收藏{}条", count);
    }

    private void collectOrCancel(int userId, int valueId, int type, LocalDateTime date) {
        //1.检查该数据是否收藏过
        LitemallCollectExample example = new LitemallCollectExample();
        example.or().andUserIdEqualTo(userId).andValueIdEqualTo(valueId).andTypeEqualTo((byte) type).andDeletedEqualTo(false);
        LitemallCollect collect = this.collectMapper.selectOneByExample(example);
        if (collect != null) {
            //2.是否取消
            int cancelRate = this.simulateProperty.getFavor().getCancelRate();
            RandomOptionGroup<Boolean> isCancelOptionGroup = new RandomOptionGroup<>(new RanOpt[]{new RanOpt(true, cancelRate), new RanOpt(false, 100 - cancelRate)});
            Boolean isCancel = isCancelOptionGroup.getRandBoolValue();
            if (isCancel) {
                //删除
                this.collectMapper.logicalDeleteByPrimaryKey(collect.getId());
            }
        } else {
            //3.收藏
            collect = new LitemallCollect();
            collect.setUserId(userId);
            collect.setValueId(valueId);
            collect.setType((byte) type);
            collect.setAddTime(date);
            collect.setDeleted(false);

            this.collectMapper.insert(collect);
        }
    }

}
