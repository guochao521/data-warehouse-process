package org.simulate.dw.bean.events;

import lombok.Data;

/**
收藏
 */
@Data
public class AppFavorites {

    private int id;//主键
    private int courseId;//商品id
    private int userId;//用户ID
    private String addTime;//创建时间

}
