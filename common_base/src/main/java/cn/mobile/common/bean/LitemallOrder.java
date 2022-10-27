package cn.mobile.common.bean;

import java.io.Serializable;
import java.util.Date;

public class LitemallOrder implements Serializable ,Cloneable{


    private int  STATUS_CREATE = 101;
    private int STATUS_PAY = 201;
    private int STATUS_SHIP = 301;
    private int STATUS_REFUND =202;


    private int  id                   ;
    private int  user_id           ;
    private String  order_sn          ;
    private int  order_status      ;
    private int  aftersale_status  ;
    private String  consignee         ;
    private String  mobile            ;
    private String  address           ;
    private String  message           ;
    private Double  goods_price       ;
    private Double  freight_price     ;
    private Double  coupon_price      ;
    private Double  integral_price    ;
    private Double  groupon_price     ;
    private Double  order_price       ;
    private Double  actual_price      ;
    private String  pay_id            ;
    private Date pay_time          ;
    private String  ship_sn           ;
    private String  ship_channel      ;
    private Date  ship_time         ;
    private Double  refund_amount     ;
    private String  refund_type       ;
    private String  refund_content    ;
    private Date   refund_time       ;
    private Date  confirm_time      ;
    private int  comments          ;
    private Date   end_time          ;
    private Date  add_time          ;
    private Date  update_time       ;
    private int   deleted           ;
    private int  province          ;
    private int   city              ;
    private int   country           ;

    public int getSTATUS_CREATE() {
        return STATUS_CREATE;
    }

    public void setSTATUS_CREATE(int STATUS_CREATE) {
        this.STATUS_CREATE = STATUS_CREATE;
    }

    public int getSTATUS_PAY() {
        return STATUS_PAY;
    }

    public void setSTATUS_PAY(int STATUS_PAY) {
        this.STATUS_PAY = STATUS_PAY;
    }

    public int getSTATUS_SHIP() {
        return STATUS_SHIP;
    }

    public void setSTATUS_SHIP(int STATUS_SHIP) {
        this.STATUS_SHIP = STATUS_SHIP;
    }

    public int getSTATUS_REFUND() {
        return STATUS_REFUND;
    }

    public void setSTATUS_REFUND(int STATUS_REFUND) {
        this.STATUS_REFUND = STATUS_REFUND;
    }

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

    public int getAftersale_status() {
        return aftersale_status;
    }

    public void setAftersale_status(int aftersale_status) {
        this.aftersale_status = aftersale_status;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getRefund_content() {
        return refund_content;
    }

    public void setRefund_content(String refund_content) {
        this.refund_content = refund_content;
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

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
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
}
