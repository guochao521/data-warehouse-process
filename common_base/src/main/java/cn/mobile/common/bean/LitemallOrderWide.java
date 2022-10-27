package cn.mobile.common.bean;

import java.util.Date;

public class LitemallOrderWide {
    private int  id                   ;
    private int  user_id           ;
    private String  order_sn          ;
    private int  order_status      ;
    private Double  goods_price       ;
    private Double  freight_price     ;
    private Double  coupon_price      ;
    private Double  integral_price    ;
    private Double  groupon_price     ;
    private Double  order_price       ;
    private Double  actual_price      ;
    private Date  add_time          ;
    private int  province          ;
    private int   city              ;
    private int   country           ;
    private String province_name;
    private String city_name;
    private String country_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public Double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Double goods_price) {
        this.goods_price = goods_price;
    }

    public Double getFreight_price() {
        return freight_price;
    }

    public void setFreight_price(Double freight_price) {
        this.freight_price = freight_price;
    }

    public Double getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(Double coupon_price) {
        this.coupon_price = coupon_price;
    }

    public Double getIntegral_price() {
        return integral_price;
    }

    public void setIntegral_price(Double integral_price) {
        this.integral_price = integral_price;
    }

    public Double getGroupon_price() {
        return groupon_price;
    }

    public void setGroupon_price(Double groupon_price) {
        this.groupon_price = groupon_price;
    }

    public Double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Double order_price) {
        this.order_price = order_price;
    }

    public Double getActual_price() {
        return actual_price;
    }

    public void setActual_price(Double actual_price) {
        this.actual_price = actual_price;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
}
