package com.stone.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.model.article.pojos.ApArticleConfig;

import java.util.Map;

public interface ApArticleConfigService extends IService<ApArticleConfig> {
    /**
     * 修改文章
     *
     * @param map
     */
    void updateByMap(Map map);
}
