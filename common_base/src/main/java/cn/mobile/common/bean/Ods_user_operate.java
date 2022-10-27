package cn.mobile.common.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class Ods_user_operate implements Serializable {

    private String timeStamp;
    private JSONObject cm;
    private String ap;
    private JSONArray et;


    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public JSONObject getCm() {
        return cm;
    }

    public void setCm(JSONObject cm) {
        this.cm = cm;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public JSONArray getEt() {
        return et;
    }

    public void setEt(JSONArray et) {
        this.et = et;
    }

    @Override
    public String toString() {
        return "Ods_user_operate{" +
                "timeStamp='" + timeStamp + '\'' +
                ", cm='" + cm + '\'' +
                ", ap='" + ap + '\'' +
                ", et=" + et +
                '}';
    }
}
