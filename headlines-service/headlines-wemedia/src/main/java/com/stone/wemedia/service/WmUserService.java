package com.stone.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.wemedia.dtos.WmLoginDto;
import com.stone.model.wemedia.pojos.WmUser;

public interface WmUserService extends IService<WmUser> {
    /**
     * 自媒体端登录
     *
     * @param dto
     * @return
     */
    public ResponseResult login(WmLoginDto dto);
}
