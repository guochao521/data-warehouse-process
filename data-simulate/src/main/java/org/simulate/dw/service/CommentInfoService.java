package org.simulate.dw.service;

import lombok.extern.slf4j.Slf4j;
import org.mallmark.litemall.db.dao.LitemallCommentMapper;
import org.mallmark.litemall.db.domain.LitemallComment;
import org.simulate.dw.dto.CommentDto;
import org.simulate.dw.util.RandomNumString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.simulate.dw.config.SimulateProperty;
import org.simulate.dw.util.ParamUtil;
import org.simulate.dw.util.RanOpt;
import org.simulate.dw.util.RandomOptionGroup;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 评论
 * 周期性事务
 *
 *
 * @desc
 *
 */
@Slf4j
@Service
public class CommentInfoService {

    @Autowired
    private SimulateProperty simulateProperty;

    @Autowired
    private CommonDataService commonDataService;

    @Autowired
    private LitemallCommentMapper commentMapper;


    public void genComments() {
        //商品评论
        saveComment(0, this.commonDataService.randomSku(30));
        //主题评论
        saveComment(1, this.commonDataService.randomTopic(30));
    }

    private void saveComment(int type, Set<CommentDto> valueIds) {
        int rate = this.simulateProperty.getComment().getRate();
        RandomOptionGroup<Boolean> ifComment = new RandomOptionGroup<>(new RanOpt[]{new RanOpt(true, rate), new RanOpt(false, 100 - rate)});

        List<Integer> appraiseRate = this.simulateProperty.getComment().getAppraiseRate();

        Date date = this.simulateProperty.isUseDate() ? ParamUtil.checkDate(this.simulateProperty.getDate()) : new Date();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Random random = new Random();

        RandomOptionGroup<Integer> appraiseOptionGroup = new RandomOptionGroup<>(new RanOpt[]{
                new RanOpt(5, appraiseRate.get(0)),
                new RanOpt(3, appraiseRate.get(1)),
                new RanOpt(0, appraiseRate.get(2)),
                new RanOpt(1, appraiseRate.get(3))});

        int commentCount = 0;
        for (CommentDto valueId : valueIds) {
            if (ifComment.getRandBoolValue()) {
                int userId = this.commonDataService.randomUserId();
                LitemallComment comment = init(userId, valueId, type, localDateTime);
                //设置评价
                int star = appraiseOptionGroup.getRandIntValue();
                star = star != 1 ? star : random.nextInt(5);
                comment.setStar((short) star);
                this.commentMapper.insert(comment);
                commentCount++;
            }
        }
        log.info("总共生成{}评价{}条", type == 1 ? "主题" : "商品", commentCount);
    }

    private LitemallComment init(int userId, CommentDto valueId, int type, LocalDateTime dateTime) {
        LitemallComment litemallComment = new LitemallComment();
        litemallComment.setUserId(userId);
        if (type == 0) {
            litemallComment.setProductId(valueId.getProductId());
        }
        litemallComment.setValueId(valueId.getValueId());
        litemallComment.setType((byte) type);
        litemallComment.setDeleted(false);
        litemallComment.setAddTime(dateTime);
        litemallComment.setContent("评论内容" + RandomNumString.getRandNumString(1, 9, 50, ""));

        return litemallComment;
    }

}
