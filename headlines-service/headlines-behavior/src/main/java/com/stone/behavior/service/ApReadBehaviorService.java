package com.stone.behavior.service;

import com.stone.model.behavior.dtos.ReadBehaviorDto;
import com.stone.model.common.dtos.ResponseResult;

public interface ApReadBehaviorService {
    /**
     * 保存阅读行为
     *
     * @param dto
     * @return
     */
    public ResponseResult readBehavior(ReadBehaviorDto dto);
}
