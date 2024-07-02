package com.stone.model.wemedia.dtos;

import com.stone.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class ChannelDto extends PageRequestDto {
    /**
     * 频道名称
     */
    private String name;
}
