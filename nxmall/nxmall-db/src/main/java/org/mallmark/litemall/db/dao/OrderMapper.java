package org.mallmark.litemall.db.dao;

import org.apache.ibatis.annotations.Param;
import org.mallmark.litemall.db.domain.LitemallOrder;

import java.time.LocalDateTime;

public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime, @Param("order") LitemallOrder order);
}