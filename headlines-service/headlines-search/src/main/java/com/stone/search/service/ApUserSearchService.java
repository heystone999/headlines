package com.stone.search.service;

import com.stone.model.common.dtos.ResponseResult;

public interface ApUserSearchService {
    /**
     * 保存用户搜索历史记录
     *
     * @return
     */
    public void insert(String keyword, Integer userId);

    /**
     * 查询搜索历史
     *
     * @return
     */
    public ResponseResult findUserSearch();
}
