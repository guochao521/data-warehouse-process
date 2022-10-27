package cn.mobile.common.bean.commonoperate.operateBean;

import cn.mobile.common.bean.commonoperate.CommonBaseBean;

/**
 * 商品详情
 */
public class AppDisplay extends CommonBaseBean {

    private String action;//动作：曝光商品=1，点击商品=2，
    private int goodsId;//商品ID（服务端下发的ID）
    private String place;//顺序（第几条商品，第一条为0，第二条为1，如此类推）
    private String extend1;//曝光类型：1 - 首次曝光2-重复曝光（没有使用）
    private String category;//分类ID（服务端定义的分类ID）



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



    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
