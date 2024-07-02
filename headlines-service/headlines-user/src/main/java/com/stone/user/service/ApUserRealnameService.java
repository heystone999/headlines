package com.stone.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.user.dtos.AuthDto;
import com.stone.model.user.pojos.ApUserRealname;

public interface ApUserRealnameService extends IService<ApUserRealname> {
    /**
     * 按照状态分页查询用户列表
     *
     * @param dto
     * @return
     */
    public ResponseResult loadListByStatus(AuthDto dto);
}
