package com.stone.behavior.service;

import com.stone.model.behavior.dtos.UnLikesBehaviorDto;
import com.stone.model.common.dtos.ResponseResult;

public interface ApUnlikesBehaviorService {
    /**
     * 不喜欢
     *
     * @param dto
     * @return
     */
    public ResponseResult unLike(UnLikesBehaviorDto dto);
}
