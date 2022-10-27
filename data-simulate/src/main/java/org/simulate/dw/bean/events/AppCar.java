package org.simulate.dw.bean.events;

import lombok.Data;

/**
 * 加购物
 *
 */
@Data
public class AppCar {

    private int userId;//用户ID
    private int goodsId;//商品ID
    private int skuId;//库存ID
    private int num;//加购数量
    private String addTime;

}
