package com.stone.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.wemedia.dtos.SensitiveDto;
import com.stone.model.wemedia.pojos.WmSensitive;

public interface WmSensitiveService extends IService<WmSensitive> {
    /**
     * 新增
     *
     * @param wmSensitive
     * @return
     */
    ResponseResult insert(WmSensitive wmSensitive);

    /**
     * 查询
     *
     * @param dto
     * @return
     */
    ResponseResult list(SensitiveDto dto);

    /**
     * 修改
     *
     * @param wmSensitive
     * @return
     */
    ResponseResult update(WmSensitive wmSensitive);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    ResponseResult delete(Integer id);
}
