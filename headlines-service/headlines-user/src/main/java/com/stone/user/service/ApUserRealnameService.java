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

    /**
     * 更新用户实名认证审核结果
     *
     * @param dto
     * @param status 2审核失败    9审核成功
     * @return
     */
    public ResponseResult updateStatus(AuthDto dto, Short status);
}
