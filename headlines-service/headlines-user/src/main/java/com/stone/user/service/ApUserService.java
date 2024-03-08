package com.stone.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.user.dtos.LoginDto;
import com.stone.model.user.pojos.ApUser;

public interface ApUserService extends IService<ApUser> {
    /**
     * app端登录
     *
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);
}
