package com.stone.model.user.dtos;

import com.stone.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class AuthDto extends PageRequestDto {
    /**
     * 状态
     */
    private Short status;

    /**
     * id
     */
    private Integer id;

    /**
     * 驳回的信息
     */
    private String msg;
}
