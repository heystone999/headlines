package com.stone.wemedia.service;

import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.wemedia.pojos.WmNews;

public interface WmNewsAutoScanService {
    /**
     * 自媒体文章审核
     *
     * @param id
     */
    public void autoScanWmNews(Integer id);

    /**
     * 保存app文章数据
     *
     * @param wmNews
     * @return
     */
    public ResponseResult saveAppArticle(WmNews wmNews);
}
