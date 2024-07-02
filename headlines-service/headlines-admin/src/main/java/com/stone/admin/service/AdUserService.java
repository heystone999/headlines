package com.stone.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.model.admin.dtos.AdUserDto;
import com.stone.model.admin.pojos.AdUser;
import com.stone.model.common.dtos.ResponseResult;

public interface AdUserService extends IService<AdUser> {
    /**
     * 登录
     *
     * @param dto
     * @return
     */
    public ResponseResult login(AdUserDto dto);
}
