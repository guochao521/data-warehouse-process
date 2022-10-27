package cn.mobile.common.bean;

import java.util.Date;

public class LitemallOrderRegion {
    private int  id;
    private int  user_id;
    private String   order_sn; //订单编号
    private int order_status; //订单状态
    private Double  goods_price; //商品总费用
    private Double freight_price;//配送费用
    private Double coupon_price;//优惠券减免
    private Double  integral_price;//用户积分减免
    private Double  groupon_price; //团购优惠价减免
    private Double order_price;//订单费用， = goods_price + freight_price - coupon_price
    private Double actual_price;//实付费用， = order_price - integral_price
    private String pay_id;//支付
    private Date pay_time;
    private String ship_sn; //发货
    private String  ship_channel;
    private Date  ship_time;
    private Double refund_amount; //退款
    private String  refund_type;
    private Date  refund_time;
    private Date confirm_time;//用户确认收货时间
    private Date add_time;//下单时间
    private Date update_time;//订单状态更新时间
    private int province;//省份ID
    private int city; //城市ID
    private int country; //乡镇ID
    private String  province_name;
    private String  city_name;
    private String  country_name;

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

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public String getShip_sn() {
        return ship_sn;
    }

    public void setShip_sn(String ship_sn) {
        this.ship_sn = ship_sn;
    }

    public String getShip_channel() {
        return ship_channel;
    }

    public void setShip_channel(String ship_channel) {
        this.ship_channel = ship_channel;
    }

    public Date getShip_time() {
        return ship_time;
    }

    public void setShip_time(Date ship_time) {
        this.ship_time = ship_time;
    }

    public Double getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(Double refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(String refund_type) {
        this.refund_type = refund_type;
    }

    public Date getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(Date refund_time) {
        this.refund_time = refund_time;
    }

    public Date getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(Date confirm_time) {
        this.confirm_time = confirm_time;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
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
