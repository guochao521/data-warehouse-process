package org.simulate.dw.dto;

import lombok.Data;


@Data
public class OrderSubmit {

    private int cartId;
    private int addressId;
    private int couponId;
    private int userCouponId;
    private String message;
    private int grouponRulesId;
    private int grouponLinkId;

}
