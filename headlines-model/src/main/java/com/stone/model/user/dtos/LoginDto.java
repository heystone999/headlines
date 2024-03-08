package com.stone.model.user.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDto {
    /**
     * 手机号
     */
    @ApiModelProperty(value = "phone", required = true)
    private String phone;
    /**
     * 密码
     */
    @ApiModelProperty(value = "password", required = true)
    private String password;
}
