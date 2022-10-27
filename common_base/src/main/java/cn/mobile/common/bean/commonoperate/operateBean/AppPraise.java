package cn.mobile.common.bean.commonoperate.operateBean;

import cn.mobile.common.bean.commonoperate.CommonBaseBean;

/**
 */
public class AppPraise extends CommonBaseBean {

    private int id; //主键id
    private int userId;//用户id
    private int targetId;//点赞的对象id
    private int type;//点赞类型1 问答点赞2 问答评论点赞3 文章点赞数4 评论点赞
    private String addTime;//添加时间



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



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
