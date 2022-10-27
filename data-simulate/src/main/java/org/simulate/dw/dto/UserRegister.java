package org.simulate.dw.dto;

import lombok.Data;


@Data
public class UserRegister extends UserBase {

    private String mobile;
    private String code;

}
