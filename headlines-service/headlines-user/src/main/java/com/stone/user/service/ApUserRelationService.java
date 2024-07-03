package com.stone.user.service;

import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.user.dtos.UserRelationDto;

public interface ApUserRelationService {
    /**
     * 用户关注/取消关注
     *
     * @param dto
     * @return
     */
    public ResponseResult follow(UserRelationDto dto);
}
