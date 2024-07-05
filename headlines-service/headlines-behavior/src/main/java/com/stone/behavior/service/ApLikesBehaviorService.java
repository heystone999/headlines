package com.stone.behavior.service;

import com.stone.model.behavior.dtos.LikesBehaviorDto;
import com.stone.model.common.dtos.ResponseResult;

public interface ApLikesBehaviorService {
    /**
     * 存储like数据
     *
     * @param dto
     * @return
     */
    public ResponseResult like(LikesBehaviorDto dto);
}
