package com.stone.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {
    /**
     * 图片上传
     *
     * @param multipartFile
     * @return
     */
    public ResponseResult uploadPicture(MultipartFile multipartFile);
}
