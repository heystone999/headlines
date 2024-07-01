package com.stone.search.service;

import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.search.dtos.UserSearchDto;

public interface ApAssociateWordsService {
    /**
     * 搜索联想词
     *
     * @param dto
     * @return
     */
    public ResponseResult search(UserSearchDto dto);

}
