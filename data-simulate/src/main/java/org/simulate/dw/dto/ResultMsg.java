package org.simulate.dw.dto;

import lombok.Data;


@Data
public class ResultMsg {

    private Integer line1;
    private Integer line2;
    private Integer line3;


    public ResultMsg() {
    }

    public ResultMsg(Integer line1, Integer line2, Integer line3) {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
    }

    /* public ResultMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }*/
}
