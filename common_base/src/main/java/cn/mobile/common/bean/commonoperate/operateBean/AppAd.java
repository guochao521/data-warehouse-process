package cn.mobile.common.bean.commonoperate.operateBean;

import cn.mobile.common.bean.commonoperate.CommonBaseBean;

/**
 * 广告
 * @author
 * @desc
 *
 */
public class AppAd extends CommonBaseBean {

    private String entry;//入口：商品列表页=1 应用首页=2 商品详情页=3
    private String action;//动作： 广告展示=1 广告点击=2
    private String contentType;//Type: 1 商品2 营销活动
    private String displayMills;//展示时长毫秒数
    private int itemId; //商品id
    private String activityId; //营销活动id

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

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDisplayMills() {
        return displayMills;
    }

    public void setDisplayMills(String displayMills) {
        this.displayMills = displayMills;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
