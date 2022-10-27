package cn.mobile.common.util;

import scala.collection.Seq;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterUtils {


    public static  ArrayList<String> list = new ArrayList(Arrays.asList("litemall_cart","litemall_collect","litemall_comment","litemall_order","litemall_order_goods"));

    public static  ArrayList<String> dimList = new ArrayList(Arrays.asList("litemall_goods","litemall_brand","litemall_category","litemall_region"));





    public static boolean filterTableWithName(ArrayList<String> tableNameList,String tableName){
        return tableNameList.contains(tableName);
    }





}
