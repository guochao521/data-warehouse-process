package cn.mobile.common.bean.commonoperate.operateBean;

import cn.mobile.common.bean.commonoperate.CommonBaseBean;

/**
 * 加购物
 *
 */
public class AppCar extends CommonBaseBean {

    private int userId;//用户ID
    private int goodsId;//商品ID
    private int skuId;//库存ID
    private int num;//加购数量
    private String addTime;



    private String ett;
    private String en;


    public String getEtt() {
        return ett;
    }

    public void setEtt(String ett) {
        this.ett = ett;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
