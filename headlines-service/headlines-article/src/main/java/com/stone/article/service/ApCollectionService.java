package com.stone.article.service;

import com.stone.model.article.dtos.CollectionBehaviorDto;
import com.stone.model.common.dtos.ResponseResult;

public interface ApCollectionService {
    /**
     * 收藏
     *
     * @param dto
     * @return
     */
    public ResponseResult collection(CollectionBehaviorDto dto);
}
