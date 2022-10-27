package org.simulate.dw.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderComment {

    private int orderGoodsId;//订单详情ID
    private String content;
    private int star;
    private boolean hasPicture;
    private List<String> picUrls;

}
