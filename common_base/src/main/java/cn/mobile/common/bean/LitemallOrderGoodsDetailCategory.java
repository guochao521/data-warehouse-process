package cn.mobile.common.bean;

import java.util.Date;

public class LitemallOrderGoodsDetailCategory {
    private int   id               ;
    private int   order_id         ;
    private int   goods_id         ;
    private String   goods_name    ;
    private String   goods_sn      ;
    private int   product_id       ;
    private int   number           ;
    private Double   price         ;
    private String   specifications;
    private String   pic_url       ;
    private Date add_time       ;
    private int brand_id;
    private String brand_name;
    private int first_category_id;
    private  String first_category_name = "";
    private int second_category_id ;
    private String second_category_name = "";



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getFirst_category_id() {
        return first_category_id;
    }

    public void setFirst_category_id(int first_category_id) {
        this.first_category_id = first_category_id;
    }

    public String getFirst_category_name() {
        return first_category_name;
    }

    public void setFirst_category_name(String first_category_name) {
        this.first_category_name = first_category_name;
    }

    public int getSecond_category_id() {
        return second_category_id;
    }

    public void setSecond_category_id(int second_category_id) {
        this.second_category_id = second_category_id;
    }

    public String getSecond_category_name() {
        return second_category_name;
    }

    public void setSecond_category_name(String second_category_name) {
        this.second_category_name = second_category_name;
    }
}
