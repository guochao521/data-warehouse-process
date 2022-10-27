package cn.mobile.common.util;


import cn.mobile.common.bean.Ods_user_operate;
import cn.mobile.common.bean.Ods_user_start;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

    /**
     *  将用户行为数据解析为对象
     * @param jsonLine
     * @return
     */
    public static Ods_user_operate parseUserOperate(String jsonLine){
        Ods_user_operate ods_user_operate = JSONObject.parseObject(jsonLine, Ods_user_operate.class);
        return ods_user_operate;
    }

    /**
     * 将启动日志数据解析为对象
     * @param jsonLine
     * @return
     */
    public static Ods_user_start parseUserStart(String jsonLine){
        Ods_user_start ods_user_operate = JSONObject.parseObject(jsonLine, Ods_user_start.class);
        return ods_user_operate;

    }

}
