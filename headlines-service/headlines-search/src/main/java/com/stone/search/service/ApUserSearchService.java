package com.stone.search.service;

public interface ApUserSearchService {
    /**
     * 保存用户搜索历史记录
     *
     * @return
     */
    public void insert(String keyword, Integer userId);
}
